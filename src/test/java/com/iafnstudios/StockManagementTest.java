package com.iafnstudios;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StockManagementTest {

    @Test
    public void testCanGetACorrectLocatorCode(){
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
        };

        StockManager stockManager = new StockManager();
        stockManager.setWebService(testWebService);
        stockManager.setDatabaseService(testDatabaseService);

        String isbn = "0307386244";
        String locatorCode = stockManager.getLocatorCode(isbn);
        assertEquals("6244O4",locatorCode);
    }

    @Test
    public void databaseIsUsedIfDataIsPresent(){
        fail();
    }

    @Test
    public void webServiceIsUsedIfDataIsNotPresentInDatabase(){
        fail();
    }
}
