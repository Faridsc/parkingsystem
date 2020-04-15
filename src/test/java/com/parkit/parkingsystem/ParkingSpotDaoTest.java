package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.model.ParkingSpot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Yahia-CHERIFI
 * this class contains the unit tests of the ParkingSpotDAO
 */
public class ParkingSpotDaoTest {

    private ParkingSpotDAO parkingSpotDAO;

    @BeforeEach
    public void setUp() {
        parkingSpotDAO = new ParkingSpotDAO();
    }

    @AfterEach
    public void clearUp() {
        parkingSpotDAO = null;
    }

    @Tag("CarParkingSlot")
    @DisplayName("Return Car parking slot number 1 if all car parking slots are available")
    @Test
    public void givenEmptySlots_whenGettingNextAvailableSlot_ThenReturnTheFirstCarSlot() {
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, true);
        parkingSpotDAO.updateParking(parkingSpot);

        final int availableSlot = new ParkingSpotDAO().getNextAvailableSlot(ParkingType.CAR);
        parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR);

        assertEquals(1, availableSlot);
    }

    @Tag("CarParkingSlot")
    @DisplayName("Return Car parking slot number 3 if car parking slots 1 and 2 are occupied")
    @Test
    public void givenOneAvailableSlot_whenGettingNextAvailableSlot_ThenReturnTheCorrectAvailableCarSlot() {
        ParkingSpot parkingSpot1 = new ParkingSpot(1, ParkingType.CAR, false);
        parkingSpotDAO.updateParking(parkingSpot1);
        ParkingSpot parkingSpot2 = new ParkingSpot(2, ParkingType.CAR, false);
        parkingSpotDAO.updateParking(parkingSpot2);
        ParkingSpot parkingSpot3 = new ParkingSpot(3, ParkingType.CAR, true);
        parkingSpotDAO.updateParking(parkingSpot3);

        final int availableSlot = new ParkingSpotDAO().getNextAvailableSlot(ParkingType.CAR);
        parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR);

        assertEquals(3, availableSlot);
    }

    @Tag("BikeParkingSlot")
    @DisplayName("Return Bike parking slot number 4 if all bike parking slots are available")
    @Test
    public void givenBike_whenGettingNextAvailableSlot_ThenReturnTheCorrectBikeSlot() {
        ParkingType parkingType = ParkingType.BIKE;

        final int availableSlot = new ParkingSpotDAO().getNextAvailableSlot(parkingType);
        parkingSpotDAO.getNextAvailableSlot(parkingType);

        assertEquals(4, availableSlot);
    }

    @DisplayName("Return -1 if Parking is null")
    @Test
    public void givenNullParkingType_whenGettingNextAvailableSlot_thenReturnCorrectValue() {

        final int availableSlot = parkingSpotDAO.getNextAvailableSlot(null);
        parkingSpotDAO.getNextAvailableSlot(null);

        assertEquals(-1, availableSlot);
    }
}
