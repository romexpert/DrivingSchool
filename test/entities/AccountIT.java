/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.util.UUID;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author admin
 */
public class AccountIT {
    
    public AccountIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
    @Test
    public void testLogin() throws Exception {
        String email = "student@driftman.ru";
        String password = "student123";
        Account expResult = Account.getTest(AccountRole.Student, "Andry");
        
        Account result = Account.login(email, password);
        
        assertEquals(expResult.getRole(), result.getRole());
        assertEquals(expResult.getName(), result.getName());
        assertNotNull(result.getSessionId());
        assertNull(expResult.getSessionId());
    }
    
    @Test
    public void testLogin_Admin() throws Exception {
        String email = "admin@driftman.ru";
        String password = "admin123";
        Account expResult = Account.getTest(AccountRole.Admin, "Admin Andry");
        
        Account result = Account.login(email, password);
        
        assertEquals(expResult.getRole(), result.getRole());
        assertEquals(expResult.getName(), result.getName());
        assertNotNull(result.getSessionId());
        assertNull(expResult.getSessionId());
    }
    
    @Test
    public void testLogin_IncorrectPassword() throws Exception {
        String email = "student@driftman.ru";
        String password = "student1234";
        
        Account result = Account.login(email, password);
        
        assertNull(result);
    }

    @Test
    public void testGet() throws Exception {
        Account expected = Account.login("admin@driftman.ru", "admin123");
        
        Account result = Account.get(expected.getSessionId());
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getRole(), result.getRole());
        assertEquals(expected.getSessionId(), result.getSessionId());
    }
    
    @Test
    public void testGet_NoSession() throws Exception {
        try {
            Account result = Account.get(UUID.randomUUID());
            
            fail("SessionNotFound expected");
        } catch(Exception ex){
            assertEquals("SessionNotExists", ex.getMessage());
        }
    }
    
}
