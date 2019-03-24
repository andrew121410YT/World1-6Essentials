package World16.Commands;

import World16.CustomConfigs.CustomConfigManager;
import World16.Main.Main;
import World16.Storage.OldMySQL;
import World16.Utils.API;
import World16.Utils.CustomYmlManager;
import World16.Utils.KeyAPI;
import World16.Utils.Translate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class msg implements CommandExecutor {

    private Main plugin;

    private OldMySQL mysql;
    private KeyAPI keyapi = new KeyAPI();
    private API api = new API();

    private CustomYmlManager shitYml = null;

    public msg(CustomConfigManager getCustomYml, Main getPlugin) {
        this.shitYml = getCustomYml.getShitYml();
        this.plugin = getPlugin;
        this.mysql = new OldMySQL();

        this.plugin.getCommand("emsg").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("world16.msg") || !p.hasPermission("world16.emsg")) {
            api.PermissionErrorMessage(p);
            return true;
        }
        if (args.length == 0) {
            p.sendMessage(Translate.chat("&cUsage: /emsg <Player> <Message>"));
        } else if (args.length >= 2) {
            Player ptarget = this.plugin.getServer().getPlayerExact(args[0]);
            if (args[0] != null && args[1] != null && ptarget != null && ptarget.isOnline()) {
                String messageFrom = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                p.sendMessage(Translate.chat("&2[&a{me} &6->&c {target}&2]&9 ->&r {message}").replace("{me}", "me").replace("{target}", ptarget.getDisplayName()).replace("{message}", messageFrom));
                ptarget.sendMessage(Translate.chat("&2[&a{me} &6->&c {target}&2]&9 ->&r {message}").replace("{me}", p.getDisplayName()).replace("{target}", "me").replace("{message}", messageFrom));
            } else {
                p.sendMessage(Translate.chat("&4Something went wrong."));
                p.sendMessage(Translate.chat("&cUsage: /emsg <Player> <Message>"));
            }
        } else {
            p.sendMessage(Translate.chat("&4Something went wrong."));
            p.sendMessage(Translate.chat("&cUsage: /emsg <Player> <Message>"));
        }
        return true;
    }
}