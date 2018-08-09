package me.skymc.skaddon.taboosk.experession;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.skymc.skaddon.taboosk.annotations.SkriptAddon;
import org.bukkit.event.Event;

@SkriptAddon(pattern = "[taboosk ]sys[tem] (date|time|millils)")
public class ExprTimeMillis extends SimpleExpression<Number> {

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public String toString(Event arg0, boolean arg1) {
        return this.getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] e, int i, Kleenean k, SkriptParser.ParseResult p) {
        return true;
    }

    @Override
    protected Number[] get(Event e) {
        return CollectionUtils.array(System.currentTimeMillis());
    }
}
