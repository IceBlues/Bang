package org.gbcraft.bang.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.gbcraft.bang.commands.bean.CommandName;
import org.gbcraft.bang.commands.bean.CommandPlayer;
import org.gbcraft.bang.commands.bean.ContainerManager;

public class PlayerCommandPreprocessListener implements Listener {
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage();
        if (message.length() > 1) {
            CommandPlayer player = ContainerManager.getCommandPlayer(CommandName.BANCHAT, event.getPlayer().getName());
            //TODO 判定是私聊信息
            if (null != player && isMsgCommand(message)) {
                event.setCancelled(true);
            }
        }

    }

    private boolean isMsgCommand(String msg) {
        boolean res = false;
        String command = msg.split(" ")[0].substring(1);
        switch (command) {
            case "tell":
            case "msg":
            case "me":
            case "m":
            case "reply":
            case "r":
                res = true;
                break;
        }
        return res;
    }
}
