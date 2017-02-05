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
    protected static Map<String, Integer> oreWeightOverworld = new HashMap();
    protected static Map<String, Integer> oreWeightNether = new HashMap();
    protected static Map<String, Integer> oreWeightEnd = new HashMap();

    static {
        // Gregtech Overworld Materials
        addOreWeight("oreAlmandine", 2654);
        addOreWeight("oreAluminium", 1655);
        addOreWeight("oreApatite", 5301);
        addOreWeight("oreBandedIron", 2510);
        addOreWeight("oreBarite", 2650);
        addOreWeight("oreBastnasite", 2642);
        addOreWeight("oreBauxite", 11685);
        addOreWeight("oreBentonite", 2661);
        addOreWeight("oreBeryllium", 2614);
        addOreWeight("oreBrownLimonite", 8832);
        addOreWeight("oreCalcite", 1019);
        addOreWeight("oreCassiterite", 1040);
        addOreWeight("oreCertusQuartz", 1680);
        addOreWeight("oreChalcopyrite", 5838);
        addOreWeight("oreCinnabar", 1135);
        addOreWeight("oreCoal", 33945);
        addOreWeight("oreCobaltite", 507);
        addOreWeight("oreCopper", 3478);
        addOreWeight("oreDiamond", 357);
        addOreWeight("oreEmerald", 377);
        addOreWeight("oreGalena", 5735);
        addOreWeight("oreGarnierite", 1776);
        addOreWeight("oreGlauconite", 1429);
        addOreWeight("oreMagnesite", 2661);
        addOreWeight("oreGold", 3657);
        addOreWeight("oreGraphite", 2357);
        addOreWeight("oreGreenSapphire", 919);
        addOreWeight("oreGrossular", 881);
        addOreWeight("oreIlmenite", 2034);
        addOreWeight("oreIridium", 76);
        addOreWeight("oreIron", 9056);
        addOreWeight("oreLapis", 741);
        addOreWeight("oreLazurite", 2859);
        addOreWeight("oreLead", 1015);
        addOreWeight("oreLepidolite", 834);
        addOreWeight("oreLignite", 64434);
        addOreWeight("oreLithium", 152);
        addOreWeight("oreMagnetite", 42597);
        addOreWeight("oreMalachite", 3072);
        addOreWeight("oreMolybdenite", 217);
        addOreWeight("oreMolybdenum", 62);
        addOreWeight("oreMonazite", 382);
        addOreWeight("oreNeodymium", 458);
        addOreWeight("oreNickel", 1771);
        addOreWeight("oreOlivine", 765);
        addOreWeight("orePalladium", 221);
        addOreWeight("orePentlandite", 1230);
        addOreWeight("orePhosphate", 912);
        addOreWeight("orePhosphorus", 765);
        addOreWeight("orePitchblende", 3563);
        addOreWeight("orePlatinum", 63);
        addOreWeight("orePowellite", 75);
        addOreWeight("orePyrite", 1655);
        addOreWeight("orePyrolusite", 255);
        addOreWeight("orePyrope", 2654);
        addOreWeight("oreQuartzite", 2647);
        addOreWeight("oreRedstone", 6590);
        addOreWeight("oreRockSalt", 2800);
        addOreWeight("oreRuby", 977);
        addOreWeight("oreSalt", 2806);
        addOreWeight("oreSapphire", 765);
        addOreWeight("oreScheelite", 876);
        addOreWeight("oreCooperite", 221);
        addOreWeight("oreSilver", 736);
        addOreWeight("oreSoapstone", 1776);
        addOreWeight("oreSodalite", 2862);
        addOreWeight("oreSpessartine", 881);
        addOreWeight("oreSpodumene", 960);
        addOreWeight("oreStibnite", 1772);
        addOreWeight("oreTalc", 1774);
        addOreWeight("oreTantalite", 305);
        addOreWeight("oreTetrahedrite", 10192);
        addOreWeight("oreThorium", 454);
        addOreWeight("oreTin", 8611);
        addOreWeight("oreTungstate", 126);
        addOreWeight("oreUraninite", 2362);
        addOreWeight("oreUranium", 1067);
        addOreWeight("oreVanadiumMagnetite", 6875);
        addOreWeight("oreWulfenite", 217);
        addOreWeight("oreYellowLimonite", 8832);
        addOreWeight("oreOilsands", 27433);

        // Gregtech Small ore Proxy
        addOreWeight("oreBismuth", 1280);
        addOreWeight("oreZinc", 2048);
        addOreWeight("oreTopaz", 256);
        addOreWeight("oreTanzanite", 256);
        addOreWeight("oreAmethyst", 256);
        addOreWeight("oreOpal", 256);
        addOreWeight("oreBlueTopaz", 256);
        addOreWeight("oreFoolsRuby", 256);
        addOreWeight("oreGarnetRed", 256);
        addOreWeight("oreGarnetYellow", 256);

        // Gregtech Nether materials (oreNetherrack<name>)
        addOreWeightNether("oreNetherrackBandedIron", 6186);
        addOreWeightNether("oreNetherrackBrownLimonite", 21769);
        addOreWeightNether("oreNetherrackChalcopyrite", 14389);
        addOreWeightNether("oreNetherrackCinnabar", 2797);
        addOreWeightNether("oreNetherrackCobaltite", 1251);
        addOreWeightNether("oreNetherrackCopper", 8573);
        addOreWeightNether("oreNetherrackGarnierite", 4377);
        addOreWeightNether("oreNetherrackIron", 22321);
        addOreWeightNether("oreNetherrackMagnetite", 52372);
        addOreWeightNether("oreNetherrackMalachite", 7572);
        addOreWeightNether("oreNetherrackNetherQuartz", 46592);
        addOreWeightNether("oreNetherrackNickel", 4366);
        addOreWeightNether("oreNetherrackPentlandite", 1521);
        addOreWeightNether("oreNetherrackPyrite", 10065);
        addOreWeightNether("oreNetherrackRedstone", 16242);
        addOreWeightNether("oreNetherrackRuby", 2408);
        addOreWeightNether("oreNetherrackSphalerite", 7761);
        addOreWeightNether("oreNetherrackStibnite", 4368);
        addOreWeightNether("oreNetherrackSulfur", 44197);
        addOreWeightNether("oreNetherrackTetrahedrite", 25121);
        addOreWeightNether("oreNetherrackVanadiumMagnetite", 8992);
        addOreWeightNether("oreNetherrackYellowLimonite", 21769);

        // Gregtech Small oreNetherrack Proxy
        addOreWeightNether("oreNetherrackSaltpeter", 1280);
        addOreWeightNether("oreNetherrackBismuth", 1280);
        addOreWeightNether("oreNetherrackZinc", 2048);
        addOreWeightNether("oreNetherrackTopaz", 256);
        addOreWeightNether("oreNetherrackTanzanite", 256);
        addOreWeightNether("oreNetherrackAmethyst", 256);
        addOreWeightNether("oreNetherrackOpal", 256);
        addOreWeightNether("oreNetherrackBlueTopaz", 256);
        addOreWeightNether("oreNetherrackFoolsRuby", 256);
        addOreWeightNether("oreNetherrackGarnetRed", 256);
        addOreWeightNether("oreNetherrackGarnetYellow", 256);

        // Gregtech End material (oreEndstone<name>)
        addOreWeightEnd("oreEndstoneBentonite", 17244);
        addOreWeightEnd("oreEndstoneBeryllium", 16944);
        addOreWeightEnd("oreEndstoneCalcite", 6607);
        addOreWeightEnd("oreEndstoneCassiterite", 6741);
        addOreWeightEnd("oreEndstoneCobaltite", 3289);
        addOreWeightEnd("oreEndstoneEmerald", 2444);
        addOreWeightEnd("oreEndstoneGarnierite", 11511);
        addOreWeightEnd("oreEndstoneGlauconite", 5956);
        addOreWeightEnd("oreEndstoneMagnesite", 17244);
        addOreWeightEnd("oreEndstoneGrossular", 5711);
        addOreWeightEnd("oreEndstoneIridium", 494);
        addOreWeightEnd("oreEndstoneLapis", 4800);
        addOreWeightEnd("oreEndstoneLazurite", 18533);
        addOreWeightEnd("oreEndstoneLithium", 985);
        addOreWeightEnd("oreEndstoneMolybdenite", 1407);
        addOreWeightEnd("oreEndstoneMolybdenum", 402);
        addOreWeightEnd("oreEndstoneNaquadah", 16411);
        addOreWeightEnd("oreEndstoneNaquadahEnriched", 2496);
        addOreWeightEnd("oreEndstoneNickel", 11481);
        addOreWeightEnd("oreEndstoneOlivine", 4956);
        addOreWeightEnd("oreEndstonePalladium", 1431);
        addOreWeightEnd("oreEndstonePentlandite", 4000);
        addOreWeightEnd("oreEndstonePlatinum", 411);
        addOreWeightEnd("oreEndstonePowellite", 487);
        addOreWeightEnd("oreEndstonePyrolusite", 1652);
        addOreWeightEnd("oreEndstoneScheelite", 5678);
        addOreWeightEnd("oreEndstoneCooperite", 1431);
        addOreWeightEnd("oreEndstoneSodalite", 18548);
        addOreWeightEnd("oreEndstoneSpessartine", 5711);
        addOreWeightEnd("oreEndstoneTantalite", 1978);
        addOreWeightEnd("oreEndstoneThorium", 2944);
        addOreWeightEnd("oreEndstoneTin", 55815);
        addOreWeightEnd("oreEndstoneTungstate", 815);
        addOreWeightEnd("oreEndstoneWulfenite", 1407);

        // Gregtech Small oreEndstone Proxy
        addOreWeightEnd("oreEndstoneZinc", 2048);
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

        Map<String, Map<String, Integer>> dump = new Hashtable();
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
