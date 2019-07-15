package World16.Managers;

import World16.Main.Main;
import World16.Utils.Translate;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Map;

public class JailManager {

    //Maps
    private Map<String, Location> jailsMap;
    //...

    private Main plugin;

    private CustomYmlManager jailsYml;

    public JailManager(CustomConfigManager customConfigManager, Main plugin) {
        this.plugin = plugin;
        this.jailsYml = customConfigManager.getJailsYml();

        this.jailsMap = this.plugin.getSetListMap().getJails();
    }

    public void getAllJailsFromConfig() {
        ConfigurationSection cs = this.jailsYml.getConfig().getConfigurationSection("Jails");
        if (cs == null) {
            this.jailsYml.getConfig().createSection("Jails");
            this.plugin.getServer().getConsoleSender().sendMessage(Translate.chat("&c[JailManager]&r&6 Jails section has been created."));
            return;
        }

        for (String jailname : cs.getKeys(false)) {
            ConfigurationSection jail = cs.getConfigurationSection(jailname);
            jailsMap.put(jailname, (Location) jail.get("Location"));
        }
    }

    public void set(String jailName, Location location) {
        jailsMap.putIfAbsent(jailName, location);

        String jailLocation = "Jails" + "." + jailName.toLowerCase();
        ConfigurationSection jail = this.jailsYml.getConfig().getConfigurationSection(jailLocation);
        if (jail == null) {
            jail = this.jailsYml.getConfig().createSection(jailLocation);
        }

        jail.set("Location", location);

        this.jailsYml.saveConfigSilent();
    }

    public boolean delete(String jailName) {
        if (jailsMap.get(jailName.toLowerCase()) != null) {
            jailsMap.remove(jailName);
            ConfigurationSection jails = this.jailsYml.getConfig().getConfigurationSection("Jails");
            jails.set(jailName.toLowerCase(), null);
            this.jailsYml.saveConfigSilent();
            return true;
        }
        return false;
    }
}
