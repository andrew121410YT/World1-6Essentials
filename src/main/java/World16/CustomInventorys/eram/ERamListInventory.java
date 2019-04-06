package World16.CustomInventorys.eram;

import World16.Utils.ICustomInventory;
import World16.Utils.InventoryUtils;
import World16.Utils.Translate;
import World16.test.ERamManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class ERamListInventory implements ICustomInventory {

    //Maps
    Map<String, Map<String, List<Location>>> eramMap = ERamManager.stringRawLocationObjectHashMap;
    //...

    private Inventory inv;

    private String inventory_name;
    private int inv_rows = 4 * 9;

    public void createCustomInv() {
        inventory_name = Translate.chat("ERam List GUI");

        inv = Bukkit.createInventory(this, inv_rows, inventory_name);
    }

    public Inventory GUI(Player player) {
        inv.clear();

//        InventoryUtils.createItem(inv, Material.REDSTONE, 1, 1, "Test101", "Test Lore");

        Map<String, List<Location>> emapIN = eramMap.get(player.getDisplayName());

        for (Map.Entry<String, List<Location>> entry : emapIN.entrySet()) {
            String k = entry.getKey();
            List<Location> v = entry.getValue();

            InventoryUtils.createItem(inv, Material.GREEN_SHULKER_BOX, v.size(), inv.firstEmpty() + 1, k, "Click me to open up");
        }

        return inv;
    }

    public void clicked(Player player, int slot, ItemStack clicked, Inventory inv) {
        Map<String, List<Location>> emapIN = eramMap.get(player.getDisplayName());

        if (emapIN.containsKey(clicked.getItemMeta().getDisplayName())) {
            player.playSound(player.getLocation(), Sound.BLOCK_PISTON_CONTRACT, 10.0f, 1.0f);
        }
    }

    @Override
    public String getInventoryName() {
        return inventory_name;
    }

    @Override
    public int getInvRows() {
        return inv_rows;
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}