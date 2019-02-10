package World16.CustomInventorys;

public class CustomInventoryManager {

    private static TestCustomInventory testMenu = null;

    public CustomInventoryManager() {

    }

    public void registerAllCustomInventory() {
        this.testMenu = new TestCustomInventory();
        this.testMenu.createCustomInv();
    }

    public static TestCustomInventory getTestMenu() {
        return testMenu;
    }
}