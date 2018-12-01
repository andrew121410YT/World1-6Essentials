package Commands;

import Utils.API;
import World16.World16.World16.Main;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class echest implements CommandExecutor {

    private Main plugin;
    API api = new API();

    public echest(World16.World16.World16.Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("echest").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission("command.echest.permission")) {
            api.PermissionErrorMessage(p);
            return true;
        }
        p.openInventory(p.getEnderChest());
        p.playSound(p.getLocation(), Sound.BLOCK_ENDERCHEST_OPEN, 10.0f, 1.0f);
        return true;
    }
}
