package com.gmail.pharaun.gregtania.misc;

import com.gmail.pharaun.gregtania.Reference;
import cpw.mods.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

// Credit: InfinityCore - helpers.LogHelper
public class LogHelper {
    public static void log(Level logLevel, String format, Object... data) {
        FMLLog.log(Reference.MODID, logLevel, format, data);
    }

    public static void log(Level level, Throwable throwable, String format, Object... data) {
        FMLLog.log(Reference.MODID, level, throwable, format, data);
    }

    public static void fatal(String format, Object... data) {
        log(Level.FATAL, format, data);
    }
    public static void fatal(Throwable throwable, String format, Object... data) {
        log(Level.FATAL, throwable, format, data);
    }

    public static void error(String format, Object... data) {
        log(Level.ERROR, format, data);
    }
    public static void error(Throwable throwable, String format,Object... data) {
        log(Level.ERROR, throwable, format, data);
    }

    public static void warn(String format, Object... data) {
        log(Level.WARN, format, data);
    }

    public static void info(String format, Object... data) {
        log(Level.INFO, format, data);
    }

    public static void debug(String format, Object... data) {
        log(Level.DEBUG, format, data);
    }
}