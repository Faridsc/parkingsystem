package com.parkit.parkingsystem.integration.config;

import com.parkit.parkingsystem.config.DataBaseConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * this class establishes connections with test database and closes them.
 */
public class DataBaseTestConfig extends DataBaseConfig {
    /**
     * DataBaseTestConfig logger.
     */
    private static final Logger LOGGER
            = LogManager.getLogger("DataBaseTestConfig");

    /**
     * credentials file path
     */
    private static final String FILE_PATH = "src/main/resources/credentials.properties";

    /**
     * mysql prod database url.
     */
    private String url;

    /**
     * user name that will be used.
     * for connecting to mysql db
     */
    private String userName;

    /**
     * password that will be used.
     * for connecting to mysql db
     */
    private String password;
    /**
     * Connect to test database.
     * @return the established connexion
     * @throws ClassNotFoundException
     * @throws SQLException
     */

    public Connection getConnection()
            throws ClassNotFoundException, SQLException {
        LOGGER.debug("Create DB connection");
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (InputStream inputStream = new FileInputStream(FILE_PATH)){
            Properties properties = new Properties();
            properties.load(inputStream);
            url = properties.getProperty("testUrl");
            userName = properties.getProperty("userName");
            password = properties.getProperty("password");
        } catch (FileNotFoundException fnf) {
            LOGGER.error("File not found. ", fnf);
        } catch (IOException ioe) {
            LOGGER.error("", ioe);
        }
        return DriverManager.getConnection(url, userName, password);
    }

    /**
     * Closes data base test connection.
     * @param con as Connection instance to be closed
     */
    public void closeConnection(final Connection con) {
        if (con != null) {
            try {
                con.close();
                LOGGER.debug("Closing DB connection");
            } catch (SQLException e) {
                LOGGER.error("Error while closing connection", e);
            }
        }
    }

    /**
     * Closes prepared statement.
     * @param ps as an instance of PreparedStatement to be closed
     */
    public void closePreparedStatement(final PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
                LOGGER.debug("Closing Prepared Statement");
            } catch (SQLException e) {
                LOGGER.error("Error while closing prepared statement", e);
            }
        }
    }

    /**
     * closes result set.
     * @param rs as an instance of ResultSet to be closed
     */
    public void closeResultSet(final ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
                LOGGER.debug("Closing Result Set");
            } catch (SQLException e) {
                LOGGER.error("Error while closing result set", e);
            }
        }
    }
}
