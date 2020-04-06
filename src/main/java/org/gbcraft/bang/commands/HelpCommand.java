package org.gbcraft.bang.commands;

import org.bukkit.command.CommandSender;
import org.gbcraft.bang.Bang;

public class HelpCommand extends MFCommand {
    public HelpCommand(Bang plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    @Override
    public boolean run() {
        if (sender.hasPermission("bang.base")) {
            sender.sendMessage(plugin.getConfig().getStringList("helpCommand").toString());
            plugin.getConfig().getStringList("helpCommand")
                    .forEach(str -> sender.sendMessage(str));
        } else {
            sender.sendMessage("no-permission");
        }
        return true;
    }
}
