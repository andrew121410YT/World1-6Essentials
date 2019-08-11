package World16.TabComplete;

import World16.Main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ElevatorTab implements TabCompleter {

    private Main plugin;

    //Maps
    private Map<String, List<String>> tabCompleteMap;
    //...

    //Lists
    //...

    public ElevatorTab(Main plugin) {
        this.plugin = plugin;
        this.tabCompleteMap = this.plugin.getSetListMap().getTabCompleteMap();

        tabCompleteMap.computeIfAbsent("elevator", k -> new ArrayList<>());

        if (tabCompleteMap.get("elevator").isEmpty()) {
            tabCompleteMap.get("elevator").add("create");
            tabCompleteMap.get("elevator").add("addfloor");
            tabCompleteMap.get("elevator").add("removefloor");
            tabCompleteMap.get("elevator").add("goto");
            tabCompleteMap.get("elevator").add("delete");
            tabCompleteMap.get("elevator").add("queue");
//            tabCompleteMap.get("back").add("");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alies, String[] args) {
        if (!(sender instanceof Player)) {
            return null;
        }
        Player p = (Player) sender;

        if (!cmd.getName().equalsIgnoreCase("elevator")) {
            return null;
        }

        if (!p.hasPermission("world16.elevator")) {
            return null;
        }

        if (args.length == 1) {
            return getContains(args[0], tabCompleteMap.get("elevator"));
        }

        return null;
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