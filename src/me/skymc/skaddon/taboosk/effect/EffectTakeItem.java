package me.skymc.skaddon.taboosk.effect;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.skymc.skaddon.taboosk.annotations.SkriptAddon;
import me.skymc.taboolib.inventory.InventoryUtil;
import me.skymc.taboolib.scoreboard.ScoreboardUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.awt.*;

@SkriptAddon(pattern = "[taboosk ](take|remove) %itemstacks% from [inventory ] %inventory%")
public class EffectTakeItem extends Effect {
    
    private Expression<ItemStack> itemstack;
    private Expression<Inventory> inventory;

    @Override
    protected void execute(Event event) {
        ItemStack[] items = itemstack.getArray(event);
        for (ItemStack item : items) {
            InventoryUtil.hasItem(inventory.getSingle(event), item, item.getAmount(), true);
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return this.getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.itemstack = (Expression<ItemStack>) expressions[0];
        this.inventory = (Expression<Inventory>) expressions[1];
        return true;
    }
}
