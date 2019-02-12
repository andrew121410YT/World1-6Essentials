package World16.Commands;

import World16.CustomConfigs.CustomConfigManager;
import World16.Main.Main;
import World16.Utils.API;
import World16.Utils.Translate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class emsg implements CommandExecutor {

    private Main plugin;
    private API api;

    public emsg(Main getPlugin, CustomConfigManager getCustomConfig) {
        this.plugin = getPlugin;
        api = new API();

        plugin.getCommand("emsg").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can do this command sadly.");
            return true;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("world16.emsg")) {
            api.PermissionErrorMessage(p);
            return true;
        }
        if (args.length == 0) {
            p.sendMessage(Translate.chat("&cUsage: /emsg <Player> <Message>"));
        } else if (args.length == 2) {
            Player ptarget = this.plugin.getServer().getPlayerExact(args[0]);
            if (args[0] != null && args[1] != null && ptarget != null && ptarget.isOnline()) {
                String messageFrom = args[1];
                p.sendMessage(Translate.chat("&2[&a{me} &6->&c {target}&2]&9 ->&r {message}").replace("{me}", "me").replace("{target}", ptarget.getDisplayName()).replace("{message}", messageFrom));
                ptarget.sendMessage(Translate.chat("&2[&a{me} &6->&c {target}&2]&9 ->&r {message}").replace("{me}", p.getDisplayName()).replace("{target}", "me").replace("{message}", messageFrom));
            } else {
                p.sendMessage(Translate.chat(api.SOMETHING_WENT_WRONG));
            }
        }
        return true;
    }
}
