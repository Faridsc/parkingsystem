package com.parkit.parkingsystem;

import com.parkit.parkingsystem.service.InteractiveShell;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InterActiveShellTest {

    @DisplayName("Loading System interface with the right values")
    @Test
    public void loadMenuShouldContainTheCorrectValues() throws Exception {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        InteractiveShell interactiveShell = new InteractiveShell();
        String userInput = "3\n";
        InputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));

        interactiveShell.loadInterface();

        assertTrue(outContent.toString().contains(
                "Please select an option."
                        + "Simply enter the number to choose an action"));
        assertTrue(outContent.toString().contains("1 New Vehicle Entering - Allocate Parking Space"));
        assertTrue(outContent.toString().contains("2 Vehicle Exiting - Generate Ticket Price"));
        assertTrue(outContent.toString().contains("3 Shutdown System"));

        inputStream.close();
    }
}