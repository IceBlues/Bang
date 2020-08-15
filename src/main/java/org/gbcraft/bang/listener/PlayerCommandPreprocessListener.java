package org.gbcraft.bang.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.gbcraft.bang.commands.bean.CommandName;
import org.gbcraft.bang.commands.bean.ContainerManager;

public class PlayerCommandPreprocessListener implements Listener {
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage();
        if (message.length() > 1) {
            String[] s = message.substring(1).split(" ");
            String cmd = s[0];
            if (ContainerManager.isContain(CommandName.BANCHAT, event.getPlayer().getName()) && ContainerManager.getCommandPlayer(CommandName.BANCHAT, event.getPlayer().getName()).isDesc("banchat_enforce") && (cmd.equalsIgnoreCase("tell") || cmd.equalsIgnoreCase("m") || cmd.equalsIgnoreCase("r") || cmd.equalsIgnoreCase("msg") || cmd.equalsIgnoreCase("reply"))) {
                event.setCancelled(true);
            }
        }

    }
}
