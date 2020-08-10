package org.gbcraft.bang.commands;

import org.bukkit.command.CommandSender;
import org.gbcraft.bang.Bang;
import org.gbcraft.bang.commands.bean.CommandName;
import org.gbcraft.bang.commands.bean.ContainerManager;

public class ListCommand extends MFCommand {
    public ListCommand(Bang plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    @Override
    public boolean run() {
        sender.sendMessage("BanChatList:\n" + ContainerManager.printContainers(CommandName.BANCHAT));
        sender.sendMessage("=====================");
        sender.sendMessage("FuckList:\n" + ContainerManager.printContainers(CommandName.FUCK));
        sender.sendMessage("=====================");
        sender.sendMessage("MagicList:\n" + ContainerManager.printContainers(CommandName.MAGIC));
        sender.sendMessage("=====================");
        sender.sendMessage("SupajpList:\n" + ContainerManager.printContainers(CommandName.SUPAJP));
        sender.sendMessage("=====================");
        sender.sendMessage("FreezeList:\n" + ContainerManager.printContainers(CommandName.FREEZE));
        sender.sendMessage("=====================");
        sender.sendMessage("DeadList:\n" + ContainerManager.printContainers(CommandName.DEAD));

        return true;
    }
}
