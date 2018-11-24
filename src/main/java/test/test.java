package test;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import Utils.API;
import Utils.CustomYmlManger;
import World16.World16.World16.Main;

public class test implements CommandExecutor {

  private Main plugin;
  API api = new API();
  // ArrayList<String> Afk1 = afk.Afk;
  // NEW ONE ..

  private CustomYmlManger configinstance = null;

  public test(CustomYmlManger getConfigInstance, World16.World16.World16.Main plugin) {
    this.configinstance = getConfigInstance;
    this.plugin = plugin;
    plugin.getCommand("testee").setExecutor(this);

    // OLD ONE

    // public bed(World16.World16.World16.Main plugin){
    // this.plugin = plugin;
    // plugin.getCommand("bed").setExecutor(this);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("Only Players Can Use This Command.");
      return true;
    }

    Player p = (Player) sender;

    if (p.hasPermission("command.test.permission")) {
      // STUFF GOES HERE'
      return true;
    } else {
      api.PermissionErrorMessage(p);
    }
    return true;
  }
}
