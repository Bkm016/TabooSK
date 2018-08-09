package me.skymc.skaddon.taboosk.experession;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.skymc.skaddon.taboosk.annotations.SkriptAddon;
import org.bukkit.event.Event;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Author sky
 * @Since 2018-08-09 17:08
 */
@SkriptAddon(pattern = "[taboosk ][string ]join %-objects%[ (with|for|as) %string%]")
public class ExprStringJoin extends SimpleExpression<String> {

    private Expression<?> obj;
    private Expression<String> str;

    @Override
    protected String[] get(Event event) {
        Object[] obj = this.obj.getArray(event);
        if (str == null) {
            return CollectionUtils.array(Arrays.stream(obj).map(String::valueOf).collect(Collectors.joining()));
        }
        String str = this.getSingle(event);
        return CollectionUtils.array(Arrays.stream(obj).map(o -> str + o).collect(Collectors.joining()).substring(str.length()));
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return get(event)[0];
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        obj = expressions[0];
        if (expressions.length > 0) {
            str = (Expression<String>) expressions[1];
        }
        return true;
    }
}
