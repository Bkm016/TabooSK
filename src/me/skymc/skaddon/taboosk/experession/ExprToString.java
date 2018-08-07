package me.skymc.skaddon.taboosk.experession;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.skymc.skaddon.taboosk.annotations.SkriptAddon;
import org.bukkit.event.Event;

/**
 * @Author sky
 * @Since 2018-08-07 15:53
 */
@SkriptAddon(pattern = "[taboosk ]%-object% (to|as) (string|text)")
public class ExprToString extends SimpleExpression<String> {

    private Expression<?> obj;

    @Override
    protected String[] get(Event event) {
        return CollectionUtils.array(obj.getSingle(event).toString());
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
        return true;
    }
}
