package me.skymc.skaddon.taboosk.effect;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.skymc.skaddon.taboosk.annotations.SkriptAddon;
import me.skymc.skaddon.taboosk.menu.DataMenuHolder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

/**
 * @Author sky
 * @Since 2018-08-06 16:05
 */
@SkriptAddon(pattern = "[taboosk ]open [a ]data menu name[d] %string% (with|for|of) row[s] %integer% to %player%")
public class EffectMenuOpen extends Effect {

    private Expression<String> name;
    private Expression<Integer> rows;
    private Expression<Player> player;

    @Override
    protected void execute(Event e) {
        player.getSingle(e).openInventory(Bukkit.createInventory(new DataMenuHolder(), rows.getSingle(e) * 9, name.getSingle(e)));
    }

    @Override
    public String toString(Event event, boolean b) {
        return this.getClass().toString();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        name = (Expression<String>) expressions[0];
        rows = (Expression<Integer>) expressions[1];
        player = (Expression<Player>) expressions[2];
        return true;
    }
}
