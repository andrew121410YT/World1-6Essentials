package World16.Commands;

import World16.Main.Main;
import World16.Translate.Translate;
import World16.Utils.API;
import World16.Utils.CustomYmlManger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class flyspeed implements CommandExecutor {

  API api = new API();
  private Main plugin;

  // NEW ONE
  private CustomYmlManger configinstance = null;

  public flyspeed(CustomYmlManger getConfigInstance, Main plugin) {
    this.configinstance = getConfigInstance;
    this.plugin = plugin;
    plugin.getCommand("fs").setExecutor(this);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("Only Players Can Use This Command.");
      return true;
    }
    Player player = (Player) sender;

    if (args.length == 0) {
      if (!player.hasPermission("command.fs.permission")) {
        api.PermissionErrorMessage(player);
        return true;
      }
      player.sendMessage(Translate.chat("&4Usage: /fs <Number>"));
      player.sendMessage(Translate.chat("&6Remember that the default flight speed is &a1"));
    }
    if (args.length == 1) {
      if (!player.hasPermission("command.fs.permission")) {
        api.PermissionErrorMessage(player);
        return true;
      }
      String oldnum = args[0];
      double num = Double.parseDouble(oldnum);
      int num1 = (int) num;
      if ((num1 > -1) && (num1 < 11)) {
        double num2 = num1;
        float flyspeed = (float) (num2 / 10.0D);

        player.setFlySpeed(flyspeed);
        player.sendMessage(
            ChatColor.GOLD + "[FlySpeed]  " + ChatColor.YELLOW + "Your flyspeed now equals: "
                + ChatColor.RED + "[" + flyspeed * 10.0F + "]" + ChatColor.YELLOW + "!");
        configinstance.getshit().set(player.getDisplayName() + ".flyspeed",
            Float.valueOf(flyspeed));
        configinstance.saveshitsilent();
        return true;
      }
      player.sendMessage(ChatColor.GOLD + "[FlySpeed]  " + ChatColor.RED
          + "Your input is not valid! must be between 0 and 10.");
      return true;
    }
    return true;
  }
}