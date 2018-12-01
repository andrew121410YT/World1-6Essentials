package Commands;

import Translate.Translate;
import Utils.API;
import Utils.CustomYmlManger;
import World16.World16.World16.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class isafk implements CommandExecutor {

    private Main plugin;
    API api = new API();

    private CustomYmlManger configinstance = null;

    public isafk(CustomYmlManger getCustomYml, World16.World16.World16.Main getPlugin) {
        this.configinstance = getCustomYml;
        this.plugin = getPlugin;
        this.plugin.getCommand("isafk").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
//        if (!(sender instanceof Player)) {
//            sender.sendMessage("Only Players Can Use This Command.");
//            return true;
//        }
        Player p = (Player) sender;

        if (!p.hasPermission("command.isafk.permission")) {
            api.PermissionErrorMessage(p);
            return true;
        }

        if (args.length >= 1) {
            Player playerFromArg = this.plugin.getServer().getPlayerExact(args[0]);
            if (api.isAfk(playerFromArg)) {
                p.sendMessage(Translate.chat("&aThe Player: " + playerFromArg.getDisplayName() + " is afk"));
            }
        }
        return true;
    }
}