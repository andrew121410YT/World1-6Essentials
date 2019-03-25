package World16.CustomInventorys;

public class CustomInventoryManager {

    private static TestCustomInventory testMenu = null;

    public CustomInventoryManager() {

    }

    public void registerAllCustomInventorys() {
        testMenu = new TestCustomInventory();
        testMenu.createCustomInv();
    }

    public static TestCustomInventory getTestMenu() {
        return testMenu;
    }
}