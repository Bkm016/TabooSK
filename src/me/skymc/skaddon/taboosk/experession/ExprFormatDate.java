package me.skymc.skaddon.taboosk.experession;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Date;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.skymc.skaddon.taboosk.annotations.SkriptAddon;
import org.bukkit.event.Event;

@SkriptAddon(pattern = "[taboosk ]skript[ date] format %number%")
public class ExprFormatDate extends SimpleExpression<Date> {

    private Expression<Number> time;

    @Override
    public Class<? extends Date> getReturnType() {
        return Date.class;
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
        this.time = (Expression<Number>) e[0];
        return true;
    }

    @Override
    protected Date[] get(Event e) {
        return CollectionUtils.array(new Date(time.getSingle(e).longValue()));
    }
}
