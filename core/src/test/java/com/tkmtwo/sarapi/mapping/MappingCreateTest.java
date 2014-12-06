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
public final class MappingCreateTest
  extends AbstractJUnit4SpringContextTests
{
  
  @Autowired
  protected PersonCreate personCreate;
  
  
  
  
  @Test
  public void testCreate() {
    for (int i = 0; i < 5; i++) {
      Person p = new Person();
      p.setFirstName("CreateFirstName" + String.valueOf(i));
      p.setLastName("CreateLastName" + String.valueOf(i));
      p.setGender("Male");
      p.setAge(i);
      personCreate.createEntry(p);
    }
  }
  
  
  
  /**
   * "SomeOtherGender" is not an enumerated value...
   */  
  @Test(expected = InvalidDataAccessResourceUsageException.class)
  public void testCreateBadEnumName() {
    Person p = new Person();
    p.setFirstName("CreateBadGenederFirstName");
    p.setLastName("CreateBadGenderLastName");
    p.setGender("SomeOtherGender");
    p.setAge(33);
    personCreate.createEntry(p);
  }
  
  
}
