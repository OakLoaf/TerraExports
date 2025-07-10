package org.lushplugins.terraexports.config;

import com.dfsek.terra.bukkit.nms.v1_21_6.config.VanillaBiomeProperties;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class BiomeInfo {
    @SerializedName("temperature")
    private final Float temperature;
    @SerializedName("downfall")
    private final Float downfall;
    @SerializedName("effects")
    private final Effects effects;

    public BiomeInfo(VanillaBiomeProperties biomeProperties) {
        this.temperature = biomeProperties.getTemperature();
        this.downfall = biomeProperties.getDownfall();
        this.effects = new Effects(biomeProperties);
    }

    public static class Effects {
        @SerializedName("water_color")
        private final Integer waterColor;
        @SerializedName("foliage_color")
        private final Integer overlayFoliageColor;
        @SerializedName("grass_color")
        private final Integer overlayGrassColor;
        @SerializedName("grass_color_modifier")
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
