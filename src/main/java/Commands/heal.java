package Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import Translate.Translate;
import Utils.API;
import World16.World16.World16.Main;

public class heal implements CommandExecutor {

  private Main plugin;
  API api = new API();

  public heal(World16.World16.World16.Main plugin) {
    this.plugin = plugin;
    plugin.getCommand("heal").setExecutor(this);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("Only Players Can Use This Command.");
      return true;
    }

    Player p = (Player) sender;

    if (!p.hasPermission("command.heal.permission")) {
      api.PermissionErrorMessage(p);
      return true;
    }
    if (args.length == 0) {
    p.setHealth(20.0D);
    p.setFoodLevel(20);
    p.setFireTicks(0);
    for (PotionEffect effect : p.getActivePotionEffects())
      p.removePotionEffect(effect.getType());
    p.sendMessage(Translate.chat("&6You have been healed."));
    return true;
  }else {
    Player target = plugin.getServer().getPlayerExact(args[0]);
    if (args.length >= 1 && target != null && target.isOnline()) {
      if (!p.hasPermission("command.heal.other.permission")) {
        api.PermissionErrorMessage(p);
        return true;
      }
      target.setHealth(20.0D);
      target.setFoodLevel(20);
      target.setFireTicks(0);
      for (PotionEffect effect : target.getActivePotionEffects())
        target.removePotionEffect(effect.getType());
      p.sendMessage(Translate.chat("&6You just healed " + target.getDisplayName()));
    }else {
      p.sendMessage(Translate.chat("&cUsage: for yourself do /heal OR /heal <Player>"));
    }
  }
    return true;
  }
}
