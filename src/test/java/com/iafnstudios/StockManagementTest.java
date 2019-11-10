package com.iafnstudios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StockManagementTest {

    ExternalISBNDataService testWebService;
    ExternalISBNDataService testDatabaseService;
    StockManager stockManager;


    @BeforeEach
    public void setUp() {
        testWebService = mock(ExternalISBNDataService.class);
        testDatabaseService = mock(ExternalISBNDataService.class);
        stockManager = new StockManager();
        stockManager.setWebService(testWebService);
        stockManager.setDatabaseService(testDatabaseService);
    }

    @Test
    public void testCanGetACorrectLocatorCode(){
        when(testWebService.lookup(anyString())).thenReturn(new Book("0307386244","The Museum of Innocence","Orhan Pamuk"));
        when(testDatabaseService.lookup(anyString())).thenReturn(null);

        String isbn = "0307386244";
        String locatorCode = stockManager.getLocatorCode(isbn);
        assertEquals("6244O4",locatorCode);
    }

    @Test
    public void databaseIsUsedIfDataIsPresent(){
        when(testDatabaseService.lookup("0307386244")).thenReturn(new Book("0307386244","asd","asd"));

        String isbn = "0307386244";
        String locatorCode = stockManager.getLocatorCode(isbn);

        verify(testDatabaseService,times(1)).lookup("0307386244");
        verify(testWebService,times(0)).lookup(anyString());
    }

    @Test
    public void webServiceIsUsedIfDataIsNotPresentInDatabase(){
        when(testWebService.lookup("0307386244")).thenReturn(new Book("0307386244","asd","asd"));
        when(testDatabaseService.lookup("0307386244")).thenReturn(null);

        String isbn = "0307386244";
        String locatorCode = stockManager.getLocatorCode(isbn);

        verify(testDatabaseService,times(1)).lookup("0307386244");
        verify(testWebService,times(1)).lookup("0307386244");
    }
}
