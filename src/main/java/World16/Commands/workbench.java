package World16.Commands;

import World16.Main.Main;
import World16.Managers.CustomConfigManager;
import World16.Utils.API;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class workbench implements CommandExecutor {

    private Main plugin;
    private API api;

    private CustomConfigManager customConfigManager;

    public workbench(CustomConfigManager getCustomYml, Main getPlugin) {
        this.plugin = getPlugin;
        this.customConfigManager = getCustomYml;
        this.api = new API(this.plugin);

        this.plugin.getCommand("workbench").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }
        Player p = (Player) sender;

        if (!p.hasPermission("world16.workbench")) {
            api.PermissionErrorMessage(p);
            return true;
        }

        p.openWorkbench(null, true);
        return true;
    }
}