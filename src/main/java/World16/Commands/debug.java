package World16.Commands;

import World16.Events.OnDeathEvent;
import World16.Events.OnJoinEvent;
import World16.Main.Main;
import World16.MysqlAPI.MySQL;
import World16.Translate.Translate;
import World16.Utils.API;
import World16.Utils.CustomYmlManger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.json.simple.parser.ParseException;

public class debug implements CommandExecutor {

  HashMap<String, String> keyDataM = OnJoinEvent.keyDataM;
  LinkedHashMap<String, Location> backmap = OnDeathEvent.backmap;
  ArrayList<String> Afk1 = afk.Afk;
  ArrayList<String> Fly1 = fly.Fly;
  API api = new API();
  MySQL mysql = new MySQL();
  private Main plugin;
  private CustomYmlManger configinstance = null;

  public debug(CustomYmlManger getCustomConfig, Main getPlugin) {
    this.configinstance = getCustomConfig;
    this.plugin = getPlugin;

    this.plugin.getCommand("debug1-6").setExecutor(this);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    Player p = (Player) sender;

    // IF THE PLAYER IS THE CONSOLE OR COMMAND BLOCk
    if (!(sender instanceof Player)) {
      sender.sendMessage("Only Players Can Use This Command.");
      return true;
    }
    if (args.length == 0) {
      if (!p.hasPermission("command.debug.permission")) { // Permission
        api.PermissionErrorMessage(p);
        return true;
      }
      p.sendMessage(Translate.chat("/debug1-6 op"));
      p.sendMessage(Translate.chat("/debug1-6 defaultstuff"));
      p.sendMessage(Translate.chat("/debug1-6 checkhashmaps"));
      p.sendMessage(Translate.chat("/debug1-6 clearallarraylists"));
      p.sendMessage(Translate.chat("/debug1-6 clearallhashmaps"));
      p.sendMessage(Translate.chat("/debug1-6 clearallhashmapswithname"));
      p.sendMessage(Translate.chat("/debug1-6 date"));
      p.sendMessage(Translate.chat("/debug1-6 playerversion"));
      p.sendMessage(Translate.chat("/debug1-6 checkuuid"));
      p.sendMessage(Translate.chat("/debug1-6 sql"));
      //p.sendMessage(World16.Translate.chat("/debug1-6 "));
      return true;
    } else if (args.length >= 1) {

      //OP
      if (args[0].equalsIgnoreCase("op")) {
        if (!p.hasPermission("command.debug.op.permission")) { // Permission
          api.PermissionErrorMessage(p);
          return true;
        }
        p.sendMessage(Translate.chat("&4Debug working oping andrew and tyler and richard"));
        // Ops Andrew
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        String command = "op andrew121410";
        Bukkit.dispatchCommand(console, command);
        String command1 = "op AlphaGibbon43";
        Bukkit.dispatchCommand(console, command1);
        String command3 = "op Robobros3";
        Bukkit.dispatchCommand(console, command3);
        p.sendMessage(Translate.chat("&4There."));
        return true;

        //DEFAULT STUFF
      } else if (args.length == 1 && (args[0].equalsIgnoreCase("defaultstuff"))) {
        if (!p.hasPermission("command.debug.defaultstuff.permission")) { // Permission
          api.PermissionErrorMessage(p);
          return true;
        }
        this.plugin.getConfig().set("TittleTOP", "&f&l[&4World 1-6&f&l]");
        this.plugin.getConfig().set("TittleBOTTOM", "&9&oHome Of Minecraft Fire Alarms.");
        this.plugin.getConfig().set("TablistTOP", "&f&l[&4World 1-6&f&l]");
        this.plugin.getConfig().set("TablistBOTTOM", "&9&oHome Of Minecraft Fire Alarms.");
        this.plugin.saveConfig();
        this.plugin.reloadConfig();
        p.sendMessage(Translate.chat("&bOK..."));
        return true;

        //CHECK HASHMAPS
      } else if (args.length >= 1 && (args[0].equalsIgnoreCase("checkhashmaps"))) {
        if (args.length == 1) {
          p.sendMessage(Translate.chat(
              "[&3/debug1-6 checkhashmaps &5@checkmine&r] &9<-- show's what is stored in the HashMap of you."));
          p.sendMessage(Translate.chat(
              "[&3/debug1-6 checkhashmaps &c@all&r] &9<-- Show's everything in the HashMap"));
          return true;
        } else {
          if (args.length >= 1) {
            if (args[1].equalsIgnoreCase("@all")) {
              p.sendMessage(String.valueOf(Arrays.asList(keyDataM)));
              p.sendMessage(String.valueOf(Arrays.asList(backmap)));
            } else if (args[1].equalsIgnoreCase("@checkmine")) {
              p.sendMessage(Translate.chat(
                  "Here's the HashMap for World16.Events.OnJoinEvent.keyDatam: " + keyDataM
                      .get(p.getDisplayName())));
              p.sendMessage(Translate.chat(
                  "Here's the HashMap for World16.Events.OnDeathEvent.backmap*death" + backmap
                      .get(p.getDisplayName())));
              p.sendMessage(Translate.chat(
                  "Here's the HashMap for World16.Events.OnDeathEvent.backmap*death" + backmap
                      .get(p.getDisplayName() + "death")));
              p.sendMessage(Translate.chat(
                  "Here's the HashMap for World16.Events.OnDeathEvent.backmap*death" + backmap
                      .get(p.getDisplayName() + "tp")));
              p.sendMessage(Translate.chat(
                  "Here's the HashMap for World16.Events.OnDeathEvent.backmap*death" + backmap
                      .get(p.getDisplayName() + "set")));

            }
          }
        }

        //CLEAR ALL ARRAYLIST
      } else if (args.length == 1 && (args[0].equalsIgnoreCase("clearallarraylists"))) {
        if (!p.hasPermission("command.debug.clearallarraylists.permission")) { // Permission
          api.PermissionErrorMessage(p);
          return true;
        }
        api.clearAllArrayList();
        p.sendMessage(Translate.chat("&bOK..."));
        return true;

        //CLEAR ALL HASHMAPS
      } else if (args.length == 1 && (args[0].equalsIgnoreCase("clearallhashmaps"))) {
        if (!p.hasPermission("command.debug.clearallhashmaps.permission")) { // Permission
          api.PermissionErrorMessage(p);
          return true;
        }
        api.clearAllHashMaps();
        p.sendMessage(Translate.chat("&bOK..."));
        return true;

        //CLEAR ALL HASHMAPS WITH THE NAME.
      } else if (args.length == 1 && (args[0].equalsIgnoreCase("clearallhashmapswithname"))) {
        if (!p.hasPermission(
            "command.debug.clearallhashmapswithname.permission")) { // Permission
          api.PermissionErrorMessage(p);
          return true;
        }
        api.clearAllHashMaps(p);
        return true;

        //DATE
      } else if (args.length == 1 && (args[0].equalsIgnoreCase("date"))) {
        if (!p.hasPermission("command.debug.date.permission")) { // Permission
          api.PermissionErrorMessage(p);
          return true;
        }
        String date = api.Time();
        p.sendMessage(Translate.chat("Time/Data:-> " + date));
        return true;

        //PLAYER VERSION
      } else if (args.length >= 1 && (args[0].equalsIgnoreCase("playerversion"))) {
        if (!p.hasPermission("command.debug.playerversion.permission")) {
          api.PermissionErrorMessage(p);
          return true;
        }
        if (args.length == 1) {
          p.sendMessage(api.getPlayerVersion(p));
        } else {
          Player target = plugin.getServer().getPlayerExact(args[1]);
          if (args.length >= 1 && target != null && target.isOnline()) {
            if (!p.hasPermission("command.debug.playerversion.other.permission")) {
              api.PermissionErrorMessage(p);
              return true;
            }
            p.sendMessage("The Player: " + target.getDisplayName() + " Version: " + api
                .getPlayerVersion(target) + " Server Version: " + api
                .getServerVersion());
          } else {
            return true;
          }
        }
      } else if (args.length >= 1 && (args[0].equalsIgnoreCase("checkuuid"))) {
        if (!p.hasPermission("command.debug.checkuuid.permission")) {
          api.PermissionErrorMessage(p);
          return true;
        }
        if (args.length == 1) {
          p.sendMessage(Translate.chat(p.getUniqueId().toString()));
        } else {
          Player target = plugin.getServer().getPlayerExact(args[1]);
          if (args.length == 2 && target != null && target.isOnline()) {
            if (!p.hasPermission("command.debug.checkuuid.other.permission")) {
              api.PermissionErrorMessage(p);
              return true;
            }
            try {
              String uuidtarget = api.getUUIDFromMojangAPI(target.getDisplayName());
              p.sendMessage(Translate
                  .chat("UUID: " + uuidtarget + " FOR " + target.getDisplayName()));

            } catch (IOException e) {
              e.printStackTrace();
            } catch (ParseException e) {
              e.printStackTrace();
            }
          } else if (args.length >= 3 && args[1] != null && args[2] != null && args[2]
              .equalsIgnoreCase("@offline")) {
            try {
              String uuidtarget2 = api.getUUIDFromMojangAPI(args[1].toString());
              p.sendMessage(
                  Translate.chat("UUID: " + uuidtarget2 + " FOR " + args[1]));
            } catch (IOException e) {
              e.printStackTrace();
            } catch (ParseException e) {
              e.printStackTrace();
            }
          }
        }
        //}
        //SQL
      } else if (args.length >= 2 && (args[0].equalsIgnoreCase("sql"))) {
        if (!p.hasPermission("command.debug.sql.permission")) { // Permission
          api.PermissionErrorMessage(p);
          return true;
        }
        // String Builder
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
          builder.append(args[i] + " ");
        }
        String msg = builder.toString();
        mysql.Connect();
        mysql.ExecuteCommand(msg);
        p.sendMessage(Translate.chat("&4&lYour command has been executed thru SQL."));
        p.sendMessage(Translate.chat("&aHere's the command you did &r" + msg));
        return true;
      } else {
      }
    }
    return true;
  }
}