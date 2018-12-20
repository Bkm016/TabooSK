package me.skymc.skaddon.taboosk.experession;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.skymc.skaddon.taboosk.annotations.SkriptAddon;
import org.bukkit.event.Event;
import org.bukkit.util.NumberConversions;

/**
 * @Author sky
 * @Since 2018-08-07 15:56
 */
@SkriptAddon(pattern = "[taboosk ]%-object% (to|as) bool")
public class ExprToBoolean extends SimpleExpression<Boolean> {

    private Expression<?> obj;

    @Override
    protected Boolean[] get(Event event) {
        return CollectionUtils.array(Boolean.parseBoolean(obj.getSingle(event).toString()));
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return String.valueOf(get(event)[0]);
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        obj = expressions[0];
        return true;
    }
}
