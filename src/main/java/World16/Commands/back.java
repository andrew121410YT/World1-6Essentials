package World16.Commands;

import World16.Main.Main;
import World16.Utils.API;
import World16.Utils.Translate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class back implements CommandExecutor {

    API api = new API();
    private Main plugin;

    //Maps
    public static Map<String, List<Location>> backm = new HashMap<>();
    //...

    public back(Main getPlugin) {
        this.plugin = getPlugin;
        this.plugin.getCommand("back").setExecutor(this);
    }

    /**
     * Death ID for getLocation is 1;
     * Tp ID for GetLocation is 2;
     * Set ID for GetLocation is 3;
     */

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }
        Player p = (Player) sender;

        if (args.length == 0) {
            p.sendMessage(Translate.chat("&cUsage:"));
            p.sendMessage(Translate.chat("/back death"));
            p.sendMessage(Translate.chat("/back tp"));
            p.sendMessage(Translate.chat("/back set"));
            p.sendMessage(Translate.chat("/back goto"));
            return true;
        }
        if (args.length >= 1) {

            //DEATH
            if (args[0].equalsIgnoreCase("death")) {
                if (!p.hasPermission("world16.back.death")) {
                    api.PermissionErrorMessage(p);
                    return true;
                }
                boolean indexExists = 0 >= 0 && 0 < backm.get(p.getDisplayName()).size();
                if (backm.get(p.getDisplayName()) != null && indexExists) {
                    if(backm.get(p.getDisplayName()).get(0) == null){
                        p.sendMessage(Translate.chat("&4Something went wrong."));
                        return true;
                    }
                    Location deathlocation = backm.get(p.getDisplayName()).get(0);

                    if (deathlocation.getBlock().isLiquid() || deathlocation.getBlock().getRelative(BlockFace.DOWN).isLiquid()) {
                        deathlocation.getBlock().getRelative(BlockFace.DOWN).setType(Material.LOG);
                        deathlocation.getBlock().getRelative(BlockFace.EAST).setType(Material.LOG);
                        deathlocation.getBlock().getRelative(BlockFace.NORTH).setType(Material.LOG);
                        deathlocation.getBlock().getRelative(BlockFace.WEST).setType(Material.LOG);
                        deathlocation.getBlock().getRelative(BlockFace.SOUTH).setType(Material.LOG);
                        deathlocation.getBlock().setType(Material.AIR);
                    }
                    p.teleport(deathlocation);
                    return true;
                } else {
                    p.sendMessage(Translate.chat("&4Look's like you didn't die yet."));
                    return true;
                }

                //TP
            } else if (args[0].equalsIgnoreCase("tp")) {
                if (!p.hasPermission("world16.back.tp")) {
                    api.PermissionErrorMessage(p);
                    return true;
                }
                boolean indexExists = 1 >= 0 && 1 < backm.get(p.getDisplayName()).size();
                if (backm.get(p.getDisplayName()) != null && indexExists) {
                    if(backm.get(p.getDisplayName()).get(1) == null){
                        p.sendMessage(Translate.chat("&4Something went wrong."));
                        return true;
                    }
                    Location tpLoc = backm.get(p.getDisplayName()).get(1);
                    p.teleport(tpLoc);
                } else {
                    p.sendMessage(Translate.chat("&4Something went wrong."));
                    return true;
                }


            } else if (args[0].equalsIgnoreCase("set")) {
                if (!p.hasPermission("world16.back.set")) {
                    api.PermissionErrorMessage(p);
                    return true;
                }
                if (backm.get(p.getDisplayName()) != null) {
                    if(backm.get(p.getDisplayName()).get(2) == null){
                        p.sendMessage(Translate.chat("&4Something went wrong."));
                        return true;
                    }
                    backm.get(p.getDisplayName()).set(2, p.getLocation());
                    p.sendMessage(Translate.chat("[&9Back&r] &aYour back has been set."));
                    return true;
                } else {
                    return true;
                }

            } else if (args[0].equalsIgnoreCase("goto")) {
                if (!p.hasPermission("world16.back.goto")) {
                    api.PermissionErrorMessage(p);
                    return true;
                }
                boolean indexExists = 2 >= 0 && 2 < backm.get(p.getDisplayName()).size();
                if (backm.get(p.getDisplayName()) != null && indexExists) {
                    if(backm.get(p.getDisplayName()).get(2) == null){
                        p.sendMessage(Translate.chat("&4Something went wrong."));
                        return true;
                    }
                    p.teleport(backm.get(p.getDisplayName()).get(2));
                    p.sendMessage(Translate.chat("[&9Back&r] &aTheir you go."));
                    return true;
                } else {
                    p.sendMessage(Translate.chat("&4Something went wrong."));
                    return true;
                }
            }
        }
        return true;
    }
}