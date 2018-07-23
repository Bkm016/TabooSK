package me.skymc.skaddon.taboosk.event.effect;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.skymc.taboolib.scoreboard.ScoreboardUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class EffectScoreboardReset extends Effect {
    private Expression<Player> player;

    @Override
    protected void execute(final Event event) {
        ScoreboardUtil.unrankedSidebarDisplay(this.player.getSingle(event));
    }

    @Override
    public String toString(final Event event, final boolean b) {
        return this.getClass().getName();
    }

    @Override
    public boolean init(final Expression<?>[] expressions, final int i, final Kleenean kleenean, final SkriptParser.ParseResult parseResult) {
        this.player = (Expression<Player>) expressions[0];
        return true;
    }
}
