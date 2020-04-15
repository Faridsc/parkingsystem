package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;


import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Yahia-CHERIFI
 * this class contains unit tests for the TicketDAO.java
 */
public class TicketDAOTest {

    private static TicketDAO ticketDAO;
    private Ticket ticket;
    private String vehicleRegNumber = "ABCDEF";
    private static DataBaseTestConfig dataBaseConfig = new DataBaseTestConfig();
    private static DataBasePrepareService dataBasePrepareService = new DataBasePrepareService();

    @BeforeAll
    private static void setUp() {
        ticketDAO = new TicketDAO();
    }

    @BeforeEach
    private void setUpPerTest() {
        ticketDAO.setDataBaseConfig(dataBaseConfig);
        ticket = new Ticket();
        ticket.getId();
        ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
        ticket.setParkingSpot(new ParkingSpot(1, ParkingType.CAR, false));
        ticket.setVehicleRegNumber(vehicleRegNumber);
        ticket.setPrice(Fare.CAR_RATE_PER_HOUR);
    }

    @AfterEach
    public void clearUp() {
        dataBasePrepareService.clearDataBaseEntries();
    }

    @DisplayName("New users with no existing tickets")
    @Test
    public void givenANewUser_whenSearchingForExistingTickets_thenItShouldReturnZero() {
        ticketDAO.saveTicket(ticket);

        int noTicketFound = ticketDAO.getNumberOfTickets(vehicleRegNumber);
        ticketDAO.getTicket(vehicleRegNumber);

        assertEquals(0, noTicketFound);
        assertNotNull(ticket.getId());
    }

    @DisplayName("Getting the correct number of tickets for recurring users")
    @Test
    public void givenARecurringUser_whenSearchingForPreviousTickets_thenItShouldReturnTheRightValue() {
        ticketDAO.saveTicket(ticket);
        ticket.setOutTime(new Date());
        ticketDAO.updateTicket(ticket);
        Ticket ticket1 = ticketDAO.getTicket("ABCDEF");
        ticket1.setOutTime(new Date());
        ticketDAO.saveTicket(ticket1);

        int numberOfTickets = ticketDAO.getNumberOfTickets("ABCDEF");

        assertEquals(1, numberOfTickets);
    }

    @DisplayName("Update ticket with outTime")
    @Test
    public void givenAnOutTime_whenUpdatingTicket_thenTicketShouldBeUpdated() {
        ticketDAO.saveTicket(ticket);
        ticket.setOutTime(new Date());

        boolean updated = ticketDAO.updateTicket(ticket);

        assertTrue(updated);
    }

    @DisplayName("return the number of tickets for recurring users")
    @Test
    public void givenAUser_whenSearchingForPreviousTickets_thenItShouldReturnTheRightValue() {
        ticketDAO.saveTicket(ticket);
        Ticket ticket1 = ticketDAO.getTicket("ABCDEF");
        ticket1.setOutTime(new Date());
        ticketDAO.saveTicket(ticket1);

        int numberOfTickets = ticketDAO.existingTicketWithNullOutTime("ABCDEF");

        assertEquals(1, numberOfTickets);
    }
}