package me.skymc.skaddon.taboosk.effect;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.skymc.skaddon.taboosk.annotations.SkriptAddon;
import me.skymc.taboolib.scoreboard.ScoreboardUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@SkriptAddon(pattern = "[taboosk ]set %player%['s] scoreboard to %strings%")
public class EffectScoreboardPush extends Effect {

    private Expression<Player> player;
    private Expression<String> scoreboard;

    @Override
    protected void execute(Event event) {
        ScoreboardUtil.unrankedSidebarDisplay(this.player.getSingle(event), (String[]) this.scoreboard.getArray(event));
    }

    @Override
    public String toString(Event event, boolean b) {
        return this.getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.player = (Expression<Player>) expressions[0];
        this.scoreboard = (Expression<String>) expressions[1];
        return true;
    }
}
