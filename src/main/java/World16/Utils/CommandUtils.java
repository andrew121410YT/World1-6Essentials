package World16.Utils;

import World16.Main.Main;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CommandUtils {

    private Main plugin;

    private API api;

    public CommandUtils(Main getPlugin) {
        this.plugin = getPlugin;
        this.api = new API(this.plugin);
    }

    public void runCommands(CommandSender commandSender, List<String[]> args) {
        args.forEach(commands -> {
            String command = String.join(" ", commands);
            this.plugin.getServer().dispatchCommand(commandSender, command);
        });
    }

    public void runCommands(boolean yesorno, Block block, CommandSender commandSender, List<String[]> args) {
        args.forEach(commands -> {

            if (yesorno) {
                for (int i = 0; i < commands.length; i++) {
                    if (commands[i].startsWith("X~") || commands[i].startsWith("Y~") || commands[i].startsWith("Z~")) {
                        String letter = commands[i].substring(0, 1);
                        String a = commands[i].replace(letter + "~", "");
                        int toPlus = 0;
                        int finishNumber = 0;

                        if (letter.equalsIgnoreCase("X")) {
                            toPlus = api.asIntOrDefault(a, 0);
                            finishNumber = block.getX() + toPlus;
                        }
                        if (letter.equalsIgnoreCase("Y")) {
                            toPlus = api.asIntOrDefault(a, 0);
                            finishNumber = block.getY() + toPlus;
                        }
                        if (letter.equalsIgnoreCase("Z")) {
                            toPlus = api.asIntOrDefault(a, 0);
                            finishNumber = block.getZ() + toPlus;
                        }
                        commands[i] = String.valueOf(finishNumber);
                    }
                }
            }
            String command = String.join(" ", commands);
            this.plugin.getServer().dispatchCommand(commandSender, command);
        });
    }
}
