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
public final class SingleValueQueryTest
  extends AbstractJUnit4SpringContextTests
{


  //@Autowired
  //protected ArsSchemaHelper arsSchemaHelper;

  @Autowired
  protected PersonNameQuery personNameQueryNoQualification;

  @Autowired
  protected PersonNameQuery personNameQueryByFirstName;

  @Autowired
  protected PersonNameQuery personNameQueryByFirstNameLike;
  
  
  
  
  
  
  @Test
  public void testNoQual() {
    List<String> names = personNameQueryNoQualification.getObjects();
    assertTrue(names.size() > 0);
  }

  @Test
  public void testNameEq() {
    ImmutableMap<String, Value> vs = ImmutableMap.of("firstName", new Value("Nicola"));
    List<String> names = personNameQueryByFirstName.getObjects(vs);
    assertEquals(1, names.size());
    assertEquals("Nicola", names.get(0));
  }

  @Test
  public void testNameLike() {
    ImmutableMap<String, Value> vs = ImmutableMap.of("firstName", new Value("SetFirstName%"));
    List<String> names = personNameQueryByFirstNameLike.getObjects(vs);
    assertTrue(names.size() > 0);

    for (String name : names) {
      assertTrue(name.startsWith("SetFirstName"));
    }
  }

  
  
}
