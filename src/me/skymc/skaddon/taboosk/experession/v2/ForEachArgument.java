package me.skymc.skaddon.taboosk.experession.v2;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.skymc.skaddon.taboosk.annotations.SkriptAddon;
import org.bukkit.event.Event;

/**
 * @Author 坏黑
 * @Since 2019-01-03 21:56
 */
@SkriptAddon(pattern = "[taboosk] for[each]-(1¦index|2¦value)")
public class ForEachArgument extends SimpleExpression {

    private int stats;

    @Override
    protected Object[] get(Event event) {
        if (event instanceof Command.ForEvent) {
            return CollectionUtils.array(stats == 1 ? ((Command.ForEvent) event).getIndex() : ((Command.ForEvent) event).getValue());
        }
        return new Object[0];
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class getReturnType() {
        return stats == 1 ? Number.class : Object.class;
    }

    @Override
    public String toString(Event arg0, boolean arg1) {
        return this.getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.stats = parseResult.mark;
        return true;
    }
}
