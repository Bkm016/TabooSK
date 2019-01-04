package me.skymc.skaddon.taboosk.experession.v2;

import ch.njol.skript.command.CommandEvent;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.skymc.skaddon.taboosk.annotations.SkriptAddon;
import me.skymc.skaddon.taboosk.asm.AsmHandler;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerEvent;

/**
 * @Author 坏黑
 * @Since 2019-01-03 21:56
 */
@SkriptAddon(pattern = "[taboosk ][command ](1¦target|2¦targets) %string%")
public class ConditionArgument extends SimpleExpression<Entity> {

    private Expression<String> variable;
    private int stats;

    @Override
    protected Entity[] get(Event event) {
        String v = variable.getSingle(event);
        if (event instanceof CommandEvent) {
            return AsmHandler.getImpl().getEntities(((CommandEvent) event).getSender(), v.startsWith("@") ? v : "@" + v).toArray(new Entity[0]);
        }
        if (event instanceof PlayerEvent) {
            return AsmHandler.getImpl().getEntities(((PlayerEvent) event).getPlayer(), v.startsWith("@") ? v : "@" + v).toArray(new Entity[0]);
        }
        return new Entity[0];
    }

    @Override
    public boolean isSingle() {
        return stats == 1;
    }

    @Override
    public Class<? extends Entity> getReturnType() {
        return Entity.class;
    }

    @Override
    public String toString(final Event arg0, final boolean arg1) {
        return this.getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.variable = (Expression<String>) expressions[0];
        this.stats = parseResult.mark;
        return true;
    }
}
