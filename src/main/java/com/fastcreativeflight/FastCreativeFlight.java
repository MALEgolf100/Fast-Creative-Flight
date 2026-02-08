package com.fastcreativeflight;

import com.fastcreativeflight.config.ConfigManager;
import com.fastcreativeflight.config.FastCreativeFlightConfig;
import net.fabricmc.api.ModInitializer;

public class FastCreativeFlight implements ModInitializer {
    public static final String MOD_ID = "fast-creative-flight";
    private static FastCreativeFlightConfig config;

    @Override
    public void onInitialize() {
        config = ConfigManager.load();
    }

    public static FastCreativeFlightConfig getConfig() {
        return config;
    }

    public static boolean isModEnabled(boolean isServer) {
        if (config == null) {
            config = ConfigManager.load();
        }
        if (!config.enabled) {
            return false;
        }
        return !isServer || config.enabledOnServers;
    }
}
