package org.gbcraft.bang.particle;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.gbcraft.bang.Bang;

public class StarRingEffect extends Effect {
    @Override
    public void doIt(Player player) {
        task = new BukkitRunnable() {
            double t = Math.PI / 4;
            final Location loc = player.getLocation().clone();

            @Override
            public void run() {
                if (null == particle) {
                    particle = new ParticleManager(player.getUniqueId(), EffectType.STAR_RING, task);
                }

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
                    particle.remove();
                }
            }
        }.runTaskTimer(Bang.getPlugin(Bang.class), 0, 2);
    }
}
