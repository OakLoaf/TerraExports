package org.lushplugins.bluemapterracompat;

import org.bukkit.plugin.java.JavaPlugin;

public final class BlueMapTerraCompat extends JavaPlugin {
    private static BlueMapTerraCompat plugin;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        // Enable implementation
    }

    @Override
    public void onDisable() {
        // Disable implementation
    }

    public static BlueMapTerraCompat getInstance() {
        return plugin;
    }
}
