package com.iafnstudios;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidateISBNTest {

    @Test
    public void checkAValid10DigitISBN(){

        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("0201633612");
        assertTrue(result, "first value");
        result = validator.checkISBN("1484240774");
        assertTrue(result,"second value");

    }

    @Test
    public void checkAValid13DigitISBN(){
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("9780134706054");
        assertTrue(result,"first value");
        result = validator.checkISBN("9788126557943");
        assertTrue(result,"second value");
    }

    @Test
    public void tenDigitISBNNumbersEndingInAnXAreValid(){
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("012000030X");
        assertTrue(result);
    }

    @Test
    public void checkAnInvalid10DigitISBN(){
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("0201633613");
        assertFalse(result);
    }

    @Test
    public void checkAnInvalid13DigitISBN(){
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("9780134706055");
        assertFalse(result);
    }

    @Test
    public void nineDigitISBNsAreNotAllowed(){
        ValidateISBN validator = new ValidateISBN();
        //boolean result = validator.checkISBN("123456789");
        assertThrows(NumberFormatException.class,()->validator.checkISBN("123456789"));
    }

    @Test
    public void nonNumericISBNsAreNotAllowed(){
        ValidateISBN validator = new ValidateISBN();
        assertThrows(NumberFormatException.class, ()->validator.checkISBN("helloworld"));
    }
}