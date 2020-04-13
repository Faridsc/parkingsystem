package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;
/**
 *this class calculates the price to be paid by the user.
 * while exiting the parking.
 * @author Yahia-CHERIFI
 */
public class FareCalculatorService {

    /**
     * a method that calculates the price to be paid by the user.
     * @param ticket the ticket to which we calculate fare
     */
    public void calculateFare(final Ticket ticket) {
        if ((ticket.getOutTime() == null)
                || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:"
                    + ticket.getOutTime().toString());
        }
        final int millisecondsPerMinute = 60000;
        final double minutesPerHour = 60;
        long inHour =  ticket.getInTime().getTime();
        long outHour = ticket.getOutTime().getTime();
        long parkingDuration = (outHour - inHour) / millisecondsPerMinute;

        switch (ticket.getParkingSpot().getParkingType()) {
            case CAR:
                ticket.setPrice(parkingDuration
                        * (Fare.CAR_RATE_PER_HOUR / minutesPerHour));
                break;
            case BIKE:
                ticket.setPrice(parkingDuration
                        * (Fare.BIKE_RATE_PER_HOUR / minutesPerHour));
                break;
            default: throw new IllegalArgumentException("Unknown Parking Type");
        }
    }
}
