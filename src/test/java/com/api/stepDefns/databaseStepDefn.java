package com.api.stepDefns;

import com.api.service.request.BaseRequest;
import com.api.service.request.RequestFactory;
import com.api.utils.*;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.restassured.specification.RequestSpecification;

import org.h2.tools.RunScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import static org.junit.Assert.fail;
public class databaseStepDefn {


    private static final Logger LOG = LoggerFactory.getLogger(databaseStepDefn.class);
    private final RequestResponse requestResponse;
    private final BaseRequest request;
    private final RequestSpecification requestSpec; // shortcut to request
    private final Config config;
    private String scenarioName;
    private String reqbody;
    public static String SsToken;
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private String sql1;
    private String sql;
    public static HashMap<String, Object> databaseResult = new HashMap<String, Object>();
    public static HashMap<String, Object> dbResult = new HashMap<String, Object>();



    public databaseStepDefn(final RequestResponse requestResponse, Config config) {
        this.requestResponse = requestResponse;
        this.config = config;
        request = RequestFactory.makeGeneratedbRequest(config);
        requestSpec = request.getRequest();

    }

    @Before
    public void before(Scenario scenario) {
        this.scenarioName = scenario.getName();
    }

    @Given("Retrieve dbdata from database {string}")
    public void dbResultset(String queryString) {

        if (databaseResult.isEmpty()) {
            try {

                createtestData();
                if (con == null) {
                    con = DBUtil.getConnection();
                }

               Log.info("feature values is " + queryString);

                sql = DBUtil.getQuery(queryString);

                Log.info(sql);

                stmt = con.createStatement();

               /* if(DataLoader.validate()) {
                    DataLoader.loadScript();
                    DataLoader.execute();
                }*/
                rs = stmt.executeQuery(sql);

                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                Log.info("column count is" + columnsNumber);


                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1)
                            System.out.print(",  ");
                        String columnValue = rs.getString(i);
                        Log.info(rsmd.getColumnName(i) + "-" + columnValue);

                        databaseResult.put(rsmd.getColumnName(i), columnValue);
                    }

                }
                System.out.println("");
            }

            catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    private void createtestData() throws SQLException {
        Log.info("enter creat test data method ");
        try {
            if (con == null) {
                con = DBUtil.getConnection();
            }

            String sql = "Create table book_user (ID int primary key, username varchar(10), password varchar(10));";
            Statement statement = con.createStatement();
            statement.execute(sql);
            System.out.println("Created table students.");

            FileReader fr = new FileReader(JsonDataReader.DEFAULT_FOLDER+"\\data.sql") ;
            BufferedReader br = new BufferedReader(fr) ;
            statement.execute(br.readLine()) ;
            System.out.println("data insertion executed");
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // con.close();


    }


   /* @Given("Retrieve data from database {string}")
    public void getdbData(String queryString) {
        if (dbResult.isEmpty()) {
            if (con == null) {
                con = DBUtil.getConnection();
            }
            Log.info("feature values is " + queryString);

            sql = DBUtil.getQuery(queryString);

            dbResult = DBUtil.executeQuery(sql);

        }
    }*/


}






