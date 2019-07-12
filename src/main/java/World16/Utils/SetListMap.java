package World16.Utils;

import World16.Objects.KeyObject;
import World16.Objects.LocationObject;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class SetListMap {

    // 0 TO CLEAR AFTER THE PLAYER LEAVES
    // 1 TO ONLY CLEAR WHEN THE SERVER SHUTS DOWN

    public static Map<String, KeyObject> keyDataM = new HashMap<>(); //0
    public static Map<UUID, LocationObject> backM = new HashMap<>(); //0
    public static Map<Player, Player> tpaM = new LinkedHashMap<>(); //0
    public static Map<String, Map<String, List<Location>>> eRamRaw = new HashMap<>(); //0
    public static Map<String, Location> latestClickedBlocked = new HashMap<>(); //0
    static Map<String, Map<String, String>> tagsMap = new HashMap<>(); //0
    public static Map<UUID, Location> afkMap = new HashMap<>(); //0
    public static Map<UUID, Map<String, Location>> homesMap = new HashMap<>(); //0

    public static Map<String, UUID> uuidCache = new HashMap<>(); //1
    public static Map<String, Location> jails = new HashMap<>(); //1
    public static Map<String, List<String>> tabCompleteMap = new HashMap<>(); //1

    public static List<String> flyList = new ArrayList<>(); //0
    public static List<String> godmList = new ArrayList<>(); //0
    public static List<Player> adminListPlayer = new ArrayList<>(); //0

    public static List<String> adminList = new ArrayList<>();//1


    //METHODS
    public static void clearSetListMap(Player p) {
        clearAllMaps(p);
        clearAllLists(p);
    }

    public static void clearSetListMap() {
        clearAllMaps();
        clearAllLists();
    }

    public static void clearAllMaps(Player p) {
        keyDataM.remove(p.getDisplayName());

        backM.remove(p.getUniqueId());

        tpaM.remove(p);

        eRamRaw.remove(p.getDisplayName());

        latestClickedBlocked.remove(p.getDisplayName());

        tagsMap.remove(p.getDisplayName());

        afkMap.remove(p.getUniqueId());

        homesMap.remove(p.getUniqueId());
    }

    public static void clearAllMaps() {
        keyDataM.clear();
        backM.clear();
        tpaM.clear();
        eRamRaw.clear();
        latestClickedBlocked.clear();
        tagsMap.clear();
        afkMap.clear();
        uuidCache.clear();
        jails.clear();
        tabCompleteMap.clear();
        homesMap.clear();
    }

    public static void clearAllLists(Player p) {
        flyList.remove(p.getDisplayName());

        godmList.remove(p.getDisplayName());

        adminListPlayer.remove(p);
    }

    public static void clearAllLists() {
        flyList.clear();

        godmList.clear();

        adminListPlayer.clear();
    }
}
