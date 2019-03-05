package me.skymc.skaddon.taboosk.experession;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.skymc.skaddon.taboosk.annotations.SkriptAddon;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.util.NumberConversions;

/**
 * @Author sky
 * @Since 2018-08-07 15:56
 */
@SkriptAddon(pattern = "[taboosk ]%-object% (to|as) (player)")
public class ExprToPlayer extends SimpleExpression<Player> {

    private Expression<?> obj;

    @Override
    protected Player[] get(Event event) {
        Object single = obj.getSingle(event);
        if (single instanceof Entity) {
            return CollectionUtils.array(Bukkit.getPlayerExact(((Entity) single).getName()));
        } else if (single instanceof CommandSender) {
            return CollectionUtils.array(Bukkit.getPlayerExact(((CommandSender) single).getName()));
        } else {
            return CollectionUtils.array(Bukkit.getPlayerExact(single.toString()));
        }
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Player> getReturnType() {
        return Player.class;
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
