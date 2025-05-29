package org.lushplugins.bluemapterracompat;

import com.dfsek.terra.addons.manifest.api.AddonInitializer;
import com.dfsek.terra.api.Platform;
import com.dfsek.terra.api.addon.BaseAddon;
import com.dfsek.terra.api.config.ConfigPack;
import com.dfsek.terra.api.event.events.config.pack.ConfigPackPostLoadEvent;
import com.dfsek.terra.api.event.functional.FunctionalEventHandler;
import com.dfsek.terra.api.inject.annotations.Inject;

import com.dfsek.terra.api.world.biome.Biome;
import com.dfsek.terra.bukkit.nms.v1_21_3.config.VanillaBiomeProperties;
import com.dfsek.terra.bukkit.world.BukkitPlatformBiome;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.bluecolored.bluemap.api.BlueMapAPI;
import de.bluecolored.bluemap.common.api.BlueMapAPIImpl;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.lushplugins.bluemapterracompat.config.BiomeInfo;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;

public class BlueMapTerraCompat implements AddonInitializer {
    private static final ObjectMapper JACKSON = new ObjectMapper();

    @Inject
    private Platform platform;
    @Inject
    private BaseAddon addon;
    @Inject
    private Logger logger;

    @Override
    public void initialize() {
        Path packsDirectory = getPacksFolder();
//        BlueMapAPIImpl blueMap = (BlueMapAPIImpl) BlueMapAPI.getInstance().orElseThrow();
//        Path packsDirectory = blueMap.blueMapService().getConfig().getPacksFolder();
        if (packsDirectory == null) {
            logger.error("Failed to get BlueMap's packs directory");
            return;
        }

        platform.getEventManager()
            .getHandler(FunctionalEventHandler.class)
            .register(addon, ConfigPackPostLoadEvent.class)
            .then(event -> {
                ConfigPack pack = event.getPack();
                String packKey = pack.getNamespace();

                for (Biome biome : pack.getBiomeProvider().getBiomes()) {
                    BukkitPlatformBiome platformBiome = (BukkitPlatformBiome) biome.getPlatformBiome();
                    NamespacedKey biomeKey = platformBiome.getHandle().getKey();
                    VanillaBiomeProperties biomeProperties = biome.getContext().get(VanillaBiomeProperties.class);

                    Path path = packsDirectory
                        .resolve(Path.of(
                            "terra_generated", 
                            "data", 
                            packKey ,
                            "worldgen", 
                            "biome", 
                            biomeKey.asString() + ".json"));

                    try {
                        JACKSON.writeValue(path.toFile(), new BiomeInfo(biomeProperties));
                    } catch (IOException e) {
                        logger.error("Failed to write biome: ", e);
                    }
                }

                logger.info("Generated datapack biomes for pack '%s'!".formatted(pack.getNamespace()));
            })
            .priority(90);
    }

    private Path getPacksFolder() {
        return Bukkit.getPluginsFolder().toPath()
            .resolve("BlueMap")
            .resolve("packs");
    }
}