package me.skymc.skaddon.taboosk.experession.v2;

import ch.njol.skript.Skript;
import ch.njol.skript.command.CommandEvent;
import ch.njol.skript.lang.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.skymc.skaddon.taboosk.TabooSK;
import me.skymc.skaddon.taboosk.util.Util;
import me.skymc.taboolib.commands.builder.SimpleCommandBuilder;
import me.skymc.taboolib.common.inject.TInject;
import me.skymc.taboolib.common.util.SimpleCounter;
import me.skymc.taboolib.string.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.util.NumberConversions;

import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author 坏黑
 * @Since 2019-01-04 0:24
 */
public class Command {

    private static Map<String, SimpleCounter> counters = Maps.newHashMap();
    private static Map<String, Condition> conditions = Maps.newHashMap();
    private static Map<String, Effect> effects = Maps.newHashMap();
    private static Map<String, ForEachData> mapForeach = Maps.newHashMap();
    private static Pattern patternFor = Pattern.compile("\\((?<expression>.+?)\\)([ ]?=>[ ]?(?<condition>.+?))?[ ]?->[ ]?(?<effect>.+)");

    @TInject
    static SimpleCommandBuilder conditionList = SimpleCommandBuilder.create("cbConditionCheck", TabooSK.getInst())
            .permission("*")
            .aliases("conditionCheck", "condition")
            .execute((sender, args) -> {
                if (!(sender instanceof BlockCommandSender)) {
                    sender.sendMessage("§c[TabooSK] §7/cbConditionCheck §4is only usable by commandblocks with analog output.");
                    return true;
                }
                if (args.length == 0) {
                    return true;
                }
                String str = ArrayUtils.arrayJoin(args, 0);
                Condition condition = conditions.computeIfAbsent(str, n -> Condition.parse(str, null));
                if (condition != null) {
                    Block target = ((BlockCommandSender) sender).getBlock().getRelative(getCommandBlockFace(((BlockCommandSender) sender).getBlock().getData()));
                    if (condition.check(new CommandEvent(sender, null, null))) {
                        // 黑曜石
                        if (target.getType() == Material.OBSIDIAN) {
                            target.setType(Material.REDSTONE_BLOCK);
                        }
                        // 中继器
                        else if (target.getType() == Material.DIODE_BLOCK_OFF) {
                            byte data = target.getData();
                            target.setType(Material.DIODE_BLOCK_ON);
                            target.setData(data);
                        }
                    } else {
                        // 红石块
                        if (target.getType() == Material.REDSTONE_BLOCK) {
                            target.setType(Material.OBSIDIAN);
                        }
                        // 中继器
                        else if (target.getType() == Material.DIODE_BLOCK_ON) {
                            byte data = target.getData();
                            target.setType(Material.DIODE_BLOCK_OFF);
                            target.setData(data);
                        }
                    }
                }
                return true;
            });

    @TInject
    static SimpleCommandBuilder conditionCheck = SimpleCommandBuilder.create("cbConditionList", TabooSK.getInst())
            .permission("*")
            .aliases("conditionList", "conditions")
            .execute((sender, args) -> {
                sender.sendMessage("§c[TabooSK] §7Condition List: §f" + conditions.size());
                conditions.keySet().stream().map(str -> "§c[TabooSK] §f - §8" + str).forEach(sender::sendMessage);
                return true;
            });

    @TInject
    static SimpleCommandBuilder conditionReset = SimpleCommandBuilder.create("cbConditionReset", TabooSK.getInst())
            .permission("*")
            .aliases("conditionReset")
            .execute((sender, args) -> {
                conditions.clear();
                sender.sendMessage("§c[TabooSK] §7Condition Reset.");
                return true;
            });

    @TInject
    static SimpleCommandBuilder effectExecute = SimpleCommandBuilder.create("cbEffectExecute", TabooSK.getInst())
            .permission("*")
            .aliases("effectExecute", "effectRun")
            .execute((sender, args) -> {
                if (!(sender instanceof BlockCommandSender)) {
                    sender.sendMessage("§c[TabooSK] §7/cbEffectExecute §4is only usable by commandblocks with analog output.");
                    return true;
                }
                if (args.length == 0) {
                    return true;
                }
                String str = ArrayUtils.arrayJoin(args, 0);
                Effect effect = effects.computeIfAbsent(str, n -> Effect.parse(str, null));
                effect.run(new CommandEvent(sender, null, null));
                return true;
            });

    @TInject
    static SimpleCommandBuilder effectList = SimpleCommandBuilder.create("cbEffectList", TabooSK.getInst())
            .permission("*")
            .aliases("effectList", "effects")
            .execute((sender, args) -> {
                sender.sendMessage("§c[TabooSK] §7Effect List: §f" + effects.size());
                effects.keySet().stream().map(str -> "§c[TabooSK] §f - §8" + str).forEach(sender::sendMessage);
                return true;
            });

    @TInject
    static SimpleCommandBuilder effectReset = SimpleCommandBuilder.create("effectReset", TabooSK.getInst())
            .permission("*")
            .execute((sender, args) -> {
                effects.clear();
                sender.sendMessage("§c[TabooSK] §7Effect Reset.");
                return true;
            });

    @TInject
    static SimpleCommandBuilder loop = SimpleCommandBuilder.create("cbLoop", TabooSK.getInst())
            .aliases("loop")
            .permission("*")
            .execute((sender, args) -> {
                if (!(sender instanceof BlockCommandSender)) {
                    sender.sendMessage("§c[TabooSK] §7/cbLoop §4is only usable by commandblocks with analog output.");
                    return true;
                }
                if (args.length < 3) {
                    return true;
                }
                if (counters.computeIfAbsent(args[0], n -> new SimpleCounter(NumberConversions.toInt(args[1]))).next()) {
                    Bukkit.dispatchCommand(sender, ArrayUtils.arrayJoin(args, 2));
                }
                return true;
            });

    @TInject
    static SimpleCommandBuilder loopList = SimpleCommandBuilder.create("cbLoopList", TabooSK.getInst())
            .aliases("loopList", "loops")
            .permission("*")
            .execute((sender, args) -> {
                sender.sendMessage("§c[TabooSK] §7Loop List: §f" + counters.size());
                counters.keySet().stream().map(str -> "§c[TabooSK] §f - §8" + str).forEach(sender::sendMessage);
                return true;
            });

    @TInject
    static SimpleCommandBuilder loopReset = SimpleCommandBuilder.create("cbLoopReset", TabooSK.getInst())
            .aliases("loopReset")
            .permission("*")
            .execute((sender, args) -> {
                if (args.length == 0) {
                    counters.clear();
                } else {
                    counters.remove(args[0]);
                }
                sender.sendMessage("§c[TabooSK] §7Loop Reset: §f" + (args.length == 0 ? "ALL" : args[0]));
                return true;
            });

    @TInject
    static SimpleCommandBuilder forEachList = SimpleCommandBuilder.create("cbForEachList", TabooSK.getInst())
            .aliases("cbForList", "forEachList", "forList")
            .permission("*")
            .execute((sender, args) -> {
                sender.sendMessage("§c[TabooSK] §7ForEach List: §f" + mapForeach.size());
                mapForeach.keySet().stream().map(str -> "§c[TabooSK] §f - §8" + str).forEach(sender::sendMessage);
                return true;
            });

    @TInject
    static SimpleCommandBuilder forEachReset = SimpleCommandBuilder.create("cbForEachReset", TabooSK.getInst())
            .aliases("cbForReset", "forEachReset", "forReset")
            .permission("*")
            .execute((sender, args) -> {
                if (args.length == 0) {
                    mapForeach.clear();
                } else {
                    mapForeach.remove(ArrayUtils.arrayJoin(args, 0));
                }
                sender.sendMessage("§c[TabooSK] §7ForEach Reset: §f" + (args.length == 0 ? "ALL" : args[0]));
                return true;
            });

    @TInject
    static SimpleCommandBuilder forEach = SimpleCommandBuilder.create("cbForEach", TabooSK.getInst())
            .aliases("cbFor", "forEach", "for")
            .permission("*")
            .execute((sender, args) -> {
                if (args.length == 0) {
                    sender.sendMessage("§c[TabooSK] §7/for ($expression) -> $effects");
                    return true;
                }
                String join = ArrayUtils.arrayJoin(args, 0);
                ForEachData data = mapForeach.get(join);
                if (data == null) {
                    Matcher matcher = patternFor.matcher(join);
                    if (!matcher.find()) {
                        sender.sendMessage("§c[TabooSK] §4Invalid ForEach: §7" + join);
                        return true;
                    }
                    Util.toggleCurrentEvent(ForEvent.class);
                    try {
                        Method parse = SkriptParser.class.getDeclaredMethod("parse", String.class, Iterator.class, String.class);
                        parse.setAccessible(true);
                        Expression expression = (Expression) parse.invoke(null, matcher.group("expression"), Skript.getExpressions(), null);
                        if (expression == null) {
                            sender.sendMessage("§c[TabooSK] §4Invalid Expression: §7" + matcher.group("expression"));
                            return true;
                        }
                        data = new ForEachData(expression, Lists.newArrayList(), Lists.newArrayList());
                        for (String s : matcher.group("effect").split("->")) {
                            data.getEffects().add(Effect.parse(s, null));
                        }
                        if (matcher.group("condition") != null) {
                            for (String condition : matcher.group("condition").split("=>")) {
                                data.getConditions().add(Condition.parse(condition, null));
                            }
                        }
                        mapForeach.put(join, data);
                    } catch (Throwable e) {
                        e.printStackTrace();
                    } finally {
                        Util.toggleCurrentEvent(null);
                    }
                }
                if (data == null || data.getExpression() == null) {
                    return true;
                }
                Object[] objects = data.getExpression().getArray(new CommandEvent(sender, null, null));
                for (int i = 0; i < objects.length; i++) {
                    int index = i;
                    Object value = objects[i];
                    if (data.getConditions().stream().filter(Objects::nonNull).allMatch(condition -> condition.check(new ForEvent(sender, index, value)))) {
                        data.getEffects().stream().filter(Objects::nonNull).forEach(effect -> effect.run(new ForEvent(sender, index, value)));
                    }
                }
                return true;
            });

    public static BlockFace getCommandBlockFace(byte data) {
        switch (data) {
            case 0:
                return BlockFace.DOWN;
            case 1:
                return BlockFace.UP;
            case 2:
                return BlockFace.NORTH;
            case 3:
                return BlockFace.SOUTH;
            case 4:
                return BlockFace.WEST;
            case 5:
                return BlockFace.EAST;
            default:
                return BlockFace.UP;
        }
    }

    public static class ForEachData {

        private Expression expression;
        private List<Condition> conditions;
        private List<Effect> effects;

        public ForEachData(Expression expression, List<Condition> conditions, List<Effect> effects) {
            this.expression = expression;
            this.conditions = conditions;
            this.effects = effects;
        }

        public Expression getExpression() {
            return expression;
        }

        public List<Condition> getConditions() {
            return conditions;
        }

        public List<Effect> getEffects() {
            return effects;
        }
    }

    public static class ForEvent extends CommandEvent {

        private int index;
        private Object value;

        public ForEvent(CommandSender sender, int index, Object value) {
            super(sender, null, null);
            this.index = index;
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public Object getValue() {
            return value;
        }

        @Override
        public HandlerList getHandlers() {
            return null;
        }
    }
}
