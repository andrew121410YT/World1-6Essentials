package World16.Commands.tp;

import World16.Main.Main;
import World16.MysqlAPI.MySQL;
import World16.Translate.Translate;
import World16.Utils.API;
import World16.Utils.CustomYmlManger;
import java.util.LinkedHashMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class tpdeny implements CommandExecutor {

  private Main plugin;

  MySQL mysql = new MySQL();
  API api = new API();

  //HASHMAPS
  private static LinkedHashMap<Player, Player> tpam = tpa.tpam;

  private CustomYmlManger configinstance = null;

  public tpdeny(CustomYmlManger getCustomYml, Main getPlugin) {
    this.configinstance = getCustomYml;
    this.plugin = getPlugin;

    this.plugin.getCommand("tpdeny").setExecutor(this);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("Only Players Can Use This Command.");
      return true;
    }
    Player p = (Player) sender;
    if (!p.hasPermission("command.tpdeny.permission")) {
      api.PermissionErrorMessage(p);
      return true;
    }
    if (args.length == 0) {
      if (tpam.get(p) != null) {
        p.sendMessage(Translate.chat("&9Ok you denied the tp request."));
        tpam.get(p).sendMessage(
            Translate.chat("[&eTPA&r] &cYour tpa request got denied from " + p.getDisplayName()));
      } else {
        p.sendMessage(Translate.chat("&4Something went wrong."));
      }
    } else {
      p.sendMessage(Translate.chat("&4???"));
    }
    return true;
  }
}