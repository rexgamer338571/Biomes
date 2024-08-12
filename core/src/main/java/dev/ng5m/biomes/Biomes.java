package dev.ng5m.biomes;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class Biomes extends JavaPlugin {
    private static final String[] VERSIONS = {
            "v1_20_R3"
    };

    @Override
    public void onEnable() {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        if (!Arrays.asList(VERSIONS).contains(version)) {
            getLogger().severe("This version of Bukkit (" + version + ") isn't supported by Biomes");
        }
    }

}
