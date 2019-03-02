package World16.MysqlAPI;

import CCUtils.Storage.OldMySQL;
import World16.Utils.API;

public class MySQL extends OldMySQL {

    public MySQL(String host, String dataBase, String userName, String passWord, String port) {
        super(host, dataBase, userName, passWord, port);
    }

    public MySQL(API api) {
        super(api.getMysql_HOST(), api.getMysql_DATABASE(), api.getMysql_USER(), api.getMysql_PASSWORD(), api.getMysql_PORT());
    }
}
