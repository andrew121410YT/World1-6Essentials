package World16.TabComplete;

import World16.Main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ERamTab implements TabCompleter {

    Main plugin = Main.getPlugin();

    //Maps
    Map<String, List<String>> tabCompleteMap = Main.tabCompleteMap;
    //...

    //Lists
    //...

    public ERamTab() {
        tabCompleteMap.computeIfAbsent("eram", k -> new ArrayList<>());

        if (tabCompleteMap.get("eram").isEmpty()) {
            tabCompleteMap.get("eram").add("save");
            tabCompleteMap.get("eram").add("load");
            tabCompleteMap.get("eram").add("do");
            tabCompleteMap.get("eram").add("undo");
            tabCompleteMap.get("eram").add("list");
            tabCompleteMap.get("eram").add("delete");
            tabCompleteMap.get("eram").add("addtag");
            tabCompleteMap.get("eram").add("deletetag");
//            tabCompleteMap.get("eram").add("");
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
