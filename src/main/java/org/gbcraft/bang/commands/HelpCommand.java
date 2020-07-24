package org.gbcraft.bang.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.gbcraft.bang.Bang;

public class HelpCommand extends MFCommand {
    public HelpCommand(Bang plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    @Override
    public boolean run() {
        plugin.getConfig().getStringList("helpCommand")
                .forEach(str -> sender.sendMessage(ChatColor.translateAlternateColorCodes('&', str)));
        return true;
    }
}
