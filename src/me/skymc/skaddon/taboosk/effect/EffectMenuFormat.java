package me.skymc.skaddon.taboosk.effect;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.skymc.skaddon.taboosk.annotations.SkriptAddon;
import me.skymc.skaddon.taboosk.menu.DataMenuHolder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * @Author sky
 * @Since 2018-08-06 16:05
 */
@SkriptAddon(pattern = "[taboosk ]format %-object% (with|for|in|to) %player%['s] data menu (with|for|in|to) slot %integers%")
public class EffectMenuFormat extends Effect {

    private Expression<?> item;
    private Expression<Player> player;
    private Expression<Integer> slot;

    @Override
    protected void execute(Event e) {
        Player player = this.player.getSingle(e);
        if (player.getOpenInventory().getTopInventory().getHolder() instanceof DataMenuHolder) {
            Object obj = item.getSingle(e);
            if (obj instanceof ItemStack) {
                Arrays.stream(slot.getArray(e)).forEach(slot -> player.getOpenInventory().getTopInventory().setItem(slot, (ItemStack) obj));
            } else if (obj instanceof ItemType) {
                Arrays.stream(slot.getArray(e)).forEach(slot -> player.getOpenInventory().getTopInventory().setItem(slot, ((ItemType) obj).getRandom()));
            } else {
                ItemStack failedItem = new ItemStack(Material.BEDROCK);
                ItemMeta itemMeta = failedItem.getItemMeta();
                itemMeta.setDisplayName("§4!Failed input");
                itemMeta.setLore(Arrays.asList("", "§c" + obj.getClass().getName()));
                failedItem.setItemMeta(itemMeta);
                Arrays.stream(slot.getArray(e)).forEach(slot -> player.getOpenInventory().getTopInventory().setItem(slot, failedItem));
            }
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return this.getClass().toString();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        item = expressions[0];
        player = (Expression<Player>) expressions[1];
        slot = (Expression<Integer>) expressions[2];
        return true;
    }
}
