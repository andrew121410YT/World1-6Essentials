package MysqlAPI;

import Translate.Translate;
import Utils.API;

import java.sql.*;

public class MySQL {

    private String Host;
    private String Database;
    private String Username;
    private String Password;
    private String Port;
    private Connection connection;

    API api = new API();

    public MySQL(/* String host, String database, String username, String password, String port */) {
        /*
         * this.Host = host; this.Database = database; this.Username = username; this.Password =
         * password; this.Port = port;
         */
        this.Host = api.getHOST();
        this.Database = api.getDATABASE();
        this.Username = api.getUSER();
        this.Password = api.getPASSWORD();
        this.Port = api.getPORT();
    }

    public void Connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            System.out.println(Translate.chat("&c[MySQLAPI] Error 01"));
            System.out.println(Translate.chat("&c[MySQLAPI] There are no classes required!"));
            e1.printStackTrace();
        }
        String url =
                "jdbc:mysql://" + this.Host + ":" + this.Port + "/" + this.Database + "?autoReconnect=true";
        try {
            this.connection = DriverManager.getConnection(url, this.Username, this.Password);
        } catch (SQLException e2) {
            System.out.println(Translate.chat("&c[MySQLAPI] Error 02"));
            System.out.println(Translate.chat("[MySQLAPI] An error occurred while connecting!"));
            e2.printStackTrace();
        }
    }

    public void Disconnect() {
        try {
            if (!this.connection.isClosed() && this.connection != null) {
                this.connection.close();
                System.out.println(Translate.chat(
                        "&a[MySQLAPI] The connection to the MySQL server was successfully disconnected!"));
            } else {
                System.out.println(Translate.chat("[MySQLAPI] The connection is already disconnected!"));
            }
        } catch (SQLException e3) {
            System.out.println(Translate.chat("&c[MySQLAPI] Error 03"));
            System.out.println(Translate.chat("[MySQLAPI] There was an error while disconnecting!"));
            e3.printStackTrace();
        }
    }

    public boolean isConnected() {
        try {
            if (this.connection.isClosed()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e2) {
            System.out.println(Translate.chat("&c[MySQLAPI] Error 02"));
            System.out.println(Translate.chat("[MySQLAPI] An error occurred while connecting!"));
            e2.printStackTrace();
        }
        return false;
    }

    public ResultSet GetResult(String command) {
        try {
            if (this.connection.isClosed()) {
                this.Connect();
            }

            Statement st = this.connection.createStatement();
            st.executeQuery(command);
            ResultSet rs = st.getResultSet();
            return rs;

        } catch (SQLException e4) {
            System.out.println(Translate.chat("&c[MySQLAPI] Error 04"));
            System.out
                    .println(Translate.chat("[MySQLAPI] An error occurred while executing the command!"));
            e4.printStackTrace();
        }
        return null;
    }

    public ResultSet GetResultPreparedStatement(String command) {
        try {
            if (this.connection.isClosed()) {
                this.Connect();
            }
            PreparedStatement pst = this.connection.prepareStatement(command);
            pst.executeQuery();
            ResultSet rs = pst.getResultSet();
            return rs;

        } catch (SQLException e4) {
            System.out.println(Translate.chat("&c[MySQLAPI] Error 04"));
            System.out
                    .println(Translate.chat("[MySQLAPI] An error occurred while executing the command!"));
            e4.printStackTrace();
        }
        return null;
    }

    public void ExecuteCommand(String command) {
        try {
            if (this.connection.isClosed()) {
                this.Connect();
            }
            Statement st = this.connection.createStatement();
            st.executeUpdate(command);
        } catch (SQLException e4) {
            System.out.println(Translate.chat("&c[MySQLAPI] Error 04"));
            System.out
                    .println(Translate.chat("[MySQLAPI] An error occurred while executing the command!"));
            e4.printStackTrace();
        }

    }
}
