package me.skymc.skaddon.taboosk.function;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.lang.function.FunctionEvent;
import ch.njol.skript.lang.function.Functions;
import ch.njol.skript.lang.function.JavaFunction;
import ch.njol.skript.lang.function.Parameter;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.Date;
import ch.njol.util.coll.CollectionUtils;
import me.skymc.skaddon.taboosk.TabooSK;
import me.skymc.skaddon.taboosk.util.FileUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.NumberConversions;

import java.util.Calendar;

/**
 * @Author sky
 * @Since 2018-08-09 16:18
 */
public class PluginFunction {

    private static FileConfiguration functionsFile;

    public static void init() {
        functionsFile = FileUtil.saveDefaultConfig(TabooSK.getInst(), "functions.yml");
        registerObjectFunction();
        registerDateFunctions();
    }

    private static void registerObjectFunction() {
        ClassInfo<Object> objClass = Classes.getExactClassInfo(Object.class);
        Parameter[] objParam = {new Parameter("obj", objClass, true, null)};

        registerFunction(new JavaFunction("int", objParam, objClass, true) {
            @Override
            public Integer[] execute(FunctionEvent event, Object[][] obj) {
                return CollectionUtils.array(NumberConversions.toInt(obj[0][0]));
            }
        });
        registerFunction(new JavaFunction("num", objParam, objClass, true) {
            @Override
            public Number[] execute(FunctionEvent event, Object[][] obj) {
                return CollectionUtils.array(NumberConversions.toDouble(obj[0][0]));
            }
        });
        registerFunction(new JavaFunction("str", objParam, objClass, true) {
            @Override
            public String[] execute(FunctionEvent event, Object[][] obj) {
                return CollectionUtils.array(String.valueOf(obj[0][0]));
            }
        });
    }

    private static void registerDateFunctions() {
        ClassInfo<Date> dateClass = Classes.getExactClassInfo(Date.class);
        Parameter[] dateParam = {new Parameter("date", dateClass, true, null)};

        registerFunction(new JavaFunction("year", dateParam, dateClass, true) {
            @Override
            public Number[] execute(FunctionEvent event, Object[][] obj) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(((Date) obj[0][0]).getTimestamp());
                return CollectionUtils.array(calendar.get(Calendar.YEAR));
            }
        });
        registerFunction(new JavaFunction("month", dateParam, dateClass, true) {
            @Override
            public Number[] execute(FunctionEvent event, Object[][] obj) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(((Date) obj[0][0]).getTimestamp());
                return CollectionUtils.array(calendar.get(Calendar.MONTH));
            }
        });
        registerFunction(new JavaFunction("day", dateParam, dateClass, true) {
            @Override
            public Number[] execute(FunctionEvent event, Object[][] obj) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(((Date) obj[0][0]).getTimestamp());
                return CollectionUtils.array(calendar.get(Calendar.DAY_OF_MONTH));
            }
        });
        registerFunction(new JavaFunction("hour", dateParam, dateClass, true) {
            @Override
            public Number[] execute(FunctionEvent event, Object[][] obj) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(((Date) obj[0][0]).getTimestamp());
                return CollectionUtils.array(calendar.get(Calendar.HOUR_OF_DAY));
            }
        });
        registerFunction(new JavaFunction("minute", dateParam, dateClass, true) {
            @Override
            public Number[] execute(FunctionEvent event, Object[][] obj) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(((Date) obj[0][0]).getTimestamp());
                return CollectionUtils.array(calendar.get(Calendar.MINUTE));
            }
        });
        registerFunction(new JavaFunction("second", dateParam, dateClass, true) {
            @Override
            public Number[] execute(FunctionEvent event, Object[][] obj) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(((Date) obj[0][0]).getTimestamp());
                return CollectionUtils.array(calendar.get(Calendar.SECOND));
            }
        });
        registerFunction(new JavaFunction("millisecond", dateParam, dateClass, true) {
            @Override
            public Number[] execute(FunctionEvent event, Object[][] obj) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(((Date) obj[0][0]).getTimestamp());
                return CollectionUtils.array(calendar.get(Calendar.MILLISECOND));
            }
        });
    }

    private static void registerFunction(JavaFunction javaFunction) {
        if (functionsFile.getBoolean("Functions." + javaFunction.getName())) {
            Functions.registerFunction(javaFunction);
        }
    }
}
