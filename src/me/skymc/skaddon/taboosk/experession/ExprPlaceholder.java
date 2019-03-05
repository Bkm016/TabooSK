package me.skymc.skaddon.taboosk.experession;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.clip.placeholderapi.PlaceholderAPI;
import me.skymc.skaddon.taboosk.annotations.SkriptAddon;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@SkriptAddon(pattern = "[taboosk ]placeholder[ format] %string% (with|for|of|to) %player%")
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
    public String toString(Event arg0, boolean arg1) {
        return this.getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] i, int u, Kleenean k, SkriptParser.ParseResult p) {
        this.str = (Expression<String>) i[0];
        this.pl = (Expression<Player>) i[1];
        return true;
    }

    @Override
    protected String[] get(Event e) {
        String t = this.str.getSingle(e);
        Player p = this.pl.getSingle(e);
        return new String[] {PlaceholderAPI.setPlaceholders(p, "%" + t + "%")};
    }
}
