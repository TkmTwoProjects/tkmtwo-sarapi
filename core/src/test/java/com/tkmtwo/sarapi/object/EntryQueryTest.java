package com.tkmtwo.sarapi.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.bmc.arsys.api.Entry;
import com.bmc.arsys.api.Value;
import java.util.List;
import com.tkmtwo.sarapi.ArsDataType;
import com.tkmtwo.sarapi.support.EntryUtil;



@ContextConfiguration
public final class EntryQueryTest
  extends AbstractJUnit4SpringContextTests
{

  //@Autowired
  //protected ArsSchemaHelper arsSchemaHelper;

  @Autowired
  protected EntryQuery entryQueryNoQualification;

  @Autowired
  protected EntryQuery entryQueryWithQualification;



    
  
  @Test
  public void testNoQual() {
    List<Entry> entries = entryQueryNoQualification.getEntries();
    for (Entry entry : entries) {
      System.out.println(String.format("I see noqual %s", entry.toString()));
    }
  }

  @Test
  public void testWithQual() {
    List<Entry> entries = entryQueryWithQualification.getEntries(ImmutableList.of(new Value("setEntry: Integer to i")));
    for (Entry entry : entries) {
      System.out.println(String.format("I see withqual %s", entry.toString()));
    }
  }
  
  
  
  
}

