package World16.TabComplete;

import World16.Main.Main;
import World16.Utils.SetListMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KeyTab implements TabCompleter {

    Main plugin = Main.getPlugin();

    //Maps
    Map<String, List<String>> tabCompleteMap = SetListMap.tabCompleteMap;
    //...

    //Lists
    //...

    public KeyTab() {
        tabCompleteMap.computeIfAbsent("key", k -> new ArrayList<>());

        if (tabCompleteMap.get("key").isEmpty()) {
            tabCompleteMap.get("key").add("set");
            tabCompleteMap.get("key").add("give");
            tabCompleteMap.get("key").add("reset");
            tabCompleteMap.get("key").add("list");
//            tabCompleteMap.get("key").add("");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alies, String[] args) {
        if (!(sender instanceof Player)) {
            return null;
        }
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("key")) {
            List<String> list = getContains(args[0], tabCompleteMap.get("key"));
            return list;
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