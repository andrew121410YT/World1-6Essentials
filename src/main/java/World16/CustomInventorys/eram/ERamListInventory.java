package World16.CustomInventorys.eram;

import World16.CustomInventorys.CustomInventoryManager;
import World16.Main.Main;
import World16.Utils.ICustomInventory;
import World16.Utils.InventoryUtils;
import World16.Utils.Translate;
import World16.test.ERamManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ERamListInventory implements ICustomInventory {

    private Main plugin;

    //Maps
    Map<String, Map<String, List<Location>>> eramMap = ERamManager.stringRawLocationObjectHashMap;
    //...

    private Inventory inv;

    private String inventory_name;
    private int inv_rows = 4 * 9;

    private CustomInventoryManager customInventoryManager;
    private ERamManager eRamManager;

    public ERamListInventory(CustomInventoryManager customInventoryManager, Main getPlugin) {
        this.customInventoryManager = customInventoryManager;
        this.plugin = getPlugin;

        eRamManager = new ERamManager(this.plugin.getCustomConfigManager());
    }

    public void createCustomInv() {
        inventory_name = Translate.chat("ERam List GUI");

        inv = Bukkit.createInventory(this, inv_rows, inventory_name);
    }

    public Inventory GUI(Player player) {
        inv.clear();

        Map<String, List<Location>> emapIN = eramMap.get(player.getDisplayName());

        List<String> bigdaddy;

        Set<String> keySet = emapIN.keySet();
        String[] papa2 = keySet.toArray(new String[0]);
        Arrays.sort(papa2);
        bigdaddy = Arrays.asList(papa2);

        bigdaddy.forEach((k) -> {
            List<Location> v = emapIN.get(k);
            InventoryUtils.createItem(inv, Material.GREEN_SHULKER_BOX, v.size(), inv.firstEmpty() + 1, k, "Click me to open up", "Shift Left Click to delete me!");
        });

        return inv;
    }

    public void clicked(Player player, ClickType clickType, int slot, ItemStack clicked, Inventory inv) {
        Map<String, List<Location>> emapIN = eramMap.get(player.getDisplayName());

        if (emapIN.containsKey(clicked.getItemMeta().getDisplayName()) && clickType.isLeftClick()) {
            player.playSound(player.getLocation(), Sound.BLOCK_PISTON_CONTRACT, 10.0f, 1.0f);
            player.closeInventory();
            player.openInventory(this.customInventoryManager.geteRamInsideInventory().GUI(player, slot, clicked, inv));
        }

        if (emapIN.containsKey(clicked.getItemMeta().getDisplayName()) && clickType.isShiftClick()) {
            eRamManager.delete(player.getDisplayName(), player.getUniqueId(), clicked.getItemMeta().getDisplayName());
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10.0f, 1.0f);
            player.closeInventory();
            player.openInventory(this.GUI(player));
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