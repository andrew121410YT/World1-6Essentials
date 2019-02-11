package World16.Commands.tp;

import World16.CustomConfigs.CustomConfigManager;
import World16.CustomEvents.handlers.TpaEventHandler;
import World16.Main.Main;
import World16.MysqlAPI.MySQL;
import World16.Utils.API;
import World16.Utils.CustomYmlManager;
import World16.Utils.Translate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;

public class tpa implements CommandExecutor {

    private Main plugin;

    MySQL mysql = new MySQL();
    API api = new API();

    //HASHMAPS
    public static LinkedHashMap<Player, Player> tpam = new LinkedHashMap<>();

    private CustomYmlManager shitYml = null;

    public tpa(CustomConfigManager getCustomYml, Main getPlugin) {
        this.shitYml = getCustomYml.getShitYml();
        this.plugin = getPlugin;

        this.plugin.getCommand("tpa").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("world16.tpa")) {
            api.PermissionErrorMessage(p);
            return true;
        }
        if (args.length == 0) {
            p.sendMessage(Translate.chat("[&eTPA&r] &cUsage: /tpa <Player>"));
        } else if (args.length >= 1) {
            Player target = plugin.getServer().getPlayerExact(args[0]); //Get the player
            if (args.length >= 1 && target != null && target.isOnline()) {
                tpam.put(target, p);
                new TpaEventHandler(p.getDisplayName(), target.getDisplayName()); //RUNS TPA EVENT
                p.sendMessage(
                        Translate.chat("[&eTPA&r] &9Sent tpa request too " + target.getDisplayName()));
                sendTpaRequestMessage(p, target);
            } else {
                p.sendMessage("&4Looks like that player is offline.");
            }
        } else {
            p.sendMessage(Translate.chat("&4Something messed up"));
            return true;
        }
        return true;
    }

    public void sendTpaRequestMessage(Player p, Player target) {
        if (p != null && target != null) {
            target.sendMessage(
                    Translate.chat("[&eTPA&r] &a" + p.getDisplayName() + " has sent a tpa request too you."));
            target.sendMessage(Translate.chat("&c/tpaccept &aOR&r &c/tpdeny"));
        } else {
        }
    }

}