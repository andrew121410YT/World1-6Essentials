package Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import Utils.API;
import Utils.CustomYmlManger;
import World16.World16.World16.Main;

public class flyspeed implements CommandExecutor {

  private Main plugin;
  API api = new API();

  // NEW ONE

  private CustomYmlManger configinstance = null;

  public flyspeed(CustomYmlManger getConfigInstance, World16.World16.World16.Main plugin) {
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
    // STUFF GOES HERE
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
        configinstance.getshit().set(player.getName().toString() + ".flyspeed",
                Float.valueOf(flyspeed));
        configinstance.saveshit();
        return true;
      }
      player.sendMessage(ChatColor.GOLD + "[FlySpeed]  " + ChatColor.RED
              + "Your input is not valid! must be between 0 and 10.");
      return true;
    } else if (!player.hasPermission("command.fs.other.permission")) {
      api.PermissionErrorMessage(player);
      return true;
    }
    String oldnum = args[1];
    double num = Double.parseDouble(oldnum);
    int num1 = (int) num;
    if ((num1 > -1) && (num1 < 11)) {
      double num2 = num1;
      float flyspeed = (float) (num2 / 10.0D);

      boolean playerfound = false;
      for (Player player2 : Bukkit.getServer().getOnlinePlayers()) {
        if (player2.getName().equalsIgnoreCase(args[0])) {
          player2.setFlySpeed(flyspeed);
          player.sendMessage(ChatColor.GOLD + "[FlySpeed]  " + ChatColor.YELLOW
                  + player.getName().toString() + " set your flyspeed to: " + ChatColor.RED + "["
                  + flyspeed * 10.0F + "]" + ChatColor.YELLOW + "!");
          player.sendMessage(ChatColor.GOLD + "[FlySpeed]  " + ChatColor.YELLOW
                  + "You succesfully set " + player.getName().toString() + "'s flyspeed to: "
                  + ChatColor.RED + "[" + flyspeed * 10.0F + "]" + ChatColor.YELLOW + "!");
          playerfound = true;
          configinstance.getshit().set(player2.getName().toString() + ".flyspeed",
                  Float.valueOf(flyspeed));
          configinstance.saveshit();
          return true;
        }
        if (!playerfound) {
          player.sendMessage(ChatColor.GOLD + "[FlySpeed]  " + ChatColor.RED + "we could not find "
                  + args[0] + "!");
          return true;
        }
      }
    } else {
      player.sendMessage(ChatColor.GOLD + "[FlySpeed]  " + ChatColor.RED
              + "Your input is not valid! must be between 0 and 10.");
      return true;
    }
    return true;
  }
}
