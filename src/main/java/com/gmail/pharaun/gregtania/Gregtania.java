package com.gmail.pharaun.gregtania;

import com.gmail.pharaun.gregtania.command.DebugSpawnListCommand;
import com.gmail.pharaun.gregtania.misc.Config;
import com.gmail.pharaun.gregtania.proxies.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES)
public class Gregtania {
    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
    public static CommonProxy proxy;

    @Mod.Instance(Reference.MODID)
    public static Gregtania instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.preInit(event.getSuggestedConfigurationFile());
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new DebugSpawnListCommand());
    }
}
