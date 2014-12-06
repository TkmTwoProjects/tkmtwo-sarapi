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
import com.bmc.arsys.api.Value;
import java.util.List;
import java.util.Map;
import com.tkmtwo.sarapi.ArsDataType;
//import com.tkmtwo.sarapi.InvalidDataAccessResourceUsageException;
import com.tkmtwo.sarapi.support.EntryUtil;
import org.springframework.dao.InvalidDataAccessResourceUsageException;



@ContextConfiguration
public final class MappingGetTest
  extends AbstractJUnit4SpringContextTests
{
  
  @Autowired
  protected PersonGet personGet;
  
  
  
  
  @Test
  public void testGet() {
    Person p = personGet.getEntry("000000000000001");
    assertNotNull(p);
    System.out.println("I see person with id 1: " + p.toString());
  }

  @Test
  public void testGetFail302() {
    try {
      Person p = personGet.getEntry("NoSuchEntryId");
      fail("Should not have gotten here.");
    } catch (Exception ex) {
      ;
    }
  }
  
}
