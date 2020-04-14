package com.parkit.parkingsystem;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Yahia-CHERIFI
 * this class tests the DataBaseConfig.java class
 */
public class DataBaseConfigTest {

    DataBaseConfig dataBaseConfig;

    @BeforeEach
    public void setUp(){
        dataBaseConfig = new DataBaseConfig();
    }

    @DisplayName("Establishing connection")
    @Test
    public void givenDataBaseConnection_whenGettingConnection_thenConnectionShouldEstablished()
            throws ClassNotFoundException, SQLException {

        Connection connection;

        connection = dataBaseConfig.getConnection();

        assertNotNull(connection);
    }

    @DisplayName("Closing connection")
    @Test
    public void givenDataBaseConnection_whenClosingConnection_thenConnectionShouldBeClosed()
            throws ClassNotFoundException, SQLException {
        Connection connection = dataBaseConfig.getConnection();

        dataBaseConfig.closeConnection(connection);

        assertTrue(connection.isClosed());
    }

    @DisplayName("Closing connection")
    @Test
    public void givenNullDataBaseConnection_whenClosingConnection_thenNothingShouldHappen() {
        Connection connection = null;
        dataBaseConfig.closeConnection(connection);
        assertNull(connection);
    }

    @DisplayName("Closing prepared statement")
    @Test
    public void givenAPreparedStatement_whenClosingPreparedStatement_thenPreparedStatementShouldBeClosed()
            throws ClassNotFoundException, SQLException {
        Connection connection = dataBaseConfig.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DBConstants.GET_NUMBER_OF_TICKETS);

        dataBaseConfig.closePreparedStatement(preparedStatement);
        dataBaseConfig.closeConnection(connection);

        assertTrue(preparedStatement.isClosed());
    }

    @DisplayName("Closing connection")
    @Test
    public void givenANullPreparedStatement_whenClosingPreparedStatement_thenNothingShouldHappen()
            throws ClassNotFoundException, SQLException {
        Connection connection = dataBaseConfig.getConnection();
        PreparedStatement preparedStatement = null;

        dataBaseConfig.closePreparedStatement(preparedStatement);
        dataBaseConfig.closeConnection(connection);

        assertNull(preparedStatement);
    }


    @DisplayName("Closing resultSet")
    @Test
    public void givenAResultSet_whenClosingResultSet_thenTheResultSetShouldBeClosed()
            throws ClassNotFoundException, SQLException {
        String vehicleRegNumber = "ABCDEF";
        Connection connection = dataBaseConfig.getConnection();
        ResultSet resultSet;
        PreparedStatement preparedStatement = connection.prepareStatement(DBConstants.GET_NUMBER_OF_TICKETS);
        preparedStatement.setString(1, vehicleRegNumber);
        resultSet = preparedStatement.executeQuery();

        dataBaseConfig.closeResultSet(resultSet);

        assertTrue(resultSet.isClosed());
    }

    @DisplayName("Closing resultSet")
    @Test
    public void givenANullResultSet_whenClosingResultSet_thenShouldHappen()
            throws ClassNotFoundException, SQLException {
        String vehicleRegNumber = "ABCDEF";
        Connection connection = dataBaseConfig.getConnection();
        ResultSet resultSet;
        PreparedStatement preparedStatement = connection.prepareStatement(DBConstants.GET_NUMBER_OF_TICKETS);
        preparedStatement.setString(1, vehicleRegNumber);
        resultSet = null;

        dataBaseConfig.closeResultSet(resultSet);

        assertNull(resultSet);
    }
}