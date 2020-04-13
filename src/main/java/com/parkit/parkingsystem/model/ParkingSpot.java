package com.parkit.parkingsystem.model;

import com.parkit.parkingsystem.constants.ParkingType;

/**
 * this class permits the storage and retrieving values.
 * from the parking table (prod database)
 * @author Yahia-CHERIFI
 */
public class ParkingSpot {
    /**
     * ParkingSpot identifier.
     */
    private int number;

    /**
     * ParkingType that can use this spot.
     * car/bike
     */
    private ParkingType parkingType;

    /**
     * availability of tha parkingSpot.
     */
    private boolean isAvailable;

    /**
     * class constructor.
     * @param num the parking spot identifier
     * @param parkingtype type of the vehicle
     * that can be parked in a parking spot: car or bike
     * @param available tells whether that parking spot
     * is available or not
     */
    public ParkingSpot(final int num,
                       final ParkingType parkingtype, final boolean available) {
        this.number = num;
        this.parkingType = parkingtype;
        this.isAvailable = available;
    }

    /**
     * getter of the parkingSpot id.
     * @return the unique number of a given parkingSpot
     */
    public int getId() {
        return number;
    }

    /**
     * setter of the parkingSpot id.
     * @param num is the parkingSpot identifier
     */
    public void setId(final int num) {
        this.number = num;
    }

    /**
     * getter of the parkingType.
     * @return return the appropriate parkingType: Car or bike
     */
    public ParkingType getParkingType() {
        return parkingType;
    }

    /**
     * setter of the parkingType.
     * @param parkingtype the appropriate parkingType: car or bike
     */
    public void setParkingType(final ParkingType parkingtype) {
        this.parkingType = parkingtype;
    }

    /**
     * getter of parkingSpot isAvailable.
     * @return true if that parkingSpot is available
     * false if busy/occupied
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * setter of the parkingSpot isAvailable.
     * @param available true if a given ParkingSpot is available
     * false if busy
     */
    public void setAvailable(final boolean available) {
        isAvailable = available;
    }

    /**
     *
     * @param o an Object instance
     * @return true if number has the same value of the attribute number
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParkingSpot that = (ParkingSpot) o;
        return number == that.number;
    }

    /**
     * overriding hashCode method.
     * @return  hash code for number
     */
    @Override
    public int hashCode() {
        return number;
    }
}
