package dev.ng5m.biomes.api;

import dev.ng5m.biomes.Biomes;
import net.minecraft.resources.MinecraftKey;

public interface VersionAbove1_16 extends VersionAll {
    boolean createBiome(MinecraftKey key, Biomes.BiomeBase base, Biomes.BiomeColor color, Biomes.Particle particle);
}
