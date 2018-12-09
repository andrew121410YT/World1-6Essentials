package World16.World16.World16;

import Commands.*;
import Events.OnBedEnter;
import Events.OnJoin;
import Events.OnJoinTitle;
import Events.OnLeave;
import KeyCommands.Key;
import KeyCommands.MutiKeys;
import Translate.Translate;
import Utils.CustomYmlManger;
import Utils.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import test.test;
import test.test1;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends JavaPlugin {//implements Listener {

    public static Main plugin;
    private CustomYmlManger customyml;
    ArrayList<String> Afk = afk.Afk;
    ArrayList<String> Fly = fly.Fly;
    HashMap<String, String> keyDataM = OnJoin.keyDataM;
    // GOT THE MYSQL API AT https://www.spigotmc.org/resources/simple-easy-mysql-api.36447/
    // GOT THE TITLE API AT https://www.spigotmc.org/resources/titleapi-1-8-1-13.1325/
    PluginManager pm = Bukkit.getPluginManager();

    public void onEnable() {
        plugin = this;
        YmlConfigGen();
        FileConfigGen();
        regEvents();
        regCommands();
        bstats();
        getLogger().info("[World1-6Essentials] is now loaded!");
        // START OF UPDATER
        // FROM https://www.spigotmc.org/resources/api-pluginupdater-with-website.5578/
        // FINISH OF UPDATER
    }

    public void onDisable() {
        this.clear();
        getLogger().info("[World1-6Essentials] is now disabled.");
    }

    public void clear() {
        Afk.clear();
        Fly.clear();
        keyDataM.clear();
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

        new test(customyml, this);
        new test1(customyml, this);
    }

    public void regEvents() {
        //Bukkit.getServer().getPluginManager().registerEvents(this, this);
        new OnJoin(this);
        new OnLeave(this);
        //...
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
                    p.sendMessage(Translate.chat("&6Made By Andrew121410 My -> Discord: Andrew121410#2035"));
                    return true;
                } else {
                    this.getLogger().info("[ERROR]");
                }
            }
        }
        return true;
    }
}