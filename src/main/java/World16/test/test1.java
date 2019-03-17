package World16.test;

import CCUtils.Storage.SQLite;
import World16.CustomConfigs.CustomConfigManager;
import World16.Main.Main;
import World16.Storage.OldMySQL;
import World16.Utils.API;
import World16.Utils.CustomYmlManager;
import World16.Utils.KeyAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class test1 implements CommandExecutor {

    private Main plugin;

    OldMySQL mysql;
    KeyAPI keyapi = new KeyAPI();
    API api = new API();

    private CustomYmlManager shitYml = null;

    public test1(CustomConfigManager getCustomYml, Main getPlugin) {
        this.shitYml = getCustomYml.getShitYml();
        this.plugin = getPlugin;
        this.mysql = new OldMySQL();

        this.plugin.getCommand("testee1").setExecutor(this);
    }

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
        } else if (args.length >= 1) {
            //SOMETHING HERE
            return true;
        } else {
            p.sendMessage("Something messed up!");
            return true;
        }
        return true;
    }
}