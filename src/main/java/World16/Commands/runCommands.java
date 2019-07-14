package World16.Commands;

import World16.Main.Main;
import World16.Managers.CustomConfigManager;
import World16.Utils.API;
import World16.Utils.CommandUtils;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class runCommands implements CommandExecutor {

    private Main plugin;

    private API api;

    private CommandUtils commandUtils;

    private CustomConfigManager customConfigManager;

    public runCommands(CustomConfigManager getCustomYml, Main getPlugin) {
        this.customConfigManager = getCustomYml;
        this.plugin = getPlugin;
        this.api = new API(this.plugin, this.customConfigManager);
        this.commandUtils = new CommandUtils(this.plugin);

        this.plugin.getCommand("runcommands").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {

            if (!(sender instanceof BlockCommandSender)) {
                return true;
            }

            BlockCommandSender cmdblock = (BlockCommandSender) sender;
            Block commandblock = cmdblock.getBlock();

            if (args.length >= 1) {
                List<String[]> stringList = Arrays.stream(args).map(str -> str.split(API.CUSTOM_COMMAND_FORMAT)).collect(Collectors.toList());
                this.commandUtils.runCommands(true, commandblock, sender, stringList);
                stringList.clear();
                return true;
            }
            return true;
        }

        Player p = (Player) sender;

        if (!sender.hasPermission("world16.runcommands")) {
            sender.sendMessage("You don't have permission to do this command.");
            return true;
        }

        if (args.length >= 1) {
            List<String[]> stringList = Arrays.stream(args).map(str -> str.split(API.CUSTOM_COMMAND_FORMAT)).collect(Collectors.toList());
            this.commandUtils.runCommands(sender, stringList);
            stringList.clear();
            return true;
        }
        return true;
    }
}