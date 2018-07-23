package me.skymc.skaddon.taboosk.experession;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Date;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import java.text.SimpleDateFormat;

public class ExprFormatString extends SimpleExpression<String> {
    private Expression<Date> dat;
    private SimpleDateFormat dateformat;

    @Override
    public Class<? extends String> getReturnType() {
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
    public boolean init(final Expression<?>[] e, final int i, final Kleenean k, final SkriptParser.ParseResult p) {
        this.dat = (Expression<Date>) e[0];
        this.dateformat = new SimpleDateFormat(String.valueOf(e[1]));
        return true;
    }

    @Override
    protected String[] get(final Event e) {
        return new String[] {this.dateformat.format(this.dat.getSingle(e).getTimestamp())};
    }
}
