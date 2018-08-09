package me.skymc.skaddon.taboosk.experession;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Date;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.skymc.skaddon.taboosk.annotations.SkriptAddon;
import org.bukkit.event.Event;

import java.text.SimpleDateFormat;

@SkriptAddon(pattern = "[taboosk ]time[ number] %string% in %date%")
public class ExprFormatTime extends SimpleExpression<Number> {

    private Expression<Date> date;
    private Expression<String> type;
    private SimpleDateFormat dateFormat;

    public ExprFormatTime() {
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
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
    public String toString(Event arg0, boolean arg1) {
        return this.getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] e, int i, Kleenean k, SkriptParser.ParseResult p) {
        this.date = (Expression<Date>) e[1];
        this.type = (Expression<String>) e[0];
        return true;
    }

    @Override
    protected Number[] get(Event e) {
        switch (type.getSingle(e)) {
            case "yyyy":
                return CollectionUtils.array(Integer.valueOf(dateFormat.format(date.getSingle(e).getTimestamp()).split("-")[0]));
            case "MM":
                return CollectionUtils.array(Integer.valueOf(dateFormat.format(date.getSingle(e).getTimestamp()).split("-")[1]));
            case "dd":
                return CollectionUtils.array(Integer.valueOf(dateFormat.format(date.getSingle(e).getTimestamp()).split("-")[2]));
            case "HH":
                return CollectionUtils.array(Integer.valueOf(dateFormat.format(date.getSingle(e).getTimestamp()).split("-")[3]));
            case "mm":
                return CollectionUtils.array(Integer.valueOf(dateFormat.format(date.getSingle(e).getTimestamp()).split("-")[4]));
            case "ss":
                return CollectionUtils.array(Integer.valueOf(dateFormat.format(date.getSingle(e).getTimestamp()).split("-")[5]));
            case "SSS":
                return CollectionUtils.array(Integer.valueOf(dateFormat.format(date.getSingle(e).getTimestamp()).split("-")[6]));
            default:
                return CollectionUtils.array(-1);
        }
    }
}
