package org.gbcraft.bang.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.gbcraft.bang.Bang;
import org.gbcraft.bang.commands.bean.CommandName;
import org.gbcraft.bang.commands.bean.ContainerManager;

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
            try {
                String commandName = Character.toString(args[0].charAt(0)).toUpperCase() + args[0].substring(1) + "Command";
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

        return false;
    }

    private static final String[] subCommands = {"banchat", "dead", "doubleRocketPunch", "fuck", "magic", "observe", "supajp", "uglyface", "freeze", "back", "teleport", "list", "reload", "help", "version"};
    private static final String[] backCommands = {"banchat", "dead", "fuck", "magic", "supajp", "freeze", "all"};
    private static final String[] banChatCommands = {"all", "message", "logs", "replace"};

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
                CommandName backCommandName;
                try {
                    backCommandName = CommandName.valueOf(backCommand.toUpperCase());
                }
                catch (Exception e) {
                    backCommandName = null;
                }

                if (null != backCommandName) {
                    switch (backCommandName) {
                        case FUCK:
                            res = Arrays.stream(ContainerManager.getArray(CommandName.FUCK)).filter(p -> p.startsWith(args[2])).collect(Collectors.toList());
                            break;
                        case MAGIC:
                            res = Arrays.stream(ContainerManager.getArray(CommandName.MAGIC)).filter(p -> p.startsWith(args[2])).collect(Collectors.toList());
                            break;
                        case SUPAJP:
                            res = Arrays.stream(ContainerManager.getArray(CommandName.SUPAJP)).filter(p -> p.startsWith(args[2])).collect(Collectors.toList());
                            break;
                        case FREEZE:
                            res = Arrays.stream(ContainerManager.getArray(CommandName.FREEZE)).filter(p -> p.startsWith(args[2])).collect(Collectors.toList());
                            break;
                        case DEAD:
                            res = Arrays.stream(ContainerManager.getArray(CommandName.DEAD)).filter(p -> p.startsWith(args[2])).collect(Collectors.toList());
                            break;
                        case BANCHAT:
                            res = Arrays.stream(ContainerManager.getArray(CommandName.BANCHAT)).filter(p -> p.startsWith(args[2])).collect(Collectors.toList());
                    }
                }

                return res;
            }
        }
        // /bang banchat(0) player(1) [](2)
        if (args.length == 3 && args[0].equalsIgnoreCase("banchat")) {
            return Arrays.stream(banChatCommands).filter(c -> c.startsWith(args[2].toLowerCase())).collect(Collectors.toList());
        }

        ArrayList<String> list = new ArrayList<>();
        plugin.getServer().getOnlinePlayers().forEach(p -> list.add(p.getName()));
        return Arrays.stream(list.toArray(new String[0])).filter(p -> p.startsWith(args[args.length - 1])).collect(Collectors.toList());
    }
}
