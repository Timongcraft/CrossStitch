package com.velocitypowered.crossstitch.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class TestArgumentType implements ArgumentType<String> {

    public static TestArgumentType test() {
        return new TestArgumentType();
    }

    private TestArgumentType() {}

    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        return reader.readString();
    }

}
