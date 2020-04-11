package org.gbcraft.bang.commands;

import org.bukkit.command.CommandSender;
import org.gbcraft.bang.Bang;

public class ReloadCommand extends MFCommand {
    public ReloadCommand(Bang plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    @Override
    public boolean run() {
        if(sender.hasPermission("bang.base")){
            plugin.saveDefaultConfig();
            plugin.reloadConfig();
        }

        return true;
    }
}
