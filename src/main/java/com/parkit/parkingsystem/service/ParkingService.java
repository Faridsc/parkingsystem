package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.Date;

/**
 * this class processes both the incoming and the out coming vehicles.
 * @author Yahia-CHERIFI
 */
public class ParkingService {

    /**
     *ParkingService logger.
     */
    private static final Logger LOGGER = LogManager.getLogger("ParkingService");

    /**
     * instantiating {@link FareCalculatorService}.
     */
    private static FareCalculatorService fareCalculatorService
            = new FareCalculatorService();

    /**
     * InputReaderUtil object.
     */
    private InputReaderUtil inputReaderUtil;

    /**
     * ParkingSpotDAO object.
     */
    private ParkingSpotDAO parkingSpotDAO;

    /**
     * TicketDAO object.
     */
    private TicketDAO ticketDAO;

    /**
     * class constructor.
     * @param readerUtil
     * @param daoParkingSpot
     * @param daoTicket
     */
    public ParkingService(final InputReaderUtil readerUtil,
                        final ParkingSpotDAO daoParkingSpot,
                        final TicketDAO daoTicket) {
        this.inputReaderUtil = readerUtil;
        this.parkingSpotDAO = daoParkingSpot;
        this.ticketDAO = daoTicket;
    }

    /**
     * a method that processes incoming/entering vehicles.
     */
    public void processIncomingVehicle() {
        try {
            ParkingSpot parkingSpot = getNextParkingNumberIfAvailable();
            if (parkingSpot != null && parkingSpot.getId() > 0) {
                String vehicleRegNumber = getVehicleRegNumber();
                while (ticketDAO.existingTicketWithNullOutTime(vehicleRegNumber) > 0) {
                    LOGGER.error("The provided vehicle registration number"
                            + " is already using the parking."
                            + " Enter a valid Vehicle registration number");
                    vehicleRegNumber = getVehicleRegNumber();
                }
                parkingSpot.setAvailable(false);
                //allot this parking space and mark it's availability as false
                parkingSpotDAO.updateParking(parkingSpot);

                Date inTime = new Date();
                Ticket ticket = new Ticket();
                ticket.setParkingSpot(parkingSpot);
                ticket.setVehicleRegNumber(vehicleRegNumber);
                ticket.setPrice(0);
                ticket.setInTime(inTime);
                ticket.setOutTime(null);
                ticketDAO.saveTicket(ticket);
                if (ticketDAO.getNumberOfTickets(
                        ticket.getVehicleRegNumber()) > 0) {
                    LOGGER.info("Welcome back! As a recurring"
                            + " user of our parking lot,"
                            + " you'll benefit from a 5% discount.");
                }
                LOGGER.info("Generated Ticket and saved in DB");
                LOGGER.info("Please park your vehicle in spot number: {}",
                        parkingSpot.getId());
                LOGGER.info("Recorded in-time for vehicle number: {} is: {}",
                        vehicleRegNumber, inTime);
            }
        } catch (Exception e) {
            LOGGER.error("Unable to process incoming vehicle", e);
        }
    }

    /**
     * calls the readVehicleRegistrationNumber method of InputReaderUtil class.
     * asks a user for his/her vehicle registration number
     * @return String: the vehicle registration number
     * @throws IllegalArgumentException
     * if the provided vehicle reg number is incorrect or null
     */
    private String getVehicleRegNumber() throws Exception {
        LOGGER.info("Please type the vehicle "
                + "registration number and press enter key");
        return inputReaderUtil.readVehicleRegistrationNumber();
    }

    /**
     * checks if there is any available parking spot for incoming users.
     * @return the available parking spot
     * @throws Exception if there is no available parking spot
     * or if the user's input is incorrect
     */
    public ParkingSpot getNextParkingNumberIfAvailable() {
        int parkingNumber = 0;
        ParkingSpot parkingSpot = null;
        try {
            ParkingType parkingType = getVehicleType();
            parkingNumber = parkingSpotDAO.getNextAvailableSlot(parkingType);
            if (parkingNumber > 0) {
                parkingSpot = new ParkingSpot(parkingNumber, parkingType, true);
            } else {
                throw new SQLException("Error fetching parking number from DB."
                        + "Parking slots might be full");
            }
        } catch (IllegalArgumentException ie) {
            LOGGER.error("Error parsing user input for type of vehicle", ie);
        } catch (Exception e) {
            LOGGER.error("Error fetching next available parking slot", e);
        }
        return parkingSpot;
    }

    /**
     * getting the incoming vehicle type: bike or car.
     * @return the provided ParkingType
     * @throws IllegalArgumentException
     * if the ParkingType is untreated by the ParkingSystem
     */
    private ParkingType getVehicleType() {
        LOGGER.info("Please select vehicle type from menu");
        LOGGER.info("1 CAR");
        LOGGER.info("2 BIKE");
        int input = inputReaderUtil.readSelection();
        switch (input) {
            case 1:
                return ParkingType.CAR;
            case 2:
                return ParkingType.BIKE;
            default:
                LOGGER.error("Incorrect input provided");
                throw new IllegalArgumentException("Entered input is invalid");
        }
    }

    /**
     * a method that processes exiting vehicles.
     */
    public void processExitingVehicle() {
        try {
            String vehicleRegNumber = getVehicleRegNumber();
            Ticket ticket = ticketDAO.getTicket(vehicleRegNumber);
            Date outTime = new Date();
            ticket.setOutTime(outTime);
            int numberOfTickets = ticketDAO.getNumberOfTickets(
                    ticket.getVehicleRegNumber());
            if (numberOfTickets > 0) {
                fareCalculatorService.calculateFareForRegularUsers(ticket);
            } else {
                fareCalculatorService.calculateFare(ticket);
            }

            if (ticketDAO.updateTicket(ticket)) {
                ParkingSpot parkingSpot = ticket.getParkingSpot();
                parkingSpot.setAvailable(true);
                parkingSpotDAO.updateParking(parkingSpot);
                LOGGER.info("Please pay the parking fare: {}",
                        ticket.getPrice());
                LOGGER.info("Recorded out-time for vehicle number: {} is {} ",
                        ticket.getVehicleRegNumber(), outTime);
            } else {
                LOGGER.error("Unable to update "
                        + "ticket information. Error occurred");
            }
        } catch (Exception e) {
            LOGGER.error("Unable to process exiting vehicle", e);
        }
    }
}
