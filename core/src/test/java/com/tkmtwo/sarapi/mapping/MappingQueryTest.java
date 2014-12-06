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



@ContextConfiguration
public final class MappingQueryTest
  extends AbstractJUnit4SpringContextTests
{

  //@Autowired
  //protected ArsSchemaHelper arsSchemaHelper;

  @Autowired
  protected PersonQuery personQueryNoQualification;

  @Autowired
  protected PersonQuery personQueryByFirstName;
  
  
  
  
  
  
  @Test
  public void testNoQual() {
    List<Person> ps = personQueryNoQualification.getObjects();
    for (Person p : ps) {
      System.out.println("I see person: " + p.toString());
    }
  }

  @Test
  public void testNoQualWithLimits() {
    OutputInteger oi = new OutputInteger(0);
    System.out.println("OutputInteger is " + String.valueOf(oi));

    List<Person> ps = personQueryNoQualification.getObjects(70, 100, oi);
    System.out.println("OutputInteger is " + String.valueOf(oi));

    assertNotNull(ps);
    //assertTrue(ps.size() <= 10);
    System.out.println(String.format("With limits, i see %s records.", String.valueOf(ps.size())));
    for (Person p : ps) {
      System.out.println("  LIMITED: " + p.toString());
    }


  }
  

  @Test
  public void testByFirstName() {
    Map<String, Value> values = ImmutableMap.of("firstName", new Value("Nicola"));
    List<Person> ps = personQueryByFirstName.getObjects(values);
    for (Person p : ps) {
      System.out.println("I see 'Nicola' : " + p.toString());
    }
  }
  
  
  
}
