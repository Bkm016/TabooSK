package me.skymc.skaddon.taboosk.experession;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Date;
import ch.njol.util.Kleenean;
import me.skymc.skaddon.taboosk.annotations.SkriptAddon;
import org.bukkit.event.Event;

@SkriptAddon(pattern = "[taboosk ]system[ date] format %date%")
public class ExprFormatMills extends SimpleExpression<Number> {
    private Expression<Date> date;

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
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
    public boolean init(final Expression<?>[] i, final int u, final Kleenean k, final SkriptParser.ParseResult p) {
        this.date = (Expression<Date>) i[0];
        return true;
    }

    @Override
    protected Number[] get(final Event e) {
        return new Number[] {((Date) this.date.getSingle(e)).getTimestamp()};
    }
}
