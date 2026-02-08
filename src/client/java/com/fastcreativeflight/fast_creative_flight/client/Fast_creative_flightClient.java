package com.fastcreativeflight.fast_creative_flight.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Fast_creative_flightClient implements ClientModInitializer {

    public static final Path CONFIG_PATH =
            new File("config/fast_creative_flight.json").toPath();
    public static final Gson GSON = new Gson();
    public static JsonObject config;

    @Override
    public void onInitializeClient() {
        loadConfig();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            MinecraftClient mc = MinecraftClient.getInstance();
            PlayerEntity player = client.player;

            // Allow on servers check
            if (!config.get("allowOnServers").getAsBoolean()
                    && mc.getCurrentServerEntry() != null) {
                return;
            }

            // Mod enabled check
            if (!config.get("enableFastCreativeFlight").getAsBoolean()) {
                return;
            }

            if (!player.getAbilities().flying) return;
            if (!player.isSprinting()) return;
            if (!mc.options.forwardKey.isPressed()) return;

            double xBoost = config.get("speedBoostX").getAsDouble();
            double yBoost = config.get("speedBoostY").getAsDouble();
            double zBoost = config.get("speedBoostZ").getAsDouble();

            player.addVelocity(
                    player.getRotationVector().x * xBoost,
                    player.getRotationVector().y * yBoost,
                    player.getRotationVector().z * zBoost
            );

            player.velocityDirty = true;
        });
    }

    /* ---------------- CONFIG ---------------- */

    private void loadConfig() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                String content = Files.readString(CONFIG_PATH);
                config = GSON.fromJson(content, JsonObject.class);
            } catch (IOException e) {
                e.printStackTrace();
                createDefaultConfig();
            }
        } else {
            createDefaultConfig();
        }
    }

    private void createDefaultConfig() {
        config = new JsonObject();
        config.addProperty("enableFastCreativeFlight", true);
        config.addProperty("allowOnServers", false);

        // Axis-specific multipliers
        config.addProperty("speedBoostX", 0.10);
        config.addProperty("speedBoostY", 0.50);
        config.addProperty("speedBoostZ", 0.10);

        saveConfig();
    }

    public static void saveConfig() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            Files.writeString(CONFIG_PATH, GSON.toJson(config));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
