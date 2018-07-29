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
public class ExprVariable extends SimpleExpression<Object> {

    public static final ConcurrentHashMap<String, Object> VARIABLES = new ConcurrentHashMap<>();

    private Expression<String> node;
    private Expression<Object> def;

    @Override
    protected Object[] get(Event event) {
        return CollectionUtils.array(VARIABLES.getOrDefault(this.node.getSingle(event), def == null ? null : def.getSingle(event)));
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        switch (mode) {
            case SET:
                VARIABLES.put(node.getSingle(e), delta[0]);
                break;
            case RESET:
                VARIABLES.put(node.getSingle(e), null);
                break;
            case REMOVE:
            case DELETE:
                VARIABLES.remove(node.getSingle(e));
                break;
            case REMOVE_ALL:
                VARIABLES.clear();
                break;
            default:
        }
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return mode == Changer.ChangeMode.ADD ? null : CollectionUtils.array(Object.class);
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
        this.node = (Expression<String>) e[0];
        this.def = e.length > 2 ? (Expression<Object>) e[1] : null;
        return true;
    }
}
