package dev.ng5m.biomes;

import dev.ng5m.biomes.api.Version;
import net.minecraft.resources.MinecraftKey;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class Biomes extends JavaPlugin {
    private static final String[] VERSIONS = {
            "v1_20_R3"
    };
    private static Biomes instance;
    private static Version versionObj;

    @Override
    public void onEnable() {
        instance = this;
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        if (!Arrays.asList(VERSIONS).contains(version)) {
            getLogger().severe("This version of Bukkit (" + version + ") isn't supported by Biomes");
        }

        try {
            Class<?> clazz = Class.forName("dev.ng5m.biomes.impl.Biomes_" + version);
            versionObj = (Version) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception x) {
            throw new RuntimeException(x);
        }

        registerSaved();
    }

    private void registerSaved() {
        getLogger().info("Attempting to register saved biomes (config.yml/biomes)");

        new BukkitRunnable() {
            @Override
            public void run() {
                for (World w : Bukkit.getWorlds()) if (w == null) return;

                ConfigurationSection biomes;

                if (getConfig().getConfigurationSection("biomes") == null) {
                    getLogger().info("No saved biomes found");
                    cancel();
                    return;
                } else {
                    biomes = getConfig().getConfigurationSection("biomes");
                }

                for (String namespace : biomes.getKeys(false)) {
                    for (String key : getConfig().getConfigurationSection("biomes." + namespace).getKeys(false)) {
                        String base = "biomes." + namespace + "." + key;
                        versionObj.createBiome(new MinecraftKey(namespace, key), new BiomeBase(base, getConfig()), new BiomeColor(base, getConfig()), new Particle(0, 0, 255, 1, 0)); // TODO particle
                    }
                }

                cancel();
            }
        }.runTaskTimerAsynchronously(this, 10, 0);
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
