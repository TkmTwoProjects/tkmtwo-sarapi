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
public final class MappingMergeTest
  extends AbstractJUnit4SpringContextTests
{
  
  @Autowired
  protected PersonCreate personCreate;

  @Autowired
  protected PersonMerge personMerge;

  @Autowired
  protected PersonGet personGet;
  
  
  
  @Test
  public void testMerge() {
    for (int i = 0; i < 5; i++) {
      Person p = new Person();
      p.setFirstName("MergeFirstName" + String.valueOf(i));
      p.setLastName("MergeLastName" + String.valueOf(i));
      p.setGender("Male");
      p.setAge(i);

      String newId = personCreate.createEntry(p);
      Person np = personGet.getEntry(newId);
      
      np.setFirstName(np.getFirstName() + "-merged");
      np.setLastName(np.getLastName() + "-merged");
      np.setAge(20 + i);
      np.setGender("Female");
      np.setWorkPhone(new PhoneNumber(null, "1", "222", "222-2222"));
      personMerge.mergeEntry(np);
    }
  }
  
  
  
}
