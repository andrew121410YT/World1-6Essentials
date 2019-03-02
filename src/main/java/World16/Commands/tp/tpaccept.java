package World16.Commands.tp;

import World16.CustomConfigs.CustomConfigManager;
import World16.Main.Main;
import World16.MysqlAPI.MySQL;
import World16.Utils.API;
import World16.Utils.CustomYmlManager;
import World16.Utils.Translate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class tpaccept implements CommandExecutor {

    private Main plugin;

    API api = new API();
    MySQL mysql = new MySQL(this.api);

    //HASHMAPS
    private static Map<Player, Player> tpam = tpa.tpam;

    private CustomYmlManager shitYml = null;

    public tpaccept(CustomConfigManager getCustomYml, Main getPlugin) {
        this.shitYml = getCustomYml.getShitYml();
        this.plugin = getPlugin;

        this.plugin.getCommand("tpaccept").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("world16.tpaccept")) {
            api.PermissionErrorMessage(p);
            return true;
        }
        if (args.length == 0) {
            if (tpam.get(p) != null) {
                tpam.get(p).teleport(p);
                tpam.get(p).sendMessage(Translate
                        .chat("[&eTPA&r] &a" + p.getDisplayName() + " has accepted your tpa request."));
                tpam.remove(p);
            }
        }
        return true;
    }
}