package World16.Utils;

import World16.Main.Main;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.plugin.Plugin;

public class OtherPlugins {

    private Main plugin;

    //Plugins
    private WorldEditPlugin worldEditPlugin;

    public OtherPlugins(Main plugin) {
        this.plugin = plugin;

        setUpWorldEditPlugin();
    }

    private void setUpWorldEditPlugin() {
        Plugin plugin = this.plugin.getServer().getPluginManager().getPlugin("WorldEdit");
        if (plugin instanceof WorldEditPlugin) {
            this.worldEditPlugin = (WorldEditPlugin) plugin;
        } else {
            this.plugin.getServer().broadcastMessage(Translate.chat(API.EMERGENCY_TAG + " No WorldEdit = no work :sad"));
            this.plugin.getServer().shutdown();
        }
    }

    public WorldEditPlugin getWorldEditPlugin() {
        return worldEditPlugin;
    }
}
