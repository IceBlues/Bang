package org.gbcraft.bang.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.gbcraft.bang.commands.bean.CommandName;
import org.gbcraft.bang.commands.bean.ContainerManager;
import org.gbcraft.bang.particle.StarRingEffect;

public class EntityDeathListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (ContainerManager.isContain(CommandName.DEAD, player.getName())) {
                event.getDrops().clear();
                new StarRingEffect().doIt(player);
            }
        }
    }
}
