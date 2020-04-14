package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

/**
 * @author Yahia-CHERIFI
 * this class contains FareCalculatorService unit tests
 */
public class FareCalculatorServiceTest {
    /**
     * Class under test.
     */
    private static FareCalculatorService fareCalculatorService;

    /**
     * instantiating Ticket.
     */
    private Ticket ticket;

    /**
     * instantiating FareCalculatorService before all tests.
     */
    @BeforeAll
    private static void setUp() {
        fareCalculatorService = new FareCalculatorService();
    }

    /**
     * instantiating a new ticket before each test.
     */
    @BeforeEach
    private void setUpPerTest() {
        ticket = new Ticket();
    }

    @Tag("CalculatingFareForCars")
    @DisplayName("calculate car fare for one hour parking duration")
    @Test
    public void givenOneHourParkingDuration_whenCalculatingCarFare_thenFareShouldBeEqualToFareCarRatePerHour() {
        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals(ticket.getPrice(), Fare.CAR_RATE_PER_HOUR);
    }

    @Tag("CalculatingFareForCars")
    @DisplayName("calculate car fare with less than one hour parking duration")
    @Test
    public void given_45_MinutesParkingDuration_whenCalculatingCarFare_thenFareShouldBeEqualTo_3_OutOf_4_CarFareRatePerHour() {
        Date inTime = new Date();
        //45 minutes parking time should give 3/4th parking fare
        inTime.setTime(System.currentTimeMillis() - (45 * 60 * 1000));
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals((0.75 * Fare.CAR_RATE_PER_HOUR), ticket.getPrice());
    }

    @Tag("CalculatingFareForCars")
    @DisplayName("calculate car fare for twenty four hours parking duration")
    @Test
    public void given_24_HoursParkingDuration_whenCalculatingCarFare_thenFareShouldBeEqualTo_24_MultipliedByFareCarRatePerHour() {
        Date inTime = new Date();
        //24 hours parking time should give 24 * parking fare per hour
        inTime.setTime(System.currentTimeMillis() - (24 * 60 * 60 * 1000));
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals((24 * Fare.CAR_RATE_PER_HOUR), ticket.getPrice());
    }

    @Tag("CalculatingFareForCars")
    @Tag("FreeParkingService")
    @DisplayName("Free parking service for less than thirty minutes parking time")
    @Test
    public void givenTwentyNineMinutesParkingDuration_whenCalculatingCarFare_thenFareShouldBeEqualToZero() {
        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis() - (29 * 60 * 1000));
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.freeParkingService(ticket);
        assertEquals(0, ticket.getPrice());
    }

    @Tag("CalculatingFareForCars")
    @Tag("CalculatingFareForRecurringUsers")
    @DisplayName("Recurring car users should pay ninety five percent of the total fare")
    @Test
    public void givenCarRecurringUser_whenCalculatingFare_thenFareShouldBeEqualToNinetyFivePercentOfTotalFare() {
        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFareForRegularUsers(ticket);
        assertEquals(1.425, ticket.getPrice());
    }

    @Tag("CalculatingFareForCars")
    @DisplayName("Throw exception when inTime comes after OutTime")
    @Test
    public void givenInTimeAfterOutTime_whenCalculatingCarFare_thenAnExceptionShouldBeThrown() {
        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis() + (60 * 60 * 1000));
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket));
    }

    @Tag("CalculatingFareForCars")
    @Tag("CalculatingFareForVehiclesWithNullOutTime")
    @DisplayName("Throw exception when Car outTime is null")
    @Test
    public void givenACarWithNullOutTime_whenCalculatingFare_thenAnExceptionShouldBeThrown() {
        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

        ticket.setInTime(inTime);
        ticket.setOutTime(null);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket));
    }

    @Tag("CalculatingFareForBikes")
    @DisplayName("calculate bike fare for one hour parking duration")
    @Test
    public void givenOneHourParkingDuration_whenCalculatingBikeFare_thenFareShouldBeEqualToFareBikeRatePerHour() {
        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals(ticket.getPrice(), Fare.BIKE_RATE_PER_HOUR);
    }

    @Tag("CalculatingFareForBikes")
    @DisplayName("calculate bike fare with less than one hour parking duration")
    @Test
    public void given45MinsParkingDuration_whenCalculatingBikeFare_thenFareShouldBeEqualTo_3_OutOf_4_FareBikeRatePerHour() {
        Date inTime = new Date();
        //45 minutes parking time should give 3/4th parking fare
        inTime.setTime(System.currentTimeMillis() - (45 * 60 * 1000));
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals((0.75 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice());
    }

    @Tag("CalculatingFareForBikes")
    @Tag("FreeParkingService")
    @DisplayName("Free parking service for less than thirty minutes parking time")
    @Test
    public void givenTwentyNineMinutesParkingDuration_whenCalculatingBikeFare_thenFareShouldBeEqualToZero() {
        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis() - (29 * 60 * 1000));
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals(0, ticket.getPrice());
    }

    @Tag("CalculatingFareForBikes")
    @Tag("CalculatingFareForRecurringUsers")
    @DisplayName("Recurring bike users should pay ninety five percent of the total fare")
    @Test
    public void givenBikeRecurringUser_whenCalculatingBikeFare_thenFareShouldBeEqualToNinetyFivePercentOfTotalFare() {
        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFareForRegularUsers(ticket);
        assertEquals(0.95, ticket.getPrice());
    }

    @Tag("CalculatingFareForBikes")
    @DisplayName("Throw exception when inTime comes after OutTime")
    @Test
    public void givenInTimeAfterOutTime_whenCalculatingBikeFare_thenAnExceptionShouldBeThrown() {
        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis() + (60 * 60 * 1000));
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket));
    }
    @Tag("CalculatingFareForBikes")
    @Tag("CalculatingFareForVehiclesWithNullOutTime")
    @DisplayName("Throw exception when Bike outTime is null")
    @Test
    public void givenABikeWithNullOutTime_whenCalculatingFare_thenAnExceptionShouldBeThrown() {
        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

        ticket.setInTime(inTime);
        ticket.setOutTime(null);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket));
    }

    @Tag("CalculatingFareForUnknownParkingType")
    @DisplayName("Throw exception when parking type is null")
    @Test
    public void givenNullParkingType_whenCalculatingFare_thenAnExceptionShouldBeThrown() {
        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, null, false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket));
    }

    @Tag("CalculatingFareForUnknownParkingType")
    @DisplayName("Throw exception when parking type is not treated by FareCalculatorService")
    @Test
    public void givenUnknownParkingType_whenCalculatingFare_thenAnExceptionShouldBeThrown() {
        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.UNKNOWN, false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket));
        assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFareForRegularUsers(ticket));
    }

}
