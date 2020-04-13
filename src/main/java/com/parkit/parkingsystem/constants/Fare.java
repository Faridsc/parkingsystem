package com.parkit.parkingsystem.constants;

/**
 * this class contains both bike and car rates per hour.
 *@author Yahia-CHERIFI
 */
public final class Fare {

    /**
     * Fare constructor.
     */
    private Fare() {

    }
    /**
     * Represents the fare that should be paid by bike users per hour.
     */
    public static final double BIKE_RATE_PER_HOUR = 1.0;

    /**
     * Represents the fare that should be paid by car users per hour.
     */
    public static final double CAR_RATE_PER_HOUR = 1.5;
}
