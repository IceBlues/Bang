package org.gbcraft.bang;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.gbcraft.bang.commands.MFCommandExecutor;
import org.gbcraft.bang.commands.bean.CommandName;
import org.gbcraft.bang.commands.bean.ContainerManager;
import org.gbcraft.bang.config.OfflinePlayersConfig;
import org.gbcraft.bang.exception.PluginNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Bang extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        saveResource("players.yml", false);

        Bukkit.getPluginManager().registerEvents(this, this);
        MFCommandExecutor executor = new MFCommandExecutor(this);

        try {
            ContainerManager.init(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        PluginCommand bang = Bukkit.getPluginCommand("bang");
        if (null != bang) {
            bang.setExecutor(executor);
            bang.setTabCompleter(executor);
        }
        else {
            try {
                throw new PluginNotFoundException();
            }
            catch (PluginNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        String pName = player.getName();
        boolean isMagic = ContainerManager.isContain(CommandName.MAGIC, pName);
        boolean isSupajp = ContainerManager.isContain(CommandName.SUPAJP, pName);
        boolean isFreeze = ContainerManager.isContain(CommandName.FREEZE, pName);
        boolean isDead = ContainerManager.isContain(CommandName.DEAD, pName);

        if (isMagic && !player.hasPotionEffect(PotionEffectType.UNLUCK)) {
            magic(player);
        }

        if (isSupajp && player.getWalkSpeed() != 0.6f) {
            supajp(player);
        }
        if (isFreeze) {
            event.setCancelled(true);
        }
        if (isDead) {
            player.addScoreboardTag("Bang");
            player.setHealth(0);

        }
    }

    public void onEntityDeath(EntityDeathEvent event){
        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if(player.getScoreboardTags().contains("Bang")){
                event.getDrops().clear();
                new BukkitRunnable() {
                    double t = Math.PI/4;
                    final Location loc = player.getLocation().clone();
                    @Override
                    public void run() {
                        t = t + 0.1*Math.PI;
                        for (double theta = 0; theta <= 2*Math.PI; theta = theta + Math.PI/32){
                            double x = t*Math.cos(theta);
                            double y = 2*Math.exp(-0.1*t) * Math.sin(t) + 1.5;
                            double z = t*Math.sin(theta);
                            loc.add(x,y,z);
                            player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc, 1);
                            loc.subtract(x, y, z);
                        }
                        if(t > 20){
                            this.cancel();
                        }
                    }
                }.runTaskTimer(this, 0, 2);
            }
        }
    }

    private void supajp(Player player) {
        player.setAllowFlight(true);
        player.setWalkSpeed(0.6f);
        player.setFlySpeed(0.3f);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 0));
    }

    private void magic(Player player) {
        List<PotionEffect> magicList = new ArrayList<>();
        int maxTime = Integer.MAX_VALUE;
        player.sendTitle(ChatColor.translateAlternateColorCodes('&', "&cEnjoying this Feeling!"), "Evade!", 10, 70, 20);

        //霉运
        magicList.add(new PotionEffect(PotionEffectType.UNLUCK, maxTime, 4));
        //恶心
        magicList.add(new PotionEffect(PotionEffectType.CONFUSION, maxTime, 4));
        //缓慢
        magicList.add(new PotionEffect(PotionEffectType.SLOW, maxTime, 4));
        //挖掘疲劳
        magicList.add(new PotionEffect(PotionEffectType.SLOW_DIGGING, maxTime, 4));
        //失明
        /*            magicList.add(new PotionEffect(PotionEffectType.BLINDNESS, maxTime, 4));*/
        //虚弱
        magicList.add(new PotionEffect(PotionEffectType.WEAKNESS, maxTime, 4));

        player.addPotionEffects(magicList);
    }

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
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (ContainerManager.isContain(CommandName.BANCHAT, event.getPlayer().getName())) {
            event.setMessage("***");
        }
    }

    @Override
    public void onDisable() {
        ContainerManager.saveAll();
    }

    public void sendMessage(CommandSender sender, String node) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString(node))));
    }

    /*public void logToFile(String msg) {
        File dataFolder = getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }
        File saveTo = new File(getDataFolder(), "log/log.txt");
        try {
            if(!saveTo.getParentFile().exists()){
                saveTo.getParentFile().mkdirs();
            }
            if (!saveTo.exists()) {
                saveTo.createNewFile();
            }
            FileWriter fw = new FileWriter(saveTo, true);
            PrintWriter pw = new PrintWriter(fw);
            msg = new SimpleDateFormat("[HH:mm:ss]: ").format(new Date()) + msg;
            pw.println(msg);
            pw.flush();
            pw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveLog() {
        File log = new File(getDataFolder(), "log/log.txt");
        File saveLog = new File(getDataFolder(), "log/log-"+new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + ".txt");
        if(log.exists()){
            log.renameTo(saveLog);
        }
    }*/
}
