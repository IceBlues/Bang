package org.gbcraft.bang.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.gbcraft.bang.commands.bean.BanChatType;
import org.gbcraft.bang.commands.bean.CommandName;
import org.gbcraft.bang.commands.bean.CommandPlayer;
import org.gbcraft.bang.commands.bean.ContainerManager;
import org.gbcraft.bang.util.ChatFrequencyContainer;

public class PlayerChatListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        CommandPlayer player = ContainerManager.getCommandPlayer(CommandName.BANCHAT, event.getPlayer().getName());
        if (null != player) {
            if (player.isDesc(BanChatType.MESSAGE.type()) || player.isDesc(BanChatType.ALL.type())) {
                event.setCancelled(true);
            }
            else if (player.isDesc(BanChatType.REPLACE.type())) {
                event.setMessage("***");
            }
            /*
            如果没有禁止或修改发言相关标记 则应该进行发言频率统计
             */
            else {
                ChatFrequencyContainer.append(event.getPlayer());
            }

        }
        /*
        如果玩家未被执行禁言封禁 则应该进行发言频率统计
         */
        else {
            ChatFrequencyContainer.append(event.getPlayer());
        }
    }
}
