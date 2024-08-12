package dev.ng5m.biomes.api;

import dev.ng5m.biomes.Biomes;
import net.minecraft.core.Registry;
import net.minecraft.resources.MinecraftKey;

import java.lang.reflect.Field;

public interface Version {
    boolean createBiome(MinecraftKey key, Biomes.BiomeBase base, Biomes.BiomeColor color, Biomes.Particle particle);
    default void freezeRegistry(Registry<?> registry, boolean shouldBeFrozen, String field) {
        try {
            Field frozen = registry.getClass().getDeclaredField(field);
            frozen.setAccessible(true);
            frozen.set(registry, shouldBeFrozen);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    };

    default void bananas(String root, Object object) {
        for (Field field : object.getClass().getDeclaredFields()) {
            try {
                String s = root + "." + field.getName();
                if (!Biomes.getInstance().getConfig().contains(s)) {
                    Biomes.getInstance().getConfig().set(s, field.get(object));
                }
            } catch (Exception x) {
                throw new RuntimeException(x);
            }
        }
    }

    default void apples(String root) {
        Biomes b = Biomes.getInstance();
        b.getConfig().set(root, null);
        b.saveConfig();
    }
}
