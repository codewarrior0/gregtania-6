package com.gmail.pharaun.gregtania.misc;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;

public class Config {
    public static Configuration config;
    public static OrechidYieldConfig orechidConfig;

    public static boolean stackedOreInTiers;
    public static boolean disableVanillaOrechid;
    private static boolean overrideOrechidWeight;

    public static void preInit(File configFile) {
        if(configFile != null) {
            config = new Configuration(configFile);
            config.load();
        }

        // Orechid weight defaults
        orechidConfig = new OrechidYieldConfig();

        // Handle regular configs
        Property property = config.get(Configuration.CATEGORY_GENERAL, "OreStacking", true);
        property.comment = "The higher tier orechid also includes the ores in the lower tier orechids.";
        stackedOreInTiers = property.getBoolean();

        property = config.get(Configuration.CATEGORY_GENERAL, "DisableVanilla", true);
        property.comment = "Do we override and disable the vanilla orechids?";
        disableVanillaOrechid = property.getBoolean();

        // Do we want to override the orechid weights
        property = config.get(Configuration.CATEGORY_GENERAL, "EnableCustomOrechidWeights", false);
        property.comment = "Do we want to generate and load a custom orechid weight json file?";
        overrideOrechidWeight = property.getBoolean();

        if(overrideOrechidWeight) {
            File orechidConfigFile = new File(configFile.getAbsolutePath().substring(0, configFile.getAbsolutePath().length() - 4) + ".json");
            if (orechidConfigFile != null) {
                if (orechidConfigFile.exists()) {
                    orechidConfig.load(orechidConfigFile);
                } else {
                    orechidConfig.save(orechidConfigFile);
                }
            }
        }

        config.save();
    }
}
