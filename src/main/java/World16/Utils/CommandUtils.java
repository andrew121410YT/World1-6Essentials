package World16.Utils;

import World16.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CommandUtils {

    private Main plugin;

    private API api;

    public CommandUtils(Main getPlugin) {
        this.plugin = getPlugin;
        this.api = new API();
    }

    public void runCommands(CommandSender commandSender, String splitType, List<String> args) {
        args.forEach(commands -> {
            String[] newShit = commands.split(splitType);
            String command = String.join(" ", commands);
            this.plugin.getServer().dispatchCommand(commandSender, command);
        });
    }

    public void runCommands(CommandSender commandSender, List<String[]> args) {
        args.forEach(commands -> {
            String command = String.join(" ", commands);
            this.plugin.getServer().dispatchCommand(commandSender, command);
        });
    }

    public void runCommands(Block block, CommandSender commandSender, List<String[]> args) {
        args.forEach(commands -> {

            for (int i = 0; i < commands.length; i++) {
                if (commands[i].startsWith("X~") || commands[i].startsWith("Y~") || commands[i].startsWith("Z~")) {
                    String letter = commands[i].substring(1);
                    String a = commands[i].replace("~", "");
                    int toPlus = 0;
                    int finishNumber = 0;
                    Bukkit.getServer().broadcastMessage(a); //DEBUG
                    Bukkit.getServer().broadcastMessage("LETTER -> " + letter); //DEBUG
                    if (letter.equalsIgnoreCase("X")) {
                        toPlus = api.asIntOrDefault(a.replace("X", ""), 1);
                        finishNumber = block.getX() + toPlus;
                    }
                    if (letter.equalsIgnoreCase("Y")) {
                        toPlus = api.asIntOrDefault(a.replace("Y", ""), 1);
                        finishNumber = block.getY() + toPlus;
                    }
                    if (letter.equalsIgnoreCase("Z")) {
                        toPlus = api.asIntOrDefault(a.replace("Z", ""), 1);
                        finishNumber = block.getZ() + toPlus;
                    }
                    Bukkit.getServer().broadcastMessage(a); //DEBUG
                    commands[i] = String.valueOf(finishNumber);
                }
            }

            String command = String.join(" ", commands);
            Bukkit.getServer().broadcastMessage(command); //DEBUG

            this.plugin.getServer().dispatchCommand(commandSender, command);
        });
    }
}
