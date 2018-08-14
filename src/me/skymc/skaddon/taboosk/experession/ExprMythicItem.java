package me.skymc.skaddon.taboosk.experession;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.adapters.AbstractItemStack;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitItemStack;
import io.lumine.xikage.mythicmobs.items.MythicItem;
import me.skymc.skaddon.taboosk.annotations.SkriptAddon;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

/**
 * @Author sky
 * @Since 2018-08-14 20:01
 */
@SkriptAddon(pattern = "[taboosk ]mythic[mobs] item %string%[ amount %number%]")
public class ExprMythicItem extends SimpleExpression<ItemStack> {

    private Expression<String> name;
    private Expression<Number> amount;

    @Override
    protected ItemStack[] get(Event event) {
        MythicItem mythicItem = MythicMobs.inst().getItemManager().getItem(name.getSingle(event)).orElse(null);
        if (mythicItem != null) {
            AbstractItemStack abstractItemStack = mythicItem.generateItemStack(amount == null ? 1 : amount.getSingle(event).intValue());
            return abstractItemStack instanceof BukkitItemStack ? CollectionUtils.array(((BukkitItemStack) abstractItemStack).build()) : null;
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends ItemStack> getReturnType() {
        return ItemStack.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        name = (Expression<String>) expressions[0];
        if (expressions.length > 1) {
            amount = (Expression<Number>) expressions[1];
        }
        return true;
    }
}
