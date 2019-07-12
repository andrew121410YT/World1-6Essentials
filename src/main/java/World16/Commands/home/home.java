package World16.Commands.home;

import CCUtils.Storage.ISQL;
import CCUtils.Storage.SQLite;
import World16.Main.Main;
import World16.Managers.HomeManager;
import World16.TabComplete.HomeListTab;
import World16.Utils.API;
import World16.Utils.SetListMap;
import World16.Utils.Translate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class home implements CommandExecutor {

    Map<UUID, Map<String, Location>> rawHomesMap = SetListMap.homesMap;

    private Main plugin;

    private ISQL sqLite;
    private HomeManager homeManager;
    private API api;

    public home(Main plugin) {
        this.plugin = plugin;
        this.api = new API(this.plugin);

        this.sqLite = new SQLite(this.plugin.getDataFolder(), "Homes");
        this.homeManager = new HomeManager(this.sqLite);

        this.plugin.getCommand("home").setExecutor(this);
        this.plugin.getCommand("home").setTabCompleter(new HomeListTab());
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

            if (defaultHomeName.equalsIgnoreCase("@regetall")) {
                if (!p.hasPermission("world16.home.op")) {
                    api.PermissionErrorMessage(p);
                    return true;
                }
                Bukkit.getServer().getOnlinePlayers().forEach((player1) -> {
                    homeManager.unloadPlayerHomes(player1);
                    homeManager.getAllHomesFromISQL(sqLite, player1);
                    player1.sendMessage(Translate.chat("[&6Homes&r] &cYour home data got wiped from memory BUT luckily it saved because Andrew's smart like that."));
                });
                return true;
            }
        }

        Location home = homeManager.getHomeFromMap(p, defaultHomeName);

        if (home != null) {
            p.teleport(home);
            p.sendMessage(Translate.chat("&6Teleporting..."));
            return true;
        } else {
            p.sendMessage(Translate.chat("&9[Homes] &4Home not found?"));
            return true;
        }
    }
}