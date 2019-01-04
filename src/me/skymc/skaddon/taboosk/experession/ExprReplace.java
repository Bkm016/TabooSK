package me.skymc.skaddon.taboosk.experession;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.skymc.skaddon.taboosk.annotations.SkriptAddon;
import org.bukkit.event.Event;

import java.util.regex.Pattern;

/**
 * @Author sky
 * @Since 2018-08-09 15:50
 */
@SkriptAddon(pattern = "[taboosk ](1¦replace|2¦replaceall) %-objects% to %-objects% (with|for|in) %-object%")
public class ExprReplace extends SimpleExpression<String> {

    private Expression<?> regex;
    private Expression<?> replace;
    private Expression<?> str;
    private int state;

    @Override
    protected String[] get(Event event) {
        String str = this.str.toString();
        Object[] regex = this.regex.getArray(event);
        Object[] replace = this.replace.getArray(event);
        try {
            for (int i = 0; i < regex.length; i++) {
                str = state == 1 ? str.replace(regex[i].toString(), replace[i].toString()) : str.replaceAll(regex[i].toString(), replace[i].toString());
            }
        } catch (Exception ignored) {
        }
        return CollectionUtils.array(str);
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return get(event)[0];
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        state = parseResult.mark;
        regex = expressions[0];
        replace = expressions[1];
        str = expressions[2];
        return true;
    }
}
