package World16.Commands;

import World16.Main.Main;
import World16.Managers.CustomConfigManager;
import World16.Managers.CustomYmlManager;
import World16.Utils.API;
import World16.Utils.Translate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class broadcast implements CommandExecutor {

    private Main plugin;

    private API api;

    private CustomYmlManager shitYml = null;

    public broadcast(CustomConfigManager getCustomYml, Main getPlugin) {
        this.shitYml = getCustomYml.getShitYml();
        this.plugin = getPlugin;
        this.api = new API(this.plugin);
        this.plugin.getCommand("broadcast").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("world16.broadcast")) {
            api.PermissionErrorMessage(p);
            return true;
        }
        if (args.length == 0) {
            p.sendMessage(Translate.chat("[&cBroadCast&r] &cUsage: /broadcast <Message>"));
        } else if (args.length >= 1) {

            this.plugin.getServer().getOnlinePlayers().stream().forEach(player -> player.sendMessage(Translate.chat("[&c&lBroadcast&r]&a {messager}").replace("{messager}", String.join(" ", args))));

            return true;
        } else {
            p.sendMessage("Something messed up!");
            return true;
        }
        return true;
    }
}