package me.skymc.skaddon.taboosk.effect;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.skymc.skaddon.taboosk.annotations.SkriptAddon;
import me.skymc.taboolib.sound.SoundPack;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

/**
 * @Author sky
 * @Since 2018-08-07 16:10
 */

@SkriptAddon(pattern = "[taboosk ][play ]sound %string% (with|for|in|to|at) %-object%")
public class EffectSound extends Effect {

    private Expression<String> sound;
    private Expression<?> target;

    @Override
    protected void execute(Event event) {
        SoundPack soundPack = new SoundPack(sound.getSingle(event));
        Object target = this.target.getSingle(event);
        if (target instanceof Player) {
            soundPack.play((Player) target);
        } else if (target instanceof Location) {
            ((Location) target).getWorld().playSound((Location) target, soundPack.getSound(), soundPack.getA(), soundPack.getB());
        } else {
            Skript.error("Invalid sound target.");
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        sound = (Expression<String>) expressions[0];
        target = expressions[1];
        return true;
    }
}
