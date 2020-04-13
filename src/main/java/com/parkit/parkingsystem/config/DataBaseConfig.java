package com.parkit.parkingsystem.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * this class establishes connections to database and closes them.
 * @author Yahia-CHERIFI
 */
public class DataBaseConfig {

    /**
     * DataBaseConfig logger.
     */
    private static final Logger LOGGER = LogManager.getLogger("DataBaseConfig");
    /**
     *  Connect to mysql as root.
     * @return Connection to database
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Connection getConnection()
            throws ClassNotFoundException, SQLException {
        LOGGER.info("Create DB connection");
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/prod "
                        + "?useSSL=false&serverTimezone=UTC",
                                "root", "rootroot");
    }

    /**
     * Close database connection.
     * @param con as Connection instance to be closed
     */
    public void closeConnection(final Connection con) {
        if (con != null) {
            try {
                con.close();
                LOGGER.info("Closing DB connection");
            } catch (SQLException e) {
                LOGGER.error("Error while closing connection", e);
            }
        }
    }

    /**
     *  Close preparedStatement.
     * @param ps as an instance of PreparedStatement to be closed
     */
    public void closePreparedStatement(final PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
                LOGGER.info("Closing Prepared Statement");
            } catch (SQLException e) {
                LOGGER.error("Error while closing prepared statement", e);
            }
        }
    }

    /**
     * Close the ResultSet.
     * @param rs as an instance of ResultSet to be closed
     */
    public void closeResultSet(final ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
                LOGGER.info("Closing Result Set");
            } catch (SQLException e) {
                LOGGER.error("Error while closing result set", e);
            }
        }
    }
}
