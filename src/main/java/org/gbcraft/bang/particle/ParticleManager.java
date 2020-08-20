package org.gbcraft.bang.particle;

import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ParticleManager {
    private static final Map<UUID, Map<EffectType, BukkitTask>> particles = new HashMap<>();

    private final UUID uuid;
    private final EffectType type;
    private final BukkitTask task;

    private boolean isEffect() {
        boolean res = false;
        Map<EffectType, BukkitTask> map = particles.get(uuid);
        if (null != map) {
            res = map.containsKey(type);
        }
        return res;
    }

    private void addEffect() {
        if (isEffect()) {
            return;
        }

        Map<EffectType, BukkitTask> map = particles.get(uuid);
        if (null == map) {
            map = new HashMap<>();
        }

        map.put(type, task);
        particles.put(uuid, map);
    }

    public ParticleManager(UUID uuid, EffectType type, BukkitTask task) {
        this.uuid = uuid;
        this.type = type;
        this.task = task;
        addEffect();
    }

    public void remove() {
        ParticleManager.remove(uuid, type);
    }

    public static boolean hasEffect(UUID uuid, EffectType type) {
        boolean res = false;
        Map<EffectType, BukkitTask> taskMap = particles.get(uuid);
        if (null != taskMap) {
            res = taskMap.containsKey(type);
        }
        return res;
    }

    public static void remove(UUID uuid, EffectType type) {
        Map<EffectType, BukkitTask> taskMap = particles.get(uuid);
        if (null != taskMap) {
            if (taskMap.containsKey(type)) {
                BukkitTask bukkitTask = taskMap.get(type);
                if (null != bukkitTask && !bukkitTask.isCancelled()) {
                    bukkitTask.cancel();
                }
                taskMap.remove(type);

                if (taskMap.isEmpty()) {
                    particles.remove(uuid);
                }
            }
        }
    }

    public static void removeAll(UUID uuid) {
        Map<EffectType, BukkitTask> taskMap = particles.get(uuid);
        if (null != taskMap) {
            taskMap.forEach((k, v) -> {
                if (!v.isCancelled()) {
                    v.cancel();
                }
            });
            taskMap.clear();
            particles.remove(uuid);
        }
    }
}
