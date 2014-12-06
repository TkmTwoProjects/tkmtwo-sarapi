package com.tkmtwo.sarapi;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;


@ContextConfiguration
public final class InMemorySchemaHelperTest
  extends AbstractJUnit4SpringContextTests
{

  @Autowired
  protected ArsSchemaHelper arsSchemaHelper;


  @Test
  public void testUserFormLookup()
    throws Exception
  {
    String formName = "User";
    String fieldName = "Status";

    assertEquals("Status", arsSchemaHelper.getFieldName(formName, new Integer(7)));
    assertEquals(Integer.valueOf(7), arsSchemaHelper.getFieldId(formName, fieldName));

    assertEquals("Current", arsSchemaHelper.getEnumName(formName, fieldName, Integer.valueOf(0)));
    assertEquals("Disabled", arsSchemaHelper.getEnumName(formName, fieldName, Integer.valueOf(1)));

    assertEquals(Integer.valueOf(0), arsSchemaHelper.getEnumNumber(formName, fieldName, "Current"));
    assertEquals(Integer.valueOf(1), arsSchemaHelper.getEnumNumber(formName, fieldName, "Disabled"));
    
    List<String> statusValues = arsSchemaHelper.getEnumNames(formName, "Status");
    assertNotNull(statusValues);
    assertEquals(2, statusValues.size());
    assertTrue(statusValues.contains("Current"));
    assertTrue(statusValues.contains("Disabled"));
  }
  

  
  @Test
  public void testBind() {
    String[] ss = new String[] {
      "Integer Field",
      "Real Number Field",
      "Character Field",
      "Diary Field",
      "Drop-Down List Field",
      "Date/Time Field",
      "Decimal Number Field",
      "Currency Field",
      "Date Field",
      "Time Field",
      "Attachment1",
      "Attachment1",
      "Attachment3"
    };

    for (String s : ss) {
      ArsField af = arsSchemaHelper.getField("SARAPI:TestFields", s);
      System.out.println("I see field: " + af.toString());
    }
    
    assertEquals(255, arsSchemaHelper.getMaxLength("SARAPI:TestFields", "Character Field"));

  }


  @Test(expected = InvalidDataAccessResourceUsageException.class)
  public void testError() {
    assertEquals(255, arsSchemaHelper.getMaxLength("SARAPI:TestFields", "Integer Field"));
  }

  @Test
  public void testHPD() {
    arsSchemaHelper.getField("HPD:Help Desk", "Incident Number");
  }
  


}
