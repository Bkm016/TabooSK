package me.skymc.skaddon.taboosk.effect;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.skymc.skaddon.taboosk.annotations.SkriptAddon;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Arrays;

/**
 * @Author sky
 * @Since 2018-08-07 17:14
 */
@SkriptAddon(pattern = "[taboosk ]set %livingentities% leash[ed] (with|for|to|at) %-object%")
public class EffectEntityLeash extends Effect {

    private Expression<LivingEntity> entity;
    private Expression<?> target;

    @Override
    protected void execute(Event event) {
        Object target = this.target.getSingle(event);
        Entity hitch;
        if (target instanceof Location) {
            if (!((Location) target).getBlock().getType().name().contains("FENCE")) {
                Skript.error("Invalid leash target. (not fence)");
                return;
            }
            hitch = (((Location) target).getWorld().spawnEntity(((Location) target), EntityType.LEASH_HITCH));
        } else if (target instanceof Block) {
            if (!((Block) target).getType().name().contains("FENCE")) {
                Skript.error("Invalid leash target. (not fence)");
                return;
            }
            hitch = (((Block) target).getWorld().spawnEntity(((Block) target).getLocation(), EntityType.LEASH_HITCH));
        } else if (target instanceof Player) {
            hitch = (Entity) target;
        } else {
            Skript.error("Invalid leash target. (not entity or block)");
            return;
        }
        Arrays.stream(entity.getArray(event)).forEach(entity -> entity.setLeashHolder(hitch));
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        entity = (Expression<LivingEntity>) expressions[0];
        target = expressions[1];
        return true;
    }
}
