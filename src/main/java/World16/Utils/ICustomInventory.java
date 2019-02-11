package World16.Utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public interface ICustomInventory extends InventoryHolder {

    //Vars
//    Inventory inv = null;
//    String inventory_name = null;
//    int inv_rows = 4 * 9;
    //...

    void createCustomInv();

    Inventory GUI(Player player);

    void clicked(Player player, int slot, ItemStack clicked, Inventory inv);

//    Inventory getInventory();

    String getInventoryName();

    int getInvRows();
}
