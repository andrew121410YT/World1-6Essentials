package World16.CustomInventorys;

import World16.Utils.InventoryUtils;
import World16.Utils.Translate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TestCustomInventory {

    public static Inventory inv;
    public static String inventory_name;
    public static int inv_rows = 4 * 9;

    public static void createCustomInv() {
        inventory_name = Translate.chat("Test GUI");

        inv = Bukkit.createInventory(null, inv_rows);
    }

    public static Inventory GUI(Player player) {

        Inventory toReturn = Bukkit.createInventory(null, inv_rows, inventory_name);

        InventoryUtils.createItem(inv, Material.REDSTONE, 1, 1, "Test101", "Test Lore");

        toReturn.setContents(inv.getContents());

        return toReturn;
    }

    public static void clicked(Player player, int slot, ItemStack clicked, Inventory inv) {
        if (clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Translate.chat("Test101"))) {
            player.sendMessage(Translate.chat("it worked."));
        }
    }

}
