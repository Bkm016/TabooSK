package me.skymc.skaddon.taboosk.experession;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Date;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

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
    public String toString(final Event arg0, final boolean arg1) {
        return this.getClass().getName();
    }

    @Override
    public boolean init(final Expression<?>[] e, final int i, final Kleenean k, final SkriptParser.ParseResult p) {
        this.time = (Expression<Number>) e[0];
        return true;
    }

    @Override
    protected Date[] get(final Event e) {
        return new Date[] {new Date((long) this.time.getSingle(e))};
    }
}
