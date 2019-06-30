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
    public static Map<String, List<String>> tabCompleteMap = new HashMap<>(); //1
    public static Map<String, Map<String, List<Location>>> eRamRaw = new HashMap<>(); //0
    public static Map<String, Location> latestClickedBlocked = new HashMap<>(); //0

    public static List<String> afkList = new ArrayList<>(); //0
    public static List<String> flyList = new ArrayList<>(); //0
    public static List<String> godmList = new ArrayList<>(); //0
    public static List<String> adminList = new ArrayList<>();//1
    public static List<Player> adminListPlayer = new ArrayList<>(); //0

    public static Map<Player, Location[]> sessions = new HashMap<>(); //0
    public static Map<String, UUID> uuidCache = new HashMap<>(); //1
}
