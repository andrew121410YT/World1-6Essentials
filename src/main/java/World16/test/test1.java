package World16.test;

import World16.Main.Main;
import World16.Managers.CustomConfigManager;
import World16.Utils.API;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class test1 implements CommandExecutor {

    private Main plugin;
    private API api;

    private CustomConfigManager customConfigManager;

    public test1(Main plugin, CustomConfigManager customConfigManager) {
        this.plugin = plugin;

        this.customConfigManager = customConfigManager;
        this.api = new API(this.plugin);

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
            Vector vector1 = p.getLocation().toVector();

            Vector vector2 = new Vector(654, 70, -188);
            Vector vector3 = new Vector(649, 73, -193);

            boolean yesorno = vector1.isInAABB(Vector.getMinimum(vector2, vector3), Vector.getMaximum(vector2, vector3));
            p.sendMessage("IN BOX: " + yesorno);

            Location locVector2 = vector2.toLocation(p.getWorld());
            Location locVector3 = vector3.toLocation(p.getWorld());
            Location locTempA = locVector3.subtract(locVector2);

            double tX = locTempA.getX();
            double tY = locTempA.getY();
            double tZ = locTempA.getZ();

            for (double x = tX; x < tX; x++) {
                for (double y = tY; y < tY; y++) {
                    for (double z = tZ; z < tZ; z++) {
                        Location locTempB = new Location(p.getWorld(), x, y, z);
                        Location locTempC = locTempB.add(locVector3);
                        Block block = p.getWorld().getBlockAt(locTempC);
                        block.setType(Material.LAPIS_BLOCK);
                        p.sendMessage(locTempC.toString());
                    }
                }
            }
            return true;
        } else {
            //SOMETHING HERE
            return true;
        }
    }
}