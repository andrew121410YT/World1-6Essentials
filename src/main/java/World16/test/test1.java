package World16.test;

import World16.Main.Main;
import World16.Managers.CustomConfigManager;
import World16.Utils.API;
import World16.Utils.SimpleMath;
import World16.Utils.Translate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.List;

public class test1 implements CommandExecutor {

    private Main plugin;
    private API api;
    private SimpleMath simpleMath;

    private CustomConfigManager customConfigManager;

    public test1(Main plugin, CustomConfigManager customConfigManager) {
        this.plugin = plugin;

        this.customConfigManager = customConfigManager;
        this.api = new API(this.plugin);
        this.simpleMath = new SimpleMath(this.plugin);

        this.plugin.getCommand("testee1").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }

        Player p = (Player) sender;
        if (!p.hasPermission("world16.testee1")) {
            api.PermissionErrorMessage(p);
            return true;
        }

        if (args.length == 0) {
            Vector playerVector = p.getLocation().toVector();

            Location loc2 = new Location(p.getWorld(), 655, 70, -187);
            Location loc3 = new Location(p.getWorld(), 649, 74, -193);

            Vector vector2 = loc2.toVector();
            Vector vector3 = loc3.toVector();

            boolean yesorno = simpleMath.isInAABBSimple(playerVector, vector2, vector3);
            p.sendMessage("IN BOX: " + yesorno);

            List<Location> locationsList = simpleMath.getHollowCubeNoOutline(loc2, loc3);

            for (Location location : locationsList) {
                p.getWorld().getBlockAt(location).setType(Material.REDSTONE_BLOCK);
            }

            Collection<Entity> entitiesInAABB = simpleMath.getEntitiesInAABB(p.getWorld(), vector2, vector3);

            for (Entity entity : entitiesInAABB) {
                p.sendMessage(entity.getName() + " OR " + entity.getCustomName());
            }

            Location center = this.simpleMath.getCenter(p.getWorld(), vector2, vector3);
            center.setY(loc2.getY());
            p.sendMessage(Translate.chat("&bDIAMOND_BLOCK: " + center.getBlockX() + " " + center.getBlockY() + " " + center.getBlockZ()));
            center.getBlock().setType(Material.DIAMOND_BLOCK);

            p.sendMessage(Translate.chat("DONE"));
            return true;
        } else {
            //SOMETHING HERE
            return true;
        }
    }
}