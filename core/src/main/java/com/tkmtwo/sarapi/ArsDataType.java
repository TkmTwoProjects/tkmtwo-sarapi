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
package com.tkmtwo.sarapi;

import static com.google.common.base.TkmTwoStrings.isBlank;

import com.bmc.arsys.api.CurrencyValue;
import com.bmc.arsys.api.DataType;
import com.bmc.arsys.api.DateInfo;
import com.bmc.arsys.api.Keyword;
import com.bmc.arsys.api.Time;
import com.bmc.arsys.api.Timestamp;
import com.bmc.arsys.api.Value;
import java.math.BigDecimal;
import java.util.EnumSet;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/* Fields of type "Data Field"
2   6161
3   136
4   43094
5   71
6   10022
7   3616
10   207
11   307
12   234
13   82
14   164
31   2244
32   2637
33   1074
34   7755
37   48
42   8

  AR_DATA_TYPE_NULL           0   Y
  AR_DATA_TYPE_KEYWORD        1
  AR_DATA_TYPE_INTEGER        2   Y
  AR_DATA_TYPE_REAL           3   Y
  AR_DATA_TYPE_CHAR           4   Y
  AR_DATA_TYPE_DIARY          5   Y
  AR_DATA_TYPE_ENUM           6   Y
  AR_DATA_TYPE_TIME           7   Y
  AR_DATA_TYPE_BITMASK        8
  AR_DATA_TYPE_BYTES          9
  AR_DATA_TYPE_DECIMAL       10   Y
  AR_DATA_TYPE_ATTACH        11   Y
  AR_DATA_TYPE_CURRENCY      12   Y
  AR_DATA_TYPE_DATE          13   Y
  AR_DATA_TYPE_TIME_OF_DAY   14   Y


  AR_DATA_TYPE_JOIN          30
  AR_DATA_TYPE_TRIM          31   Y
  AR_DATA_TYPE_CONTROL       32   Y
  AR_DATA_TYPE_TABLE         33   Y
  AR_DATA_TYPE_COLUMN        34   Y
  AR_DATA_TYPE_PAGE          35
  AR_DATA_TYPE_PAGE_HOLDER   36
  AR_DATA_TYPE_ATTACH_POOL   37   Y
  AR_DATA_TYPE_ULONG         40
  AR_DATA_TYPE_COORDS        41
  AR_DATA_TYPE_VIEW          42   Y
  AR_DATA_TYPE_DISPLAY       43
  AR_DATA_TYPE_MAX_TYPE      43


  
  
  
  
  
  
  THESE ARE Fields we are intrested in...they all show up in field type "data" filter
  =================================
DATA
  AR_DATA_TYPE_INTEGER        2   Y   java.lang.Integer
  AR_DATA_TYPE_REAL           3   Y   java.lang.Double
  AR_DATA_TYPE_CHAR           4   Y   java.lang.String
  AR_DATA_TYPE_DIARY          5   Y   com.bmc.arsys.api.DiaryListValue
  AR_DATA_TYPE_ENUM           6   Y   java.lang.Integer
  AR_DATA_TYPE_TIME           7   Y   com.bmc.arsys.api.Timestamp
  AR_DATA_TYPE_DECIMAL       10   Y   java.math.BigDecimal
  AR_DATA_TYPE_CURRENCY      12   Y   com.bmc.arsys.api.CurrencyValue
  AR_DATA_TYPE_DATE          13   Y   com.bmc.arsys.api.DateInfo
  AR_DATA_TYPE_TIME_OF_DAY   14   Y   com.bmc.arsys.api.Time

ATTACH
  AR_DATA_TYPE_ATTACH        11   Y   com.bmc.arsys.api.AttachmentValue


*/


/**
 *
 */
public enum ArsDataType {
  
  /**
     Specifies NULL value.
  */
  NULL("AR_DATA_TYPE_NULL", DataType.NULL) {

    public Value getArValue(String s) {
      return new Value();
    }
    public Value getArValue(Keyword k) { 
      return new Value();
    }
    public Value getArValue(int i) { 
      return new Value();
    }
    public Value getArValue(Integer i) { 
      return new Value();
    }
    public Value getArValue(double d) { 
      return new Value();
    }
    public Value getArValue(Double d) { 
      return new Value();
    }
    public Value getArValue(Timestamp t) { 
      return new Value();
    }
    public Value getArValue(BigDecimal bd) { 
      return new Value();
    }
    public Value getArValue(CurrencyValue cv) { 
      return new Value();
    }
    public Value getArValue(DateInfo di) { 
      return new Value();
    }
    public Value getArValue(Time t) { 
      return new Value();
    }
    public Value getArValue(DateTime dt) {
      return new Value();
    }
    public Value getArValue(LocalDate ld) {
      return new Value();
    }
    public Value getArValue(LocalTime lt) {
      return new Value();
    }
    
  },

  /**
     An integer that specifies the particular keyword
     (defined in the ar.h file).
  */
  KEYWORD("AR_DATA_TYPE_KEYWORD", DataType.KEYWORD) {
    public Value getArValue(String s) {
      return (isBlank(s)) ? new Value() : new Value(Keyword.toKeyword(s));
    }
    public Value getArValue(Keyword k) {
      return (k == null) ? new Value() : new Value(k);
    }
  },
  
  
  /**
     A 32-bit signed integer value.
  */
  INTEGER("AR_DATA_TYPE_INTEGER", DataType.INTEGER) {
    public Value getArValue(String s) {
      return (isBlank(s)) ? new Value() : new Value(Integer.parseInt(s));
    }
    public Value getArValue(int i) {
      return new Value(i);
    }
    public Value getArValue(Integer i) {
      return (i == null) ? new Value() : new Value(i.intValue());
    }
  },

  /**
     A 64-bit floating-point value.
  */
  REAL("AR_DATA_TYPE_REAL", DataType.REAL) {
    public Value getArValue(String s) {
      return (isBlank(s)) ? new Value() : new Value(Double.parseDouble(s));
    }
    public Value getArValue(double d) {
      return new Value(d);
    }
    public Value getArValue(Double d) {
      return (d == null) ? new Value() : new Value(d.doubleValue());
    }
  },

  /**
     A null-terminated string that requires freeing
     allocated space. A NULL pointer of this type is
     equivalent to using AR_DATA_TYPE_NULL.
  */
  CHAR("AR_DATA_TYPE_CHAR", DataType.CHAR) {
    public Value getArValue(String s) {
      return (isBlank(s)) ? new Value() : new Value(s);
    }
    public Value getArValue(int i) {
      return new Value(String.valueOf(i));
    }
    public Value getArValue(Integer i) {
      return (i == null) ? new Value() : new Value(i.toString());
    }
    public Value getArValue(double d) {
      return new Value(String.valueOf(d));
    }
    public Value getArValue(Double d) {
      return (d == null) ? new Value() : new Value(d.toString());
    }

  },

  
  /**
     A null-terminated string that requires freeing
     allocated space. A NULL pointer of this type is
     equivalent to using AR_DATA_TYPE_NULL.
  */
  DIARY("AR_DATA_TYPE_DIARY", DataType.DIARY) {
    public Value getArValue(String s) {
      return (isBlank(s)) ? new Value() : new Value(s);
    }
  },
      
  /**
     A set of integer values (beginning with zero) that
     represents possible selection values as an indexed
     list. You must specify a field limit by using
     ARNameList to define string values for each
     selection (see Lists on page 36).
  */
  ENUM("AR_DATA_TYPE_ENUM", DataType.ENUM) {
    public Value getArValue(String s) {
      if (isBlank(s)) {
        return new Value();
      }
      
      Integer i = null;
      try {
        i = new Integer(s);
      } catch (Exception ex) {
        i = null;
      }
      if (i == null) {
        return new Value(s);
      }
      return new Value(i.intValue());
      //return (StringUtils.isEmpty(s)) ? new Value() : new Value(Integer.parseInt(s));
    }
    public Value getArValue(int i) {
      return new Value(i);
    }
    public Value getArValue(Integer i) {
      return (i == null) ? new Value() : new Value(i.intValue());
    }
  },

  /**
     A UNIX-style date/time stamp (number of seconds
     since midnight January 1, 1970).
   */
  TIME("AR_DATA_TYPE_TIME", DataType.TIME) {

    public Value getArValue(DateTime dt) {
      if (dt == null) { return new Value(); }
      return new Value(new Timestamp(dt.getMillis() / 1000L));
    }
    
    private Timestamp parseLong(String s) {
      if (isBlank(s)) { return null; }
      long l = 0L;
      try {
        l = Long.parseLong(s);
      } catch (Exception ex) {
        return null;
      }
      return new Timestamp(l);
    }
    private Timestamp parseIso(String s) {
      if (isBlank(s)) { return null; }
      
      DateTime dt = null;
      for (DateTimeFormatter dateTimeFormatter : dateTimeFormatters) {
        try {
          dt = dateTimeFormatter.parseDateTime(s);
          return new Timestamp(dt.getMillis() / 1000L);
        } catch (Exception ex) {
          continue;
        }
      }
      return null;
    }

    public Value getArValue(String s) {
      if (isBlank(s)) { return new Value(); }

      Timestamp t = null;
      
      if ((t = parseLong(s)) != null) {
        return new Value(t);
      }
      if ((t = parseIso(s)) != null) {
        return new Value(t);
      }
      throw new IllegalArgumentException("String '" + s + "' is not a date/time format.");
    }
    
    public Value getArValue(Timestamp t) {
      return (t == null) ? new Value() : new Value(t);
    }
  },

  /**
     A 32-bit unsigned integer value in which each bit
     represents a flag turned on or off.
   */
  BITMASK("AR_DATA_TYPE_BITMASK", DataType.BITMASK),

  /**
     A list of bytes containing binary data (represented
     the ARByteList structure).
   */
  BYTES("AR_DATA_TYPE_BYTES", DataType.BYTES),

  /**
     A fixed-point decimal field. Values must be
     specified in C locale, for examp.le 1234.56
   */
  DECIMAL("AR_DATA_TYPE_DECIMAL", DataType.DECIMAL) {
    public Value getArValue(String s) {
      return (isBlank(s)) ? new Value() : new Value(new BigDecimal(s));
    }
    public Value getArValue(BigDecimal bd) {
      return (bd == null) ? new Value() : new Value(bd);
    }
  },

  /**
     An attachment field.
   */
  ATTACH("AR_DATA_TYPE_ATTACH", DataType.ATTACHMENT),

  /**
     A currency field. The numeric part of the values
     must be specified in C locale, for example 29.99
   */
  CURRENCY("AR_DATA_TYPE_CURRENCY", DataType.CURRENCY) {
    public Value getArValue(String s) {
      return (isBlank(s)) ? new Value() : new Value(s, DataType.CURRENCY);
    }
    public Value getArValue(CurrencyValue cv) {
      return (cv == null) ? new Value() : new Value(cv);
    }
  },

  /**
     A date field. The value (know as the Chronological
     Julian Day) is the integer number of days since
     January 1, 4713 B.C. The highest valid date is
     January 1, 9999.
  */
  DATE("AR_DATA_TYPE_DATE", DataType.DATE) {
    
    
    public Value getArValue(LocalDate ld) {
      if (ld == null) { return new Value(); }
      return new Value(new DateInfo(ld.getYear(),
                                    ld.getMonthOfYear(),
                                    ld.getDayOfMonth()));
    }

    private DateInfo parseIso(String s) {
      if (isBlank(s)) { return null; }
      
      LocalDate ld = null;
      for (DateTimeFormatter dateTimeFormatter : dateFormatters) {
        try {
          ld = dateTimeFormatter.parseDateTime(s).toLocalDate();
          return new DateInfo(ld.getYear(),
                              ld.getMonthOfYear(),
                              ld.getDayOfMonth());
        } catch (Exception ex) {
          continue;
        }
      }
      return null;
      
    }
    public Value getArValue(String s) {
      if (isBlank(s)) { return new Value(); }
      DateInfo di = null;
      if ((di = parseIso(s)) != null) {
        return new Value(di);
      }
      return new Value(s, DataType.DATE);
    }
    public Value getArValue(DateInfo di) {
      return (di == null) ? new Value() : new Value(di);
    }
  },
  
  /**
     Time of day field. The value is the integer number of
     seconds since 12:00:00 a.m.
   */
  TIME_OF_DAY("AR_DATA_TYPE_TIME_OF_DAY", DataType.TIME_OF_DAY) {

    public Value getArValue(LocalTime lt) {
      if (lt == null) { return new Value(); }
      return new Value(new Time(lt.getMillisOfDay() / 1000L));
    }

    private Time parseIso(String s) {
      if (isBlank(s)) { return null; }

      LocalTime lt = null;
      for (DateTimeFormatter dateTimeFormatter : timeFormatters) {
        try {
          lt = dateTimeFormatter.parseDateTime(s).toLocalTime();
          return new Time(lt.getMillisOfDay() / 1000L);
        } catch (Exception ex) {
          continue;
        }
      }
      return null;
      
    }

    public Value getArValue(String s) {
      if (isBlank(s)) { return new Value(); }
      Time t = null;
      if ((t = parseIso(s)) != null) {
        return new Value(t);
      }
      return new Value(s, DataType.TIME_OF_DAY);
    }
    public Value getArValue(Time t) {
      return (t == null) ? new Value() : new Value(t);
    }
  },

  //JOIN("AR_DATA_TYPE_JOIN", DataType.JOIN),
  //TRIM("AR_DATA_TYPE_TRIM", DataType.TRIM),
  //CONTROL("AR_DATA_TYPE_CONTROL", DataType.CONTROL),
  //TABLE("AR_DATA_TYPE_TABLE", DataType.TABLE),
  //COLUMN("AR_DATA_TYPE_COLUMN", DataType.COLUMN),
  //PAGE("AR_DATA_TYPE_PAGE", DataType.PAGE),
  //HOLDER("AR_DATA_TYPE_PAGE_HOLDER", DataType.PAGE_HOLDER),
  //ATTACH_POOL("AR_DATA_TYPE_ATTACH_POOL", DataType.ATTACHMENT_POOL),

  /**
     A 32-bit unsigned integer value.
   */
  ULONG("AR_DATA_TYPE_ULONG", DataType.ULONG),

  /**
     A list of (x,y) coordinate pairs.
   */
  COORDS("AR_DATA_TYPE_COORDS", DataType.COORDS);
  //VIEW("AR_DATA_TYPE_VIEW", DataType.VIEW),
  //DISPLAY("AR_DATA_TYPE_DISPLAY", DataType.DISPLAY);


  
  private final String constantName;
  private final DataType dataType;
  
  private static final DateTimeFormatter[] dateTimeFormatters = new DateTimeFormatter[]
  {
    ISODateTimeFormat.dateTimeNoMillis(),
    ISODateTimeFormat.basicDateTimeNoMillis(),

    ISODateTimeFormat.ordinalDateTimeNoMillis(),
    ISODateTimeFormat.basicOrdinalDateTimeNoMillis(),

    ISODateTimeFormat.weekDateTimeNoMillis(),
    ISODateTimeFormat.basicWeekDateTimeNoMillis()
  };
  private static final DateTimeFormatter[] dateFormatters = new DateTimeFormatter[]
  {
    ISODateTimeFormat.date(),
    ISODateTimeFormat.basicDate(),

    ISODateTimeFormat.ordinalDate(),
    ISODateTimeFormat.basicOrdinalDate(),

    ISODateTimeFormat.weekDate(),
    ISODateTimeFormat.basicWeekDate()
  };
  private static final DateTimeFormatter[] timeFormatters = new DateTimeFormatter[]
  {
    ISODateTimeFormat.timeNoMillis(),
    ISODateTimeFormat.basicTimeNoMillis()
  };
  
    



  
  
  ArsDataType(String cn, DataType dt) {
    this.constantName = cn;
    this.dataType = dt;
  }
  
  public String getConstantName() { return constantName; }
  public DataType getDataType() { return dataType; }
  
  
  
  public static boolean isNull(Value v) {
    if (v == null) { return true; }
    if (v.getValue() == null) { return true; }
    if (v.getDataType() == DataType.NULL) { return true; }
    
    return false;
  }
  
  public static ArsDataType valueOfArInt(int i) {
    for (ArsDataType adt : EnumSet.allOf(ArsDataType.class)) {
      if (i == adt.dataType.toInt()) {
        return adt;
      }
    }
    return null;
  }
  
  public static ArsDataType valueOfDataType(DataType dt) {
    for (ArsDataType adt : EnumSet.allOf(ArsDataType.class)) {
      if (adt.getDataType().equals(dt)) {
        return adt;
      }
    }
    return null;
  }
  

  public static String getArString(Value v) {
    if (isNull(v)) { return null; }
    ArsDataType adt = valueOfDataType(v.getDataType());
    if (adt == null) {
      throw new IllegalArgumentException("DataType of Value not known.");
    }
    
    String s = null;
    switch(adt) {
    case NULL:
      s = null;
      break;
    case INTEGER:
      s = ((Integer) v.getValue()).toString();
      break;
    case REAL:
      s = ((Double) v.getValue()).toString();
      break;
    case CHAR:
      s = ((String) v.getValue());
      break;
    case ENUM:
      s = ((Integer) v.getValue()).toString();
      break;
    case TIME:
      DateTime timestampDt = new DateTime(((Timestamp) v.getValue()).getValue() * 1000L);
      s = ISODateTimeFormat.dateTimeNoMillis().print(timestampDt);
      break;
    case DECIMAL:
      s = ((BigDecimal) v.getValue()).toString();
      break;
    case CURRENCY:
      CurrencyValue cv = (CurrencyValue) v.getValue();
      s = cv.getValue().toString() + " " + cv.getCurrencyCode();
      break;
    case DATE:
      DateTime dateInfoDt = new DateTime(((DateInfo) v.getValue()).GetDate());
      s = ISODateTimeFormat.date().print(dateInfoDt);
      break;
    case TIME_OF_DAY:
      DateTime timeDt = 
        LocalTime
        .fromMillisOfDay(((Time) v.getValue()).getValue() * 1000L)
        .toDateTimeToday(DateTimeZone.UTC);
      s = ISODateTimeFormat.timeNoMillis().print(timeDt);
      break;
    default:
      s = v.getValue().toString();
    }
    return s;
  }
  
  
  
  
  
  
  
  
  public Value convert(Object o) {
    if (o == null) {
      return new Value();
    }

    if (o instanceof String) {
      return getArValue((String) o);
    }

    if (o instanceof Keyword) {
      return getArValue((Keyword) o);
    }

    if (o instanceof Integer) {
      return getArValue((Integer) o);
    }

    if (o instanceof Double) {
      return getArValue((Double) o);
    }

    if (o instanceof Timestamp) {
      return getArValue((Timestamp) o);
    }

    if (o instanceof BigDecimal) {
      return getArValue((BigDecimal) o);
    }

    if (o instanceof CurrencyValue) {
      return getArValue((CurrencyValue) o);
    }

    if (o instanceof DateInfo) {
      return getArValue((DateInfo) o);
    }

    if (o instanceof Time) {
      return getArValue((Time) o);
    }

    if (o instanceof DateTime) {
      return getArValue((DateTime) o);
    }

    if (o instanceof LocalDate) {
      return getArValue((LocalDate) o);
    }

    if (o instanceof LocalTime) {
      return getArValue((LocalTime) o);
    }

    throw new InvalidValueAccessException("Class " + o.getClass().getName()
                                          + " can not be converted.");
  }
  
  
  
  
  
  
  public Value getArValue(String s) { 
    throw new UnsupportedOperationException("AR Value from String not supported.");
  }
  public Value getArValue(Keyword k) { 
    throw new UnsupportedOperationException("AR Value from Keyword not supported.");
  }
  public Value getArValue(int i) { 
    throw new UnsupportedOperationException("AR Value from int not supported.");
  }
  public Value getArValue(Integer i) { 
    throw new UnsupportedOperationException("AR Value from Integer not supported.");
  }
  public Value getArValue(double d) { 
    throw new UnsupportedOperationException("AR Value from double not supported.");
  }
  public Value getArValue(Double d) { 
    throw new UnsupportedOperationException("AR Value from Double not supported.");
  }
  public Value getArValue(Timestamp t) { 
    throw new UnsupportedOperationException("AR Value from Timestamp not supported.");
  }
  public Value getArValue(BigDecimal bd) { 
    throw new UnsupportedOperationException("AR Value from BigDecimal not supported.");
  }
  public Value getArValue(CurrencyValue cv) { 
    throw new UnsupportedOperationException("AR Value from CurrencyValue not supported.");
  }
  public Value getArValue(DateInfo di) { 
    throw new UnsupportedOperationException("AR Value from DateInfo not supported.");
  }
  public Value getArValue(Time t) { 
    throw new UnsupportedOperationException("AR Value from Time not supported.");
  }
  public Value getArValue(DateTime dt) {
    throw new UnsupportedOperationException("AR Value from Joda DateTime not supported.");
  }
  public Value getArValue(LocalDate ld) {
    throw new UnsupportedOperationException("AR Value from Joda LocalDate not supported.");
  }
  public Value getArValue(LocalTime lt) {
    throw new UnsupportedOperationException("AR Value from Joda LocalTime not supported.");
  }
  
  


}
