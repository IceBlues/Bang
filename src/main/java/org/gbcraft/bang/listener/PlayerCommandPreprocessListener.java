package org.gbcraft.bang.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.gbcraft.bang.commands.bean.BanChatType;
import org.gbcraft.bang.commands.bean.CommandName;
import org.gbcraft.bang.commands.bean.CommandPlayer;
import org.gbcraft.bang.commands.bean.ContainerManager;
import org.gbcraft.bang.util.ChatFrequencyContainer;

public class PlayerCommandPreprocessListener implements Listener {
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage();
        if (message.length() > 1) {
            String[] s = message.substring(1).split(" ");
            String cmd = s[0];

            boolean isMessage = (cmd.equalsIgnoreCase("tell") || cmd.equalsIgnoreCase("m") || cmd.equalsIgnoreCase("r") || cmd.equalsIgnoreCase("msg") || cmd.equalsIgnoreCase("reply"));

            CommandPlayer player = ContainerManager.getCommandPlayer(CommandName.BANCHAT, event.getPlayer().getName());
            if (null != player && (player.isDesc(BanChatType.MESSAGE.type()) || player.isDesc(BanChatType.ALL.type()))) {
                event.setCancelled(true);
            }
            else if (isMessage) {
                ChatFrequencyContainer.append(event.getPlayer());
            }
        }

    }
}
