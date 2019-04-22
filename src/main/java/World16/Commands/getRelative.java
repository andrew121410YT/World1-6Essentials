package World16.Commands;

import World16.CustomConfigs.CustomConfigManager;
import World16.Main.Main;
import World16.Utils.API;
import World16.Utils.SetListMap;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class getRelative implements CommandExecutor {

    private Main plugin;

    API api;

    Map<Player, Location[]> sessions = SetListMap.sessions;

    private CustomConfigManager customConfigManager;

    public getRelative(CustomConfigManager getCustomYml, Main getPlugin) {
        this.customConfigManager = getCustomYml;
        this.plugin = getPlugin;
        this.api = new API(this.customConfigManager);

        this.plugin.getCommand("getRelative").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("world16.getrelative")) {
            api.PermissionErrorMessage(p);
            return true;
        }
        /*  47 */
        if (p.isOp()) {
            /*  49 */
            if (sessions.containsKey(p)) {
                /*  51 */
                Location[] locs = (Location[]) sessions.get(p);

                /*  53 */
                if ((locs[0] != null) && (locs[1] != null)) {
                    /*  55 */
                    String cords = relativeString(p);
                    p.sendMessage("Your relative coordinates are §c" + cords);
                    BaseComponent[] components = new ComponentBuilder("[CMD] Click me to copy more easier!").event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, cords)).create();
                    p.spigot().sendMessage(components);
                } else {
                    /*  59 */
                    p.sendMessage("Take a slime ball a mark two blocks (Your first block is the parent block). (Left - Rightclick)");
                }

            } else {
                /*  65 */
                p.sendMessage("Take a slime ball a mark two blocks (Your first block is the parent block). (Left - Rightclick)");
            }


        } else {
            /*  71 */
            p.sendMessage("§cYou dont have Permission");
        }
        return true;
    }


    public void putIntoSession(Player p, Location loc) {
        /* 182 */
        if (!sessions.containsKey(p)) {
            /* 184 */
            Location[] locs = new Location[2];
            /* 185 */
            locs[0] = loc;

            /* 187 */
            sessions.put(p, locs);
        } else {
            /* 191 */
            Location[] locs = (Location[]) sessions.get(p);
        }
    }


    public String relativeString(Player p) {
        /* 203 */
        if (sessions.containsKey(p)) {
            /* 205 */
            Location[] locs = (Location[]) sessions.get(p);

            /* 207 */
            if ((locs[0] != null) && (locs[1] != null)) {
                /* 209 */
                Location one = locs[0];
                /* 210 */
                Location two = locs[1];

                /* 212 */
                Location last = two.subtract(one);

                /* 222 */
                return "~" + last.getBlockX() + " ~" + last.getBlockY() + " ~" + last.getBlockZ();
            }
        }
        /* 228 */
        return "No Points marked!";
    }
}