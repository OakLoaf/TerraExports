package org.lushplugins.bluemapterracompat.config;

import com.dfsek.terra.bukkit.nms.v1_21_3.config.VanillaBiomeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BiomeInfo {
    @JsonProperty("temperature")
    private final float temperature;
    @JsonProperty("downfall")
    private final float downfall;
    @JsonProperty("effects")
    private final Effects effects;

    public BiomeInfo(VanillaBiomeProperties biomeProperties) {
        this.temperature = biomeProperties.getTemperature();
        this.downfall = biomeProperties.getDownfall();
        this.effects = new Effects(biomeProperties);
    }

    public static class Effects {
        @JsonProperty("water_color")
        private final int waterColor;
        @JsonProperty("foliage_color")
        private final int overlayFoliageColor;
        @JsonProperty("grass_color")
        private final int overlayGrassColor;
        @JsonProperty("grass_color_modifier")
        private final String grassColorModifier;

        public Effects(VanillaBiomeProperties biomeProperties) {
            this.waterColor = biomeProperties.getWaterColor();
            this.overlayFoliageColor = biomeProperties.getFoliageColor();
            this.overlayGrassColor = biomeProperties.getGrassColor();
            // TODO: Find way of getting grass color modifier as a string without depending on nms
//            this.grassColorModifier = biomeProperties.getGrassColorModifier()
            this.grassColorModifier = null;
        }
    }
}
