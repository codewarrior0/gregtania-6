package com.gmail.pharaun.gregtania;

public class Reference {
    public static final String MODID = "gregtania";
    public static final String NAME = "Gregtania";
    public static final String VERSION = "0.0.2";
    public static final String COMMON_PROXY = "com.gmail.pharaun.gregtania.proxies.CommonProxy";
    public static final String CLIENT_PROXY = "com.gmail.pharaun.gregtania.proxies.ClientProxy";
    // required-after: hard dep
    // after: soft dep
    public static final String DEPENDENCIES = "required-after:gregtech; required-after:Botania";
}
