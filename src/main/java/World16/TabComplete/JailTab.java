package World16.TabComplete;

import World16.Main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JailTab implements TabCompleter {

    private Main plugin;

    //Maps
    private Map<String, List<String>> tabCompleteMap;
    //...

    //Lists
    //...

    public JailTab(Main plugin) {
        this.plugin = plugin;
        this.tabCompleteMap = this.plugin.getSetListMap().getTabCompleteMap();

        tabCompleteMap.computeIfAbsent("jail", k -> new ArrayList<>());

        if (tabCompleteMap.get("jail").isEmpty()) {
            tabCompleteMap.get("jail").add("list");
            tabCompleteMap.get("jail").add("set");
            tabCompleteMap.get("jail").add("delete");
//            tabCompleteMap.get("back").add("");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alies, String[] args) {
        if (!(sender instanceof Player)) {
            return null;
        }
        Player player = (Player) sender;

        if (!cmd.getName().equalsIgnoreCase("jail")) {
            return null;
        }

        if (args.length == 1) {
            return getContains(args[0], tabCompleteMap.get("jail"));
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