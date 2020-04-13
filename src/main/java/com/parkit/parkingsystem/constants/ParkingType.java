package com.parkit.parkingsystem.constants;

/**
 * an enumeration that contains the different parking type.
 * @author Yahia-CHERIFI
 */
public enum ParkingType {
    /**
     *  a car parking spot.
     */
    CAR,

    /**
     *  a bike parking spot.
     */
    BIKE,

    /**
     * a parking spot that is used only for test purposes.
     * it represents a parking type that
     * is not treated by the FareCalculatorService
     */
    UNKNOWN; //used for test purposes only
}
