package com.parkit.parkingsystem.model;

import java.util.Date;
/**
 * this class permits the storage.
 * and retrieving values from the ticket table (prod database)
 * @author Yahia-CHERIF
 */
public class Ticket {
    /**
     * represents the unique identifier of a ticket.
     */
    private int id;

    /**
     * the number of parkingSpot in which the vehicle will park.
     */
    private ParkingSpot parkingSpot;

    /**
     * represents the user's vehicle registration number.
     */
    private String vehicleRegNumber;

    /**
     * the price that will be paid by the user when exiting the parking.
     */
    private double price;

    /**
     * the time at which a user enters to the parking.
     */
    private Date inTime;

    /**
     * the time at which a user leaves the parking.
     */
    private Date outTime;

    /**
     * getter of a Ticket id.
     * @return int ticket identifier
     */
    public int getId() {
        return id;
    }

    /**
     * setter of a Ticket id.
     * @param identifier the ticket identifier
     */
    public void setId(final int identifier) {
        this.id = identifier;
    }

    /**
     * getter of the Ticket parkingSpot.
     * @return parkingSpot instance
     */
    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    /**
     * setter of the Ticket parkingSpot.
     * @param spot instance of ParkingSpot
     */
    public void setParkingSpot(final ParkingSpot spot) {
        this.parkingSpot = spot;
    }

    /**
     * getter of the Ticket vehicle registration number.
     * @return the vehicle registration number associated
     * to a given ticket
     */
    public String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    /**
     * setter of the Ticket vehicleRegNumber.
     * @param vehicleRegistrationNumber the set
     * vehicle registration number
     */
    public void setVehicleRegNumber(final String vehicleRegistrationNumber) {
        this.vehicleRegNumber = vehicleRegistrationNumber;
    }

    /**
     * getter of Ticket price.
     * @return the price to be paid when leaving the parking
     */
    public double getPrice() {
        return price;
    }

    /**
     * setter of the Ticket price.
     * @param priceToPay the price to be set on the ticket
     */
    public void setPrice(final double priceToPay) {
        this.price = priceToPay;
    }

    /**
     * getter of the Ticket inTime.
     * @return the incoming time
     */
    public Date getInTime() {
        return inTime;
    }

    /**
     * setter of the Ticket inTime.
     * @param incomingTime the incoming time
     */
    public void setInTime(final Date incomingTime) {
        this.inTime = incomingTime;
    }

    /**
     * getter of the Ticket outTime.
     * @return the exit time
     */
    public Date getOutTime() {
        return outTime;
    }

    /**
     * setter of the Ticket outTime.
     * @param leavingTime the exit time
     */
    public void setOutTime(final Date leavingTime) {
        this.outTime = leavingTime;
    }
}
