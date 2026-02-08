package com.fastcreativeflight.fast_creative_flight.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.player.PlayerEntity;

public class Fast_creative_flightClient implements ClientModInitializer {

    private static final double SPEED_BOOST_X = 0.05;
    private static final double SPEED_BOOST_Y = 1.00;
    private static final double SPEED_BOOST_Z = 0.05;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            PlayerEntity player = client.player;

            if (!player.getAbilities().flying) return;
            if (!player.isSprinting()) return;

            if (client.options.forwardKey.isPressed()) {
                player.addVelocity(
                        player.getRotationVector().x * SPEED_BOOST_X,
                        player.getRotationVector().y * SPEED_BOOST_Y,
                        player.getRotationVector().z * SPEED_BOOST_Z
                );
                player.velocityDirty = true;
            }
        });
    }
}
