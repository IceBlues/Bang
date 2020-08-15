package org.gbcraft.bang.listener;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.gbcraft.bang.Bang;
import org.gbcraft.bang.commands.bean.CommandName;
import org.gbcraft.bang.commands.bean.ContainerManager;

public class EntityDeathListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (ContainerManager.isContain(CommandName.DEAD, player.getName())) {
                event.getDrops().clear();
                new BukkitRunnable() {
                    double t = Math.PI / 4;
                    final Location loc = player.getLocation().clone();

                    @Override
                    public void run() {
                        t = t + 0.1 * Math.PI;
                        for (double theta = 0; theta <= 2 * Math.PI; theta = theta + Math.PI / 32) {
                            double x = t * Math.cos(theta);
                            double y = 2 * Math.exp(-0.1 * t) * Math.sin(t) + 1.5;
                            double z = t * Math.sin(theta);
                            loc.add(x, y, z);
                            player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc, 1);
                            loc.subtract(x, y, z);
                        }
                        if (t > 10) {
                            this.cancel();
                        }
                    }
                }.runTaskTimer(Bang.getPlugin(Bang.class), 0, 2);
            }
        }
    }
}
