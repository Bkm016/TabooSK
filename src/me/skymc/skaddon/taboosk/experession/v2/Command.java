package me.skymc.skaddon.taboosk.experession.v2;

import ch.njol.skript.command.CommandEvent;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Effect;
import com.google.common.collect.Maps;
import me.skymc.skaddon.taboosk.TabooSK;
import me.skymc.taboolib.commands.builder.SimpleCommandBuilder;
import me.skymc.taboolib.common.inject.TInject;
import me.skymc.taboolib.common.util.SimpleCounter;
import me.skymc.taboolib.string.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.util.NumberConversions;

import java.util.Map;

/**
 * @Author 坏黑
 * @Since 2019-01-04 0:24
 */
public class Command {

    private static Map<String, SimpleCounter> counters = Maps.newHashMap();
    private static Map<String, Condition> conditions = Maps.newHashMap();
    private static Map<String, Effect> effects = Maps.newHashMap();

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
                sender.sendMessage("§c[TabooSK] §7Conditions: §f" + conditions.size());
                conditions.keySet().stream().map(str -> "§c[TabooSK] §f - §8" + str).forEach(sender::sendMessage);
                return true;
            });

    @TInject
    static SimpleCommandBuilder conditionReset = SimpleCommandBuilder.create("cbConditionReset", TabooSK.getInst())
            .permission("*")
            .aliases("conditionReset")
            .execute((sender, args) -> {
                conditions.clear();
                sender.sendMessage("§c[TabooSK] §7Conditions Reset.");
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
                sender.sendMessage("§c[TabooSK] §7Effects: §f" + effects.size());
                effects.keySet().stream().map(str -> "§c[TabooSK] §f - §8" + str).forEach(sender::sendMessage);
                return true;
            });

    @TInject
    static SimpleCommandBuilder effectReset = SimpleCommandBuilder.create("effectReset", TabooSK.getInst())
            .permission("*")
            .execute((sender, args) -> {
                effects.clear();
                sender.sendMessage("§c[TabooSK] §7Effects Reset.");
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
                sender.sendMessage("§c[TabooSK] §7Loop List: §f" + counters.keySet());
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
}
