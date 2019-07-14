package World16.CustomInventorys;

import World16.Main.Main;
import World16.Utils.ICustomInventory;
import World16.Utils.InventoryUtils;
import World16.Utils.Translate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TestCustomInventory implements ICustomInventory {

    private Main plugin;

    private Inventory inv;

    private String inventory_name;
    private int inv_rows = 4 * 9;

    private CustomInventoryManager customInventoryManager;

    public TestCustomInventory(CustomInventoryManager customInventoryManager, Main getPlugin) {
        this.customInventoryManager = customInventoryManager;
        this.plugin = getPlugin;
    }

    public void createCustomInv() {
        inventory_name = Translate.chat("Test GUI");

        inv = Bukkit.createInventory(this, inv_rows, inventory_name);
    }

    public Inventory GUI(Player player) {

        InventoryUtils.createItem(inv, Material.REDSTONE, 1, 1, "Test101", "Test Lore");

        return inv;
    }

    public void clicked(Player player, ClickType clickType, int slot, ItemStack clicked, Inventory inv) {
        if (clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Translate.chat("Test101"))) {
            player.sendMessage(Translate.chat("it worked."));
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