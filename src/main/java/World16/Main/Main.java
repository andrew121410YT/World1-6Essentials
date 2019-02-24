package World16.Main;

import World16.Commands.*;
import World16.Commands.tp.tpa;
import World16.Commands.tp.tpaccept;
import World16.Commands.tp.tpdeny;
import World16.CustomConfigs.CustomConfigManager;
import World16.CustomInventorys.CustomInventoryManager;
import World16.Events.*;
import World16.Objects.KeyObject;
import World16.Objects.UserObject;
import World16.Utils.API;
import World16.Utils.Metrics;
import World16.Utils.Translate;
import World16.test.test;
import World16.test.test1;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;

public class Main extends JavaPlugin {//implements Listener {

    private static Main plugin;

    //Configs
    private CustomConfigManager customconfig;

    private API api;

    //Lists
    List<String> Afk = afk.Afk;
    List<String> Fly = fly.Fly;
    List<String> GodM = god.godm;
    //...

    //Maps
    Map<String, KeyObject> keyDataM = OnJoinEvent.keyDataM;
    Map<String, UserObject> backm = back.backm;
    Map<Player, Player> tpam = tpa.tpam;
    //...
    PluginManager pm = Bukkit.getPluginManager();

    public void onEnable() {
        plugin = this;
        regCustomConfigManager();
        regFileConfigGEN();
        regAPIS();
        regEvents();
        regCommands();
        regbstats();
        getLogger().info("[World1-6Essentials] is now loaded!");
    }

    public void onDisable() {
        this.clear();
        getLogger().info("[World1-6Essentials] is now disabled.");
    }

    public void clear() {
        Afk.clear();
        Fly.clear();
        GodM.clear();

        keyDataM.clear();
        backm.clear();
        tpam.clear();
    }

    public void regCommands() {
        new gmc(this);
        new gms(this);
        new gmsp(this);
        new gma(this);

        new day(this);
        new night(this);
        new feed(this);
        new heal(this);
        new fly(this);
        new debug(this);
        new commandblock(this);
        new bed(this);
        new ram(this);
        new spawn(this.customconfig, this);
        new echest(this);
        new sign(this);
        new key(this); //KEY COMMAND
        new colors(this);
        new setjail(this.customconfig, this);
        new setspawn(this.customconfig, this);
        new jail(this.customconfig, this);
        new afk(this);
        new flyspeed(this.customconfig, this);
        new isafk(this.customconfig, this);
        new back(this);
        new broadcast(this.customconfig, this);
        new god(this);

        new tpa(this.customconfig, this);
        new tpaccept(this.customconfig, this);
        new tpdeny(this.customconfig, this);

        new test(customconfig, this);
        new test1(customconfig, this);
    }

    public void regEvents() {
        //Bukkit.getServer().getPluginManager().registerEvents(this, this);
        new OnJoinEvent(this);
        new OnLeaveEvent(this);
        //...
        new OnDeathEvent(this);
        new PlayerDamageEvent(this);
        new OnTpEvent(this);
        new PistonEvent(this);
        //...
        new OnBedEnterEvent(this);
        new OnJoinTitleEvent(this);
        //...
        new InventoryClickEvent(this);
        new AsyncPlayerChatEvent(this);
        new CustomInventoryManager().registerAllCustomInventorys();
    }

    public void regFileConfigGEN() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.reloadConfig();
    }

    public void regCustomConfigManager() {
        this.customconfig = new CustomConfigManager();
        customconfig.registerAllCustomConfigs();
    }

    public void checkForPlugins() {

    }

    public void regAPIS() {
        api = new API();
    }

    public void regbstats() {
        Metrics metrics = new Metrics(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        {
            Player p = (Player) sender;

            if (cmd.getName().equalsIgnoreCase("World1-6Essentials")) {
                if (args.length >= 0) {
                    p.sendMessage(Translate.chat("&6Made By Andrew121410 My -> Discord: Andrew121410#2035"));
                    return true;
                }
            }
        }
        return true;
    }

    public static Main getPlugin() {
        return plugin;
    }

    public API getApi() {
        return this.api;
    }

}