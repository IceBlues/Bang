package org.gbcraft.bang.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.gbcraft.bang.Bang;

public class VersionCommand extends MFCommand {

    public VersionCommand(Bang plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    @Override
    public boolean run() {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4[Bang]") + plugin.getDescription().getVersion());
        return true;
    }
}
