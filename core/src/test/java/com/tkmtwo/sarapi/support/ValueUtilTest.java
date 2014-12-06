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


public class ValueUtilTest {
  Value integerValue;
  Value realValue;
  Value charValue;
  Value enumValue;
  Value timestampValue;
  Value decimalValue;
  Value currencyValue;
  Value dateValue;
  Value timeValue;
  
  @Before
  public void setUp() {
    integerValue = new Value(73);
    realValue = new Value(new Double("73.37"));
    charValue = new Value("Hello World");
    enumValue = new Value("73", DataType.ENUM);
    timestampValue = new Value(new Timestamp(73L));
    decimalValue = new Value(new BigDecimal("73.37"));
    currencyValue = new Value(new CurrencyValue(new BigDecimal("73.37"),
                                                "USD",
                                                new Timestamp(73L),
                                                null));
    dateValue = new Value(new DateInfo(1969, 11, 2));
    timeValue = new Value(new Time(73L));
  }
  
  
  
  @Test
  public void testTimestampToString() {
    Timestamp ts = new Timestamp(73L);
    String targetResult = "01/01/1970 00:01:13";
    assertEquals(targetResult,
                 ValueUtil.toString(ts));
  }
  
  
  
  
  @Test
  public void testInteger() {
    Integer i = new Integer(73);
    assertEquals(i, ValueUtil.getInteger(integerValue));
  }
  
  @Test
  public void testIntegerNullable() {
    assertNull(ValueUtil.getInteger(null));
    assertNull(ValueUtil.getInteger(new Value()));
  }
  
  
  @Test(expected = com.tkmtwo.sarapi.InvalidValueAccessException.class)
  public void testIntegerTypeMismatch() {
    ValueUtil.getInteger(charValue);
  }
  
  
  
  
  
  
  
  @Test
  public void testReal() {
    Double d = new Double("73.37");
    assertEquals(d, ValueUtil.getReal(realValue));
  }
  
  @Test
  public void testRealNullable() {
    assertNull(ValueUtil.getReal(null));
    assertNull(ValueUtil.getReal(new Value()));
  }
  
  @Test(expected = com.tkmtwo.sarapi.InvalidValueAccessException.class)
  public void testRealTypeMismatch() {
    ValueUtil.getReal(integerValue);
  }
  
  
  
  
  
  
  
  @Test
  public void testCharacter() {
    String s = "Hello World";
    assertEquals(s, ValueUtil.getCharacter(charValue));
  }
  
  @Test
  public void testCharacterNullable() {
    assertNull(ValueUtil.getCharacter(null));
    assertNull(ValueUtil.getCharacter(new Value()));
  }
  
  @Test(expected = com.tkmtwo.sarapi.InvalidValueAccessException.class)
  public void testCharacterTypeMismatch() {
    ValueUtil.getCharacter(integerValue);
  }
  
  
  
  

  
  
  @Test
  public void testEnum() {
    Integer i = new Integer(73);
    assertEquals(i, ValueUtil.getEnum(enumValue));
  }
  
  @Test
  public void testEnumNullable() {
    assertNull(ValueUtil.getEnum(null));
    assertNull(ValueUtil.getEnum(new Value()));
  }
  
  @Test(expected = com.tkmtwo.sarapi.InvalidValueAccessException.class)
  public void testEnumTypeMismatch() {
    ValueUtil.getEnum(integerValue);
  }
  
  
  
  
  
  
  
  
  @Test
  public void testTimestamp() {
    Timestamp t = new Timestamp(73L);
    assertEquals(t, ValueUtil.getTimestamp(timestampValue));
  }
  @Test
  public void testTimestampNullable() {
    assertNull(ValueUtil.getTimestamp(null));
    assertNull(ValueUtil.getTimestamp(new Value()));
  }
  @Test(expected = com.tkmtwo.sarapi.InvalidValueAccessException.class)
  public void testTimestampTypeMismatch() {
    ValueUtil.getTimestamp(integerValue);
  }
  
  
  
  
  
  
  @Test
  public void testDecimal() {
    BigDecimal bd = new BigDecimal("73.37");
    assertEquals(bd, ValueUtil.getDecimal(decimalValue));
  }
  @Test
  public void testDecimalNullable() {
    assertNull(ValueUtil.getDecimal(null));
    assertNull(ValueUtil.getDecimal(new Value()));
  }
  @Test(expected = com.tkmtwo.sarapi.InvalidValueAccessException.class)
  public void testDecimalTypeMismatch() {
    ValueUtil.getDecimal(integerValue);
  }
  
  
  
  
  @Test
  public void testCurrency() {
    CurrencyValue cv = new CurrencyValue(new BigDecimal("73.37"),
                                         "USD",
                                         new Timestamp(73L),
                                         null);
    assertEquals(cv, ValueUtil.getCurrencyValue(currencyValue));
  }
  
  @Test
  public void testCurrencyNullable() {
    assertNull(ValueUtil.getCurrencyValue(null));
    assertNull(ValueUtil.getCurrencyValue(new Value()));
  }
  @Test(expected = com.tkmtwo.sarapi.InvalidValueAccessException.class)
  public void testCurrencyValueTypeMismatch() {
    ValueUtil.getCurrencyValue(integerValue);
  }
  
  
  
  
  
  
  @Test
  public void testDateInfo() {
    DateInfo di = new DateInfo(1969, 11, 2);
    assertEquals(di, ValueUtil.getDateInfo(dateValue));
  }
  @Test
  public void testDateInfoNullable() {
    assertNull(ValueUtil.getDateInfo(null));
    assertNull(ValueUtil.getDateInfo(new Value()));
  }
  @Test(expected = com.tkmtwo.sarapi.InvalidValueAccessException.class)
  public void testDateInfoTypeMismatch() {
    ValueUtil.getDateInfo(integerValue);
  }
  
  
  
  
  @Test
  public void testTime() {
    Time t = new Time(73L);
    assertEquals(t, ValueUtil.getTime(timeValue));
  }
  @Test
  public void testTimeNullable() {
    assertNull(ValueUtil.getTime(null));
    assertNull(ValueUtil.getTime(new Value()));
  }
  
  @Test(expected = com.tkmtwo.sarapi.InvalidValueAccessException.class)
  public void testTimeTypeMismatch() {
    ValueUtil.getTime(integerValue);
  }
  
  
  
  
  
}

