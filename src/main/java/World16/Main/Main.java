package World16.Main;

import World16.Commands.afk;
import World16.Commands.back;
import World16.Commands.bed;
import World16.Commands.colors;
import World16.Commands.commandblock;
import World16.Commands.day;
import World16.Commands.debug;
import World16.Commands.echest;
import World16.Commands.feed;
import World16.Commands.fly;
import World16.Commands.flyspeed;
import World16.Commands.gmc;
import World16.Commands.gms;
import World16.Commands.gmsp;
import World16.Commands.heal;
import World16.Commands.isafk;
import World16.Commands.jail;
import World16.Commands.night;
import World16.Commands.ram;
import World16.Commands.setjail;
import World16.Commands.setspawn;
import World16.Commands.sign;
import World16.Commands.spawn;
import World16.Commands.tp.tpa;
import World16.Commands.tp.tpaccept;
import World16.Commands.tp.tpdeny;
import World16.Events.OnBedEnterEvent;
import World16.Events.OnDeathEvent;
import World16.Events.OnJoinEvent;
import World16.Events.OnJoinTitleEvent;
import World16.Events.OnLeaveEvent;
import World16.Events.OnTpEvent;
import World16.KeyCommands.Key;
import World16.KeyCommands.MutiKeys;
import World16.Translate.Translate;
import World16.Utils.API;
import World16.Utils.CustomYmlManger;
import World16.Utils.Metrics;
import World16.test.test;
import World16.test.test1;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {//implements Listener {

    private static Main plugin;
    private CustomYmlManger customyml;
    private API api;

    //ARRAY LIST AND HASH MAPS
    ArrayList<String> Afk = afk.Afk;
    ArrayList<String> Fly = fly.Fly;
    HashMap<String, String> keyDataM = OnJoinEvent.keyDataM;
    LinkedHashMap<String, Location> backmap = OnDeathEvent.backmap;
    //END
    // GOT THE MYSQL API AT https://www.spigotmc.org/resources/simple-easy-mysql-api.36447/
    // GOT THE TITLE API AT https://www.spigotmc.org/resources/titleapi-1-8-1-13.1325/
    // FROM https://www.spigotmc.org/resources/api-pluginupdater-with-website.5578/
    PluginManager pm = Bukkit.getPluginManager();

    public void onEnable() {
        plugin = this;
        regCustomYmlConfigGEN();
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
        keyDataM.clear();
        backmap.clear();
    }

    public void regCommands() {
        new gmc(this);
        new gms(this);
        new gmsp(this);
        new day(this);
        new night(this);
        new feed(this);
        new heal(this);
        new fly(this);
        new debug(customyml, this);
        new commandblock(this);
        new bed(this);
        new ram(this);
        new spawn(customyml, this);
        new echest(this);
        new sign(this);
        new Key(this); //KEY COMMAND
        new MutiKeys(this); //MKEY COMMAND
        new colors(this);
        new setjail(customyml, this);
        new setspawn(customyml, this);
        new jail(customyml, this);
        new afk(this);
        new flyspeed(customyml, this);
        new isafk(customyml, this);
        new back(this);

        new tpa(customyml, this);
        new tpaccept(customyml, this);
        new tpdeny(customyml, this);

        new test(customyml, this);
        new test1(customyml, this);
    }

    public void regEvents() {
        //Bukkit.getServer().getPluginManager().registerEvents(this, this);
        new OnJoinEvent(this);
        new OnLeaveEvent(this);
        new OnDeathEvent(this);
        new OnTpEvent(this);
        //...
        new OnBedEnterEvent(this);
        new OnJoinTitleEvent(this);
    }

    public void regFileConfigGEN() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.reloadConfig();
    }

    public void regCustomYmlConfigGEN() {
        customyml = new CustomYmlManger();
        // Shit.yml
        customyml.setupshit();
        customyml.saveshit();
        customyml.reloadshit();
        // END OF Shit.yml
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
        return api;
    }
}