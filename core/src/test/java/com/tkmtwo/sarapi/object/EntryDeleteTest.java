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
public final class EntryDeleteTest
  extends AbstractJUnit4SpringContextTests
{

  @Autowired
  protected EntryCreate entryCreate;

  @Autowired
  protected EntryDelete entryDelete;



    

  @Test
  public void testThis()
  {
    String idToDelete = 
      entryCreate.create(new Value[] {
          ArsDataType.CHAR.getArValue("DELETE ME"),
          ArsDataType.INTEGER.getArValue(3)
        });
    
    entryDelete.delete(idToDelete);
  }




}

