package World16.CustomInventorys.eram;

import World16.CustomInventorys.CustomInventoryManager;
import World16.Utils.ICustomInventory;
import World16.Utils.InventoryUtils;
import World16.Utils.Translate;
import World16.test.ERamManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ERamInsideInventory implements ICustomInventory {

    //Maps
    Map<String, Map<String, List<Location>>> eramMap = ERamManager.stringRawLocationObjectHashMap;
    //...

    private Inventory inv;

    private String inventory_name;
    private int inv_rows = 5 * 9;

    private CustomInventoryManager customInventoryManager;

    public ERamInsideInventory(CustomInventoryManager customInventoryManager) {
        this.customInventoryManager = customInventoryManager;
    }

    public void createCustomInv() {
        inventory_name = Translate.chat("ERam Inside GUI");

        inv = Bukkit.createInventory(this, inv_rows, inventory_name);
    }

    public Inventory GUI(Player player) {
        InventoryUtils.createItem(inv, Material.RED_SHULKER_BOX, 1, inv.firstEmpty() + 1, "ERROR[]", "ERROR[]");
        return inv;
    }

    public Inventory GUI(Player player, int slot, ItemStack clicked, Inventory inv) {
        this.inv.clear();

        Map<String, List<Location>> emapIN = eramMap.get(player.getDisplayName());

        List<String> locationListToStrings = this.LocationListToStrings(emapIN.get(clicked.getItemMeta().getDisplayName()));

        for (String locationListToString : locationListToStrings) {
            InventoryUtils.createItem(this.inv, Material.REDSTONE_BLOCK, 1, this.inv.firstEmpty() + 1, locationListToString, "Click me to tp");
        }
        InventoryUtils.createItem(this.inv, Material.ARROW, 1, 37, "Back", "Back");

        return this.inv;
    }

    public void clicked(Player player, int slot, ItemStack clicked, Inventory inv) {
        Map<String, List<Location>> emapIN = eramMap.get(player.getDisplayName());

        if (clicked.getItemMeta().getDisplayName().equalsIgnoreCase("Back")) {
            player.closeInventory();
            player.openInventory(this.customInventoryManager.geteRamListMenu().GUI(player));
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

    public List<String> LocationListToStrings(List<Location> locationList) {

        List<String> newList = new ArrayList<>();

        locationList.forEach(location -> {
            String x = String.valueOf(location.getX());
            String y = String.valueOf(location.getY());
            String z = String.valueOf(location.getZ());
            newList.add("X:" + x + " Y:" + y + " Z:" + z);
        });

        Collections.sort(newList);

        return newList;
    }
}