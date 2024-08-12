package dev.ng5m.biomes;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class Biomes extends JavaPlugin {
    private static final String[] VERSIONS = {
            "v1_20_R3"
    };
    private static Biomes instance;

    @Override
    public void onEnable() {
        instance = this;
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        if (!Arrays.asList(VERSIONS).contains(version)) {
            getLogger().severe("This version of Bukkit (" + version + ") isn't supported by Biomes");
        }

        try {
            Class<?> clazz = Class.forName("dev.ng5m.biomes.impl.Biomes_" + version);
            clazz.getConstructor(this.getClass()).newInstance(this);
        } catch (Exception x) {
            throw new RuntimeException(x);
        }
    }

    public record BiomeBase(float temperature, float downfall, boolean frozen, String grassColorModifier) {
        public BiomeBase(String cfgKey, FileConfiguration a) {
            this(
                    (float) (double) a.get(cfgKey + ".temperature"),
                    (float) (double) a.get(cfgKey + ".downfall"),
                    (boolean) a.get(cfgKey + ".frozen"),
                    (String) a.get(cfgKey + ".grassColorModifier")
            );
        }
    }

    public record BiomeColor(int fogColor, int waterColor, int waterFogColor, int skyColor, int foliageColor, int grassColor) {
        public BiomeColor(String cfgKey, FileConfiguration a) {
            this(
                    (int) a.get(cfgKey + ".fogColor"),
                    (int) a.get(cfgKey + ".waterColor"),
                    (int) a.get(cfgKey + ".waterFogColor"),
                    (int) a.get(cfgKey + ".skyColor"),
                    (int) a.get(cfgKey + ".foliageColor"),
                    (int) a.get(cfgKey + ".grassColor")
            );
        }
    }

    public record Particle(int r, int g, int b, float scale, float probability) {
        public Particle(String cfgKey, FileConfiguration a) {
            this(
                    (int) a.get(cfgKey + ".red"),
                    (int) a.get(cfgKey + ".green"),
                    (int) a.get(cfgKey + ".blue"),
                    (float) a.get(cfgKey + ".scale"),
                    (float) a.get(cfgKey + ".probability")
            );
        }
    }

    public static Biomes getInstance() {
        return instance;
    }

}
