package World16.CustomInventorys;

public class CustomInventoryManager {

    public static TestCustomInventory testmenu = null;

    public CustomInventoryManager(){

    }

    public void registerAllCustomInventory(){
        this.testmenu = new TestCustomInventory();
        this.testmenu.createCustomInv();
    }
}
