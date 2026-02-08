package com.fastcreativeflight.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import net.fabricmc.loader.api.FabricLoader;

public final class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String CONFIG_FILE = "fast-creative-flight.json";

    private ConfigManager() {
    }

    public static FastCreativeFlightConfig load() {
        Path configPath = getConfigPath();
        if (Files.notExists(configPath)) {
            FastCreativeFlightConfig config = new FastCreativeFlightConfig();
            save(config);
            return config;
        }

        try (Reader reader = Files.newBufferedReader(configPath)) {
            FastCreativeFlightConfig config = GSON.fromJson(reader, FastCreativeFlightConfig.class);
            return config == null ? new FastCreativeFlightConfig() : config;
        } catch (IOException error) {
            return new FastCreativeFlightConfig();
        }
    }

    public static void save(FastCreativeFlightConfig config) {
        Path configPath = getConfigPath();
        try (Writer writer = Files.newBufferedWriter(configPath)) {
            GSON.toJson(config, writer);
        } catch (IOException error) {
            throw new IllegalStateException("Failed to write config", error);
        }
    }

    private static Path getConfigPath() {
        return FabricLoader.getInstance().getConfigDir().resolve(CONFIG_FILE);
    }
}
