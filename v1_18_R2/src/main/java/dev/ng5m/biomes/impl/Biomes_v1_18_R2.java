package dev.ng5m.biomes.impl;

import com.mojang.math.Vector3fa;
import com.mojang.serialization.Lifecycle;
import dev.ng5m.biomes.Biomes;
import dev.ng5m.biomes.api.VersionAbove1_16;
import dev.ng5m.biomes.api.VersionAll;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryWritable;
import net.minecraft.core.particles.ParticleParamRedstone;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeFog;
import net.minecraft.world.level.biome.BiomeParticles;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_18_R2.CraftServer;

public class Biomes_v1_18_R2 implements VersionAbove1_16 {
    @Override
    public boolean createBiome(MinecraftKey key, Biomes.BiomeBase base, Biomes.BiomeColor color, Biomes.Particle particle) {
        String root = "biomes." + key.a() + "." + key.b();

        Server server = Bukkit.getServer();
        CraftServer craftServer = (CraftServer) server;
        DedicatedServer dedicatedServer = craftServer.getServer();

        ResourceKey<BiomeBase> newKey = ResourceKey.a(IRegistry.aP, key);
        ResourceKey<BiomeBase> oldKey = ResourceKey.a(IRegistry.aP, new MinecraftKey("minecraft", "forest"));

        IRegistryWritable<BiomeBase> writableRegistry = (IRegistryWritable<BiomeBase>) dedicatedServer.aU().d(IRegistry.aP);

        if (writableRegistry.c(key)) {
            Biomes.getInstance().getLogger().warning("Registry already contains key " + key);
        }

        freezeRegistry(writableRegistry, false, "ca");

        try {
            BiomeBase forest = writableRegistry.a(oldKey);

            BiomeBase.a builder = new BiomeBase.a();
            builder.a(forest.b());
            builder.a(forest.e());

            builder.a(base.temperature());
            builder.b(base.downfall());
            builder.a(base.frozen() ? BiomeBase.TemperatureModifier.b : BiomeBase.TemperatureModifier.a);

            BiomeFog.a specialFx = new BiomeFog.a();

            if (particle.scale() > 0) {
                specialFx.a(new BiomeParticles(new ParticleParamRedstone(new Vector3fa(particle.r(), particle.g(), particle.b()), particle.scale()), particle.probability()));
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

        freezeRegistry(writableRegistry, true, "ca");

        Biomes.getInstance().getLogger().info("Successfully registered biome: " + key);
        return true;
    }
}
