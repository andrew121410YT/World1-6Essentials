package World16.TabComplete;

import World16.Main.Main;
import World16.Utils.SetListMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DebugTab implements TabCompleter {

    Main plugin = Main.getPlugin();

    //Maps
    Map<String, List<String>> tabCompleteMap = SetListMap.tabCompleteMap;
    //...

    //Lists
    //...

    public DebugTab() {
        tabCompleteMap.computeIfAbsent("debug1-6", k -> new ArrayList<>());

        if (tabCompleteMap.get("debug1-6").isEmpty()) {
            tabCompleteMap.get("debug1-6").add("op");
            tabCompleteMap.get("debug1-6").add("defaultstuff");
            tabCompleteMap.get("debug1-6").add("clearalllists");
            tabCompleteMap.get("debug1-6").add("clearallmaps");
            tabCompleteMap.get("debug1-6").add("clearalllistswithname");
            tabCompleteMap.get("debug1-6").add("clearallmapswithname");
            tabCompleteMap.get("debug1-6").add("date");
            tabCompleteMap.get("debug1-6").add("playerversion");
            tabCompleteMap.get("debug1-6").add("checkuuid");
            tabCompleteMap.get("debug1-6").add("debugmessages");
            tabCompleteMap.get("debug1-6").add("finddefaultspawn");
            tabCompleteMap.get("debug1-6").add("uuidcache");
            tabCompleteMap.get("debug1-6").add("sql");
//            tabCompleteMap.get("debug").add("");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alies, String[] args) {
        if (!(sender instanceof Player)) {
            return null;
        }
        Player player = (Player) sender;

        if (!cmd.getName().equalsIgnoreCase("debug1-6")) {
            return null;
        }

        if (args.length == 1) {
            return getContains(args[0], tabCompleteMap.get("debug1-6"));
        }

        if (args.length >= 2 && args[0].equalsIgnoreCase("checkmaps")) {
            List<String> checkmaps = Arrays.asList("@checkmine", "@all", "@allwithdepth");
            return getContains(args[1], checkmaps);
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