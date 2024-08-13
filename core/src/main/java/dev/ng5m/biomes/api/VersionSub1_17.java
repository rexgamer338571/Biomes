package dev.ng5m.biomes.api;

import dev.ng5m.biomes.Biomes;

import java.lang.reflect.Field;

public interface VersionSub1_17 extends VersionAll {
    static void freezeRegistry(Object registryWritable, boolean shouldBeFrozen, String field) {
        try {
            Field frozen = registryWritable.getClass().getDeclaredField(field);
            frozen.setAccessible(true);
            frozen.set(registryWritable, shouldBeFrozen);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    boolean createBiome(Object key, Biomes.BiomeBase base, Biomes.BiomeColor color, Biomes.Particle particle);

}
