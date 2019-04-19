package World16.Commands;

import World16.CustomConfigs.CustomConfigManager;
import World16.Main.Main;
import World16.Utils.API;
import World16.Utils.CommandUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class runCommands implements CommandExecutor {

    private Main plugin;

    private API api;

    private CommandUtils commandUtils;

    private CustomConfigManager customConfigManager;

    public runCommands(CustomConfigManager getCustomYml, Main getPlugin) {
        this.customConfigManager = getCustomYml;
        this.plugin = getPlugin;
        this.api = new API(this.customConfigManager);
        this.commandUtils = new CommandUtils(this.plugin);

        this.plugin.getCommand("runcommands").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("world16.runcommands")) {
            sender.sendMessage("You don't have permission to do this command.");
            return true;
        }
        if (args.length >= 1) {
            List<String[]> stringList = new ArrayList<>();
            for (int i = 0; i < args.length; i++) {
                stringList.add(args[i].split(API.CUSTOM_COMMAND_FORMAT));
            }
            this.commandUtils.runCommands(sender, stringList);
            stringList.clear();
            return true;
        }
        return true;
    }
}