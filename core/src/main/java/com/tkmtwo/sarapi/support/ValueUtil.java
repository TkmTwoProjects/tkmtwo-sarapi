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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.bmc.arsys.api.AttachmentValue;
import com.bmc.arsys.api.ByteListValue;
import com.bmc.arsys.api.CoordinateInfo;
import com.bmc.arsys.api.CurrencyValue;
import com.bmc.arsys.api.DataType;
import com.bmc.arsys.api.DateInfo;
import com.bmc.arsys.api.DiaryListValue;
import com.bmc.arsys.api.Keyword;
import com.bmc.arsys.api.Time;
import com.bmc.arsys.api.Timestamp;
import com.bmc.arsys.api.Value;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.tkmtwo.sarapi.ArsField;
import com.tkmtwo.sarapi.InvalidValueAccessException;
import java.math.BigDecimal;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 */
public final class ValueUtil {

  private static final Logger logger = LoggerFactory.getLogger(ValueUtil.class);
  
  public static final DateTimeFormatter AR_DATE_FORMAT =
    DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
  
  
  
  public static String toString(Value v) {
    return Objects.toStringHelper(v)
      .add("dataType", v.getDataType())
      .add("dataTypeToString", v.getDataType().toString())
      .add("toString", v.toString())
      .toString();
  }
  
  public static String toString(Timestamp ts) {
    if (ts == null) {
      return null;
    }
    DateTime dt = new DateTime(ts.getValue() * 1000L);
    return AR_DATE_FORMAT.print(dt);
  }

  public static Timestamp toTimestamp(DateTime dt) {
    if (dt == null) {
      return null;
    }

    return new Timestamp(dt.getMillis() / 1000L);
  }
  
  public static DateInfo toDateInfo(DateTime dt) {
    if (dt == null) {
      return null;
    }
    return new DateInfo(dt.getYear(),
                        dt.getMonthOfYear(),
                        dt.getDayOfMonth());
  }

  public static Time toTime(DateTime dt) {
    if (dt == null) {
      return null;
    }
    return new Time(dt.getSecondOfDay());
  }
  
  public static DateTime toDateTime(Timestamp t) {
    if (t == null) { return null; }
    return new DateTime(t.getValue() * 1000L);
  }
  
  
  
  
  public static Value valueOf(DateTime dt) {
    return (dt == null) ? new Value() : new Value(new Timestamp(dt.getMillis() / 1000L));
  }
  public static Value valueOf(AttachmentValue av) {
    return (av == null) ? new Value() : new Value(av);
  }
  public static Value valueOf(BigDecimal bd) {
    return (bd == null) ? new Value() : new Value(bd);
  }
  public static Value valueOf(ByteListValue blv) {
    return (blv == null) ? new Value() : new Value(blv);
  }
  public static Value valueOf(CurrencyValue cv) {
    return (cv == null) ? new Value() : new Value(cv);
  }
  public static Value valueOf(DateInfo di) {
    return (di == null) ? new Value() : new Value(di);
  }
  public static Value valueOf(DiaryListValue dlv) {
    return (dlv == null) ? new Value() : new Value(dlv);
  }
  public static Value valueOf(double d) {
    return new Value(d);
  }
  public static Value valueOf(Double d) {
    return (d == null) ? new Value() : new Value(d);
  }
  public static Value valueOf(int i) {
    return new Value(i);
  }
  public static Value valueOf(Integer i) {
    return (i == null) ? new Value() : new Value(i.intValue());
  }
  public static Value valueOf(Keyword k) {
    return (k == null) ? new Value() : new Value(k);
  }
  public static Value valueOf(List<CoordinateInfo> cis) {
    return (cis == null) ? new Value() : new Value(cis);
  }
  public static Value valueOf(long l) {
    return new Value(l);
  }
  public static Value valueOf(Long l) {
    return (l == null) ? new Value() : new Value(l.longValue());
  }
  public static Value valueOf(String s) {
    return (s == null) ? new Value() : new Value(s);
  }
  public static Value valueOf(Time t) {
    return (t == null) ? new Value() : new Value(t);
  }
  public static Value valueOf(Timestamp t) {
    return (t == null) ? new Value() : new Value(t);
  }
  
  
  
  
  
  
  
  
  
  
  
  
  public static boolean isNull(Value v) {
    if (v == null) { return true; }
    if (v.getValue() == null) { return true; }
    if (v.getDataType() == DataType.NULL) { return true; }
    
    return false;
  }
  
  public static Integer getInteger(Value v)
    throws InvalidValueAccessException {

    if (isNull(v)) { return null; }

    if (!v.getDataType().equals(DataType.INTEGER)) {
      throw new InvalidValueAccessException("Value is not a DataType.INTEGER");
    }
    
    Object o = v.getValue();
    if (o instanceof Integer == false) {
      throw new InvalidValueAccessException("Value is not an Integer");
    }
    
    return (Integer) o;
  }
  
  
  public static Double getReal(Value v)
    throws InvalidValueAccessException {

    if (isNull(v)) { return null; }

    if (!v.getDataType().equals(DataType.REAL)) {
      throw new InvalidValueAccessException("Value is not a DataType.REAL");
    }
    
    Object o = v.getValue();
    if (o instanceof Double == false) {
      throw new InvalidValueAccessException("Value is not a Double");
    }
    
    return (Double) o;
  }
  
  
  
  public static String getCharacter(Value v)
    throws InvalidValueAccessException {

    if (isNull(v)) { return null; }

    if (!v.getDataType().equals(DataType.CHAR)) {
      throw new InvalidValueAccessException("Value is not a DataType.CHAR");
    }
    
    Object o = v.getValue();
    if (o instanceof String == false) {
      throw new InvalidValueAccessException("Value is not a String");
    }
    
    return (String) o;
  }
  
  
  
  
  
  
  public static Integer getEnum(Value v)
    throws InvalidValueAccessException {

    if (isNull(v)) { return null; }

    if (!v.getDataType().equals(DataType.ENUM)) {
      throw new InvalidValueAccessException("Value is not a DataType.ENUM");
    }
    
    Object o = v.getValue();
    if (o instanceof Integer == false) {
      throw new InvalidValueAccessException("Value is not an Integer");
    }
    
    return (Integer) o;
  }
  
  
  
  public static Timestamp getTimestamp(Value v)
    throws InvalidValueAccessException {

    if (isNull(v)) { return null; }

    if (!v.getDataType().equals(DataType.TIME)) {
      throw new InvalidValueAccessException("Value is not a DataType.TIME");
    }
    
    Object o = v.getValue();
    if (o instanceof Timestamp == false) {
      throw new InvalidValueAccessException("Value is not a Timestamp");
    }
    
    return (Timestamp) o;
  }
  public static DateTime getTimestampAsDateTime(Value v)
    throws InvalidValueAccessException {

    return toDateTime(getTimestamp(v));
  }
  
  
  

  public static BigDecimal getDecimal(Value v)
    throws InvalidValueAccessException {

    if (isNull(v)) { return null; }

    if (!v.getDataType().equals(DataType.DECIMAL)) {
      throw new InvalidValueAccessException("Value is not a DataType.DECIMAL");
    }
    
    Object o = v.getValue();
    if (o instanceof BigDecimal == false) {
      throw new InvalidValueAccessException("Value is not a BigDecimal");
    }
    
    return (BigDecimal) o;
  }



  //
  public static AttachmentValue getAttachmentValue(Value v)
    throws InvalidValueAccessException {

    if (isNull(v)) { return null; }

    if (!v.getDataType().equals(DataType.ATTACHMENT)) {
      throw new InvalidValueAccessException("Value is not a DataType.ATTACHMENT");
    }
    
    Object o = v.getValue();
    if (o instanceof AttachmentValue == false) {
      throw new InvalidValueAccessException("Value is not an AttachmentValue");
    }
    
    return (AttachmentValue) o;
  }
  //  
  
  public static CurrencyValue getCurrencyValue(Value v)
    throws InvalidValueAccessException {

    if (isNull(v)) { return null; }

    if (!v.getDataType().equals(DataType.CURRENCY)) {
      throw new InvalidValueAccessException("Value is not a DataType.CURRENCY");
    }
    
    Object o = v.getValue();
    if (o instanceof CurrencyValue == false) {
      throw new InvalidValueAccessException("Value is not a CurrencyValue");
    }
    
    return (CurrencyValue) o;
  }
  
  
  
  public static DateInfo getDateInfo(Value v)
    throws InvalidValueAccessException {

    if (isNull(v)) { return null; }

    if (!v.getDataType().equals(DataType.DATE)) {
      throw new InvalidValueAccessException("Value is not a DataType.DATE");
    }
    
    Object o = v.getValue();
    if (o instanceof DateInfo == false) {
      throw new InvalidValueAccessException("Value is not a DateInfo");
    }
    
    return (DateInfo) o;
  }
  
  
  public static Time getTime(Value v)
    throws InvalidValueAccessException {

    if (isNull(v)) { return null; }

    if (!v.getDataType().equals(DataType.TIME_OF_DAY)) {
      throw new InvalidValueAccessException("Value is not a DataType.TIME_OF_DAY");
    }
    
    Object o = v.getValue();
    if (o instanceof Time == false) {
      throw new InvalidValueAccessException("Value is not a Time");
    }
    
    return (Time) o;
  }

  public static List<Value> compileValues(List<ArsField> arsFields,
                                          List<String> stringValues) {
    checkNotNull(arsFields, "Need a list of ArsFields");
    checkNotNull(stringValues, "Need a list of String values");

    int numValues = stringValues.size();
    checkArgument(arsFields.size() >= numValues,
                  "There are more String values (%s) than fields (%s)",
                  numValues,
                  String.valueOf(arsFields.size()));

    List<Value> l = Lists.newArrayList();
    for (int i = 0; i < numValues; i++) {
      l.add(arsFields.get(i).getDataType().getArValue(stringValues.get(i)));
    }
    return l;
  }

  
  
  
  
  
}
