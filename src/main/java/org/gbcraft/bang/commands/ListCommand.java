package org.gbcraft.bang.commands;

import org.bukkit.command.CommandSender;
import org.gbcraft.bang.Bang;

public class ListCommand extends MFCommand {
    public ListCommand(Bang plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    @Override
    public boolean run() {
        sender.sendMessage("BanChatList:\n" + BanchatCommand.printContainers());
        sender.sendMessage("=====================");
        sender.sendMessage("FuckList:\n" + FuckCommand.printContainers());
        sender.sendMessage("=====================");
        sender.sendMessage("MagicList:\n" + MagicCommand.printContainers());
        sender.sendMessage("=====================");
        sender.sendMessage("SupajpList:\n" + SupajpCommand.printContainers());
        sender.sendMessage("=====================");
        sender.sendMessage("FreezeList:\n" + FreezeCommand.printContainers());
        sender.sendMessage("=====================");
        sender.sendMessage("DeadList:\n" + DeadCommand.printContainers());

        return true;
    }
}
