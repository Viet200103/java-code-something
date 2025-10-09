/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viettl.data;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author viett
 */
public class DatabaseHelper implements Serializable {

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        // 1. load driver is available in project
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        // 2. Create connection string
//        String url = "jdbc:sqlserver://localhost:1433;"
//                + "databaseName=master";
//                + "instanceName=SQL2019";
        // 3. Open connection

//        Connection con = DriverManager.getConnection(url, "sa", "123456");
//        return con;
        Connection con = null;
        try {
            con = getDataSourceConntect();
        } catch (NamingException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    public static Connection getDataSourceConntect() throws NamingException, SQLException {
        Context ctx = new InitialContext();
        Context envCtx = (Context) ctx.lookup("java:comp/env");
        DataSource ds = (DataSource) envCtx.lookup("DBConnect");
        return ds.getConnection();
    }
}
