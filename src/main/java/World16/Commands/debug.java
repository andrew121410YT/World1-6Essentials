package World16.Commands;

import World16.Main.Main;
import World16.TabComplete.DebugTab;
import World16.Utils.API;
import World16.Utils.SetListMap;
import World16.Utils.Translate;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class debug implements CommandExecutor {

    //Maps
    private Map<String, UUID> uuidCache;
    //...

    //Lists
    //...

    private API api;
    private SetListMap setListMap;

    private Main plugin;

    public debug(Main getPlugin) {
        this.plugin = getPlugin;
        this.api = new API(this.plugin);

        this.setListMap = this.plugin.getSetListMap();
        this.uuidCache = this.plugin.getSetListMap().getUuidCache();

        this.plugin.getCommand("debug1-6").setExecutor(this);
        this.plugin.getCommand("debug1-6").setTabCompleter(new DebugTab(this.plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }

        Player p = (Player) sender;

        if (args.length == 0) {
            if (!p.hasPermission("world16.debug")) { // Permission
                api.PermissionErrorMessage(p);
                return true;
            }
            p.sendMessage(Translate.chat("&6Please use tab complete."));
            return true;
        } else {
            //OP
            if (args[0].equalsIgnoreCase("op")) {
                if (!p.hasPermission("world16.debug.op")) { // Permission
                    api.PermissionErrorMessage(p);
                    return true;
                }
                p.sendMessage(Translate.chat("&4Debug working oping andrew and tyler and richard"));
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                Set<String> opSet = new HashSet<>();
                opSet.add("AlphaGibbon43");
                opSet.add("Robobros3");
                opSet.add("andrew121410");
                opSet.forEach(set -> this.plugin.getServer().dispatchCommand(console, "op " + set));
                return true;
            } else if (args.length == 1 && (args[0].equalsIgnoreCase("default"))) {
                if (!p.hasPermission("world16.debug.defaultstuff")) { // Permission
                    api.PermissionErrorMessage(p);
                    return true;
                }
                this.plugin.getConfig().set("TittleTOP", "&f&l[&4World 1-6&f&l]");
                this.plugin.getConfig().set("TittleBOTTOM", "&9&oHome Of Minecraft Fire Alarms.");
                this.plugin.getConfig().set("TablistTOP", "&f&l[&4World 1-6&f&l]");
                this.plugin.getConfig().set("TablistBOTTOM", "&9&oHome Of Minecraft Fire Alarms.");
                this.plugin.saveConfig();
                this.plugin.reloadConfig();
                p.sendMessage(Translate.chat("&bOK..."));
                return true;
                //DATE
            } else if (args.length == 1 && (args[0].equalsIgnoreCase("date"))) {
                if (!p.hasPermission("world16.debug.date")) { // Permission
                    api.PermissionErrorMessage(p);
                    return true;
                }
                String date = api.Time();
                p.sendMessage(Translate.chat("Time/Data:-> " + date));
                return true;
            } else if (args[0].equalsIgnoreCase("checkuuid")) {
                if (!p.hasPermission("world16.debug.checkuuid")) {
                    api.PermissionErrorMessage(p);
                    return true;
                }
                if (args.length == 1) {
                    p.sendMessage(Translate.chat(p.getUniqueId().toString()));
                    return true;
                }
            } else {
                Player target = plugin.getServer().getPlayerExact(args[1]);
                if (args.length == 2 && target != null && target.isOnline()) {
                    if (!p.hasPermission("world16.debug.checkuuid.other")) {
                        api.PermissionErrorMessage(p);
                        return true;
                    }
                    UUID uuidtarget = api.getUUIDFromMojangAPI(target.getDisplayName());
                    p.sendMessage(Translate
                            .chat("UUID: " + uuidtarget + " FOR " + target.getDisplayName()));
                    BaseComponent[] components = new ComponentBuilder("[CMD] Click me to copy more easier!").event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, uuidtarget.toString())).create();
                    p.spigot().sendMessage(components);
                    return true;
                } else if (args.length >= 3 && args[1] != null && args[2] != null && args[2].equalsIgnoreCase("@offline")) {
                    UUID uuidtarget2 = api.getUUIDFromMojangAPI(args[1]);
                    p.sendMessage(Translate.chat("UUID: " + uuidtarget2 + " FOR " + args[1]));
                    BaseComponent[] components = new ComponentBuilder("[CMD] Click me to copy more easier!").event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, uuidtarget2.toString())).create();
                    p.spigot().sendMessage(components);
                    return true;
                }
                return true;
            }
            return true;
        }
    }
}