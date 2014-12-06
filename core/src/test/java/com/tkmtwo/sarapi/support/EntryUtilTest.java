/*
 *
 * Copyright 2014 Tom Mahaffey
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.tkmtwo.sarapi.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.bmc.arsys.api.CurrencyValue;
import com.bmc.arsys.api.DataType;
import com.bmc.arsys.api.DateInfo;
import com.bmc.arsys.api.Entry;
import com.bmc.arsys.api.Field;
import com.bmc.arsys.api.Time;
import com.bmc.arsys.api.Timestamp;
import com.bmc.arsys.api.Value;
import com.tkmtwo.sarapi.InvalidValueAccessException;
import java.math.BigDecimal;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;


/**
 *
 */
public class EntryUtilTest {

  Entry testEntry;

  
  @Before
  public void setUp() {

    testEntry = new Entry();
    testEntry.setEntryId("SomeEntryId");
    
    testEntry.put(new Integer(0),   //NULL
                  new Value());

    testEntry.put(new Integer(2),   //INTEGER
                  new Value(73));

    testEntry.put(new Integer(3),   //REAL
                  new Value(new Double("73.37")));

    testEntry.put(new Integer(4),   //CHAR
                  new Value("Hello World"));

    testEntry.put(new Integer(6),   //ENUM
                  new Value("73", DataType.ENUM));

    testEntry.put(new Integer(7),   //TIME
                  new Value(new Timestamp(73L)));

    testEntry.put(new Integer(10),  //DECIMAL
                  new Value(new BigDecimal("73.37")));

    testEntry.put(new Integer(12),  //CURRENCY
                  new Value(new CurrencyValue(new BigDecimal("73.37"),
                                              "USD",
                                              new Timestamp(73L),
                                              null)));
    
    testEntry.put(new Integer(13),  //DATE
                  new Value(new DateInfo(1969, 11, 2)));
    testEntry.put(new Integer(14),  //TIME_OF_DAY
                  new Value(new Time(73L)));
                  

  }


  @Test(expected = com.tkmtwo.sarapi.InvalidEntryAccessException.class)
  public void testNullEntry() {
    EntryUtil.getCharacter(null, new Integer(4));
  }
  
  @Test(expected = com.tkmtwo.sarapi.InvalidEntryAccessException.class)
  public void testNullFieldId() {
    EntryUtil.getCharacter(testEntry, null);
  }
  
  @Test(expected = com.tkmtwo.sarapi.InvalidEntryAccessException.class)
  public void testNullEntryAndFieldId() {
    EntryUtil.getCharacter(null, null);
  }
  
  @Test(expected = com.tkmtwo.sarapi.InvalidEntryAccessException.class)
  public void testInvalidFieldId() {
    EntryUtil.getCharacter(testEntry, new Integer(-1));
  }

  @Test(expected = com.tkmtwo.sarapi.InvalidEntryAccessException.class)
  public void testInvalidFieldType() {
    EntryUtil.getCharacter(testEntry, new Integer(2));
  }
  
  
  
  @Test
  public void testGetters() {

    assertEquals(new Integer(73),
                 EntryUtil.getInteger(testEntry, new Integer(2)));
    assertEquals(new Integer(73),
                 EntryUtil.getInteger(testEntry, 2));

    assertEquals(new Double("73.37"),
                 EntryUtil.getReal(testEntry, new Integer(3)));
    assertEquals(new Double("73.37"),
                 EntryUtil.getReal(testEntry, 3));

    assertEquals("Hello World",
                 EntryUtil.getCharacter(testEntry, new Integer(4)));
    assertEquals("Hello World",
                 EntryUtil.getCharacter(testEntry, 4));

    assertEquals(new Integer("73"),
                 EntryUtil.getEnum(testEntry, new Integer(6)));
    assertEquals(new Integer("73"),
                 EntryUtil.getEnum(testEntry, 6));

    assertEquals(new Timestamp(73L),
                 EntryUtil.getTimestamp(testEntry, new Integer(7)));
    assertEquals(new Timestamp(73L),
                 EntryUtil.getTimestamp(testEntry, 7));

    assertEquals(new BigDecimal("73.37"),
                 EntryUtil.getDecimal(testEntry, new Integer(10)));
    assertEquals(new BigDecimal("73.37"),
                 EntryUtil.getDecimal(testEntry, 10));

    assertEquals(new CurrencyValue(new BigDecimal("73.37"),
                                   "USD",
                                   new Timestamp(73L),
                                   null),
                 EntryUtil.getCurrencyValue(testEntry, new Integer(12)));
    assertEquals(new CurrencyValue(new BigDecimal("73.37"),
                                   "USD",
                                   new Timestamp(73L),
                                   null),
                 EntryUtil.getCurrencyValue(testEntry, 12));
    
    assertEquals(new DateInfo(1969, 11, 2),
                 EntryUtil.getDateInfo(testEntry, new Integer(13)));
    assertEquals(new DateInfo(1969, 11, 2),
                 EntryUtil.getDateInfo(testEntry, 13));

    assertEquals(new Time(73L),
                 EntryUtil.getTime(testEntry, new Integer(14)));
    assertEquals(new Time(73L),
                 EntryUtil.getTime(testEntry, 14));

  }
  
  @Test
  public void testNullable() {
    assertNull(EntryUtil.getInteger(testEntry, new Integer(0)));
    assertNull(EntryUtil.getInteger(testEntry, 0));
    
    assertNull(EntryUtil.getReal(testEntry, new Integer(0)));
    assertNull(EntryUtil.getReal(testEntry, 0));
    
    assertNull(EntryUtil.getCharacter(testEntry, new Integer(0)));
    assertNull(EntryUtil.getCharacter(testEntry, 0));
    
    assertNull(EntryUtil.getEnum(testEntry, new Integer(0)));
    assertNull(EntryUtil.getEnum(testEntry, 0));

    assertNull(EntryUtil.getTimestamp(testEntry, new Integer(0)));
    assertNull(EntryUtil.getTimestamp(testEntry, 0));
    
    assertNull(EntryUtil.getDecimal(testEntry, new Integer(0)));
    assertNull(EntryUtil.getDecimal(testEntry, 0));
    
    assertNull(EntryUtil.getCurrencyValue(testEntry, new Integer(0)));
    assertNull(EntryUtil.getCurrencyValue(testEntry, 0));
    
    assertNull(EntryUtil.getDateInfo(testEntry, new Integer(0)));
    assertNull(EntryUtil.getDateInfo(testEntry, 0));
    
    assertNull(EntryUtil.getTime(testEntry, new Integer(0)));
    assertNull(EntryUtil.getTime(testEntry, 0));
  }
  
  
  
}

