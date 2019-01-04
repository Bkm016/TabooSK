package me.skymc.skaddon.taboosk.asm;

import com.google.common.collect.Lists;
import net.minecraft.server.v1_11_R1.CommandException;
import net.minecraft.server.v1_11_R1.PlayerSelector;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_11_R1.command.CraftBlockCommandSender;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author 坏黑
 * @Since 2019-01-03 23:49
 */
public class AsmHandlerImpl extends AsmHandler{

    @Override
    public List<Entity> getEntities(CommandSender sender, String variable) {
        if (sender instanceof CraftPlayer) {
            try {
                return PlayerSelector.getPlayers(((CraftPlayer) sender).getHandle(), variable, net.minecraft.server.v1_11_R1.Entity.class).stream().map(net.minecraft.server.v1_11_R1.Entity::getBukkitEntity).collect(Collectors.toList());
            } catch (CommandException e) {
                e.printStackTrace();
            }
        } else if (sender instanceof CraftBlockCommandSender) {
            try {
                return PlayerSelector.getPlayers(((CraftBlockCommandSender) sender).getTileEntity(), variable, net.minecraft.server.v1_11_R1.Entity.class).stream().map(net.minecraft.server.v1_11_R1.Entity::getBukkitEntity).collect(Collectors.toList());
            } catch (CommandException e) {
                e.printStackTrace();
            }
        }
        return Lists.newArrayList();
    }
}
