package me.skymc.skaddon.taboosk.experession;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.event.Event;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author sky
 * @Since 2018-07-29 22:34
 */
public class ExprVariables extends SimpleExpression<Object> {

    @Override
    protected Object[] get(Event event) {
        return ExprVariable.VARIABLES.keySet().toArray();
    }

    @Override
    public boolean isSingle() {
        return false;
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
        return true;
    }
}
