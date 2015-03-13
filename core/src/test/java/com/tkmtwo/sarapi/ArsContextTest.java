package com.tkmtwo.sarapi;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import java.util.List;


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

  @Test
  public void testConstuctors() {
    String nullString = null;

    verifyFull(new ArsContext("hostname", Integer.valueOf(1803)));
    verifyFull(new ArsContext("hostname", "1803"));
    verifyFull(new ArsContext("hostname:1803"));
    
    verifyHostNoPort(new ArsContext("hostname"));
    verifyHostNoPort(new ArsContext("hostname", nullString));
    verifyHostNoPort(new ArsContext("hostname:"));
    
  }
  
  @Test
  public void testValueOf() {
    verifyFull(ArsContext.valueOf("hostname:1803"));
  }
  
  @Test
  public void testValuesOf() {
    String s = "hostname:1803,hostname:1803,hostname:1803,otherhostname:1804";
    
    List<ArsContext> l = ArsContext.valuesOf(s);
    assertNotNull(l);
    assertEquals(4, l.size());
    for (int i = 0; i < 3; i++) {
      verifyFull(l.get(i));
    }
    assertEquals("otherhostname", l.get(3).getHostName());
    assertEquals(Integer.valueOf(1804), l.get(3).getHostPort());
  }
  

}
