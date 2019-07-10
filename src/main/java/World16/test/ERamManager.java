package World16.test;

import World16.CustomConfigs.CustomConfigManager;
import World16.Utils.CustomYmlManager;
import World16.Utils.SetListMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.*;

public class ERamManager {

    Map<String, Map<String, List<Location>>> stringRawLocationObjectHashMap = SetListMap.eRamRaw;

    private CustomYmlManager customYmlManager;

    public ERamManager(CustomConfigManager customConfigManager) {
        customYmlManager = customConfigManager.getERamYML();
    }

    public void loadUp(Player player) {
        ConfigurationSection cs = this.customYmlManager.getConfig().getConfigurationSection(player.getUniqueId().toString());
        if (cs == null) {
            player.sendMessage("NO NO LOADUP!");
            return;
        }

        stringRawLocationObjectHashMap.computeIfAbsent(player.getDisplayName(), k -> new HashMap<>());

        //Name of the save is [data]!!!
        for (String data : cs.getKeys(false)) {
            stringRawLocationObjectHashMap.get(player.getDisplayName()).computeIfAbsent(data, k -> new ArrayList<>());
            ConfigurationSection csdata = cs.getConfigurationSection(data);

            List<Location> aye = (List<Location>) csdata.get("list");
            aye.forEach((location -> stringRawLocationObjectHashMap.get(player.getDisplayName()).get(data).add(location)));
        }
    }

    public void delete(String playerName, UUID uuid, String saveName) {
        if (stringRawLocationObjectHashMap.get(playerName).get(saveName) != null) {
            stringRawLocationObjectHashMap.get(playerName).remove(saveName);
            ConfigurationSection cs = this.customYmlManager.getConfig().getConfigurationSection(uuid.toString());
            cs.set(saveName.toLowerCase(), null);
            this.customYmlManager.saveConfigSilent();
        }

    }

    public void saveThingy(String keyName, UUID uuid, String saveName) {
        String path = uuid.toString() + "." + saveName.toLowerCase();
        ConfigurationSection cs = this.customYmlManager.getConfig().getConfigurationSection(path);
        if (cs == null) {
            cs = this.customYmlManager.getConfig().createSection(path);
        }
        List<Location> rawLocationObject = stringRawLocationObjectHashMap.get(keyName).get(saveName);

        cs.set("list", rawLocationObject);

        customYmlManager.saveConfigSilent();
    }

    public void doIt(String keyName, String saveName) {
        List<Location> rawLocationObject = stringRawLocationObjectHashMap.get(keyName).get(saveName);

        if (rawLocationObject == null) {
            return;
        }

        for (Location location : rawLocationObject) {
            location.getBlock().setType(Material.REDSTONE_BLOCK);
        }
    }

    public void undoIt(String keyName, String saveName) {
        List<Location> rawLocationObject = stringRawLocationObjectHashMap.get(keyName).get(saveName);

        if (rawLocationObject == null) {
            return;
        }

        for (Location location : rawLocationObject) {
            location.getBlock().setType(Material.AIR);
        }
    }
}
