package com.gmail.pharaun.gregtania.misc;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

// Credit to MineMaarten for their json config - pneumaticCraft/common/config/JsonConfig.java
public class OrechidYieldConfig {
    // For initalizing the tieredOreWeight tables
    protected static Map<String, Integer> oreWeightOverworld = new HashMap<>();
    protected static Map<String, Integer> oreWeightNether = new HashMap<>();
    protected static Map<String, Integer> oreWeightEnd = new HashMap<>();

    static {
        // Gregtech Overworld Materials
        //addOreWeight("oreAlmandine", 2654);

    }

    private static void addOreWeight(String oreDict, int weight) {
        oreWeightOverworld.put(oreDict, weight);
    }

    private static void addOreWeightNether(String oreDict, int weight) {
        oreWeightNether.put(oreDict, weight);
    }

    private static void addOreWeightEnd(String oreDict, int weight) {
        oreWeightEnd.put(oreDict, weight);
    }

    public void load(File orechidConfigFile) {
        try {
            if (orechidConfigFile.exists()) {
                JsonParser parser = new JsonParser();
                JsonObject root = (JsonObject) parser.parse(FileUtils.readFileToString(orechidConfigFile));

                // TypeTokens
                Gson gson = new Gson();
                Type StringStringIntegerMap = new TypeToken<Map<String, Map<String, Integer>>>(){}.getType();
                Map<String, Map<String, Integer>> loads = gson.fromJson(root, StringStringIntegerMap);

                oreWeightOverworld = loads.get("oreWeightOverworld");
                oreWeightNether = loads.get("oreWeightNether");
                oreWeightEnd = loads.get("oreWeightEnd");
            }
        } catch (IOException e) {
            LogHelper.error(e, "Something went wrong with reading the orechid config json");
        }
    }

    public void save(File orechidConfigFile) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Map<String, Map<String, Integer>> dump = new Hashtable<>();
        dump.put("oreWeightOverworld", oreWeightOverworld);
        dump.put("oreWeightNether", oreWeightNether);
        dump.put("oreWeightEnd", oreWeightEnd);

        try {
            FileUtils.write(orechidConfigFile, gson.toJson(dump));
        } catch (IOException e) {
            LogHelper.error(e, "Something went wrong with writing the orechid config json");
        }
    }
}
