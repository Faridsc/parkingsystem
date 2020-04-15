package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * this class contains methods that allow interaction.
 * between the system and the database(parking table of the prod DB)
 * @author yahia-CHERIFI
 */
public class ParkingSpotDAO {
    /**
     * ParkingSpotDAO logger.
     */
    private static final Logger LOGGER = LogManager.getLogger("ParkingSpotDAO");

    /**
     * dataBaseConfig object.
     */
    private DataBaseConfig dataBaseConfig = new DataBaseConfig();

    /**
     * setter of the dataBaseConfig.
     * used mainly in ParkingDataBaseIT.java
     * @param dataBConfig
     */
    public void setDataBaseConfig(final DataBaseConfig dataBConfig) {
        this.dataBaseConfig = dataBConfig;
    }

    /**
     * checks whether there is an available parking slot or not.
     * @param parkingType refers to the vehicle type: bike or car
     * @return -1 if no parking  slot is available
     * int: the parking slot id
     */
    public int getNextAvailableSlot(final ParkingType parkingType) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int result = -1;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(
                    DBConstants.GET_NEXT_PARKING_SPOT);
            ps.setString(1, parkingType.toString());
            rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(ps);
        } catch (Exception ex) {
            LOGGER.error("Error fetching next available slot", ex);
        } finally {
            dataBaseConfig.closeConnection(con);
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeResultSet(rs);
        }
        return result;
    }

    /**
     * update the availability of a parking slot.
     * @param parkingSpot the parking slot that will be updated
     * @return updateRowCount to which
     * 1 is affected if the parking slot was updated successfully
     * false if the update failed
     */
    public boolean updateParking(final ParkingSpot parkingSpot) {
        //update the availability fo that parking slot
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(
                    DBConstants.UPDATE_PARKING_SPOT);
            ps.setBoolean(1, parkingSpot.isAvailable());
            ps.setInt(2, parkingSpot.getId());
            int updateRowCount = ps.executeUpdate();
            return (updateRowCount == 1);
        } catch (Exception ex) {
            LOGGER.error("Error updating parking info", ex);
            return false;
        } finally {
            dataBaseConfig.closeConnection(con);
            dataBaseConfig.closePreparedStatement(ps);
        }
    }

}
