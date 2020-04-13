package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class FareCalculatorServiceTest {

    private static FareCalculatorService fareCalculatorService;
    private Ticket ticket;

    @BeforeAll
    private static void setUp() {
        fareCalculatorService = new FareCalculatorService();
    }

    @BeforeEach
    private void setUpPerTest() {
        ticket = new Ticket();
    }

    @Tag("CalculatingFareForCars")
    @DisplayName("calculate car fare for one hour parking duration")
    @Test
    public void givenOneHourParkingDuration_whenCalculatingCarFare_thenFareShouldBeEqualToFareCarRatePerHour(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals(ticket.getPrice(), Fare.CAR_RATE_PER_HOUR);
    }

    @Tag("CalculatingFareForCars")
    @DisplayName("calculate car fare with less than one hour parking duration")
    @Test
    public void given_45_MinutesParkingDuration_whenCalculatingCarFare_thenFareShouldBeEqualTo_3_OutOf_4_CarFareRatePerHour(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  45 * 60 * 1000) );//45 minutes parking time should give 3/4th parking fare
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals( (0.75 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
    }

    @Tag("CalculatingFareForCars")
    @DisplayName("calculate car fare for twenty four hours parking duration")
    @Test
    public void given_24_HoursParkingDuration_whenCalculatingCarFare_thenFareShouldBeEqualTo_24_MultipliedByFareCarRatePerHour(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  24 * 60 * 60 * 1000) );//24 hours parking time should give 24 * parking fare per hour
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals( (24 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
    }

    @Tag("CalculatingFareForCars")
    @DisplayName("Throw exception when inTime comes after OutTime")
    @Test
    public void givenInTimeAfterOutTime_whenCalculatingCarFare_thenAnExceptionShouldBeThrown(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() + (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket));
    }

    @Tag("CalculatingFareForCars")
    @Tag("CalculatingFareForVehiclesWithNullOutTime")
    @DisplayName("Throw exception when Car outTime is null")
    @Test
    public void givenACarWithNullOutTime_whenCalculatingFare_thenAnExceptionShouldBeThrown(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(null);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket));
    }

    @Tag("CalculatingFareForBikes")
    @DisplayName("calculate bike fare for one hour parking duration")
    @Test
    public void givenOneHourParkingDuration_whenCalculatingBikeFare_thenFareShouldBeEqualToFareBikeRatePerHour(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals(ticket.getPrice(), Fare.BIKE_RATE_PER_HOUR);
    }

    @Tag("CalculatingFareForBikes")
    @DisplayName("calculate bike fare with less than one hour parking duration")
    @Test
    public void given45MinsParkingDuration_whenCalculatingBikeFare_thenFareShouldBeEqualTo_3_OutOf_4_FareBikeRatePerHour(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  45 * 60 * 1000) );//45 minutes parking time should give 3/4th parking fare
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals((0.75 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice() );
    }

    @Tag("CalculatingFareForBikes")
    @DisplayName("Throw exception when inTime comes after OutTime")
    @Test
    public void givenInTimeAfterOutTime_whenCalculatingBikeFare_thenAnExceptionShouldBeThrown(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() + (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket));
    }
    @Tag("CalculatingFareForBikes")
    @Tag("CalculatingFareForVehiclesWithNullOutTime")
    @DisplayName("Throw exception when Bike outTime is null")
    @Test
    public void givenABikeWithNullOutTime_whenCalculatingFare_thenAnExceptionShouldBeThrown(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(null);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket));
    }

    @Tag("CalculatingFareForUnknownParkingType")
    @DisplayName("Throw exception when parking type is null")
    @Test
    public void givenNullParkingType_whenCalculatingFare_thenAnExceptionShouldBeThrown(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, null,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket));
    }

    @Tag("CalculatingFareForUnknownParkingType")
    @DisplayName("Throw exception when parking type is not treated by FareCalculatorService")
    @Test
    public void givenUnknownParkingType_whenCalculatingFare_thenAnExceptionShouldBeThrown(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.UNKNOWN,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket));
    }
}
