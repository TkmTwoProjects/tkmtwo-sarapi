package com.tkmtwo.sarapi.mapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.bmc.arsys.api.Entry;
import com.bmc.arsys.api.OutputInteger;
import com.bmc.arsys.api.Value;
import java.util.List;
import java.util.Map;
import com.tkmtwo.sarapi.ArsDataType;
import com.tkmtwo.sarapi.support.EntryUtil;



public final class ConversionValueMapperTest {
  
  

  
  @Test
  public void testThis() {
    assertTrue(System.currentTimeMillis() > 0L);
  }
  

  
  @Test
  public void testCharacter() {
    String stringValue = "Hello World";
    
    ValueMapper<String> vm = new ConversionValueMapper<String>(String.class);
    assertEquals(stringValue, vm.mapValue(new Value(stringValue)));
  }
  
}
