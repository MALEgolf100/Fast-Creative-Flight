package com.fastcreativeflight.ui;

import com.fastcreativeflight.config.ConfigManager;
import com.fastcreativeflight.config.FastCreativeFlightConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public final class FastCreativeFlightConfigScreen {
    private FastCreativeFlightConfigScreen() {
    }

    public static Screen create(Screen parent, FastCreativeFlightConfig config) {
        ConfigBuilder builder = ConfigBuilder.create()
            .setParentScreen(parent)
            .setTitle(Text.translatable("config.fast-creative-flight.title"));

        builder.setSavingRunnable(() -> ConfigManager.save(config));

        ConfigCategory category = builder.getOrCreateCategory(Text.translatable("config.fast-creative-flight.general"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        category.addEntry(entryBuilder.startBooleanToggle(
                Text.translatable("config.fast-creative-flight.enabled"),
                config.enabled)
            .setDefaultValue(true)
            .setSaveConsumer(value -> config.enabled = value)
            .build());

        category.addEntry(entryBuilder.startBooleanToggle(
                Text.translatable("config.fast-creative-flight.enabled_on_servers"),
                config.enabledOnServers)
            .setDefaultValue(true)
            .setSaveConsumer(value -> config.enabledOnServers = value)
            .build());

        category.addEntry(entryBuilder.startDoubleField(
                Text.translatable("config.fast-creative-flight.x_speed"),
                config.xSpeed)
            .setDefaultValue(1.0)
            .setSaveConsumer(value -> config.xSpeed = value)
            .build());

        category.addEntry(entryBuilder.startDoubleField(
                Text.translatable("config.fast-creative-flight.y_speed"),
                config.ySpeed)
            .setDefaultValue(1.0)
            .setSaveConsumer(value -> config.ySpeed = value)
            .build());

        category.addEntry(entryBuilder.startDoubleField(
                Text.translatable("config.fast-creative-flight.z_speed"),
                config.zSpeed)
            .setDefaultValue(1.0)
            .setSaveConsumer(value -> config.zSpeed = value)
            .build());

        return builder.build();
    }
}
