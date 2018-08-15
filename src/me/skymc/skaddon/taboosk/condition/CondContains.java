package me.skymc.skaddon.taboosk.condition;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.skymc.skaddon.taboosk.annotations.SkriptAddon;
import org.bukkit.event.Event;

import java.util.Arrays;

/**
 * @Author sky
 * @Since 2018-08-13 13:34
 */
@SkriptAddon(pattern = "[taboosk ]%-objects% contains of %-object%")
public class CondContains extends Condition {

    private Expression<?> array;
    private Expression<?> obj;

    @Override
    public boolean check(Event event) {
        return Arrays.asList(array.getArray(event)).contains(obj.getSingle(event));
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.array = expressions[0];
        this.obj = expressions[1];
        return true;
    }
}
