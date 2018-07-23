package me.skymc.skaddon.taboosk.event.effect;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Objective;

public class EffectScoreboardEdit extends Effect {

    private Expression<Player> player;
    private Expression<String> scoreboard;
    private Expression<Integer> score;

    @Override
    protected void execute(final Event event) {
        int score = this.score.getSingle(event);
        Player player = this.player.getSingle(event);
        Objective objective = player.getScoreboard().getObjective(player.getUniqueId().toString().substring(0, 16));
        for (String entry : player.getScoreboard().getEntries()) {
            if (objective.getScore(entry).getScore() == score) {
                player.getScoreboard().resetScores(entry);
                objective.getScore(scoreboard.getSingle(event)).setScore(score);
                return;
            }
        }
    }

    @Override
    public String toString(final Event event, final boolean b) {
        return this.getClass().getName();
    }

    @Override
    public boolean init(final Expression<?>[] expressions, final int i, final Kleenean kleenean, final SkriptParser.ParseResult parseResult) {
        this.score = (Expression<Integer>) expressions[0];
        this.player = (Expression<Player>) expressions[1];
        this.scoreboard = (Expression<String>) expressions[2];
        return true;
    }
}
