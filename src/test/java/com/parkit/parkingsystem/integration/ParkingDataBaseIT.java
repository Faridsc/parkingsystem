package com.parkit.parkingsystem.integration;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;
    private ParkingService parkingService;

    @Mock
    private static InputReaderUtil inputReaderUtil;

    @Mock
    private ParkingSpot parkingSpot;

    @BeforeAll
    private static void setUp() throws Exception{
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    private void setUpPerTest() throws Exception {
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        dataBasePrepareService.clearDataBaseEntries();
        parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
    }

    @AfterAll
    private static void tearDown(){

    }

    @DisplayName("The ParkingSystem should save tickets to DB and update parkingSpots with availability")
    @Test
    public void givenAnIncomingVehicle_whenTheSystemProcessesIt_ThenTheAvailabilityOfTheParkingSpotShouldBeUpdatedAndATicketShouldBeSavedToDB(){
        parkingService.processIncomingVehicle();
        //TODO: check that a ticket is actually saved in DB and Parking table is updated with availability
        assertNotNull(ticketDAO.getTicket("ABCDEF"));
        assertTrue(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR) > 1);
        assertFalse(parkingSpot.isAvailable());
    }

    @DisplayName("Saving both fare and out time to DB correctly")
    @Test
    public void givenAnExitingVehicle_whenTheSystemProcessesIt_ThenTheParkingFareAndOutTimeShouldBeSavedToDB() throws InterruptedException {
        parkingService.processIncomingVehicle();
        Thread.sleep(500);
        parkingService.processExitingVehicle();
        //TODO: check that the fare generated and out time are populated correctly in the database
        assertNotNull(ticketDAO.getTicket("ABCDEF").getOutTime());
        assertNotNull(ticketDAO.getTicket("ABCDEF").getPrice());
        assertEquals(0.0, ticketDAO.getTicket("ABCDEF").getPrice());
    }

}
