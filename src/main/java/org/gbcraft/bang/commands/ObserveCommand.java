package org.gbcraft.bang.commands;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.gbcraft.bang.Bang;

public class ObserveCommand extends MFCommand {
    public ObserveCommand(Bang plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    @Override
    public boolean run() {
        if (sender instanceof Player && sender.hasPermission("bang.observe")) {
            Player p = (Player) sender;
            if (p.getGameMode() == GameMode.SURVIVAL) {
                p.setGameMode(GameMode.SPECTATOR);
                plugin.sendMessage(sender, "info.observe.spectator");
            }
            else if (p.getGameMode() == GameMode.SPECTATOR) {
                p.setGameMode(GameMode.SURVIVAL);
                plugin.sendMessage(sender, "info.observe.survival");
            }
            else{
                plugin.sendMessage(sender, "info.observe.wrong");
            }
        }
        else {
            plugin.sendMessage(sender, "info.permission.no");
        }
        return true;
    }
}
