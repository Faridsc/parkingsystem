package com.parkit.parkingsystem;

import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InputReaderUtilTest {

    private InputReaderUtil inputReaderUtil;

    @BeforeEach
    private void setUpPerTest() {
        inputReaderUtil = new InputReaderUtil();
    }

    @DisplayName("Return the correct input value")
    @Tag("ReadSelectionTests")
    @Test
    public void givenCorrectIntInputVue_whenReadingSelection_thenReturnSameValue() {
        String input = "1\n";
        InputStream inputStream = new ByteArrayInputStream((input).getBytes(StandardCharsets.UTF_8));

        Scanner scan = new Scanner(inputStream);
        inputReaderUtil.setScan(scan);

        assertEquals(1, inputReaderUtil.readSelection());
    }

    @DisplayName("Return -1 when input value is incorrect")
    @Tag("ReadSelectionTests")
    @Test
    public void givenZeroAsInput_whenReadingSelection_thenReturnMinusOne() {
        String input = "0\n";
        InputStream inputStream = new ByteArrayInputStream((input).getBytes(StandardCharsets.UTF_8));

        Scanner scan = new Scanner(inputStream);
        inputReaderUtil.setScan(scan);

        assertEquals(-1, inputReaderUtil.readSelection());
    }

    @DisplayName("Return -1 when input value is incorrect")
    @Tag("ReadSelectionTests")
    @Test
    public void givenFiveAsInputValue_whenReadingSelection_thenReturnMinusOne() {
        String input = "5\n";
        InputStream inputStream = new ByteArrayInputStream((input).getBytes(StandardCharsets.UTF_8));

        Scanner scan = new Scanner(inputStream);
        inputReaderUtil.setScan(scan);

        assertEquals(-1, inputReaderUtil.readSelection());
    }

    @DisplayName("Return -1 when input value is incorrect")
    @Tag("ReadSelectionTests")
    @Test
    public void givenStringAsInputValue_whenReadingSelection_thenReturnMinusOne() {
        String input = "Junit5\n";
        InputStream inputStream = new ByteArrayInputStream((input).getBytes(StandardCharsets.UTF_8));

        Scanner scan = new Scanner(inputStream);
        inputReaderUtil.setScan(scan);

        assertEquals(-1, inputReaderUtil.readSelection());
    }

    @DisplayName("Empty vehicle registration number throws an IllegalArgumentException")
    @Tag("ReadVehicleRegistrationNumberTests")
    @Test
    public void givenEmptyVehicleRegNumber_whenReadingVehicleRegNumber_thenAnExceptionShouldBeThrown() {
        String input = "         \n";
        InputStream inputStream = new ByteArrayInputStream((input).getBytes(StandardCharsets.UTF_8));

        Scanner scan = new Scanner(inputStream);
        inputReaderUtil.setScan(scan);

        assertThrows(IllegalArgumentException.class, () -> inputReaderUtil.readVehicleRegistrationNumber());
    }


    @DisplayName("Returning the correct input value")
    @Tag("ReadVehicleRegistrationNumberTests")
    @Test
    public void givenCorrectInputValue_whenReadingRegistrationNumber_thenReturnSameValue() throws Exception {
        String input = "AZERTY\n";
        InputStream inputStream = new ByteArrayInputStream((input).getBytes(StandardCharsets.UTF_8));

        Scanner scan = new Scanner(inputStream);
        inputReaderUtil.setScan(scan);

        assertEquals("AZERTY", inputReaderUtil.readVehicleRegistrationNumber());
    }
}

