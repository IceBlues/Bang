package org.gbcraft.bang.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.gbcraft.bang.Bang;
import org.gbcraft.bang.commands.bean.ContainerManager;
import org.gbcraft.bang.config.OfflinePlayersConfig;

public class FreezeCommand extends MFCommand {
    public FreezeCommand(Bang plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    @Override
    public boolean run() {
        if (args.length >= 2) {
            OfflinePlayer player = OfflinePlayersConfig.get(args[1]);
            ContainerManager.prePut(this.getClass(), sender, player);
        }
        else {
            plugin.sendMessage(sender, "info.command.freeze");
        }
        return true;
    }

}
