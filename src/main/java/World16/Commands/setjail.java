package World16.Commands;

import World16.CustomConfigs.CustomConfigManager;
import World16.Main.Main;
import World16.Utils.API;
import World16.Utils.CustomYmlManager;
import World16.Utils.Translate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setjail implements CommandExecutor {

    API api;
    private Main plugin;
    private CustomYmlManager shitYml = null;

    public setjail(CustomConfigManager getCustomYml, Main getPlugin) {
        this.shitYml = getCustomYml.getShitYml();
        this.api = new API();

        this.plugin = getPlugin;
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

        this.api.setLocationToFile(this.shitYml, "Jail.default", p.getLocation());
        p.sendMessage(Translate.chat("&6The jail has been set."));
        return true;
    }
}
