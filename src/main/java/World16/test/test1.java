package World16.test;

import World16.Main.Main;
import World16.Managers.CustomConfigManager;
import World16.Utils.API;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class test1 implements CommandExecutor {

    private Main plugin;
    private API api;

    private CustomConfigManager customConfigManager;

    public test1(CustomConfigManager getCustomYml, Main getPlugin) {
        this.plugin = getPlugin;
        this.customConfigManager = getCustomYml;
        this.api = new API(this.plugin);
        this.plugin.getCommand("testee1").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("world16.testee1")) {
            api.PermissionErrorMessage(p);
            return true;
        }
        if (args.length == 0) {

            return true;
        } else if (args.length >= 1) {
            //SOMETHING HERE
            return true;
        } else {
            p.sendMessage("Something messed up!");
            return true;
        }
    }
}