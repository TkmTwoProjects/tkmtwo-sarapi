package com.tkmtwo.sarapi;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;


public final class ArsSourceTest {

  
  @Test
  public void testThis() {
    StringBuffer sb = new StringBuffer();
    sb
      .append("environmentName=itsmtesting.accenture.com")
      .append("&userName=itsm.app")
      //.append("&userPassword=somepassword")
      .append("&userPassword=itsmAppTst")
      .append("&contexts=wtrsfes19202:1803,wtrsfes19203:1803");
    ArsSource as = new ArsSource(sb.toString());


    as.afterPropertiesSet();
    
    ArsField af = as.getSchemaHelper().getField("User", "Login Name");
    assertEquals(Integer.valueOf(101), af.getId());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testError() {
    String nullString = null;
    ArsSource as = new ArsSource(nullString);
    as.afterPropertiesSet();
  }
    
}
