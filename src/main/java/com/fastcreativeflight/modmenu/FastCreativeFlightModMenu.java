package com.fastcreativeflight.modmenu;

import com.fastcreativeflight.config.ConfigManager;
import com.fastcreativeflight.config.FastCreativeFlightConfig;
import com.fastcreativeflight.ui.FastCreativeFlightConfigScreen;
import com.terraformersmc.modmenu.api.ModMenuApi;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;

public class FastCreativeFlightModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> FastCreativeFlightConfigScreen.create(parent, ConfigManager.load());
    }
}
