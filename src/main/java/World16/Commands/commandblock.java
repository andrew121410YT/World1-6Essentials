package World16.Commands;

import World16.Main.Main;
import World16.Utils.API;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class commandblock implements CommandExecutor {

  API api = new API();
  private Main plugin;

  public commandblock(Main getPlugin) {
    this.plugin = getPlugin;
    this.plugin.getCommand("commandblock").setExecutor(this);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("Only Players Can Use This Command.");
      return true;
    }

    Player p = (Player) sender;

    if (!p.hasPermission("world16.commandblock")) {
      api.PermissionErrorMessage(p);
      return true;
    }
    ItemStack item1 = new ItemStack(Material.COMMAND, 1);
    item1.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
    p.getInventory().addItem(item1);
    return true;
  }
}
