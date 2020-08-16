package org.gbcraft.bang.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.gbcraft.bang.Bang;
import org.gbcraft.bang.commands.bean.BanChatType;
import org.gbcraft.bang.commands.bean.ContainerManager;
import org.gbcraft.bang.config.OfflinePlayersConfig;

public class BanchatCommand extends MFCommand {
    public BanchatCommand(Bang plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    @Override
    public boolean run() {
        if (args.length >= 2) {
            OfflinePlayer player = OfflinePlayersConfig.get(args[1]);
            BanChatType desc = BanChatType.MESSAGE;
            if (args.length > 2) {
                BanChatType type = BanChatType.fromSubCommand(args[2]);
                if (null != type) {
                    desc = type;
                }
            }
            ContainerManager.prePut(this.getClass(), sender, player, desc.type());

        }
        else {
            plugin.sendMessage(sender, "info.command.banchat");
        }
        return true;
    }


}
