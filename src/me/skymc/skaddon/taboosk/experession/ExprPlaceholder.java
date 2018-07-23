package me.skymc.skaddon.taboosk.experession;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExprPlaceholder extends SimpleExpression<String> {
    private Expression<String> str;
    private Expression<Player> pl;

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
    public boolean init(final Expression<?>[] i, final int u, final Kleenean k, final SkriptParser.ParseResult p) {
        this.str = (Expression<String>) i[0];
        this.pl = (Expression<Player>) i[1];
        return true;
    }

    @Override
    protected String[] get(final Event e) {
        final String t = this.str.getSingle(e);
        final Player p = this.pl.getSingle(e);
        return new String[] {PlaceholderAPI.setPlaceholders(p, "%" + t + "%")};
    }
}
