package World16.Commands;

import World16.Main.Main;
import World16.Managers.CustomConfigManager;
import World16.TabComplete.ElevatorTab;
import World16.Utils.API;
import World16.Utils.Translate;
import World16Elevators.Objects.BoundingBox;
import World16Elevators.Objects.ElevatorObject;
import World16Elevators.Objects.FloorObject;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.Map;
import java.util.Queue;

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

        this.worldEditPlugin = this.plugin.getOtherPlugins().getWorldEditPlugin();
        this.elevatorObjectMap = this.plugin.getSetListMap().getElevatorObjectMap();

        this.plugin.getCommand("elevator").setExecutor(this);
        this.plugin.getCommand("elevator").setTabCompleter(new ElevatorTab(this.plugin));
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
            } else if (args.length == 4 && args[0].equalsIgnoreCase("call")) {
                String elevatorName = args[1].toLowerCase();
                int floorNum = api.asIntOrDefault(args[2], 0);
                int toFloorNum = api.asIntOrDefault(args[3], 0);

                if (elevatorObjectMap.get(elevatorName) == null) {
                    return true;
                }

                if (elevatorObjectMap.get(elevatorName).getFloorsMap().get(floorNum) == null || elevatorObjectMap.get(elevatorName).getFloorsMap().get(toFloorNum) == null) {
                    return true;
                }

                elevatorObjectMap.get(elevatorName).callElevator(floorNum, toFloorNum);
                return true;
            } else if (args.length == 3 && args[0].equalsIgnoreCase("click")) {
                String elevatorName = args[1].toLowerCase();
                String key = args[2].toLowerCase();

                if (elevatorObjectMap.get(elevatorName) == null) {
                    return true;
                }

                ElevatorObject elevatorObject = elevatorObjectMap.get(elevatorName);

                if (key.equalsIgnoreCase("@dxdydz")) {
                    elevatorObject.getPlayers().forEach(elevatorObject::clickMessageGoto);
                }
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
            p.sendMessage(Translate.chat("/elevator create <Shows help for creation of a elevator>"));
            p.sendMessage(Translate.chat("/elevator floor <Shows hel for the floor"));
            p.sendMessage(Translate.chat("/elevator goto <ELEName> <FloorNumber>"));
            return true;
        } else {

            //CREATE
            if (args[0].equalsIgnoreCase("create")) {
                if (args.length == 1) {
                    p.sendMessage(Translate.chat("[Elevator creation]"));
                    p.sendMessage(Translate.chat("/elevator create <ElevatorName> <XAX> <XAY> <XAZ> <XBX> <XBY> <XAZ>"));
                    return true;
                }
                if (args.length == 8) {
                    String elevatorName = args[1].toLowerCase();
                    int XAX = api.asIntOrDefault(args[2], 0);
                    int XAY = api.asIntOrDefault(args[3], 0);
                    int XAZ = api.asIntOrDefault(args[4], 0);

                    int XBX = api.asIntOrDefault(args[5], 0);
                    int XBY = api.asIntOrDefault(args[6], 0);
                    int XBZ = api.asIntOrDefault(args[7], 0);

                    Location down = new Location(p.getWorld(), XAX, XAY, XAZ);
                    Location up = new Location(p.getWorld(), XBX, XBY, XBZ);

                    Selection selection = worldEditPlugin.getSelection(p);
                    if (selection == null) {
                        p.sendMessage("Please make a selection with WorldEdit and then redo the command please.");
                        return true;
                    }
                    FloorObject floorObject = new FloorObject(0, api.getBlockPlayerIsLookingAt(p).getLocation(), new BoundingBox(selection.getMinimumPoint().toVector(), selection.getMaximumPoint().toVector()));
                    BoundingBox boundingBox = new BoundingBox(new Vector(XAX, XAY, XAZ), new Vector(XBX, XBY, XBZ));

                    ElevatorObject elevatorObject = new ElevatorObject(plugin, p.getWorld().getName(), elevatorName, floorObject, boundingBox);
                    elevatorObjectMap.putIfAbsent(elevatorName, elevatorObject);
                    p.sendMessage(Translate.chat("Elevator: " + elevatorName + " has been created"));
                    return true;
                }
            }

            //FLOOR
            if (args[0].equalsIgnoreCase("floor")) {
                if (args.length == 1) {
                    p.sendMessage(Translate.chat("[Elevator Floor Setup]"));
                    p.sendMessage(Translate.chat("/elevator floor create <ElevatorName> <FloorNumber>"));
                    p.sendMessage(Translate.chat("/elevator floor delete <ElevatorName> <FloorNumber>"));
                }
                if (args.length == 4 && args[1].equalsIgnoreCase("create")) {
                    String elevatorName = args[2].toLowerCase();
                    int floorNum = api.asIntOrDefault(args[3], 0);

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
                } else if (args.length == 4 && args[1].equalsIgnoreCase("delete")) {
                    String elevatorName = args[2].toLowerCase();
                    int floorNum = api.asIntOrDefault(args[3], 0);

                    if (elevatorObjectMap.get(elevatorName) == null) {
                        p.sendMessage(Translate.chat("That elevator doesn't exist."));
                        return true;
                    }

                    elevatorObjectMap.get(elevatorName).removeFloor(floorNum);
                    p.sendMessage(Translate.chat("The floor: " + floorNum + " has been removed from the elevator: " + elevatorName));
                    return true;
                }
                return true;
            }

            if (args.length == 2 && args[0].equalsIgnoreCase("delete")) {
                String elevatorName = args[1].toLowerCase();

                if (elevatorObjectMap.get(elevatorName) == null) {
                    p.sendMessage(Translate.chat("That elevator doesn't exist."));
                    return true;
                }
                this.plugin.getElevatorMain().deleteElevator(elevatorName);
                p.sendMessage(Translate.chat("Elevator: " + elevatorName + " has been deleted."));
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

            if (args.length == 4 && args[0].equalsIgnoreCase("call")) {
                String elevatorName = args[1].toLowerCase();
                int floorNum = api.asIntOrDefault(args[2], 0);
                int toFloorNum = api.asIntOrDefault(args[3], 0);

                if (elevatorObjectMap.get(elevatorName) == null) {
                    p.sendMessage(Translate.chat("That elevator doesn't exist."));
                    return true;
                }

                if (elevatorObjectMap.get(elevatorName).getFloorsMap().get(floorNum) == null || elevatorObjectMap.get(elevatorName).getFloorsMap().get(toFloorNum) == null) {
                    p.sendMessage(Translate.chat("Floor doesn't exist on this elevator"));
                    return true;
                }

                elevatorObjectMap.get(elevatorName).callElevator(floorNum, toFloorNum);
                p.sendMessage(Translate.chat("The elevator: " + elevatorName + " has been called to " + floorNum + " to go to " + toFloorNum));
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

            if (args[0].equalsIgnoreCase("queue")) {
                if (args.length == 1) {
                    p.sendMessage(Translate.chat("Elevator queue HELP"));
                    p.sendMessage(Translate.chat("/elevator queue list"));
                    p.sendMessage(Translate.chat("/elevator queue clear"));
                    return true;
                }

                if (args.length == 3 && args[1].equalsIgnoreCase("list")) {
                    String elevatorName = args[2].toLowerCase();

                    if (elevatorObjectMap.get(elevatorName) == null) {
                        p.sendMessage(Translate.chat("That elevator doesn't exist."));
                        return true;
                    }
                    Queue<Integer> floorQ = elevatorObjectMap.get(elevatorName).getFloorQueueBuffer();
                    Integer[] integers = floorQ.toArray(new Integer[0]);
                    p.sendMessage(Arrays.toString(integers));
                    return true;
                }
                if (args.length == 3 && args[1].equalsIgnoreCase("clear")) {
                    String elevatorName = args[2].toLowerCase();

                    if (elevatorObjectMap.get(elevatorName) == null) {
                        p.sendMessage(Translate.chat("That elevator doesn't exist."));
                        return true;
                    }

                    elevatorObjectMap.get(elevatorName).getFloorQueueBuffer().clear();
                    p.sendMessage(Translate.chat("The floor queue for the elevator: " + elevatorName + " has been cleared."));
                    return true;
                }
            }
            if (args.length == 2 && args[0].equalsIgnoreCase("click")) {
                String elevatorName = args[1].toLowerCase();

                if (elevatorObjectMap.get(elevatorName) == null) {
                    p.sendMessage(Translate.chat("That elevator doesn't exist."));
                    return true;
                }

                elevatorObjectMap.get(elevatorName).clickMessageGoto(p);
                return true;
            }
            return true;
        }
    }
}