package com.tkmtwo.sarapi.convert;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

import com.bmc.arsys.api.Value;
import org.springframework.core.convert.ConversionService;


public final class ArsConversionServiceTest
{
  ConversionService cs = null;

  @Before
  public void setUp() {
    cs = new ArsConversionService();
  }


  @Test
  public void testString() {
    String s = "Hello World";
    Value v = new Value("Hello World");
    
    assertEquals(s, cs.convert(new Value("Hello World"), String.class));

  }
  
  @Test
  public void testStringToValue() {
    String s = "Hello World";
    Value v = new Value("Hello World");
    
    assertEquals(s, cs.convert(new Value("Hello World"), String.class));
  }
  @Test
  public void testStringToValueNull() {
    String s = null;
    Value v = new Value();
    
    Value cv = cs.convert(s, Value.class);
    assertNotNull(cv);
    assertEquals(new Value(), cv);
  }
  

}
