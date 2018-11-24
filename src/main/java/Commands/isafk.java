package Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import Translate.Translate;
import Utils.API;
import Utils.CustomYmlManger;
import World16.World16.World16.Main;

public class isafk implements CommandExecutor {

  private Main plugin;
  API api = new API();
  // ArrayList<String> Afk1 = afk.Afk;
  // NEW ONE ..

  private CustomYmlManger configinstance = null;

  public isafk(CustomYmlManger getConfigInstance, World16.World16.World16.Main plugin) {
    this.configinstance = getConfigInstance;
    this.plugin = plugin;
    plugin.getCommand("isafk").setExecutor(this);

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

    if (!p.hasPermission("command.isafk.permission")) {
      api.PermissionErrorMessage(p);
      return true;
    }
    // STUFF GOES HERE
    if (args.length > 0) {
      Player playerDONE = Bukkit.getServer().getPlayer(args[1]);
      if (api.isAfk(playerDONE)) {

        p.sendMessage(Translate.chat("OMG IT WORKED."));
      }
    }
    return true;
  }
}
