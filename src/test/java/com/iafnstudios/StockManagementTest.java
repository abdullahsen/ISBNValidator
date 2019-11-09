package com.iafnstudios;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StockManagementTest {

    @Test
    public void testCanGetACorrectLocatorCode(){
        /*
        ExternalISBNDataService testWebService = new ExternalISBNDataService() {
            @Override
            public Book lookup(String isbn) {
                return new Book("0307386244","The Museum of Innocence","Orhan Pamuk");
            }
        };

        ExternalISBNDataService testDatabaseService = new ExternalISBNDataService() {
            @Override
            public Book lookup(String isbn) {
                return null;
            }
        };*/

        ExternalISBNDataService testWebService = mock(ExternalISBNDataService.class);
        ExternalISBNDataService testDatabaseService = mock(ExternalISBNDataService.class);

        when(testWebService.lookup("0307386244")).thenReturn(new Book("0307386244","The Museum of Innocence","Orhan Pamuk"));
        when(testDatabaseService.lookup("0307386244")).thenReturn(null);

        StockManager stockManager = new StockManager();
        stockManager.setWebService(testWebService);
        stockManager.setDatabaseService(testDatabaseService);

        String isbn = "0307386244";
        String locatorCode = stockManager.getLocatorCode(isbn);
        assertEquals("6244O4",locatorCode);
    }

    @Test
    public void databaseIsUsedIfDataIsPresent(){
        ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
        ExternalISBNDataService webService = mock(ExternalISBNDataService.class);

        when(databaseService.lookup("0307386244")).thenReturn(new Book("0307386244","asd","asd"));

        StockManager stockManager = new StockManager();
        stockManager.setWebService(webService);
        stockManager.setDatabaseService(databaseService);

        String isbn = "0307386244";
        String locatorCode = stockManager.getLocatorCode(isbn);

        verify(databaseService,times(1)).lookup("0307386244");
        verify(webService,times(0)).lookup(anyString());
    }

    @Test
    public void webServiceIsUsedIfDataIsNotPresentInDatabase(){
        ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
        ExternalISBNDataService webService = mock(ExternalISBNDataService.class);


        when(webService.lookup("0307386244")).thenReturn(new Book("0307386244","asd","asd"));
        when(databaseService.lookup("0307386244")).thenReturn(null);

        StockManager stockManager = new StockManager();
        stockManager.setWebService(webService);
        stockManager.setDatabaseService(databaseService);

        String isbn = "0307386244";
        String locatorCode = stockManager.getLocatorCode(isbn);

        verify(databaseService,times(1)).lookup("0307386244");
        verify(webService,times(1)).lookup("0307386244");
    }
}
