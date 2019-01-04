package me.skymc.skaddon.taboosk.asm;

import me.skymc.skaddon.taboosk.TabooSK;
import me.skymc.taboolib.common.function.TFunction;
import me.skymc.taboolib.common.versioncontrol.SimpleVersionControl;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

import java.util.List;

/**
 * @Author 坏黑
 * @Since 2019-01-03 23:47
 */
@TFunction
public abstract class AsmHandler {

    private static AsmHandler impl;

    public static AsmHandler getImpl() {
        return impl;
    }

    static void onEnable() {
        try {
            impl = (AsmHandler) SimpleVersionControl.createNMS("me.skymc.skaddon.taboosk.asm.AsmHandlerImpl").useCache().translate(TabooSK.getInst()).newInstance();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    abstract public List<Entity> getEntities(CommandSender sender, String variable);

}
