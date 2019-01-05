package test;

import MysqlAPI.MySQL;
import Utils.API;
import Utils.CustomYmlManger;
import World16.World16.World16.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class test1 implements CommandExecutor {

  private Main plugin;

  MySQL mysql = new MySQL();
  API api = new API();

  private CustomYmlManger configinstance = null;

  public test1(CustomYmlManger getCustomYml, World16.World16.World16.Main getPlugin){
    this.configinstance = getCustomYml;
      this.plugin = getPlugin;

    this.plugin.getCommand("testee1").setExecutor(this);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("Only Players Can Use This Command.");
      return true;
    }
    Player p = (Player) sender;
    if (!p.hasPermission("command.testee1.permission")){
      api.PermissionErrorMessage(p);
      return true;
    }
    if (args.length == 0) {
      //SOMETHING HERE
    }else if (args.length >= 1) {
      //SOMETHING HERE
      return true;
    }else{
      p.sendMessage("Something messed up!");
      return true;
    }
    return true;
  }
  }