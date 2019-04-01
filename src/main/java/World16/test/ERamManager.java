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

    public static HashMap<String, Map<String, RawLocationObject>> stringRawLocationObjectHashMap = new HashMap<>();

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
        stringRawLocationObjectHashMap.computeIfAbsent(player.getDisplayName(), k -> new RawLocationObject());

        for (String data : cs.getKeys(false)) {
            ConfigurationSection csdata = cs.getConfigurationSection(data);

            for (String data2 : csdata.getKeys(false)) {
                stringRawLocationObjectHashMap.get(player.getDisplayName()).set(data2, new Location(Bukkit.getWorld("world"), Double.valueOf(csdata.getString(data2 + ".X")), Double.valueOf(csdata.getString(data2 + ".Y")), Double.valueOf(csdata.getString(data2 + ".Z"))));

            }

        }
    }

    public void saveThingy(String keyName, UUID uuid, String saveName) {
        String path = uuid.toString() + "." + saveName.toLowerCase();
        ConfigurationSection cs = this.customYmlManager.getConfig().getConfigurationSection(path);
        if (cs == null) {
            cs = this.customYmlManager.getConfig().createSection(path);
        }
        RawLocationObject rawLocationObject = stringRawLocationObjectHashMap.get(keyName).get(saveName);

        if (stringRawLocationObjectHashMap.get(keyName).getA() != null) {
            cs.set("A.X", rawLocationObject.getA().getX());
            cs.set("A.Y", stringRawLocationObjectHashMap.get(keyName).getA().getY());
            cs.set("A.Z", stringRawLocationObjectHashMap.get(keyName).getA().getZ());
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getB() != null) {
            cs.set("B.X", stringRawLocationObjectHashMap.get(keyName).getB().getX());
            cs.set("B.Y", stringRawLocationObjectHashMap.get(keyName).getB().getY());
            cs.set("B.Z", stringRawLocationObjectHashMap.get(keyName).getB().getZ());
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getC() != null) {
            cs.set("C.X", stringRawLocationObjectHashMap.get(keyName).getC().getX());
            cs.set("C.Y", stringRawLocationObjectHashMap.get(keyName).getC().getY());
            cs.set("C.Z", stringRawLocationObjectHashMap.get(keyName).getC().getZ());
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getD() != null) {
            cs.set("D.X", stringRawLocationObjectHashMap.get(keyName).getD().getX());
            cs.set("D.Y", stringRawLocationObjectHashMap.get(keyName).getD().getY());
            cs.set("D.Z", stringRawLocationObjectHashMap.get(keyName).getD().getZ());
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getE() != null) {
            cs.set("E.X", stringRawLocationObjectHashMap.get(keyName).getE().getX());
            cs.set("E.Y", stringRawLocationObjectHashMap.get(keyName).getE().getY());
            cs.set("E.Z", stringRawLocationObjectHashMap.get(keyName).getE().getZ());
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getF() != null) {
            cs.set("F.X", stringRawLocationObjectHashMap.get(keyName).getF().getX());
            cs.set("F.Y", stringRawLocationObjectHashMap.get(keyName).getF().getY());
            cs.set("F.Z", stringRawLocationObjectHashMap.get(keyName).getF().getZ());
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getG() != null) {
            cs.set("G.X", stringRawLocationObjectHashMap.get(keyName).getG().getX());
            cs.set("G.Y", stringRawLocationObjectHashMap.get(keyName).getG().getY());
            cs.set("G.Z", stringRawLocationObjectHashMap.get(keyName).getG().getZ());
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getH() != null) {
            cs.set("H.X", stringRawLocationObjectHashMap.get(keyName).getH().getX());
            cs.set("H.Y", stringRawLocationObjectHashMap.get(keyName).getH().getY());
            cs.set("H.Z", stringRawLocationObjectHashMap.get(keyName).getH().getZ());
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getI() != null) {
            cs.set("I.X", stringRawLocationObjectHashMap.get(keyName).getI().getX());
            cs.set("I.Y", stringRawLocationObjectHashMap.get(keyName).getI().getY());
            cs.set("I.Z", stringRawLocationObjectHashMap.get(keyName).getI().getZ());
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getJ() != null) {
            cs.set("J.X", stringRawLocationObjectHashMap.get(keyName).getJ().getX());
            cs.set("J.Y", stringRawLocationObjectHashMap.get(keyName).getJ().getY());
            cs.set("J.Z", stringRawLocationObjectHashMap.get(keyName).getJ().getZ());
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getK() != null) {
            cs.set("K.X", stringRawLocationObjectHashMap.get(keyName).getK().getX());
            cs.set("K.Y", stringRawLocationObjectHashMap.get(keyName).getK().getY());
            cs.set("K.Z", stringRawLocationObjectHashMap.get(keyName).getK().getZ());
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getL() != null) {
            cs.set("L.X", stringRawLocationObjectHashMap.get(keyName).getL().getX());
            cs.set("L.Y", stringRawLocationObjectHashMap.get(keyName).getL().getY());
            cs.set("L.Z", stringRawLocationObjectHashMap.get(keyName).getL().getZ());
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getM() != null) {
            cs.set("M.X", stringRawLocationObjectHashMap.get(keyName).getM().getX());
            cs.set("M.Y", stringRawLocationObjectHashMap.get(keyName).getM().getY());
            cs.set("M.Z", stringRawLocationObjectHashMap.get(keyName).getM().getZ());
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getN() != null) {
            cs.set("N.X", stringRawLocationObjectHashMap.get(keyName).getN().getX());
            cs.set("N.Y", stringRawLocationObjectHashMap.get(keyName).getN().getY());
            cs.set("N.Z", stringRawLocationObjectHashMap.get(keyName).getN().getZ());
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getO() != null) {
            cs.set("O.X", stringRawLocationObjectHashMap.get(keyName).getO().getX());
            cs.set("O.Y", stringRawLocationObjectHashMap.get(keyName).getO().getY());
            cs.set("O.Z", stringRawLocationObjectHashMap.get(keyName).getO().getZ());
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getP() != null) {
            cs.set("P.X", stringRawLocationObjectHashMap.get(keyName).getP().getX());
            cs.set("P.Y", stringRawLocationObjectHashMap.get(keyName).getP().getY());
            cs.set("P.Z", stringRawLocationObjectHashMap.get(keyName).getP().getZ());
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getQ() != null) {
            cs.set("Q.X", stringRawLocationObjectHashMap.get(keyName).getQ().getX());
            cs.set("Q.Y", stringRawLocationObjectHashMap.get(keyName).getQ().getY());
            cs.set("Q.Z", stringRawLocationObjectHashMap.get(keyName).getQ().getZ());
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getR() != null) {
            cs.set("R.X", stringRawLocationObjectHashMap.get(keyName).getR().getX());
            cs.set("R.Y", stringRawLocationObjectHashMap.get(keyName).getR().getY());
            cs.set("R.Z", stringRawLocationObjectHashMap.get(keyName).getR().getZ());
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getS() != null) {
            cs.set("S.X", stringRawLocationObjectHashMap.get(keyName).getS().getX());
            cs.set("S.Y", stringRawLocationObjectHashMap.get(keyName).getS().getY());
            cs.set("S.Z", stringRawLocationObjectHashMap.get(keyName).getS().getZ());
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getT() != null) {
            cs.set("T.X", stringRawLocationObjectHashMap.get(keyName).getT().getX());
            cs.set("T.Y", stringRawLocationObjectHashMap.get(keyName).getT().getY());
            cs.set("T.Z", stringRawLocationObjectHashMap.get(keyName).getT().getZ());
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getU() != null) {
            cs.set("U.X", stringRawLocationObjectHashMap.get(keyName).getU().getX());
            cs.set("U.Y", stringRawLocationObjectHashMap.get(keyName).getU().getY());
            cs.set("U.Z", stringRawLocationObjectHashMap.get(keyName).getU().getZ());
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getV() != null) {
            cs.set("V.X", stringRawLocationObjectHashMap.get(keyName).getV().getX());
            cs.set("V.Y", stringRawLocationObjectHashMap.get(keyName).getV().getY());
            cs.set("V.Z", stringRawLocationObjectHashMap.get(keyName).getV().getZ());
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getW() != null) {
            cs.set("W.X", stringRawLocationObjectHashMap.get(keyName).getW().getX());
            cs.set("W.Y", stringRawLocationObjectHashMap.get(keyName).getW().getY());
            cs.set("W.Z", stringRawLocationObjectHashMap.get(keyName).getW().getZ());
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getX() != null) {
            cs.set("X.X", stringRawLocationObjectHashMap.get(keyName).getX().getX());
            cs.set("X.Y", stringRawLocationObjectHashMap.get(keyName).getX().getY());
            cs.set("X.Z", stringRawLocationObjectHashMap.get(keyName).getX().getZ());
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getY() != null) {
            cs.set("Y.X", stringRawLocationObjectHashMap.get(keyName).getY().getX());
            cs.set("Y.Y", stringRawLocationObjectHashMap.get(keyName).getY().getY());
            cs.set("Y.Z", stringRawLocationObjectHashMap.get(keyName).getY().getZ());
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getZ() != null) {
            cs.set("Z.X", stringRawLocationObjectHashMap.get(keyName).getZ().getX());
            cs.set("Z.Y", stringRawLocationObjectHashMap.get(keyName).getZ().getY());
            cs.set("Z.Z", stringRawLocationObjectHashMap.get(keyName).getZ().getZ());
        }
        customYmlManager.saveConfigSilent();
        //
    }

    public void doIt(String keyName, String saveName) {
        RawLocationObject rawLocationObject = stringRawLocationObjectHashMap.get(keyName);

        if (stringRawLocationObjectHashMap.get(keyName).getA() != null) {
            stringRawLocationObjectHashMap.get(keyName).getA().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getB() != null) {
            stringRawLocationObjectHashMap.get(keyName).getB().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getC() != null) {
            stringRawLocationObjectHashMap.get(keyName).getC().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getD() != null) {
            stringRawLocationObjectHashMap.get(keyName).getD().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getE() != null) {
            stringRawLocationObjectHashMap.get(keyName).getE().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getF() != null) {
            stringRawLocationObjectHashMap.get(keyName).getF().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getG() != null) {
            stringRawLocationObjectHashMap.get(keyName).getG().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getH() != null) {
            stringRawLocationObjectHashMap.get(keyName).getH().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getI() != null) {
            stringRawLocationObjectHashMap.get(keyName).getI().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getJ() != null) {
            stringRawLocationObjectHashMap.get(keyName).getJ().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getK() != null) {
            stringRawLocationObjectHashMap.get(keyName).getK().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getL() != null) {
            stringRawLocationObjectHashMap.get(keyName).getL().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getM() != null) {
            stringRawLocationObjectHashMap.get(keyName).getM().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getN() != null) {
            stringRawLocationObjectHashMap.get(keyName).getN().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getO() != null) {
            stringRawLocationObjectHashMap.get(keyName).getO().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getP() != null) {
            stringRawLocationObjectHashMap.get(keyName).getP().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getQ() != null) {
            stringRawLocationObjectHashMap.get(keyName).getQ().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getR() != null) {
            stringRawLocationObjectHashMap.get(keyName).getR().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getS() != null) {
            stringRawLocationObjectHashMap.get(keyName).getS().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        if (stringRawLocationObjectHashMap.get(keyName).getT() != null) {
            stringRawLocationObjectHashMap.get(keyName).getT().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getU() != null) {
            stringRawLocationObjectHashMap.get(keyName).getU().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getV() != null) {
            stringRawLocationObjectHashMap.get(keyName).getV().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getW() != null) {
            stringRawLocationObjectHashMap.get(keyName).getW().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getX() != null) {
            stringRawLocationObjectHashMap.get(keyName).getX().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getY() != null) {
            stringRawLocationObjectHashMap.get(keyName).getY().getBlock().setType(Material.REDSTONE_BLOCK);
        }
        //
        if (stringRawLocationObjectHashMap.get(keyName).getZ() != null) {
            stringRawLocationObjectHashMap.get(keyName).getZ().getBlock().setType(Material.REDSTONE_BLOCK);
        }
    }
}
