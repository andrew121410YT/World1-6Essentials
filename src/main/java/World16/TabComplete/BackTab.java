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

public class BackTab implements TabCompleter {

    private Main plugin;

    //Maps
    Map<String, List<String>> tabCompleteMap = SetListMap.tabCompleteMap;
    //...

    //Lists
    //...

    public BackTab(Main plugin) {
        this.plugin = plugin;

        tabCompleteMap.computeIfAbsent("back", k -> new ArrayList<>());

        if (tabCompleteMap.get("back").isEmpty()) {
            tabCompleteMap.get("back").add("death");
            tabCompleteMap.get("back").add("tp");
            tabCompleteMap.get("back").add("set");
            tabCompleteMap.get("back").add("goto");
//            tabCompleteMap.get("back").add("");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alies, String[] args) {
        if (!(sender instanceof Player)) {
            return null;
        }
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("back")) {
            List<String> list = getContains(args[0], tabCompleteMap.get("back"));
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