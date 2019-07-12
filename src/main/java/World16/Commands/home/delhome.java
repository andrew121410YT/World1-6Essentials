package World16.Commands.home;

import CCUtils.Storage.ISQL;
import CCUtils.Storage.SQLite;
import World16.Main.Main;
import World16.Managers.HomeManager;
import World16.TabComplete.HomeListTab;
import World16.Utils.API;
import World16.Utils.Translate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class delhome implements CommandExecutor {

    private Main plugin;

    private ISQL isql;
    private HomeManager homeManager;
    private API api;

    public delhome(Main plugin) {
        this.plugin = plugin;
        this.api = new API(this.plugin);

        this.isql = new SQLite(this.plugin.getDataFolder(), "Homes");
        this.homeManager = new HomeManager(isql);

        this.plugin.getCommand("delhome").setExecutor(this);
        this.plugin.getCommand("delhome").setTabCompleter(new HomeListTab());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission("world16.home")) {
            api.PermissionErrorMessage(p);
            return true;
        }

        String defaultHomeName = "home";

        if (args.length == 1) {
            defaultHomeName = args[0].toLowerCase();

            if (defaultHomeName.equalsIgnoreCase("@allHomes")) {
                homeManager.removeAllHomesFromISQL(isql, p);
                return true;
            }
        }

        homeManager.removeHome(isql, p, defaultHomeName);
        p.sendMessage(Translate.chat("&9[Homes] &cHome deleted."));
        return true;
    }
}