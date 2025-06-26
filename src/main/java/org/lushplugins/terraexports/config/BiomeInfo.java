package org.lushplugins.terraexports.config;

import com.dfsek.terra.bukkit.nms.v1_21_6.config.VanillaBiomeProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BiomeInfo {
    @JsonProperty("temperature")
    private final Float temperature;
    @JsonProperty("downfall")
    private final Float downfall;
    @JsonProperty("effects")
    private final Effects effects;

    public BiomeInfo(VanillaBiomeProperties biomeProperties) {
        this.temperature = biomeProperties.getTemperature();
        this.downfall = biomeProperties.getDownfall();
        this.effects = new Effects(biomeProperties);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Effects {
        @JsonProperty("water_color")
        private final Integer waterColor;
        @JsonProperty("foliage_color")
        private final Integer overlayFoliageColor;
        @JsonProperty("grass_color")
        private final Integer overlayGrassColor;
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
