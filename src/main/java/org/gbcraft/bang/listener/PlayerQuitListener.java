package org.gbcraft.bang.listener;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.gbcraft.bang.particle.ParticleManager;

public class PlayerQuitListener implements Listener {
    public void onPlayerQuit(PlayerQuitEvent event) {
        ParticleManager.removeAll(event.getPlayer().getUniqueId());
    }
}
