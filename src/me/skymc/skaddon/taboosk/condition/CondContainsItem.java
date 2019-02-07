package me.skymc.skaddon.taboosk.condition;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.skymc.skaddon.taboosk.annotations.SkriptAddon;
import me.skymc.taboolib.inventory.InventoryUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * @Author sky
 * @Since 2018-08-13 13:34
 */
@SkriptAddon(pattern = "[taboosk ]%inventory% (1¦doesn't contains|2¦contains) [item ]%itemstack%")
public class CondContainsItem extends Condition {

    private Expression<?> inventory;
    private Expression<?> itemstack;
    private int mark;

    @Override
    public boolean check(Event event) {
        ItemStack item = (ItemStack) itemstack.getSingle(event);
        return (mark == 1) != InventoryUtil.hasItem((Inventory) inventory.getSingle(event), item, item.getAmount(), false);
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.mark = parseResult.mark;
        this.inventory = expressions[0];
        this.itemstack = expressions[1];
        return true;
    }
}
