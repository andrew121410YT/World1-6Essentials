package World16.World16.World16;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import Commands.afk;
import Commands.bed;
import Commands.colors;
import Commands.commandblock;
import Commands.day;
import Commands.debug;
import Commands.echest;
import Commands.feed;
import Commands.fly;
import Commands.flyspeed;
import Commands.gmc;
import Commands.gms;
import Commands.gmsp;
import Commands.heal;
import Commands.isafk;
import Commands.jail;
import Commands.night;
import Commands.ram;
import Commands.setjail;
import Commands.setspawn;
import Commands.sign;
import Commands.spawn;
import Events.OnBedEnter;
import Events.OnJoin;
import Events.OnJoinTitle;
import KeyCommands.Key;
import KeyCommands.MutiKeys;
import Translate.Translate;
import Utils.CustomYmlManger;
import Utils.Metrics;
import test.test;
import test.test1;

public class Main extends JavaPlugin implements Listener {

  public static Main instance;
  public static Main plugin;
  private CustomYmlManger customyml;
  ArrayList<String> Afk = afk.Afk;
  ArrayList<String> Fly = fly.Fly;
  // MySQL mysql = new MySQL(Main.plugin.getConfig().getString("MysqlHOST"),
  // Main.plugin.getConfig().getString("MysqlDATABASE"),
  // Main.plugin.getConfig().getString("MysqlUSER"),
  // Main.plugin.getConfig().getString("MysqlPASSWORD"));
  // GOT THE MYSQL API AT https://www.spigotmc.org/resources/simple-easy-mysql-api.36447/
  // GOT THE TITLE API AT https://www.spigotmc.org/resources/titleapi-1-8-1-13.1325/
  PluginManager pm = Bukkit.getPluginManager();

  @Override
  public void onEnable() {
    plugin = this;
    YmlConfigGen();
    FileConfigGen();
    eventsEnable();
    commandsEnable();
    bstats();
    getLogger().info("[World1-6Essentials] is now loaded!");
    // START OF UPDATER
    // FROM https://www.spigotmc.org/resources/api-pluginupdater-with-website.5578/
    // FINISH OF UPDATER
  }

  public void onDisable() {
    Afk.clear();
    Fly.clear();
    getLogger().info("[World1-6Essentials] is now disabled.");
  }

  public void commandsEnable() {
    new gmc(this);
    new gms(this);
    new gmsp(this);
    new day(this);
    new night(this);
    new feed(this);
    new heal(this);
    // new gm1(this);
    // new gm0(this);
    new fly(this);
    getCommand("debug1-6").setExecutor(new debug()); // debug1-6 command that ops you.
    new commandblock(this);
    new bed(this);
    new ram(this);
    // new gm3(this);
    new spawn(customyml, this);
    new echest(this);
    new sign(this);
    getCommand("key").setExecutor(new Key()); // KEY COMMAND
    getCommand("mkey").setExecutor(new MutiKeys()); // MKEY COMMAND
    new colors(this);
    new setjail(customyml, this);
    new setspawn(customyml, this);
    new jail(customyml, this);
    new afk(this);
    new flyspeed(customyml, this);
    new isafk(customyml, this);
    new test(customyml, this);
    getCommand("test1").setExecutor(new test1());
  }

  public void eventsEnable() {
    Bukkit.getServer().getPluginManager().registerEvents(this, this);
    new OnJoin(this);
    new OnBedEnter(this);
    new OnJoinTitle(this);
  }

  public void FileConfigGen() {
    getConfig().options().copyDefaults(true);
    saveConfig();
  }

  public void YmlConfigGen() {
    customyml = new CustomYmlManger();
    // Shit.yml
    customyml.setupshit();
    customyml.saveshit();
    customyml.reloadshit();
    // END OF Shit.yml
  }

  public void bstats() {
    Metrics metrics = new Metrics(this);
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    {
      Player p = (Player) sender;

      if (cmd.getName().equalsIgnoreCase("World1-6Essentials")) {
        if (args.length == 0) {
          p.sendMessage(Translate.chat("&6Made By Andrew121410 for 1-6 Server."));
          return true;
        }
      }
    }
    return false;
  }
}
