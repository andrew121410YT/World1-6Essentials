package World16.test;

import World16.CustomConfigs.CustomConfigManager;
import World16.Main.Main;
import World16.Utils.API;
import World16.Utils.CustomYmlManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class test implements CommandExecutor {

    private Main plugin;
    API api = new API();

    private CustomYmlManager shitYml = null;

    public test(CustomConfigManager getCustomYml, Main getPlugin) {
        this.shitYml = getCustomYml.getShitYml();
        this.plugin = getPlugin;

        this.plugin.getCommand("testee").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission("world16.testee")) {
            api.PermissionErrorMessage(p);
            return true;
        }
        //STUFF HERE
        return true;
    }
}