package org.gbcraft.bang.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.gbcraft.bang.Bang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MFCommandExecutor implements TabExecutor {
    private final Bang plugin;

    public MFCommandExecutor(Bang plugin) {
        this.plugin = Objects.requireNonNull(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1 && sender.hasPermission("bang.base")) {
            if (sender.hasPermission("bang.base")) {
                try {
                    String commandName = new Character(args[0].charAt(0)).toString().toUpperCase() + args[0].substring(1) + "Command";
                    Class<MFCommand> clazz = (Class<MFCommand>) Class.forName("org.gbcraft.bang.commands." + commandName);
                    MFCommand cmd = clazz.getConstructor(Bang.class, CommandSender.class, String[].class).newInstance(plugin, sender, args);
                    return cmd.run();
                }
                catch (Exception e) {
                }
            }
            else {
                plugin.sendMessage(sender, "info.permission.no");
            }
        }

        return false;
    }

    private static String[] subCommands = {"dead", "fuck", "magic", "bless", "supajp", "uglyface", "freeze", "back", "teleport", "list", "reload", "help"};
    private static String[] backCommands = {"dead", "fuck", "magic", "bless", "supajp", "freeze", "all"};

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!sender.hasPermission("bang.base"))
            return null;

        if (args.length == 1) {
            return Arrays.stream(subCommands).filter(c -> c.startsWith(args[0])).collect(Collectors.toList());
        }
        //if have sub command and equals back
        if (args.length > 0 && args[0].equalsIgnoreCase("back")) {
            //if want input back command name
            if (args.length == 2) {
                return Arrays.stream(backCommands).filter(c -> c.startsWith(args[1])).collect(Collectors.toList());
            }
            //bigger than 2
            else {
                List<String> res = null;
                String backCommand = args[1];
                switch (backCommand) {
                    case "fuck":
                        res = Arrays.stream(FuckCommand.getKeyArray()).filter(p -> p.startsWith(args[2])).collect(Collectors.toList());
                        break;
                    case "magic":
                        res = Arrays.stream(MagicCommand.getKeyArray()).filter(p -> p.startsWith(args[2])).collect(Collectors.toList());
                        break;
                    case "supajp":
                        res = Arrays.stream(SupajpCommand.getKeyArray()).filter(p -> p.startsWith(args[2])).collect(Collectors.toList());
                        break;
                    case "freeze":
                        res = Arrays.stream(FreezeCommand.getKeyArray()).filter(p -> p.startsWith(args[2])).collect(Collectors.toList());
                }
                return res;
            }
        }

        ArrayList<String> list = new ArrayList<>();
        plugin.getServer().getOnlinePlayers().forEach(p -> list.add(p.getName()));
        return Arrays.stream(list.toArray(new String[0])).filter(p -> p.startsWith(args[args.length - 1])).collect(Collectors.toList());
    }
}
