package org.gbcraft.bang.commands;

import org.bukkit.command.CommandSender;
import org.gbcraft.bang.Bang;

public class ListCommand extends MFCommand{
    public ListCommand(Bang plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    @Override
    public boolean run() {
        if(sender.hasPermission("bang.base")) {
            sender.sendMessage("FuckList:\n" + FuckCommand.printContainers() + "\n");
            sender.sendMessage("MagicList:\n" + MagicCommand.printContainers() + "\n");
            sender.sendMessage("BlessList:\n" + BlessCommand.printContainers() + "\n");
            sender.sendMessage("SupajpList:\n" + SupajpCommand.printContainers());
        }
        else{
            plugin.sendMessage(sender, "info.permission.no");
        }
        return true;
    }
}
