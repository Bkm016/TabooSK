package me.skymc.skaddon.taboosk.experession;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public class ExprCloneObject extends SimpleExpression<Object> {

    private Expression<Object> object;
    private int stats;

    @Override
    public Class<? extends Object> getReturnType() {
        return String.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public String toString(final Event arg0, final boolean arg1) {
        return this.getClass().getName();
    }

    @Override
    public boolean init(final Expression<?>[] e, final int i, final Kleenean k, final SkriptParser.ParseResult result) {
        this.object = (Expression<Object>) e[0];
        this.stats = result.mark;
        return true;
    }

    @Override
    protected Object[] get(final Event e) {
        Object single = this.object.getSingle(e);
        if (stats == 1 && single instanceof ItemStack) {
            return new Object[] {((ItemStack) single).clone()};
        } else if (stats == 2 && single instanceof Location) {
            return new Object[] {((Location) single).clone()};
        }
        return null;
    }
}
