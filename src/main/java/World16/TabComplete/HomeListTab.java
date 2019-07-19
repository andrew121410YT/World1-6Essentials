package World16.TabComplete;

import World16.Main.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;

public class HomeListTab implements TabCompleter {

    //This is just a reminder for myself to not copy this because it won't work with other stuff since it's custom coded.

    //Lists
    private Map<UUID, Map<String, Location>> rawHomesMap;
    //...

    private Main plugin;

    public HomeListTab(Main plugin) {
        this.plugin = plugin;

        this.rawHomesMap = this.plugin.getSetListMap().getHomesMap();
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alies, String[] args) {
        if (!(sender instanceof Player)) {
            return null;
        }
        Player player = (Player) sender;

        if (!cmd.getName().equalsIgnoreCase("home") || !cmd.getName().equalsIgnoreCase("delhome")) {
            return null;
        }

        Set<String> homeSet = rawHomesMap.get(player.getUniqueId()).keySet();
        String[] homeString = homeSet.toArray(new String[0]);
        return getContains(args[0], Arrays.asList(homeString));
    }


    private List<String> getContains(String args, List<String> oldArrayList) {
        List<String> list = new ArrayList<>();

        for (String mat : oldArrayList) {
            if (mat.contains(args.toLowerCase())) {
                list.add(mat);
            }
        }

        return list;
    }
}