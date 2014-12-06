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
public final class MappingSetTest
  extends AbstractJUnit4SpringContextTests
{
  
  @Autowired
  protected PersonCreate personCreate;

  @Autowired
  protected PersonSet personSet;
  
  
  
  @Test
  public void testSet() {
    for (int i = 0; i < 5; i++) {
      Person p = new Person();
      p.setFirstName("SetFirstName" + String.valueOf(i));
      p.setLastName("SetLastName" + String.valueOf(i));
      p.setGender("Male");
      p.setAge(i);
      String newId = personCreate.createEntry(p);
      p.setId(newId);

      p.setFirstName(p.getFirstName() + "-set");
      p.setLastName(p.getLastName() + "-set");
      p.setAge(10 + i);
      p.setGender("Female");
      p.setMobilePhone(new PhoneNumber(null, "1", "333", "333-3333"));
      personSet.setEntry(p);
    }
  }
  
  
  
}
