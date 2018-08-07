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
 * @Since 2018-08-06 17:11
 */
@SkriptAddon(pattern = "[taboosk ](1¦compile|2¦decompile|) [java]script %string%[ (with|for) %string%]")
public class EffectScriptCompile extends Effect {

    private Expression<String> name;
    private Expression<String> script;
    private int mark;

    @Override
    protected void execute(Event e) {
        if (mark == 1) {
            ScriptHandler.getScripts().put(name.getSingle(e), ScriptHandler.compile(script.getSingle(e)));
        } else {
            ScriptHandler.getScripts().remove(name.getSingle(e));
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        mark = parseResult.mark;
        name = (Expression<String>) expressions[0];
        if (expressions.length > 1) {
            script = (Expression<String>) expressions[1];
        }
        return true;
    }
}
