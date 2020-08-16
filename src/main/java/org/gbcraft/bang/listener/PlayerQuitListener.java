package org.gbcraft.bang.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.gbcraft.bang.commands.bean.BanChatType;
import org.gbcraft.bang.commands.bean.CommandName;
import org.gbcraft.bang.commands.bean.CommandPlayer;
import org.gbcraft.bang.commands.bean.ContainerManager;
import org.gbcraft.bang.util.JoinQuitFrequencyContainer;

public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        CommandPlayer bcPlayer = ContainerManager.getCommandPlayer(CommandName.BANCHAT, event.getPlayer().getName());
        /*
        如果玩家在禁言列表中 且 玩家禁言标记为LOGS或ALL 将禁止显示相关系统信息
         */
        if (null != bcPlayer && (bcPlayer.isDesc(BanChatType.LOGS.type()) || bcPlayer.isDesc(BanChatType.ALL.type()))) {
            event.setQuitMessage("");
        }
        /*
        如果玩家不在禁言列表中 或 玩家没有LOGS有关的禁言标记 则应当进行频率统计
         */
        else {
            JoinQuitFrequencyContainer.append(event.getPlayer());
        }

    }
}
