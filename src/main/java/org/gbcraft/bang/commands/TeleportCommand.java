package org.gbcraft.bang.commands;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.gbcraft.bang.Bang;

public class TeleportCommand extends MFCommand {
    public TeleportCommand(Bang plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    @Override
    public boolean run() {
        if (args.length >= 2) {
            if (args.length == 2 && sender instanceof Player) {
                Player to = Bukkit.getPlayer(args[1]);
                if (null != to) {
                    ((Player) sender).teleport(to.getLocation());
                }
                else {
                    plugin.sendMessage(sender, "info.player.no-found");
                }
            }
            else {
                Player from = Bukkit.getPlayer(args[1]);
                Player to = Bukkit.getPlayer(args[2]);
                if (null != from && null != to) {
                    from.teleport(to.getLocation());
                }
                else {
                    plugin.sendMessage(sender, "info.player.no-found");
                }
            }
        }
        else {
            plugin.sendMessage(sender, "info.command.teleport");
        }

        return true;
    }
}
