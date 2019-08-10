package World16.Commands;

import World16.Main.Main;
import World16.Managers.CustomConfigManager;
import World16.Utils.API;
import World16.Utils.Translate;
import World16Elevators.Objects.BoundingBox;
import World16Elevators.Objects.ElevatorObject;
import World16Elevators.Objects.FloorObject;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class elevator implements CommandExecutor {

    private Main plugin;
    private API api;

    private CustomConfigManager customConfigManager;
    private WorldEditPlugin worldEditPlugin;

    private Map<String, ElevatorObject> elevatorObjectMap;

    public elevator(Main plugin, CustomConfigManager customConfigManager) {
        this.plugin = plugin;

        this.customConfigManager = customConfigManager;
        this.api = new API(this.plugin);

        this.plugin.getCommand("elevator").setExecutor(this);
        this.worldEditPlugin = this.plugin.getOtherPlugins().getWorldEditPlugin();
        this.elevatorObjectMap = this.plugin.getSetListMap().getElevatorObjectMap();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {

            if (!(sender instanceof BlockCommandSender)) {
                return true;
            }

            BlockCommandSender cmdblock = (BlockCommandSender) sender;
            Block commandblock = cmdblock.getBlock();

            if (args.length == 3 && args[0].equalsIgnoreCase("goto")) {
                String elevatorName = args[1].toLowerCase();
                int floorNum = api.asIntOrDefault(args[2], 0);

                if (elevatorObjectMap.get(elevatorName) == null) {
                    return true;
                }

                if (elevatorObjectMap.get(elevatorName).getFloorsMap().get(floorNum) == null) {
                    return true;
                }

                elevatorObjectMap.get(elevatorName).goToFloor(floorNum);
                return true;
            }

            return true;
        }

        Player p = (Player) sender;
        if (!p.hasPermission("world16.elevator")) {
            api.PermissionErrorMessage(p);
            return true;
        }

        if (args.length == 0) {
            p.sendMessage(Translate.chat("Elevator Help"));
            p.sendMessage(Translate.chat("/elevator create <ELEName>"));
            p.sendMessage(Translate.chat("/elvator addfloor <ELEName> <FloorNumber>"));
            p.sendMessage(Translate.chat("/elevator removefloor <ELEName> <FloorNumber>"));
            p.sendMessage(Translate.chat("/elevator goto <ELEName> <FloorNumber>"));
            //SOMETHING HERE
            return true;
        } else {
            //SOMETHING HERE
            if (args.length == 2 && args[0].equalsIgnoreCase("create")) {
                Selection selection = worldEditPlugin.getSelection(p);
                if (selection == null) {
                    p.sendMessage("Please make a selection with WorldEdit and then redo the command please.");
                    return true;
                }
                String elevatorName = args[1].toLowerCase();
                BoundingBox boundingBox = new BoundingBox(selection.getMinimumPoint().toVector(), selection.getMaximumPoint().toVector());
                ElevatorObject elevatorObject = new ElevatorObject(plugin, p.getWorld().getName().toLowerCase(), elevatorName, new FloorObject(0, api.getBlockPlayerIsLookingAt(p).getLocation(), boundingBox));
                elevatorObjectMap.putIfAbsent(elevatorName, elevatorObject);
                p.sendMessage(Translate.chat("You have made an elevator!"));
                return true;
            }
            if (args.length == 3 && args[0].equalsIgnoreCase("addfloor")) {
                String elevatorName = args[1].toLowerCase();
                int floorNum = api.asIntOrDefault(args[2], 1);

                Selection selection = worldEditPlugin.getSelection(p);
                if (selection == null) {
                    p.sendMessage("Please make a selection with WorldEdit and then redo the command please.");
                    return true;
                }

                if (elevatorObjectMap.get(elevatorName) == null) {
                    p.sendMessage(Translate.chat("That elevator doesn't exist."));
                    return true;
                }
                BoundingBox boundingBox = new BoundingBox(selection.getMinimumPoint().toVector(), selection.getMaximumPoint().toVector());
                elevatorObjectMap.get(elevatorName).addFloor(new FloorObject(floorNum, api.getBlockPlayerIsLookingAt(p).getLocation(), boundingBox));
                p.sendMessage(Translate.chat("Floor: " + floorNum + " has been added to the elevator: " + elevatorName));
                return true;
            }
            if (args.length == 3 && args[0].equalsIgnoreCase("removefloor")) {
                String elevatorName = args[1].toLowerCase();
                int floorNum = api.asIntOrDefault(args[2], 0);

                if (elevatorObjectMap.get(elevatorName) == null) {
                    p.sendMessage(Translate.chat("That elevator doesn't exist."));
                    return true;
                }

                elevatorObjectMap.get(elevatorName).removeFloor(floorNum);
                p.sendMessage(Translate.chat("The floor: " + floorNum + " has been removed for the elevator: " + elevatorName));
                return true;
            }

            if (args.length == 1 && args[0].equalsIgnoreCase("save")) {
                this.plugin.getElevatorMain().saveAllElevators();
                p.sendMessage(Translate.chat("All elevators have been saved."));
                return true;
            }

            if (args.length == 1 && args[0].equalsIgnoreCase("load")) {
                this.plugin.getElevatorMain().loadAllElevators();
                p.sendMessage(Translate.chat("All elevators have been loaded in memory."));
                return true;
            }

            if (args.length == 1 && args[0].equalsIgnoreCase("clear")) {
                elevatorObjectMap.clear();
                p.sendMessage(Translate.chat("All elevators have been cleared in memory."));
                return true;
            }

            if (args.length == 3 && args[0].equalsIgnoreCase("goto")) {
                String elevatorName = args[1].toLowerCase();
                int floorNum = api.asIntOrDefault(args[2], 0);

                if (elevatorObjectMap.get(elevatorName) == null) {
                    p.sendMessage(Translate.chat("That elevator doesn't exist."));
                    return true;
                }

                if (elevatorObjectMap.get(elevatorName).getFloorsMap().get(floorNum) == null) {
                    p.sendMessage(Translate.chat("Floor doesn't exist on this elevator"));
                    return true;
                }

                elevatorObjectMap.get(elevatorName).goToFloor(floorNum);
                p.sendMessage(Translate.chat("Going to floor: " + floorNum + " for the elevator: " + elevatorName));
                return true;
            }

            if (args.length == 2 && args[0].equalsIgnoreCase("stop")) {
                String elevatorName = args[1].toLowerCase();

                if (elevatorObjectMap.get(elevatorName) == null) {
                    p.sendMessage(Translate.chat("That elevator doesn't exist."));
                    return true;
                }

                elevatorObjectMap.get(elevatorName).emergencyStop();
                p.sendMessage(Translate.chat("emergency stop has been activated."));
                return true;
            }
            return true;
        }
    }
}