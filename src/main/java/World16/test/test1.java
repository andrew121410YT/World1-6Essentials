package World16.test;

import World16.CustomConfigs.CustomConfigManager;
import World16.Main.Main;
import World16.Objects.RawLocationObject;
import World16.Storage.OldMySQL;
import World16.Utils.API;
import World16.Utils.CustomYmlManager;
import World16.Utils.KeyAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class test1 implements CommandExecutor {

    private Main plugin;

    OldMySQL mysql;
    KeyAPI keyapi = new KeyAPI();
    API api = new API();

    private CustomYmlManager shitYml = null;
    private CustomConfigManager customConfigManager;

    public test1(CustomConfigManager getCustomYml, Main getPlugin) {
        this.shitYml = getCustomYml.getShitYml();
        this.customConfigManager = getCustomYml;
        this.plugin = getPlugin;
        this.mysql = new OldMySQL();

        eRamManager = new ERamManager(this.customConfigManager);

        this.plugin.getCommand("testee1").setExecutor(this);
    }

    Map<String, Map<String, RawLocationObject>> aaa = ERamManager.stringRawLocationObjectHashMap;

    ERamManager eRamManager;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("world16.testee1")) {
            api.PermissionErrorMessage(p);
            return true;
        }
        if (args.length == 0) {
            //SOMETHING HERE
//            CountdownTimer timer = new CountdownTimer(this.plugin, 20, () -> p.sendMessage(Translate.chat("Starting")), () -> p.sendMessage(Translate.chat("END")), (t) -> p.sendMessage(Translate.chat("Timer:" + t.getSecondsLeft())));
//            timer.scheduleTimer();
            aaa.computeIfAbsent(p.getDisplayName(), k -> new HashMap<>());
            if (aaa.get(p.getDisplayName()).get("test") == null) {
                aaa.get(p.getDisplayName()).put("test", new RawLocationObject());
            }
            aaa.get(p.getDisplayName()).get("test").set("A", p.getLocation());
            eRamManager.saveThingy(p.getDisplayName(), p.getUniqueId(), "test");

        } else if (args.length >= 1) {
            //SOMETHING HERE
            eRamManager.loadUp(p);
            return true;
        } else {
            p.sendMessage("Something messed up!");
            return true;
        }
        return true;
    }
}