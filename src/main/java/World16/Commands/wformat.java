package World16.Commands;

import World16.CustomConfigs.CustomConfigManager;
import World16.Main.Main;
import World16.Utils.API;
import World16.Utils.Translate;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class wformat implements CommandExecutor {

    private Main plugin;

    API api = new API();

    private CustomConfigManager customConfigManager;

    public wformat(CustomConfigManager getCustomYml, Main getPlugin) {
        this.customConfigManager = getCustomYml;
        this.plugin = getPlugin;

        this.plugin.getCommand("wformat").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {

            if (!(sender instanceof BlockCommandSender)) {
                return true;
            }

            BlockCommandSender cmdblock = (BlockCommandSender) sender;
            Block commandblock = cmdblock.getBlock();

            if (args.length == 0) {
                sender.sendMessage("Wtf? what you mean?");
                return true;
            } else if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("cmd")) {
                    if (args[1] == null) {
                        sender.sendMessage(Translate.chat("&cUsage: /wformat cmd <String>"));
                        return true;
                    }
                    String command = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                    String commandFormat = command.replace(" ", API.CUSTOM_COMMAND_FORMAT);
                    sender.sendMessage(commandFormat);
                    return true;
                }
                return true;
            }
            return true;
        }


        Player p = (Player) sender;
        if (!p.hasPermission("world16.wformat")) {
            api.PermissionErrorMessage(p);
            return true;
        }
        if (args.length == 0) {
            p.sendMessage("Wtf? what you mean?");
            return true;
        } else if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("cmd")) {
                if (args[1] == null) {
                    p.sendMessage(Translate.chat("&cUsage: /wformat cmd <String>"));
                    return true;
                }
                String command = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                String commandFormat = command.replace(" ", API.CUSTOM_COMMAND_FORMAT);
                BaseComponent[] components = new ComponentBuilder("Now Click Me").event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, commandFormat)).create();
                p.spigot().sendMessage(components);
                return true;
            }
            return true;
        }
        return true;
    }
}