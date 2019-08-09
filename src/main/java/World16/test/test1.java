package World16.test;

import World16.Main.Main;
import World16.Managers.CustomConfigManager;
import World16.Utils.API;
import World16Elevators.Objects.ElevatorObject;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
            //SOMETHING HERE
            Location locationM = new Location(p.getWorld(), -4, 64, -1);
            Location locationL = new Location(p.getWorld(), -2, 64, -3);
            Location locationT = new Location(p.getWorld(), -6, 68, 1);
            ElevatorObject elevatorObject = new ElevatorObject(plugin, "hello", 0, locationM, locationL, locationT);
            elevatorObject.armorStandSetup();
            elevatorObject.goToFloor(1);
            return true;
        } else {
            //SOMETHING HERE
            return true;
        }
    }
}