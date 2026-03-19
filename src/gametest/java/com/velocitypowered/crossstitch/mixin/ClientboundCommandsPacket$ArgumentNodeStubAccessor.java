package com.velocitypowered.crossstitch.mixin;

import com.mojang.brigadier.arguments.ArgumentType;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.FriendlyByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(targets = "net.minecraft.network.protocol.game.ClientboundCommandsPacket$ArgumentNodeStub")
public interface ClientboundCommandsPacket$ArgumentNodeStubAccessor {

    @Invoker("serializeCap")
    static <A extends ArgumentType<?>> void invokeSerializeCap(FriendlyByteBuf output, ArgumentTypeInfo.Template<A> argumentType) {
        throw new AssertionError();
    }

}