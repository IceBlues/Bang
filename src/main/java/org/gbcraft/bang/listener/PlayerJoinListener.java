package org.gbcraft.bang.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.gbcraft.bang.commands.bean.BanChatType;
import org.gbcraft.bang.commands.bean.CommandName;
import org.gbcraft.bang.commands.bean.CommandPlayer;
import org.gbcraft.bang.commands.bean.ContainerManager;
import org.gbcraft.bang.config.OfflinePlayersConfig;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        // player first join, append to offline player map
        if (!OfflinePlayersConfig.isContain(player.getName())) {
            OfflinePlayersConfig.append(player.getName(), player);
        }

        /*Player has been moved but have buff*/
        if (!ContainerManager.isContain(CommandName.MAGIC, player.getName()) && player.hasPotionEffect(PotionEffectType.UNLUCK)) {
            for (PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());
            }
        }

        if (!ContainerManager.isContain(CommandName.SUPAJP, player.getName()) && player.getWalkSpeed() != 0.6f) {
            player.setAllowFlight(false);
            player.setWalkSpeed(0.2f);
            player.setFlySpeed(0.1f);
            player.removePotionEffect(PotionEffectType.SATURATION);
        }

        CommandPlayer bcPlayer = ContainerManager.getCommandPlayer(CommandName.BANCHAT, player.getName());
        if (null != bcPlayer && (bcPlayer.isDesc(BanChatType.LOGS.type()) || bcPlayer.isDesc(BanChatType.ALL.type()))) {
            event.setJoinMessage("");
        }
    }

}
