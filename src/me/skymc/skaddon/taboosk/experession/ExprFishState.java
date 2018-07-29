package me.skymc.skaddon.taboosk.experession;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.skymc.skaddon.taboosk.event.ListenerLatestDoing;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExprFishState extends SimpleExpression<String> {
    private Expression<Player> p;

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
        this.p = (Expression<Player>) e[0];
        return true;
    }

    @Override
    protected String[] get(final Event e) {
        return new String[] {ListenerLatestDoing.fishState.get(this.p.getSingle(e))};
    }
}
