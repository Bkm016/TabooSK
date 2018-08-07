package me.skymc.skaddon.taboosk.experession;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.skymc.skaddon.taboosk.annotations.SkriptAddon;
import me.skymc.skaddon.taboosk.handler.ScriptHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import javax.script.CompiledScript;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

/**
 * @Author sky
 * @Since 2018-08-06 20:12
 */
@SkriptAddon(pattern = "[taboosk ](1¦eval|2¦execute) [java]script %string%[ (with|for) %-objects%]")
public class ExprScriptEval extends SimpleExpression<Object> {

    private Expression<String> script;
    private Expression<Object> obj;
    private int state;

    @Override
    protected Object[] get(Event event) {
        CompiledScript script;
        if (state == 1) {
            script = ScriptHandler.getScripts().get(this.script.getSingle(event));
        } else {
            script = ScriptHandler.getScriptsDynamic().computeIfAbsent(this.script.getSingle(event), ScriptHandler::compile);
        }
        SimpleBindings bindings = new SimpleBindings();
        bindings.put("event", event);
        if (obj != null) {
            bindings.put("args", obj.getArray(event));
        }
        try {
            return CollectionUtils.array(script.eval(bindings));
        } catch (ScriptException e) {
            Bukkit.getConsoleSender().sendMessage("§8[§3§lTabooSK§8] §4JavaScript §c" + this.script.getSingle(event) + "§4 Eval Failed: §c" + e.toString());
            return null;
        }
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<?> getReturnType() {
        return Object.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        state = parseResult.mark;
        script = (Expression<String>) expressions[0];
        if (expressions.length > 1) {
            obj = (Expression<Object>) expressions[1];
        }
        return true;
    }
}
