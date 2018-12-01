package Commands;

import Utils.API;
import World16.World16.World16.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class bed implements CommandExecutor {

    private Main plugin;
    API api = new API();

    public bed(World16.World16.World16.Main getPlugin) {
        this.plugin = getPlugin;
        this.plugin.getCommand("bed").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission("command.bed.permission")) {
            api.PermissionErrorMessage(p);
            return true;
        }
        ItemStack item1 = new ItemStack(Material.BED, 1);
        item1.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        p.getInventory().addItem(item1);
        return true;
    }
}