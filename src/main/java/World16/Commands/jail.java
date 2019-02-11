package World16.Commands;

import World16.CustomConfigs.ShitConfig;
import World16.Main.Main;
import World16.Utils.API;
import World16.Utils.CustomYmlManager;
import World16.Utils.Translate;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class jail implements CommandExecutor {

    API api;
    private Main plugin;
    private CustomYmlManager configinstance = null;

    public jail(ShitConfig getCustomYml, Main getPlugin) {
        this.configinstance = getCustomYml.getInstance();
        this.api = new API(this.configinstance);

        this.plugin = getPlugin;
        this.plugin.getCommand("jail").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }
        Player p = (Player) sender;

        Location jail = api.getLocationFromFile(null, "Jail", "default");

        if (!p.hasPermission("world16.jail")) {
            api.PermissionErrorMessage(p);
            return true;
        }
        p.teleport(jail);
        p.sendMessage(Translate.chat("&6Teleporting..."));
        return true;
    }
}
