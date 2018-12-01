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

public class sign implements CommandExecutor {

  private Main plugin;
  API api = new API();

  public sign(World16.World16.World16.Main plugin) {
    this.plugin = plugin;
    plugin.getCommand("sign").setExecutor(this);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("Only Players Can Use This Command.");
      return true;
    }

    Player p = (Player) sender;

    if (!p.hasPermission("command.sign.permission")) {
      api.PermissionErrorMessage(p);
      return true;
    }
    ItemStack item1 = new ItemStack(Material.SIGN, 1);
    item1.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
    p.getInventory().addItem(item1);
    return true;
  }
}
