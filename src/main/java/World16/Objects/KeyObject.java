package World16.Objects;

public class KeyObject {

    String playername;

    String keyname1;
    String key1;

    String keyname2;
    String key2;

    String keyname3;
    String key3;


    public KeyObject(){

    }

    public KeyObject(String playerName, String keyName1, String key1){
        this.playername = playerName;
        this.keyname1 = keyName1;
        this.key1 = key1;
    }

    public String getPlayerName(){
        return this.playername;
    }

    public String getKeyName1(){
        return this.keyname1;
    }

    public String getKey1(){
        return this.key1;
    }
}
