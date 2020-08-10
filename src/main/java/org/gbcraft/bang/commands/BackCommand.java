package org.gbcraft.bang.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.gbcraft.bang.Bang;
import org.gbcraft.bang.commands.bean.CommandName;
import org.gbcraft.bang.commands.bean.ContainerManager;

public class BackCommand extends MFCommand {
    public BackCommand(Bang plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    @Override
    public boolean run() {
        if (args.length >= 3) {
            String node;
            if (args[1].equalsIgnoreCase(CommandName.FUCK.name())) {
                node = ContainerManager.remove(CommandName.FUCK, args[2]);
            }
            else if (args[1].equalsIgnoreCase(CommandName.MAGIC.name())) {
                Player p = Bukkit.getPlayer(args[2]);
                if (null != p) {
                    for (PotionEffect effect : p.getActivePotionEffects()) {
                        p.removePotionEffect(effect.getType());
                    }
                }
                node = ContainerManager.remove(CommandName.MAGIC, args[2]);
            }
            else if (args[1].equalsIgnoreCase(CommandName.SUPAJP.name())) {
                Player p = Bukkit.getPlayer(args[2]);
                if (null != p) {
                    p.setAllowFlight(false);
                    p.setWalkSpeed(0.2f);
                    p.setFlySpeed(0.1f);
                    p.removePotionEffect(PotionEffectType.SATURATION);
                }
                node = ContainerManager.remove(CommandName.SUPAJP, args[2]);
            }
            else if (args[1].equalsIgnoreCase(CommandName.FREEZE.name())) {
                node = ContainerManager.remove(CommandName.FREEZE, args[2]);
            }
            else if (args[1].equalsIgnoreCase(CommandName.DEAD.name())) {
                node = ContainerManager.remove(CommandName.DEAD, args[2]);
            }
            else if (args[1].equalsIgnoreCase(CommandName.BANCHAT.name())) {
                node = ContainerManager.remove(CommandName.BANCHAT, args[2]);
            }
            else if (args[1].equalsIgnoreCase("all")) {
                ContainerManager.removeAll(args[2]);
                node = "info.remove.success";
            }
            else {
                node = "info.command.back";
            }

            plugin.sendMessage(sender, node);
        }
        else {
            plugin.sendMessage(sender, "info.command.back");
        }

        return true;
    }
}
