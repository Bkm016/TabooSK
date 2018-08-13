package me.skymc.skaddon.taboosk.effect;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.skymc.skaddon.taboosk.TabooSK;
import me.skymc.skaddon.taboosk.annotations.SkriptAddon;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

/**
 * @Author sky
 * @Since 2018-08-07 16:02
 */
@SkriptAddon(pattern = "[taboosk ](execute|sync)[:] %teffect%[ delay %number%]")
public class EffectExecute extends Effect {

    private Expression<Effect> effect;
    private Expression<Number> delay;

    @Override
    protected void execute(Event event) {
        if (delay == null) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(TabooSK.getInst(), () -> effect.getSingle(event).run(event));
        } else {
            Bukkit.getScheduler().scheduleSyncDelayedTask(TabooSK.getInst(), () -> effect.getSingle(event).run(event), delay.getSingle(event).intValue());
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        effect = (Expression<Effect>) expressions[0];
        if (expressions.length > 1) {
            delay = (Expression<Number>) expressions[1];
        }
        return true;
    }
}
