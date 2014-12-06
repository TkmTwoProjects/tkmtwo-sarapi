package com.tkmtwo.sarapi.object;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;



import com.bmc.arsys.api.Value;
import com.tkmtwo.sarapi.ArsDataType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.ArrayList;
import java.util.List;


@ContextConfiguration
public final class EntryUpdateTest
  extends AbstractJUnit4SpringContextTests
{

  //@Autowired
  //protected ArsSchemaHelper arsSchemaHelper;

  @Autowired
  protected EntryCreate entryCreate;

  @Autowired
  protected EntryUpdate entryUpdate;

  @Autowired
  protected EntryUpdate entryUpdateStatic;



  private List<String> createEntries(int numEntries, String testDescr, String charField) {
    List<String> l = new ArrayList<>();

    for (int i = 1; i <= numEntries; i++) {
      l.add(entryCreate.create(new Value[] { new Value(testDescr), new Value(charField) }));
    }
    
    return l;
  }

  //@Test
  public void testSanity() {
    List<String> l = createEntries(5, "testSanity", "Delete Me");
  }
  

  @Test
  public void testUpdateSet() {
    List<String> ids = createEntries(5, "testUpdateSet", "setEntry: Integer to i");

    for (int i = 0; i < ids.size(); i++) {
      entryUpdate.setEntry(ids.get(i), 
                           new Value[] {ArsDataType.INTEGER.getArValue(i) });
    }
  }
  
  @Test
  public void testUpdateSetStatic() {
    List<String> ids = createEntries(5, "testUpdateSetStatic", "setEntry static: Decimal to 73.0");
    for (String id : ids) {
      entryUpdateStatic.setEntry(id);
    }
  }


  @Test
  public void testUpdateMerge() {
    List<String> ids = createEntries(5, "testUpdateMerge", "mergeEntry: Integer to i+10");

    for (int i = 0; i < ids.size(); i++) {
      entryUpdate.mergeEntry(ids.get(i), 
                             new Value[] {ArsDataType.INTEGER.getArValue(i + 10) });
    }
  }
  
  @Test
  public void testUpdateMergeStatic() {
    List<String> ids = createEntries(5, "testUpdateMergeStatic", "mergeEntry static: Decimal to 73.0");
    for (String id : ids) {
      entryUpdateStatic.mergeEntry(id);
    }
  }
  



}

