package me.skymc.skaddon.taboosk.event.effect;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.skymc.skaddon.taboosk.yaml.CacheYaml;
import me.skymc.taboolib.playerdata.DataUtils;
import org.bukkit.event.Event;

import java.io.File;
import java.util.Optional;

/**
 * @Author sky
 * @Since 2018-07-18 21:06
 */
public class EffectYamlSave extends Effect {

    private Expression<String> path;

    @Override
    protected void execute(Event event) {
        String filePath = path.getSingle(event).replace("/'", File.separator);
        Optional.ofNullable(CacheYaml.CACHE_YAML.get(filePath)).ifPresent(x -> DataUtils.saveConfiguration(x.getConf(), x.getFile()));
    }

    @Override
    public String toString(Event event, boolean b) {
        return this.getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        path = (Expression<String>) expressions[0];
        return true;
    }
}
