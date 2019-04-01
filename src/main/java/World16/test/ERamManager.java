package World16.test;

import World16.CustomConfigs.CustomConfigManager;
import World16.Objects.RawLocationObject;
import World16.Utils.CustomYmlManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ERamManager {

    public static Map<String, Map<String, RawLocationObject>> stringRawLocationObjectHashMap = new HashMap<>();

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
            stringRawLocationObjectHashMap.get(player.getDisplayName()).computeIfAbsent(data, k -> new RawLocationObject());
            ConfigurationSection csdata = cs.getConfigurationSection(data);

            for (String data2 : csdata.getKeys(false)) {
                stringRawLocationObjectHashMap.get(player.getDisplayName()).get(data).set(data2, new Location(Bukkit.getWorld("world"), Double.valueOf(csdata.getString(data2 + ".X")), Double.valueOf(csdata.getString(data2 + ".Y")), Double.valueOf(csdata.getString(data2 + ".Z"))));
                player.sendMessage("LOADED MEMORY: " + data + " -> " + data2 + " " + csdata.getString(data2 + ".X")+" -> " + csdata.getString(data2 + ".Y") + " -> " + csdata.getString(data2 + ".Z"));
            }

        }
    }

    public void delete(String playerName,UUID uuid, String saveName){
        if(stringRawLocationObjectHashMap.get(playerName).get(saveName) != null){
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
        RawLocationObject rawLocationObject = stringRawLocationObjectHashMap.get(keyName).get(saveName);

        if (rawLocationObject.getA() != null) {
            cs.set("A.X", rawLocationObject.getA().getX());
            cs.set("A.Y", rawLocationObject.getA().getY());
            cs.set("A.Z", rawLocationObject.getA().getZ());
        }
        //
        if (rawLocationObject.getB() != null) {
            cs.set("B.X", rawLocationObject.getB().getX());
            cs.set("B.Y", rawLocationObject.getB().getY());
            cs.set("B.Z", rawLocationObject.getB().getZ());
        }
        //
        if (rawLocationObject.getC() != null) {
            cs.set("C.X", rawLocationObject.getC().getX());
            cs.set("C.Y", rawLocationObject.getC().getY());
            cs.set("C.Z", rawLocationObject.getC().getZ());
        }
        //
        if (rawLocationObject.getD() != null) {
            cs.set("D.X", rawLocationObject.getD().getX());
            cs.set("D.Y", rawLocationObject.getD().getY());
            cs.set("D.Z", rawLocationObject.getD().getZ());
        }
        //
        if (rawLocationObject.getE() != null) {
            cs.set("E.X", rawLocationObject.getE().getX());
            cs.set("E.Y", rawLocationObject.getE().getY());
            cs.set("E.Z", rawLocationObject.getE().getZ());
        }
        //
        if (rawLocationObject.getF() != null) {
            cs.set("F.X", rawLocationObject.getF().getX());
            cs.set("F.Y", rawLocationObject.getF().getY());
            cs.set("F.Z", rawLocationObject.getF().getZ());
        }
        //
        if (rawLocationObject.getG() != null) {
            cs.set("G.X", rawLocationObject.getG().getX());
            cs.set("G.Y", rawLocationObject.getG().getY());
            cs.set("G.Z", rawLocationObject.getG().getZ());
        }
        //
        if (rawLocationObject.getH() != null) {
            cs.set("H.X", rawLocationObject.getH().getX());
            cs.set("H.Y", rawLocationObject.getH().getY());
            cs.set("H.Z", rawLocationObject.getH().getZ());
        }
        //
        if (rawLocationObject.getI() != null) {
            cs.set("I.X", rawLocationObject.getI().getX());
            cs.set("I.Y", rawLocationObject.getI().getY());
            cs.set("I.Z", rawLocationObject.getI().getZ());
        }
        //
        if (rawLocationObject.getJ() != null) {
            cs.set("J.X", rawLocationObject.getJ().getX());
            cs.set("J.Y", rawLocationObject.getJ().getY());
            cs.set("J.Z", rawLocationObject.getJ().getZ());
        }
        //
        if (rawLocationObject.getK() != null) {
            cs.set("K.X", rawLocationObject.getK().getX());
            cs.set("K.Y", rawLocationObject.getK().getY());
            cs.set("K.Z", rawLocationObject.getK().getZ());
        }
        //
        if (rawLocationObject.getL() != null) {
            cs.set("L.X", rawLocationObject.getL().getX());
            cs.set("L.Y", rawLocationObject.getL().getY());
            cs.set("L.Z", rawLocationObject.getL().getZ());
        }
        //
        if (rawLocationObject.getM() != null) {
            cs.set("M.X", rawLocationObject.getM().getX());
            cs.set("M.Y", rawLocationObject.getM().getY());
            cs.set("M.Z", rawLocationObject.getM().getZ());
        }
        //
        if (rawLocationObject.getN() != null) {
            cs.set("N.X", rawLocationObject.getN().getX());
            cs.set("N.Y", rawLocationObject.getN().getY());
            cs.set("N.Z", rawLocationObject.getN().getZ());
        }
        //
        if (rawLocationObject.getO() != null) {
            cs.set("O.X", rawLocationObject.getO().getX());
            cs.set("O.Y", rawLocationObject.getO().getY());
            cs.set("O.Z", rawLocationObject.getO().getZ());
        }
        //
        if (rawLocationObject.getP() != null) {
            cs.set("P.X", rawLocationObject.getP().getX());
            cs.set("P.Y", rawLocationObject.getP().getY());
            cs.set("P.Z", rawLocationObject.getP().getZ());
        }
        //
        if (rawLocationObject.getQ() != null) {
            cs.set("Q.X", rawLocationObject.getQ().getX());
            cs.set("Q.Y", rawLocationObject.getQ().getY());
            cs.set("Q.Z", rawLocationObject.getQ().getZ());
        }
        //
        if (rawLocationObject.getR() != null) {
            cs.set("R.X", rawLocationObject.getR().getX());
            cs.set("R.Y", rawLocationObject.getR().getY());
            cs.set("R.Z", rawLocationObject.getR().getZ());
        }
        //
        if (rawLocationObject.getS() != null) {
            cs.set("S.X", rawLocationObject.getS().getX());
            cs.set("S.Y", rawLocationObject.getS().getY());
            cs.set("S.Z", rawLocationObject.getS().getZ());
        }
        //
        if (rawLocationObject.getT() != null) {
            cs.set("T.X", rawLocationObject.getT().getX());
            cs.set("T.Y", rawLocationObject.getT().getY());
            cs.set("T.Z", rawLocationObject.getT().getZ());
        }
        //
        if (rawLocationObject.getU() != null) {
            cs.set("U.X", rawLocationObject.getU().getX());
            cs.set("U.Y", rawLocationObject.getU().getY());
            cs.set("U.Z", rawLocationObject.getU().getZ());
        }
        //
        if (rawLocationObject.getV() != null) {
            cs.set("V.X", rawLocationObject.getV().getX());
            cs.set("V.Y", rawLocationObject.getV().getY());
            cs.set("V.Z", rawLocationObject.getV().getZ());
        }
        //
        if (rawLocationObject.getW() != null) {
            cs.set("W.X", rawLocationObject.getW().getX());
            cs.set("W.Y", rawLocationObject.getW().getY());
            cs.set("W.Z", rawLocationObject.getW().getZ());
        }
        //
        if (rawLocationObject.getX() != null) {
            cs.set("X.X", rawLocationObject.getX().getX());
            cs.set("X.Y", rawLocationObject.getX().getY());
            cs.set("X.Z", rawLocationObject.getX().getZ());
        }
        //
        if (rawLocationObject.getY() != null) {
            cs.set("Y.X", rawLocationObject.getY().getX());
            cs.set("Y.Y", rawLocationObject.getY().getY());
            cs.set("Y.Z", rawLocationObject.getY().getZ());
        }
        //
        if (rawLocationObject.getZ() != null) {
            cs.set("Z.X", rawLocationObject.getZ().getX());
            cs.set("Z.Y", rawLocationObject.getZ().getY());
            cs.set("Z.Z", rawLocationObject.getZ().getZ());
        }
            customYmlManager.saveConfigSilent();
            //
    }

    public void doIt(String keyName, String saveName) {
        RawLocationObject rawLocationObject = stringRawLocationObjectHashMap.get(keyName).get(saveName);

        if (rawLocationObject.getA() != null) {
            rawLocationObject.getA().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (rawLocationObject.getB() != null) {
            rawLocationObject.getB().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (rawLocationObject.getC() != null) {
            rawLocationObject.getC().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (rawLocationObject.getD() != null) {
            rawLocationObject.getD().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (rawLocationObject.getE() != null) {
            rawLocationObject.getE().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (rawLocationObject.getF() != null) {
            rawLocationObject.getF().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (rawLocationObject.getG() != null) {
            rawLocationObject.getG().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (rawLocationObject.getH() != null) {
            rawLocationObject.getH().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (rawLocationObject.getI() != null) {
            rawLocationObject.getI().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (rawLocationObject.getJ() != null) {
            rawLocationObject.getJ().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (rawLocationObject.getK() != null) {
            rawLocationObject.getK().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (rawLocationObject.getL() != null) {
            rawLocationObject.getL().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (rawLocationObject.getM() != null) {
            rawLocationObject.getM().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (rawLocationObject.getN() != null) {
            rawLocationObject.getN().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (rawLocationObject.getO() != null) {
            rawLocationObject.getO().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (rawLocationObject.getP() != null) {
            rawLocationObject.getP().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (rawLocationObject.getQ() != null) {
            rawLocationObject.getQ().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (rawLocationObject.getR() != null) {
            rawLocationObject.getR().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (rawLocationObject.getS() != null) {
            rawLocationObject.getS().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        if (rawLocationObject.getT() != null) {
            rawLocationObject.getT().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (rawLocationObject.getU() != null) {
            rawLocationObject.getU().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (rawLocationObject.getV() != null) {
            rawLocationObject.getV().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (rawLocationObject.getW() != null) {
            rawLocationObject.getW().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (rawLocationObject.getX() != null) {
            rawLocationObject.getX().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (rawLocationObject.getY() != null) {
            rawLocationObject.getY().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (rawLocationObject.getZ() != null) {
            rawLocationObject.getZ().getBlock().setType(Material.REDSTONE_BLOCK);
        }
    }
}
