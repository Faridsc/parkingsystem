package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * this class contains methods that allow interaction.
 * between the system and the database(ticket table of the prod DB)
 * @author yahia-CHERIFI
 */
public class TicketDAO {

    /**
     * TicketDAO logger.
     */
    private static final Logger LOGGER = LogManager.getLogger("TicketDAO");

    /**
     * instantiating DataBaseConfig.
     */
    public DataBaseConfig dataBaseConfig = new DataBaseConfig();

    /**
     *  save tickets to database.
     * @param ticket the Ticket to be saved
     * @return true if ticket was saved successfully
     * false if the saving process failed
     */
    public boolean saveTicket(final Ticket ticket) {
        Connection con = null;
        try {
            con = dataBaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    DBConstants.SAVE_TICKET);
            //ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
            //ps.setInt(1,ticket.getId());
            ps.setInt(1, ticket.getParkingSpot().getId());
            ps.setString(2, ticket.getVehicleRegNumber());
            ps.setDouble(3, ticket.getPrice());
            ps.setTimestamp(4, new Timestamp(ticket.getInTime().getTime()));
            ps.setTimestamp(5, (ticket.getOutTime() == null) ? null
                    : (new Timestamp(ticket.getOutTime().getTime())));
            return ps.execute();
        } catch (Exception ex) {
            LOGGER.error("Error fetching next available slot", ex);
        } finally {
            dataBaseConfig.closeConnection(con);
            return false;
        }
    }

    /**
     * used to retrieve a ticket from database.
     * @param vehicleRegNumber the user's vehicle registration number
     * @return the latest ticket found in database
     */
    public Ticket getTicket(final String vehicleRegNumber) {
        Connection con = null;
        Ticket ticket = null;
        try {
            con = dataBaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(DBConstants.GET_TICKET);
            //ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
            ps.setString(1, vehicleRegNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ticket = new Ticket();
                ParkingSpot parkingSpot = new ParkingSpot(rs.getInt(1),
                        ParkingType.valueOf(rs.getString(6)), false);
                ticket.setParkingSpot(parkingSpot);
                ticket.setId(rs.getInt(2));
                ticket.setVehicleRegNumber(vehicleRegNumber);
                ticket.setPrice(rs.getDouble(3));
                ticket.setInTime(rs.getTimestamp(4));
                ticket.setOutTime(rs.getTimestamp(5));
            }
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(ps);
        } catch (Exception ex) {
            LOGGER.error("Error fetching next available slot", ex);
        } finally {
            dataBaseConfig.closeConnection(con);
            return ticket;
        }
    }

    /**
     * used to to update given ticket with the price and outTime.
     * @param ticket the Ticket that should be updated
     * @return true if the ticket was updated successfully
     * false if the updating process failed
     */
    public boolean updateTicket(final Ticket ticket) {
        Connection con = null;
        try {
            con = dataBaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    DBConstants.UPDATE_TICKET);
            ps.setDouble(1, ticket.getPrice());
            ps.setTimestamp(2, new Timestamp(ticket.getOutTime().getTime()));
            ps.setInt(3, ticket.getId());
            ps.execute();
            return true;
        } catch (Exception ex) {
            LOGGER.error("Error saving ticket info", ex);
        } finally {
            dataBaseConfig.closeConnection(con);
        }
        return false;
    }
}
