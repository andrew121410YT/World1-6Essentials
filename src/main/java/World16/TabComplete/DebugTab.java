package World16.TabComplete;

import World16.Main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DebugTab implements TabCompleter {

    private Main plugin;

    //Maps
    private Map<String, List<String>> tabCompleteMap;
    //...

    //Lists
    //...

    public DebugTab(Main plugin) {
        this.plugin = plugin;
        this.tabCompleteMap = this.plugin.getSetListMap().getTabCompleteMap();

        tabCompleteMap.computeIfAbsent("debug1-6", k -> new ArrayList<>());

        if (tabCompleteMap.get("debug1-6").isEmpty()) {
            tabCompleteMap.get("debug1-6").add("op");
            tabCompleteMap.get("debug1-6").add("default");
            tabCompleteMap.get("debug1-6").add("date");
            tabCompleteMap.get("debug1-6").add("checkuuid");
//            tabCompleteMap.get("debug").add("");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alies, String[] args) {
        if (!(sender instanceof Player)) {
            return null;
        }
        Player p = (Player) sender;

        if (!cmd.getName().equalsIgnoreCase("debug1-6")) {
            return null;
        }

        if (!p.hasPermission("world16.debug")) {
            return null;
        }

        if (args.length == 1) {
            return getContains(args[0], tabCompleteMap.get("debug1-6"));
        }

        return null;
    }

    private List<String> getContains(String args, List<String> oldArrayList) {
        List<String> list = new ArrayList<>();

        for (String s : oldArrayList) {
            if (s.contains(args.toLowerCase())) {
                list.add(s);
            }
        }

        return list;
    }
}