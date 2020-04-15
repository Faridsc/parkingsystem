package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *this class allows interaction between the user and the ParkingSystem.
 * it also loads and displays the ParkingSystem's main menu
 * @author Yahia-CHERIFI
 */
public final class InteractiveShell {

    /**
     * InteractiveShell logger.
     */
    private static final Logger LOGGER
            = LogManager.getLogger("InteractiveShell");

    /**
     * a method that loads and displays the parking main menu.
     * it is responsible for processing the incoming vehicles
     * the out coming vehicles
     * and shutting down the ParkingSystem
     */
    public static void loadInterface() {
        LOGGER.info("App initialized!!!");
        System.out.println("Welcome to Parking System!");
        final int i = 3;
        boolean continueApp = true;
        InputReaderUtil inputReaderUtil = new InputReaderUtil();
        ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO();
        TicketDAO ticketDAO = new TicketDAO();
        ParkingService parkingService =
                new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

        while (continueApp) {
            loadMenu();
            int option = inputReaderUtil.readSelection();
            switch (option) {
                case 1:
                    parkingService.processIncomingVehicle();
                    break;
                case 2:
                    parkingService.processExitingVehicle();
                    break;
                case i:
                    System.out.println("Exiting from the system!");
                    continueApp = false;
                    break;
                default: System.out.println("Unsupported option."
                        + "Please enter a number corresponding"
                        + "to the provided menu");
            }
        }
    }

    /**
     * the elements that will displayed after launching the ParkingSystem.
     */
    private static void loadMenu() {
        System.out.println("Please select an option."
                + "Simply enter the number to choose an action");
        System.out.println("1 New Vehicle Entering - Allocate Parking Space");
        System.out.println("2 Vehicle Exiting - Generate Ticket Price");
        System.out.println("3 Shutdown System");
    }

}
