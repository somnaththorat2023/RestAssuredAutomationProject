package com.api.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.testDataTypes.JQLConstants;

public class DBUtil {

    private static final Logger LOG = LoggerFactory.getLogger(DBUtil.class);

    private static Connection con;
    private static final Config config = new Config();
    private static Statement stmt = null;
    private static ResultSet rs = null;
    private static String sql;
    private static HashMap<String, Object> dbResult = new HashMap<String, Object>();

    public static Connection getConnection() {

        try {
           // Class.forName(config.driverName);
            System.out.println("Before Connected to H2 in-memory database.");
            try {
                con = DriverManager.getConnection(config.url, config.username, config.password);
               // Connection connection = DriverManager.getConnection(config.url);

                System.out.println("Connected to H2 in-memory database.");




            } catch (SQLException ex) {
                // log an exception. fro example:
                System.out.println("Failed to create the database connection.");
            }
        } catch (Exception ex) {
            // log an exception. for example:
            System.out.println("Driver not found.");
        }
        return con;
    }

    public static void closeConnection(Connection connection) throws Exception {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
            Log.info("An error occured while closing the database connecton" + sqlE);
            throw new Exception(sqlE);

        }
    }


    public static String getQuery(String queryString) {
        JQLConstants js = new JQLConstants();
        try {
            sql = (String) js.getClass().getDeclaredField(queryString).get(js);
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // sql = JQLConstants.getString(sss);

        Log.info(sql);
        return sql;
    }

    public static  HashMap<String, Object> executeQuery(String sql) {

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            Log.info("column count is" + columnsNumber);
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    // Log.info(rsmd.getColumnName(i) + "-" + columnValue);

                    dbResult.put(rsmd.getColumnName(i), columnValue);

                }
                System.out.println("");
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dbResult;
    }


}
