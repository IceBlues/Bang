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

    // /bang teleport(0) playerA|x(1) playerB|y|x(2) |z|y z(3)
    @Override
    public boolean run() {
        // /bang teleport(0) playerA(1)
        if (args.length == 2) {
            // tp到某人

            // 控制台并不能移动=-=
            if (!(sender instanceof Player)) {
                plugin.sendMessage(sender, "info.command.teleport");
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
        // bang teleport(0) playerA(1)|x playerB(2)|y|x z|y(3) z(4)
        else if (args.length > 2) {
            // args由多到少
            if (args.length > 4) {
                Player from = Bukkit.getPlayer(args[1]);
                if (null != from) {
                    try {
                        double x = Double.parseDouble(args[2]);
                        double y = Double.parseDouble(args[3]);
                        double z = Double.parseDouble(args[4]);
                        from.teleport(new Location(from.getWorld(), x, y, z));
                    }
                    catch (Exception E) {
                        plugin.sendMessage(sender, "info.command.teleport");
                    }
                }
                else {
                    String reg = "^[0-9]+(.[0-9]+)??$";
                    if (args[1].matches(reg) && args[2].matches(reg) && args[3].matches(reg)) {
                        plugin.sendMessage(sender, "info.command.teleport");
                    }
                    else {
                        plugin.sendMessage(sender, "info.player.no-found");
                    }

                }
            }
            else if (args.length > 3) {
                if (sender instanceof Player) {
                    Player from = (Player) sender;
                    try {
                        double x = Double.parseDouble(args[1]);
                        double y = Double.parseDouble(args[2]);
                        double z = Double.parseDouble(args[3]);
                        from.teleport(new Location(from.getWorld(), x, y, z));
                    }
                    catch (Exception E) {
                        plugin.sendMessage(sender, "info.command.teleport");
                    }
                }
                else {
                    plugin.sendMessage(sender, "info.command.teleport");
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
