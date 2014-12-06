package com.tkmtwo.sarapi.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.bmc.arsys.api.Value;

import com.tkmtwo.sarapi.ArsDataType;



@ContextConfiguration
public final class EntryCreateTest
  extends AbstractJUnit4SpringContextTests
{

  //@Autowired
  //protected ArsSchemaHelper arsSchemaHelper;

  @Autowired
  protected EntryCreate entryCreate;

  @Autowired
  protected EntryCreate entryCreateOneStatic;

  @Autowired
  protected EntryCreate entryCreateAllStatic;



    
  
  @Test
  public void testEntryCreate()
  {
    String[] ss = new String[] {
      "AAA",
      "BBB",
      "CCC",
      "DDD",
      "EEE"
    };

    for (int i = 0; i < ss.length; i++) {
      entryCreate.create(new Value[] {
            ArsDataType.CHAR.getArValue(ss[i]),
            ArsDataType.INTEGER.getArValue(i)
        });
      
    }
    
  }


  @Test
  public void testEntryCreateOneStatic()
  {
    for (int i = 0; i < 5; i++) {
      entryCreateOneStatic.create(new Value[] { ArsDataType.INTEGER.getArValue(i) });
    }
    
  }
  
  @Test
  public void testEntryCreateAllStatic()
  {
    for (int i = 0; i < 5; i++) {
      entryCreateAllStatic.create();
    }
    
  }


  
}

