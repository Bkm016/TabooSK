package me.skymc.skaddon.taboosk.experession;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.event.Event;

/**
 * @Author sky
 * @Since 2018-07-29 23:08
 */
public class ExprBinaryOperation extends SimpleExpression<Object> {

    private Expression<Condition> condition;
    private Expression<Object> obj1;
    private Expression<Object> obj2;

    @Override
    protected Object[] get(Event event) {
        return CollectionUtils.array(condition.getSingle(event).check(event) ? obj1.getSingle(event) : obj2.getSingle(event));
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<?> getReturnType() {
        return Object.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return this.getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] e, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.condition = (Expression<Condition>) e[0];
        this.obj1 = (Expression<Object>) e[1];
        this.obj2 = (Expression<Object>) e[2];
        return true;
    }
}
