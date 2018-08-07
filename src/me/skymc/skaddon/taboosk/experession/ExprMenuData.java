package me.skymc.skaddon.taboosk.experession;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.skymc.skaddon.taboosk.annotations.SkriptAddon;
import me.skymc.skaddon.taboosk.menu.DataMenuHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.HashMap;

/**
 * @Author sky
 * @Since 2018-07-29 22:34
 */
@SkriptAddon(pattern = "[taboosk ]%string% (with|for|of|in) %player%['s] data menu[ default %-object%]")
public class ExprMenuData extends SimpleExpression<Object> {

    private Expression<String> node;
    private Expression<Player> player;
    private Expression<Object> def;

    @Override
    protected Object[] get(Event e) {
        if (player.getSingle(e).getOpenInventory().getTopInventory().getHolder() instanceof DataMenuHolder) {
            return CollectionUtils.array(((DataMenuHolder) player.getSingle(e).getOpenInventory().getTopInventory().getHolder()).getMenuData().getOrDefault(this.node.getSingle(e), def == null ? null : def.getSingle(e)));
        } else {
            return CollectionUtils.array(def.getSingle(e));
        }
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        if (!(player.getSingle(e).getOpenInventory().getTopInventory().getHolder() instanceof DataMenuHolder)) {
            return;
        }
        HashMap<String, Object> menuData = ((DataMenuHolder) player.getSingle(e).getOpenInventory().getTopInventory().getHolder()).getMenuData();
        switch (mode) {
            case SET:
                menuData.put(node.getSingle(e), delta[0]);
                break;
            case RESET:
                menuData.put(node.getSingle(e), null);
                break;
            case REMOVE:
            case DELETE:
                menuData.remove(node.getSingle(e));
                break;
            case REMOVE_ALL:
                menuData.clear();
                break;
            default:
        }
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return mode == Changer.ChangeMode.ADD ? null : CollectionUtils.array(Object.class);
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
        return this.getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] e, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.node = (Expression<String>) e[0];
        this.player = (Expression<Player>) e[1];
        this.def = e.length > 2 ? (Expression<Object>) e[2] : null;
        return true;
    }
}
