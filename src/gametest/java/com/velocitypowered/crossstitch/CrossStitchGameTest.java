package com.velocitypowered.crossstitch;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.velocitypowered.crossstitch.arguments.TestArgumentType;
import com.velocitypowered.crossstitch.mixin.ClientboundCommandsPacket$ArgumentNodeStubAccessor;
import io.netty.buffer.Unpooled;
import java.lang.reflect.Method;
import net.fabricmc.fabric.api.gametest.v1.CustomTestMethodInvoker;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.commands.arguments.AngleArgument;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.FriendlyByteBuf;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class CrossStitchGameTest implements CustomTestMethodInvoker {

    private static final int MOD_ARGUMENT_INDICATOR = -256;

    @GameTest
    public void brigadierArgumentType_isNotWrapped(GameTestHelper context) {
        builtInArgumentType_isNotWrapped(context, StringArgumentType.word());
    }

    @GameTest
    public void minecraftArgumentType_isNotWrapped(GameTestHelper context) {
        builtInArgumentType_isNotWrapped(context, AngleArgument.angle());
    }

    @GameTest
    public void testArgumentType_isWrapped(GameTestHelper context) {
        modArgumentType_isWrapped(context, TestArgumentType.test());
    }

    private void builtInArgumentType_isNotWrapped(GameTestHelper context, ArgumentType<?> argType) {
        ArgumentTypeInfo<ArgumentType<?>, ?> info = ArgumentTypeInfos.byClass(argType);
        ArgumentTypeInfo.Template<ArgumentType<?>> template = info.unpack(argType);

        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        ClientboundCommandsPacket$ArgumentNodeStubAccessor.invokeSerializeCap(buf, template);

        int firstInt = buf.readVarInt();
        context.assertFalse(firstInt == MOD_ARGUMENT_INDICATOR,
                "Built-in argument type %s should not be wrapped".formatted(argType.getClass().getSimpleName()));
        context.succeed();
    }

    private void modArgumentType_isWrapped(GameTestHelper context, ArgumentType<?> argType) {
        ArgumentTypeInfo<ArgumentType<?>, ?> info = ArgumentTypeInfos.byClass(argType);
        ArgumentTypeInfo.Template<ArgumentType<?>> template = info.unpack(argType);

        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        ClientboundCommandsPacket$ArgumentNodeStubAccessor.invokeSerializeCap(buf, template);

        int firstInt = buf.readVarInt();
        context.assertValueEqual(firstInt, MOD_ARGUMENT_INDICATOR,
                "Modded argument type %s should be wrapped".formatted(argType.getClass().getSimpleName()));
        context.succeed();
    }

    @Override
    public void invokeTestMethod(GameTestHelper context, Method method) throws ReflectiveOperationException {
        method.invoke(this, context);
    }

}
