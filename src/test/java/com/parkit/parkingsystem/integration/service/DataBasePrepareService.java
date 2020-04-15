package com.parkit.parkingsystem.integration.service;

import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;

import java.sql.Connection;

/**
 * @author Yahia-CHERIFI
 * this class is used for clearing data base test entries.
 */
public class DataBasePrepareService {
    /**
     * instantiating databaseTestConfig for tests.
     */
    private DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();

    /**
     * this method is responsible for clearing database test data.
     * before each test
     */
    public void clearDataBaseEntries() {
        Connection connection = null;
        try {
            connection = dataBaseTestConfig.getConnection();

            //set parking entries to available
            connection.prepareStatement("update "
                    + "parking set available = true").execute();

            //clear ticket entries;
            connection.prepareStatement("truncate table ticket").execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dataBaseTestConfig.closeConnection(connection);
        }
    }


}
