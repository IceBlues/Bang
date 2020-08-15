package org.gbcraft.bang.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.gbcraft.bang.commands.bean.CommandName;
import org.gbcraft.bang.commands.bean.CommandPlayer;
import org.gbcraft.bang.commands.bean.ContainerManager;

public class PlayerChatListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        CommandPlayer player = ContainerManager.getCommandPlayer(CommandName.BANCHAT, event.getPlayer().getName());
        if (null != player) {
            if (player.isDesc("banchat_enforce")) {
                event.setCancelled(true);
            }
            else {
                event.setMessage("***");
            }

        }
    }
}
