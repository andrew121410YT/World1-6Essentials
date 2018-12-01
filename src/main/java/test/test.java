package test;

import Utils.API;
import Utils.CustomYmlManger;
import World16.World16.World16.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class test implements CommandExecutor {

  private Main plugin;
  API api = new API();

  private CustomYmlManger configinstance = null;

  public test(CustomYmlManger getCustomYml, World16.World16.World16.Main getPlugin) {
    this.configinstance = getCustomYml;
    this.plugin = getPlugin;

    this.plugin.getCommand("testee").setExecutor(this);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("Only Players Can Use This Command.");
      return true;
    }

    Player p = (Player) sender;

    if (!p.hasPermission("command.test.permission")) {
      api.PermissionErrorMessage(p);
      return true;
    }
    //STUFF HERE
    return true;
  }
}