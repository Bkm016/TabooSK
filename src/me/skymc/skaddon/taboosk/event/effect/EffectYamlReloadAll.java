package me.skymc.skaddon.taboosk.event.effect;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.skymc.skaddon.taboosk.yaml.CacheYaml;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;

/**
 * @Author sky
 * @Since 2018-07-18 21:06
 */
public class EffectYamlReloadAll extends Effect {

    @Override
    protected void execute(Event event) {
        CacheYaml.CACHE_YAML.forEach((key, value) -> value.setConf(YamlConfiguration.loadConfiguration(value.getFile())));
    }

    @Override
    public String toString(Event event, boolean b) {
        return this.getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
