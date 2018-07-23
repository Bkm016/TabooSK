package me.skymc.skaddon.taboosk.experession;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Date;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import java.text.SimpleDateFormat;

public class ExprGettime extends SimpleExpression<Number> {

    private Expression<Date> date;
    private Expression<String> type;
    private SimpleDateFormat dateForamt;

    public ExprGettime() {
        this.dateForamt = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
    }

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
    public boolean init(final Expression<?>[] e, final int i, final Kleenean k, final SkriptParser.ParseResult p) {
        this.date = (Expression<Date>) e[1];
        this.type = (Expression<String>) e[0];
        return true;
    }

    @Override
    protected Number[] get(final Event e) {
        final String type = this.type.getSingle(e);
        final String d = this.dateForamt.format(this.date.getSingle(e).getTimestamp());
        if (type.equals("yyyy")) {
            return new Number[] {Integer.valueOf(d.split("-")[0])};
        }
        if (type.equals("MM")) {
            return new Number[] {Integer.valueOf(d.split("-")[1])};
        }
        if (type.equals("dd")) {
            return new Number[] {Integer.valueOf(d.split("-")[2])};
        }
        if (type.equals("HH")) {
            return new Number[] {Integer.valueOf(d.split("-")[3])};
        }
        if (type.equals("mm")) {
            return new Number[] {Integer.valueOf(d.split("-")[4])};
        }
        if (type.equals("ss")) {
            return new Number[] {Integer.valueOf(d.split("-")[5])};
        }
        if (type.equals("SSS")) {
            return new Number[] {Integer.valueOf(d.split("-")[6])};
        }
        return new Number[] {-1};
    }
}
