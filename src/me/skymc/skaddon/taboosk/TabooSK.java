package me.skymc.skaddon.taboosk;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import me.skymc.skaddon.taboosk.annotations.SkriptAddon;
import me.skymc.skaddon.taboosk.classes.PluginClasses;
import me.skymc.skaddon.taboosk.command.MainCommand;
import me.skymc.skaddon.taboosk.event.ListenerLatestDoing;
import me.skymc.skaddon.taboosk.handler.ScriptHandler;
import me.skymc.skaddon.taboosk.handler.YamlHandler;
import me.skymc.skaddon.taboosk.util.PackageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TabooSK extends JavaPlugin {

    private static TabooSK inst;

    @Override
    public void onLoad() {
        inst = this;
    }

    @Override
    public void onEnable() {
        YamlHandler.init();
        PluginClasses.init();
        ScriptHandler.inst();
        ScriptHandler.reloadGlobalScripts();
        Bukkit.getConsoleSender().sendMessage("§8[§3§lTabooSK§8] §7Author: §f@坏黑");
        Bukkit.getConsoleSender().sendMessage("§8[§3§lTabooSK§8] §7Registered §f" + registerEffect() + "§7 effects.");
        Bukkit.getConsoleSender().sendMessage("§8[§3§lTabooSK§8] §7Registered §f" + registerExpression() + "§7 expressions.");
        Bukkit.getPluginManager().registerEvents(new ListenerLatestDoing(), this);
        Bukkit.getPluginCommand("taboosk").setExecutor(new MainCommand());
        Bukkit.getScheduler().runTaskTimer(this, () -> ScriptHandler.getScriptsDynamic().clear(), 0, 20 * 60 * 5);
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

    // *********************************
    //
    //        Getter and Setter
    //
    // *********************************

    public static TabooSK getInst() {
        return inst;
    }
}
