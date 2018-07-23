package me.skymc.skaddon.taboosk;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.util.Date;
import me.skymc.skaddon.taboosk.event.ListenerLatestDoing;
import me.skymc.skaddon.taboosk.event.effect.*;
import me.skymc.skaddon.taboosk.experession.*;
import me.skymc.skaddon.taboosk.yaml.CacheYaml;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class TabooSK extends JavaPlugin implements Listener {

    private static TabooSK inst;

    @Override
    public void onLoad() {
        inst = this;
    }

    @Override
    public void onEnable() {
        CacheYaml.init();

        Bukkit.getConsoleSender().sendMessage("§8[§3§lTabooSK§8] §7插件已载入!");
        Bukkit.getConsoleSender().sendMessage("§8[§3§lTabooSK§8] §7作者: §f坏黑");

        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new ListenerLatestDoing(), this);

        Skript.registerEffect(EffectScoreboardPush.class, "[taboosk ]set %player%['s] scoreboard to %strings%");
        Skript.registerEffect(EffectScoreboardEdit.class, "[taboosk ]set [score ]%integer% in %player%['s] scoreboard to %string%");
        Skript.registerEffect(EffectScoreboardReset.class, "[taboosk ]reset %player%['s] scoreboard");

        Skript.registerEffect(EffectYamlSave.class, "[taboosk ]save yaml %string%");
        Skript.registerEffect(EffectYamlReload.class, "[taboosk ]reload yaml %string%");
        Skript.registerEffect(EffectYamlReloadAll.class, "[taboosk ]reload all yaml");

        Skript.registerExpression(ExprClickType.class, String.class, ExpressionType.PROPERTY, "[taboosk ][latest ]click[ type] with %player%");
        Skript.registerExpression(ExprFishState.class, String.class, ExpressionType.PROPERTY, "[taboosk ][latest ]fish[ state] with %player%");

        Skript.registerExpression(ExprTimeMillis.class, Number.class, ExpressionType.PROPERTY, "[taboosk ]system (date|time|millils)");
        Skript.registerExpression(ExprPlaceholder.class, String.class, ExpressionType.PROPERTY, "[taboosk ]placeholder[ format] %string% with %player%");
        Skript.registerExpression(ExprFormatMills.class, Number.class, ExpressionType.PROPERTY, "[taboosk ]system[ date] format %date%");
        Skript.registerExpression(ExprFormatDate.class, Date.class, ExpressionType.PROPERTY, "[taboosk ]skript[ date] format %number%");
        Skript.registerExpression(ExprFormatString.class, String.class, ExpressionType.PROPERTY, "[taboosk ]string[ date] format %date% with %string%");
        Skript.registerExpression(ExprGettime.class, Number.class, ExpressionType.PROPERTY, "[taboosk ]time[ number] %string% in %date%");
        Skript.registerExpression(ExprYaml.class, Object.class, ExpressionType.PROPERTY, "[taboosk ]yaml (1¦value|2¦nodes|3¦nodes all|4¦list|5¦values|6¦values all) %string% in [file ]%string%[ default %-object%]");
        Skript.registerExpression(ExprCloneObject.class, Object.class, ExpressionType.PROPERTY, "[taboosk ]clone %-object% as (1¦item|2¦location)");
    }

    @Override
    public void onDisable() {
        CacheYaml.CACHE_YAML.clear();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("§c[TabooSK] §f插件作者 §e@坏黑");
        sender.sendMessage("§c[TabooSK] §7当前已缓存配置: §f" + CacheYaml.CACHE_YAML.size());
        return true;
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
