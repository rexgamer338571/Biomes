package dev.ng5m.biomes.impl;

import com.mojang.serialization.Lifecycle;
import dev.ng5m.biomes.Biomes;
import dev.ng5m.biomes.api.VersionSub1_17;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;

public class Biomes_v1_16_R3 implements VersionSub1_17 {
    @Override
    public boolean createBiome(Object compat, Biomes.BiomeBase base, Biomes.BiomeColor color, Biomes.Particle particle) {
        MinecraftKey key = (MinecraftKey) compat;
        String root = "biomes." + key.getKey() + "." + key.getNamespace();

        Server server = Bukkit.getServer();
        CraftServer craftServer = (CraftServer) server;
        DedicatedServer dedicatedServer = craftServer.getServer();

        ResourceKey<BiomeBase> newKey = ResourceKey.a(IRegistry.ay, key);
        ResourceKey<BiomeBase> oldKey = ResourceKey.a(IRegistry.ay, new MinecraftKey("minecraft", "forest"));

        IRegistryWritable<BiomeBase> writableRegistry = dedicatedServer.getCustomRegistry().b(IRegistry.ay);

        if (writableRegistry.keySet().contains(key)) {
            Biomes.getInstance().getLogger().warning("Registry already contains key " + key);
        }

        VersionSub1_17.freezeRegistry(writableRegistry, false, "ca");

        try {
            BiomeBase forest = writableRegistry.a(oldKey);

            BiomeBase.a builder = new BiomeBase.a();
            builder.a(forest.b());
            builder.a(forest.e());

            builder.a(base.temperature());
            builder.b(base.downfall());
            builder.a(base.frozen() ? BiomeBase.TemperatureModifier.FROZEN : BiomeBase.TemperatureModifier.NONE);

            BiomeFog.a specialFx = new BiomeFog.a();

            if (particle.scale() > 0) {
                specialFx.a(new BiomeParticles(new ParticleParamRedstone(particle.r(), particle.g(), particle.b(), particle.scale()), particle.probability()));
            }

            specialFx.a(color.fogColor());
            specialFx.b(color.waterColor());
            specialFx.c(color.waterFogColor());
            specialFx.d(color.skyColor());
            specialFx.e(color.foliageColor());
            specialFx.f(color.grassColor());

            builder.a(specialFx.a());

            BiomeBase built = builder.a();

            writableRegistry.a(newKey, built, Lifecycle.stable());
        } catch (Exception x) {
            x.printStackTrace();
            apples(root);
            return false;
        }

        VersionSub1_17.freezeRegistry(writableRegistry, true, "ca");

        Biomes.getInstance().getLogger().info("Successfully registered biome: " + key);
        return true;
    }
}
