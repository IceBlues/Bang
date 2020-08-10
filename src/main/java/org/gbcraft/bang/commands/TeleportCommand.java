package org.gbcraft.bang.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.gbcraft.bang.Bang;

public class TeleportCommand extends MFCommand {
    public TeleportCommand(Bang plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    @Override
    public boolean run() {
        if (args.length == 2) {
            if (!(sender instanceof Player)) {
                plugin.sendMessage(sender, "info.command.teleport");
            }
            else {
                String[] loc = args[1].split(",");
                if (loc.length == 3) {
                    try {
                        double x = Double.parseDouble(loc[0]);
                        double z = Double.parseDouble(loc[1]);
                        double y = Double.parseDouble(loc[2]);
                        ((Player) sender).teleport(new Location(((Player) sender).getWorld(), x, y, z));
                    }
                    catch (Exception E) {
                        plugin.sendMessage(sender, "info.command.teleport");
                    }

                }
                else {
                    Player to = Bukkit.getPlayer(args[1]);
                    if (null != to) {
                        ((Player) sender).teleport(to.getLocation());
                    }
                    else {
                        plugin.sendMessage(sender, "info.player.no-found");
                    }
                }
            }
        }
        else if (args.length > 2) {
            Player from = Bukkit.getPlayer(args[1]);
            String[] toLoc = args[2].split(",");
            if (null != from) {
                if (toLoc.length == 3) {
                    try {
                        double x = Double.parseDouble(toLoc[0]);
                        double z = Double.parseDouble(toLoc[1]);
                        double y = Double.parseDouble(toLoc[2]);
                        from.teleport(new Location(from.getWorld(), x, y, z));
                    }
                    catch (Exception E) {
                        plugin.sendMessage(sender, "info.command.teleport");
                    }

                }
                else {
                    Player to = Bukkit.getPlayer(args[2]);
                    if (null != to) {
                        from.teleport(to.getLocation());
                    }
                    else {
                        plugin.sendMessage(sender, "info.player.no-found");
                    }
                }
            }
            else {
                plugin.sendMessage(sender, "info.player.no-found");
            }

        }

        return true;
    }
}
