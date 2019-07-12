package World16.TabComplete;

import World16.Utils.SetListMap;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;

public class HomeListTab implements TabCompleter {

    //Lists
    Map<UUID, Map<String, Location>> rawHomesMap = SetListMap.homesMap;
    //...

    public HomeListTab() {

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alies, String[] args) {
        if (!(sender instanceof Player)) {
            return null;
        }
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("home") || cmd.getName().equalsIgnoreCase("delhome")) {
            Set<String> homeSet = rawHomesMap.get(player.getUniqueId()).keySet();
            String[] homeString = homeSet.toArray(new String[0]);
            return getContains(args[0], Arrays.asList(homeString));
        }
        return null;
    }

    private List<String> getContains(String args, List<String> a) {
        List<String> list = new ArrayList<>();
        for (String mat : a) {
            if (mat.contains(args.toLowerCase())) {
                list.add(mat);
            }
        }
        return list;
    }
}