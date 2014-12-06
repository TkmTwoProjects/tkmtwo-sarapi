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
public final class ConversionValueQueryTest
  extends AbstractJUnit4SpringContextTests
{


  //@Autowired
  //protected ArsSchemaHelper arsSchemaHelper;

  @Autowired
  protected PersonIdQuery personIdQueryNoQualification;

  @Autowired
  protected PersonIdQuery personIdQueryByFirstName;

  @Autowired
  protected PersonIdQuery personIdQueryByFirstNameLike;
  
  
  
  
  
  
  @Test
  public void testNoQual() {
    List<String> pids = personIdQueryNoQualification.getValues();
    for (String pid : pids) {
      System.out.println("I see person id: " + pid);
    }
  }

  
  
}
