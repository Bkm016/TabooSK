package me.skymc.skaddon.taboosk;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import me.skymc.skaddon.taboosk.annotations.SkriptAddon;
import me.skymc.skaddon.taboosk.classes.PluginClasses;
import me.skymc.skaddon.taboosk.command.MainCommand;
import me.skymc.skaddon.taboosk.listener.ListenerLatestDoing;
import me.skymc.skaddon.taboosk.function.PluginFunction;
import me.skymc.skaddon.taboosk.handler.ScriptHandler;
import me.skymc.skaddon.taboosk.handler.YamlHandler;
import me.skymc.skaddon.taboosk.util.PackageUtil;
import me.skymc.taboolib.plugin.PluginLoadState;
import me.skymc.taboolib.plugin.PluginLoadStateType;
import me.skymc.taboolib.plugin.PluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;

public class TabooSK extends JavaPlugin {

    private static TabooSK inst;

    @Override
    public void onLoad() {
        inst = this;
    }

    @Override
    public void onEnable() {
        try {
            packageAddons();
            YamlHandler.init();
            PluginClasses.init();
            PluginFunction.init();
            ScriptHandler.inst();
            ScriptHandler.reloadGlobalScripts();
            Bukkit.getConsoleSender().sendMessage("§8[§3§lTabooSK§8] §7Author: §f@坏黑");
            Bukkit.getConsoleSender().sendMessage("§8[§3§lTabooSK§8] §7Registered §f" + registerEffect() + "§7 effects.");
            Bukkit.getConsoleSender().sendMessage("§8[§3§lTabooSK§8] §7Registered §f" + registerCondition() + "§7 conditions.");
            Bukkit.getConsoleSender().sendMessage("§8[§3§lTabooSK§8] §7Registered §f" + registerExpression() + "§7 expressions.");
            Bukkit.getPluginManager().registerEvents(new ListenerLatestDoing(), this);
            Bukkit.getPluginCommand("taboosk").setExecutor(new MainCommand());
            Bukkit.getScheduler().runTaskTimer(this, () -> ScriptHandler.getScriptsDynamic().clear(), 0, 20 * 60 * 5);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        YamlHandler.CACHE_YAML.clear();
    }

    private int registerEffect() {
        int i = 0;
        for (Class addonClass : PackageUtil.getClasses()) {
            if (Effect.class.isAssignableFrom(addonClass) && addonClass.isAnnotationPresent(SkriptAddon.class)) {
                Skript.registerEffect(addonClass, ((SkriptAddon) addonClass.getAnnotation(SkriptAddon.class)).pattern());
                i++;
            }
        }
        return i;
    }

    private int registerExpression() {
        int i = 0;
        for (Class addonClass : PackageUtil.getClasses()) {
            try {
                if (Expression.class.isAssignableFrom(addonClass) && addonClass.isAnnotationPresent(SkriptAddon.class)) {
                    Expression expression = (Expression) addonClass.newInstance();
                    Skript.registerExpression(addonClass, expression.getReturnType(), ExpressionType.PROPERTY, ((SkriptAddon) addonClass.getAnnotation(SkriptAddon.class)).pattern());
                    i++;
                }
            } catch (Exception ignored) {
            }
        }
        return i;
    }

    private int registerCondition() {
        int i = 0;
        for (Class addonClass : PackageUtil.getClasses()) {
            try {
                if (Condition.class.isAssignableFrom(addonClass) && addonClass.isAnnotationPresent(SkriptAddon.class)) {
                    Condition condition = (Condition) addonClass.newInstance();
                    Skript.registerCondition(addonClass, ((SkriptAddon) addonClass.getAnnotation(SkriptAddon.class)).pattern());
                    i++;
                }
            } catch (Exception ignored) {
            }
        }
        return i;
    }

    private void packageAddons() {
        if (Bukkit.getPluginManager().getPlugin("TabooLib") == null) {
            return;
        }
        try (InputStreamReader reader = new InputStreamReader(getResource("package-addon.txt")); BufferedReader bufferedReader = new BufferedReader(reader)) {
            // 释放扩展
            bufferedReader.lines().forEach(line -> {
                if (!line.isEmpty()) {
                    saveResource("addon/" + line + ".jar", true);
                }
            });
            // 载入扩展
            File file = new File(getDataFolder(), "addon");
            if (file.exists() && file.isDirectory()) {
                Arrays.stream(file.listFiles()).filter(addonFIle -> !addonFIle.isDirectory()).forEach(addonFIle -> {
                    PluginLoadState load = PluginUtils.load("TabooSK/addon/" + addonFIle.getName().split("\\.")[0]);
                    if (load.getStateType() == PluginLoadStateType.LOADED) {
                        Bukkit.getConsoleSender().sendMessage("§8[§3§lTabooSK§8] §7Loaded: §f" + addonFIle.getName().split("\\.")[0]);
                    } else {
                        Bukkit.getConsoleSender().sendMessage("§8[§3§lTabooSK§8] §cLoading Failed: §4" + addonFIle.getName().split("\\.")[0] + " §7(" + load.getStateType() + ": " + load.getMessage() + "§8)");
                    }
                });
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    // *********************************
    //
    //        Getter and Setter
    //
    // *********************************

    public static TabooSK getInst() {
        return inst;
    }
}
