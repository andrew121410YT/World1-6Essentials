package World16.Utils;

import World16.Main.Main;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CommandUtils {

    private Main plugin;

    public CommandUtils(Main getPlugin) {
        this.plugin = getPlugin;
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
}
