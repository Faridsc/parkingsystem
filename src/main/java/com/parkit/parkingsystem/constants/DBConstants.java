package com.parkit.parkingsystem.constants;
/**
 * this class contains the different SQL queries.
 * that permit exchange between the app and the different tables
 *@author Yahia-CHERIFI
 */
public final class DBConstants {
    /**
     * Class constructor.
     */
    private DBConstants() {

    }

    /**
     * sql query that is used to search for available parking spots.
     */
    public static final String GET_NEXT_PARKING_SPOT = "select "
       + "min(PARKING_NUMBER) from parking where AVAILABLE = true and TYPE = ?";

    /**
     * sql query that is used to update parking spot availability.
     */
    public static final String UPDATE_PARKING_SPOT = "update parking"
            + " set available = ? where PARKING_NUMBER = ?";

    /**
     * sql query used to save tickets into database.
     */
    public static final String SAVE_TICKET = "insert into ticket "
            + "(PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)"
            + " values(?,?,?,?,?)";

/**
 * sql query used to update ticket with price and outTime.
 */
    public static final String UPDATE_TICKET = "update ticket"
            + " set PRICE=?, OUT_TIME=? where ID=?";

    /**
     * sql query used to retrieve a ticket from database.
     */
    public static final String GET_TICKET = "select "
            + "t.PARKING_NUMBER, t.ID, t.PRICE, t.IN_TIME, t.OUT_TIME, p.TYPE"
            + " from ticket t,parking p"
            + " where p.parking_number = t.parking_number and"
            + " t.VEHICLE_REG_NUMBER=? order by t.IN_TIME DESC limit 1";

    /**
     * sql query that is used to search for recurring tickets in database.
     * used mainly to decide if the five percent
     * discount feature will be applied or not
     */
    public static final String GET_NUMBER_OF_TICKETS =
            "select count(*) as numberOfTickets from ticket "
                    + "where VEHICLE_REG_NUMBER=? and OUT_TIME is not null";

    /**
     * compare incoming vehicle registration number.
     * to the vehicles that are using the parking
     */
    public static final String CHECK_VEHICLE_REG_NUMBER = "select count(*) "
            + "as existingTicket from ticket"
            + " where VEHICLE_REG_NUMBER=? and OUT_TIME is null";
}
