package com.velocitypowered.crossstitch;

import com.velocitypowered.crossstitch.arguments.TestArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrossStitchTestMod implements ModInitializer {

    public static Logger LOGGER = LoggerFactory.getLogger("CrossStitch-Test");

    @Override
    public void onInitialize() {
        LOGGER.info("Registering test argument");
        ArgumentTypeRegistry.registerArgumentType(
                Identifier.fromNamespaceAndPath("crossstitch_gametest", "test_arg"),
                TestArgumentType.class,
                SingletonArgumentInfo.contextFree(TestArgumentType::test)
        );
    }

}
