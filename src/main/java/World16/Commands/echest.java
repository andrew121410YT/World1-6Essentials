package World16.Commands;

import World16.Main.Main;
import World16.Utils.API;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class echest implements CommandExecutor {

    private Main plugin;
    private API api;

    public echest(Main plugin) {
        this.plugin = plugin;
        this.api = new API(this.plugin);
        plugin.getCommand("echest").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission("world16.echest")) {
            api.PermissionErrorMessage(p);
            return true;
        }
        p.openInventory(p.getEnderChest());
        p.playSound(p.getLocation(), Sound.BLOCK_ENDERCHEST_OPEN, 10.0f, 1.0f);
        return true;
    }
}
