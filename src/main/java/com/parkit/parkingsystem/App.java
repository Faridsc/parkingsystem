package com.parkit.parkingsystem;

import com.parkit.parkingsystem.service.InteractiveShell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Yahia-CHERIFI
 * the class that starts the application
 */
public final class App {
    /**
     * app logger.
     */
    private static final Logger LOGGER = LogManager.getLogger("App");

    /**
     * Class constructor.
     */
    private App() {
    }

    /**
     * The main method that stars the whole application.
     * @param args
     */
    public static void main(final String[] args) {
        LOGGER.info("Initializing Parking System");
        InteractiveShell.loadInterface();
    }
}
