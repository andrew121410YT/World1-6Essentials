package World16.Commands;

import World16.Main.Main;
import World16.Managers.CustomConfigManager;
import World16.Managers.JailManager;
import World16.Utils.API;
import World16.Utils.Translate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setjail implements CommandExecutor {

    private API api;
    private Main plugin;
    private JailManager jailManager;

    public setjail(CustomConfigManager getCustomYml, Main getPlugin, JailManager jailManager) {
        this.plugin = getPlugin;
        this.jailManager = jailManager;
        this.api = new API(this.plugin);
        this.plugin.getCommand("setjail").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }
        Player p = (Player) sender;

        if (!p.hasPermission("world16.setjail")) {
            api.PermissionErrorMessage(p);
            return true;
        }

        if (args.length == 0) {
            p.sendMessage(Translate.chat("&2[SetJail]&r&c Usage: /setjail <JailName>"));
            return true;
        } else if (args.length == 1 && args[0] != null) {
            jailManager.set(args[0].toLowerCase(), p.getLocation());
            p.sendMessage(Translate.chat("&2[SetJail]&r&6 The Jail has been set."));
            return true;
        }
        return true;
    }
}
