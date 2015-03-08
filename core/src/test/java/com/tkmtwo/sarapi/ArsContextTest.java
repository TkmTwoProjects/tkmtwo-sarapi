package com.tkmtwo.sarapi;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;


public final class ArsContextTest {

  
  private void verifyFull(ArsContext ac) {
    assertEquals("hostname", ac.getHostName());
    assertEquals(Integer.valueOf(1803), ac.getHostPort());
    assertEquals("hostname:1803", ac.getConnectionString());
  }
  private void verifyHostNoPort(ArsContext ac) {
    assertEquals("hostname", ac.getHostName());
    assertNull(ac.getHostPort());
    assertEquals("hostname:", ac.getConnectionString());
  }
  private void verifyNoHostPort(ArsContext ac) {
    //assertNull(ac.getHostName());
    assertTrue(ac.getHostName() == null || ac.getHostName().equals(""));
    assertEquals(Integer.valueOf(1803), ac.getHostPort());
    assertEquals(":1803", ac.getConnectionString());
  }

  @Test
  public void testConstuctors() {
    String nullString = null;

    verifyFull(new ArsContext("hostname", Integer.valueOf(1803)));
    verifyFull(new ArsContext("hostname", "1803"));
    verifyFull(new ArsContext("hostname:1803"));
    
    verifyHostNoPort(new ArsContext("hostname"));
    verifyHostNoPort(new ArsContext("hostname", nullString));
    verifyHostNoPort(new ArsContext("hostname:"));
    
    verifyNoHostPort(new ArsContext(nullString, Integer.valueOf(1803)));
    verifyNoHostPort(new ArsContext("", Integer.valueOf(1803)));
    verifyNoHostPort(new ArsContext(nullString, "1803"));
    verifyNoHostPort(new ArsContext("", "1803"));
    verifyNoHostPort(new ArsContext(":1803"));

  }
  
  

}
