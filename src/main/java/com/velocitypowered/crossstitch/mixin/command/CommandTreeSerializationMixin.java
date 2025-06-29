package com.velocitypowered.crossstitch.mixin.command;

import com.mojang.brigadier.arguments.ArgumentType;
import io.netty.buffer.Unpooled;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;

@Mixin(targets = "net.minecraft.network.protocol.game.ClientboundCommandsPacket$ArgumentNodeStub")
public class CommandTreeSerializationMixin {

    @Unique private static final int MOD_ARGUMENT_INDICATOR = -256;
    @Unique private static final String[] BUILT_IN_NAMESPACES = {"minecraft", "brigadier"};

    @Inject(method = "serializeCap(Lnet/minecraft/network/FriendlyByteBuf;Lnet/minecraft/commands/synchronization/ArgumentTypeInfo;Lnet/minecraft/commands/synchronization/ArgumentTypeInfo$Template;)V",
            at = @At("HEAD"), cancellable = true)
    private static <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>> void writeArgumentNode$wrapInVelocityModArgument(
            FriendlyByteBuf buf, ArgumentTypeInfo<A, T> info, T template, CallbackInfo ci) {
        Optional<ResourceKey<ArgumentTypeInfo<?, ?>>> entry = BuiltInRegistries.COMMAND_ARGUMENT_TYPE.getResourceKey(info);

        if (entry.isEmpty()) return;

        String namespace = entry.get().location().getNamespace();
        for (String builtInNamespace : BUILT_IN_NAMESPACES) {
            if (namespace.equals(builtInNamespace)) return;
        }

        ci.cancel();

        // Not a standard Minecraft argument type - so we need to wrap it
        serializeWrappedArgumentType(buf, info, template);
    }

    @Unique
    private static <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>> void serializeWrappedArgumentType(
            FriendlyByteBuf packetByteBuf, ArgumentTypeInfo<A, T> info, T template) {
        packetByteBuf.writeVarInt(MOD_ARGUMENT_INDICATOR);
        packetByteBuf.writeVarInt(BuiltInRegistries.COMMAND_ARGUMENT_TYPE.getId(info));

        FriendlyByteBuf extraData = new FriendlyByteBuf(Unpooled.buffer());
        info.serializeToNetwork(template, extraData);

        packetByteBuf.writeVarInt(extraData.readableBytes());
        packetByteBuf.writeBytes(extraData);
    }

}
