package com.tkmtwo.sarapi;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;


public final class ArsSourceTest {

  private String getConnectionStringBasic() {
    StringBuffer sb = new StringBuffer();
    sb
      .append("environmentName=itsmtesting.accenture.com")
      .append("&userName=itsm.app")
      //.append("&userPassword=somepassword")
      .append("&userPassword=itsmAppTst")
      .append("&contexts=wtrsfes19202:1803,wtrsfes19203:1803");
    
    return sb.toString();
  }
  private String getConnectionStringSecure() {
    return getConnectionStringBasic() + "&secure=true";
  }

  @Test
  public void testBasic() {
    ArsSource as = new ArsSource(getConnectionStringBasic());
    as.afterPropertiesSet();
    
    ArsField af = as.getSchemaHelper().getField("User", "Login Name");
    assertEquals(Integer.valueOf(101), af.getId());
  }
  
  
  @Test(expected = AuthenticationCredentialsNotFoundException.class)
  public void testSecure() {
    ArsSource as = new ArsSource(getConnectionStringSecure());
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
