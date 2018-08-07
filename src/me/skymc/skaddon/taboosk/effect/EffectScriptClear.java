package me.skymc.skaddon.taboosk.effect;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.skymc.skaddon.taboosk.annotations.SkriptAddon;
import me.skymc.skaddon.taboosk.handler.ScriptHandler;
import org.bukkit.event.Event;

/**
 * @Author sky
 * @Since 2018-8-6 20:38
 */
@SkriptAddon(pattern = "[taboosk ](clear|clean|delete) dynamic [java]script")
public class EffectScriptClear extends Effect {

    @Override
    protected void execute(Event e) {
        ScriptHandler.getScriptsDynamic().clear();
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
