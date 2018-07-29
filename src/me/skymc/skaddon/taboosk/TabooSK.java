package me.skymc.skaddon.taboosk;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.skript.registrations.Classes;
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

        try {
            registerClasses();
            registerEffects();
            registerExpressions();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerEffects() {
        // Scoreboard
        Skript.registerEffect(EffectScoreboardPush.class, "[taboosk ]set %player%['s] scoreboard to %strings%");
        Skript.registerEffect(EffectScoreboardEdit.class, "[taboosk ]set [score ]%integer% in %player%['s] scoreboard to %string%");
        Skript.registerEffect(EffectScoreboardReset.class, "[taboosk ]reset %player%['s] scoreboard");
        // Yaml
        Skript.registerEffect(EffectYamlSave.class, "[taboosk ]save yaml %string%");
        Skript.registerEffect(EffectYamlReload.class, "[taboosk ]reload yaml %string%");
        Skript.registerEffect(EffectYamlReloadAll.class, "[taboosk ]reload all yaml");
    }

    private void registerExpressions() {
        // Enums
        Skript.registerExpression(ExprClickType.class, String.class, ExpressionType.PROPERTY, "[taboosk ][latest ]click[ type] with %player%");
        Skript.registerExpression(ExprFishState.class, String.class, ExpressionType.PROPERTY, "[taboosk ][latest ]fish[ state] with %player%");
        // Date
        Skript.registerExpression(ExprTimeMillis.class, Number.class, ExpressionType.PROPERTY, "[taboosk ]system (date|time|millils)");
        Skript.registerExpression(ExprFormatMills.class, Number.class, ExpressionType.PROPERTY, "[taboosk ]system[ date] format %date%");
        Skript.registerExpression(ExprFormatDate.class, Date.class, ExpressionType.PROPERTY, "[taboosk ]skript[ date] format %number%");
        Skript.registerExpression(ExprFormatString.class, String.class, ExpressionType.PROPERTY, "[taboosk ]string[ date] format %date% with %string%");
        Skript.registerExpression(ExprGettime.class, Number.class, ExpressionType.PROPERTY, "[taboosk ]time[ number] %string% in %date%");
        // Object
        Skript.registerExpression(ExprCloneObject.class, Object.class, ExpressionType.PROPERTY, "[taboosk ]clone %-object% as (1¦item|2¦location)");
        // Yaml
        Skript.registerExpression(ExprYaml.class, Object.class, ExpressionType.PROPERTY, "[taboosk ]yaml (1¦value|2¦nodes|3¦nodes all|4¦list|5¦values|6¦values all) %string% in [file ]%string%[ default %-object%]");
        // Variable
        Skript.registerExpression(ExprVariable.class, Object.class, ExpressionType.PROPERTY, "[taboosk ](var|variable) %string%[ default %-object%]");
        Skript.registerExpression(ExprVariables.class, Object.class, ExpressionType.PROPERTY, "[taboosk ](vars|variables)");
        // Binary Operation
        Skript.registerExpression(ExprBinaryOperation.class, Object.class, ExpressionType.PROPERTY, "[taboosk ]check[ed] %tcondition% if %-object% else %-object%");
        // PlaceholderAPI
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Skript.registerExpression(ExprPlaceholder.class, String.class, ExpressionType.PROPERTY, "[taboosk ]placeholder[ format] %string% with %player%");
        }
    }

    private void registerClasses() {
        Classes.registerClass(new ClassInfo(Condition.class, "tcondition").parser(new Parser() {
            @Override
            public Condition parse(String s, ParseContext parseContext) {
                if ((s.length() > 2) && (s.charAt(0) == '[') && (s.charAt(s.length() - 1) == ']')) {
                    Condition e = Condition.parse(s.substring(1, s.length() - 1), null);
                    if (e == null) {
                        Skript.error(s + " is an invalid condition.", ErrorQuality.SEMANTIC_ERROR);
                    } else {
                        return e;
                    }
                }
                return null;
            }

            @Override
            public boolean canParse(ParseContext context) {
                return true;
            }

            @Override
            public String toString(Object o, int i) {
                return null;
            }

            @Override
            public String toVariableNameString(Object o) {
                return null;
            }

            @Override
            public String getVariableNamePattern() {
                return ".+";
            }
        }));
    }

    @Override
    public void onDisable() {
        CacheYaml.CACHE_YAML.clear();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("§c[TabooSK] §7Author §f@坏黑");
        sender.sendMessage("§c[TabooSK] §7Cached Variable: §f" + ExprVariable.VARIABLES.size());
        sender.sendMessage("§c[TabooSK] §7Cached Configuration: §f" + CacheYaml.CACHE_YAML.size());
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
