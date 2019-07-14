package World16.Events;

import World16.CustomInventorys.CustomInventoryManager;
import World16.Main.Main;
import World16.Utils.ICustomInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.InventoryHolder;

public class OnInventoryClickEvent implements Listener {

    private Main plugin;

    private CustomInventoryManager customInventoryManager;

    public OnInventoryClickEvent(Main plugin, CustomInventoryManager customInventoryManager) {
        this.plugin = plugin;
        this.customInventoryManager = customInventoryManager;

        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onClick(org.bukkit.event.inventory.InventoryClickEvent event) {
        String inv_name = event.getInventory().getTitle();
        final InventoryHolder holder = event.getInventory().getHolder();

        if (inv_name.equals(this.customInventoryManager.getTestMenu().getInventoryName()) && (event.getCurrentItem() != null) && (!event.getCurrentItem().getType().equals(Material.AIR)) && (event.getClickedInventory() != null) && (holder instanceof ICustomInventory)) {
            event.setCancelled(true);
            this.customInventoryManager.getTestMenu().clicked((Player) event.getWhoClicked(), event.getClick(), event.getSlot(), event.getCurrentItem(), event.getInventory());
        }
    }
}
