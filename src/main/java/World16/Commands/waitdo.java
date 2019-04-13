package World16.Commands;

import World16.CustomConfigs.CustomConfigManager;
import World16.Main.Main;
import World16.Utils.API;
import World16.Utils.CountdownTimer;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class waitdo implements CommandExecutor {

    private Main plugin;

    private API api;

    private CustomConfigManager customConfigManager;

    public waitdo(CustomConfigManager getCustomYml, Main getPlugin) {
        this.customConfigManager = getCustomYml;
        this.plugin = getPlugin;
        this.api = new API(this.customConfigManager);

        this.plugin.getCommand("waitdo").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {

            if (!(sender instanceof BlockCommandSender)) {
                return true;
            }

            BlockCommandSender cmdblock = (BlockCommandSender) sender;
            Block commandblock = cmdblock.getBlock();

            if (args.length == 5) {
                int sec = api.asIntOrDefault(args[0], 1);
                String[] doStart = args[1].split(":");
                String[] doEnd = args[2].split(":");
                String[] doEverySec = args[3].split(":");
                boolean debug = api.asBooleanOrDefault(args[4], false);

                String doStartString = String.join(" ", doStart);
                String doEndString = String.join(" ", doEnd);
                String doEverySecString = String.join(" ", doEverySec);

                if (debug) {
                    this.plugin.getServer().broadcastMessage(sec + " " + doStartString + " " + doEndString + " " + doEverySecString);
                }

                CountdownTimer timer = new CountdownTimer(this.plugin, sec, () -> this.plugin.getServer().dispatchCommand(sender, doStartString), () -> this.plugin.getServer().dispatchCommand(sender, doEndString), (t) -> this.plugin.getServer().dispatchCommand(sender, doEverySecString));
                timer.scheduleTimer();
                return true;
            }
            return true;
        }

        Player p = (Player) sender;
        if (!p.hasPermission("world16.waitdo")) {
            api.PermissionErrorMessage(p);
            return true;
        }
        p.sendMessage("Only command blocks right now!");
        return true;
    }
}