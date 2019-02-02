package World16.Commands;

import World16.Main.Main;
import World16.Translate.Translate;
import World16.Utils.API;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gma implements CommandExecutor {

    API api = new API();
    private Main plugin;

    public gma(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("gma").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission("world16.gma")) {
            api.PermissionErrorMessage(p);
            return true;
        }
        if (args.length == 0) {
            p.setGameMode(GameMode.ADVENTURE);
            p.sendMessage(
                    Translate.chat("&6Set game mode &cadventure&6 for " + ((Player) sender).getDisplayName()));
            return true;
        } else {
            Player target = plugin.getServer().getPlayerExact(args[0]);
            if (args.length == 1 && target != null && target.isOnline()) {
                if (!p.hasPermission("world16.gma.other")) {
                    api.PermissionErrorMessage(p);
                    return true;
                }
                target.setGameMode(GameMode.ADVENTURE);
                p.sendMessage(
                        Translate.chat("&6Set game mode &cadventure&6 for " + target.getDisplayName()));
            } else {
                p.sendMessage(Translate.chat("&aAliases: gma && gm2"));
                p.sendMessage(Translate
                        .chat("&cUsage: for yourself do /gma OR /gm2 OR /gma <Player> OR /gm2 <Player>"));
            }
        }
        return true;
    }
}