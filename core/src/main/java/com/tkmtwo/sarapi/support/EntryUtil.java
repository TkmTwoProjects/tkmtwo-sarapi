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
import static com.google.common.base.TkmTwoStrings.isBlank;

import com.bmc.arsys.api.AttachmentValue;
import com.bmc.arsys.api.CurrencyValue;
import com.bmc.arsys.api.DataType;
import com.bmc.arsys.api.DateInfo;
import com.bmc.arsys.api.Entry;
import com.bmc.arsys.api.Time;
import com.bmc.arsys.api.Timestamp;
import com.bmc.arsys.api.Value;
import com.tkmtwo.sarapi.ArsField;
import com.tkmtwo.sarapi.ArsSchemaHelper;
import com.tkmtwo.sarapi.InvalidEntryAccessException;
import com.tkmtwo.sarapi.InvalidValueAccessException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/*
Data Fields
 Character     java.lang.String
 Diary         Not Supported
 Date/Time     Timestamp
 Date          DateInfo
 Time          Time
 Currency      CurrencyValue
 Integer       
 Real
 Decimal
 Selection 

#define AR_DATA_TYPE_NULL           0
#define AR_DATA_TYPE_KEYWORD        1
#define AR_DATA_TYPE_INTEGER        2  X
#define AR_DATA_TYPE_REAL           3
#define AR_DATA_TYPE_CHAR           4  X
#define AR_DATA_TYPE_DIARY          5
#define AR_DATA_TYPE_ENUM           6
#define AR_DATA_TYPE_TIME           7  X
#define AR_DATA_TYPE_BITMASK        8
#define AR_DATA_TYPE_BYTES          9
#define AR_DATA_TYPE_DECIMAL       10  X
#define AR_DATA_TYPE_ATTACH        11
#define AR_DATA_TYPE_CURRENCY      12
#define AR_DATA_TYPE_DATE          13  X
#define AR_DATA_TYPE_TIME_OF_DAY   14  X
 */

/**
 *
 */
public final class EntryUtil {

  private static final Logger logger = LoggerFactory.getLogger(EntryUtil.class);
  
  /*
    
   */
  


  public static String entryToString(Entry entry) {
    StringBuffer sb = new StringBuffer();
    
    sb.append("[");
    for (Map.Entry<Integer, Value> me : entry.entrySet()) {
      Integer fid = me.getKey();
      Value val = me.getValue();
      sb
        .append(fid.toString())
        .append("=")
        .append((val != null) ? val.toString() : "javanull")
        .append(" ");
    }
    sb.append("]");
    
    return sb.toString();
  }



  public static String entryToString(Entry entry,
                                     ArsSchemaHelper arsSchemaHelper,
                                     String formName) {
    StringBuffer sb = new StringBuffer();
    
    sb.append("[");
    for (Map.Entry<Integer, Value> me : entry.entrySet()) {
      Integer fid = me.getKey();
      Value val = me.getValue();
      sb
        .append(arsSchemaHelper.getFieldName(formName, fid))
        .append("=")
        .append((val != null) ? val.toString() : "javanull")
        .append(" ");
    }
    sb.append("]");
    
    return sb.toString();
  }
  
  public static String entryToDetailedString(Entry entry) {
    if (entry == null) {
      return "Entry: null";
    }

    StringBuffer sb = new StringBuffer();
    String lineSep = System.getProperty("line.separator");

    sb.append(lineSep);
    sb.append("Entry: ").append(entry.getEntryId()).append(lineSep);
    
    for (Map.Entry<Integer, Value> me : entry.entrySet()) {
      Integer fid = me.getKey();
      Value val = me.getValue();
      
      sb.append("    Field ID: ").append(fid.toString()).append(lineSep);

      DataType dt = val.getDataType();
      if (dt.equals(DataType.NULL)) {
        sb.append("        ").append("Value:       ").append("null");
      } else {
        sb.append("        ").append("Value:       ").append(val.toString()).append(lineSep);
        sb.append("        ").append("Value Class: ").append(val.getValue().getClass().getName()).append(lineSep);
        sb.append("        ").append("DataType:    ").append(dt.toString()).append(lineSep);
      }
      sb.append(lineSep);
    }

    return sb.toString();
  }

  /*  
  public static String entryToString(Entry e,
                                     List<Field> fieldList)
  {
    StringBuffer sb = new StringBuffer();
    
    sb.append("[");
    for (Map.Entry<Integer,Value> me : e.entrySet()) {
      Integer fid = me.getKey();
      Value val = me.getValue();
      sb
        .append(FieldUtil.getFieldName(fieldList, fid))
        .append("=")
        .append(val.toString())
        .append(" ");
    }
    sb.append("]");
    
    return sb.toString();
  }
  */  






  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  public static Value[] getValues(Entry entry,
                                  Integer[] fieldIds)
    throws InvalidEntryAccessException {

    if (fieldIds == null) {
      throw new InvalidEntryAccessException("Getting values from entry.",
                                            "fieldIDs is null.");
    }

    Value[] vs = new Value[fieldIds.length];
    for (int i = 0; i < fieldIds.length; i++) {
      vs[i] = getValue(entry, fieldIds[i]);
    }
    return vs;
  }
  
  public static Value getValue(Entry entry,
                               Integer fieldId)
    throws InvalidEntryAccessException {

    if (entry == null) {
      throw new InvalidEntryAccessException("Getting value from entry",
                                            "Entry is null");
    }
    if (fieldId == null) {
      throw new InvalidEntryAccessException("Getting value from entry",
                                            "fieldId is null");
    }
    if (!entry.containsKey(fieldId)) {
      throw new InvalidEntryAccessException(entry,
                                            "Field with id "
                                            + fieldId.toString()
                                            + " not found.");
    }
    
    Value value = entry.get(fieldId);
    return value;
    
  }
  /*
  public static Value getValue(Entry entry,
                               ArsSchemaHelper ash,
                               String formName,
                               String fieldName)
  {
    return getValue(entry, ash.getFieldId(formName, fieldName));
  }
  */                           
  
  
  
  
  public static Integer getInteger(Entry entry,
                                   Integer fieldId)
    throws InvalidEntryAccessException {

    Integer rv = null;
    try {
      rv = ValueUtil.getInteger(getValue(entry, fieldId));
    } catch (InvalidValueAccessException ivae) {
      throw new InvalidEntryAccessException(entry, fieldId, ivae);
    }
    return rv;
  }
  
  public static Double getReal(Entry entry,
                               Integer fieldId)
    throws InvalidEntryAccessException {

    Double rv = null;
    try {
      rv = ValueUtil.getReal(getValue(entry, fieldId));
    } catch (InvalidValueAccessException ivae) {
      throw new InvalidEntryAccessException(entry, fieldId, ivae);
    }
    return rv;
  }
  
  public static String getCharacter(Entry entry,
                                    Integer fieldId)
    throws InvalidEntryAccessException {

    String rv = null;
    try {
      rv = ValueUtil.getCharacter(getValue(entry, fieldId));
    } catch (InvalidValueAccessException ivae) {
      throw new InvalidEntryAccessException(entry, fieldId, ivae);
    }
    return rv;
  }
  
  public static Integer getEnum(Entry entry,
                                Integer fieldId)
    throws InvalidEntryAccessException {

    Integer rv = null;
    try {
      rv = ValueUtil.getEnum(getValue(entry, fieldId));
    } catch (InvalidValueAccessException ivae) {
      throw new InvalidEntryAccessException(entry, fieldId, ivae);
    }
    return rv;
  }
  
  public static Timestamp getTimestamp(Entry entry,
                                       Integer fieldId)
    throws InvalidEntryAccessException {

    Timestamp rv = null;
    try {
      rv = ValueUtil.getTimestamp(getValue(entry, fieldId));
    } catch (InvalidValueAccessException ivae) {
      throw new InvalidEntryAccessException(entry, fieldId, ivae);
    }
    return rv;
  }
  public static DateTime getTimestampAsDateTime(Entry entry,
                                                Integer fieldId)
    throws InvalidEntryAccessException {

    return ValueUtil.toDateTime(getTimestamp(entry, fieldId));
  }    
  
  public static BigDecimal getDecimal(Entry entry,
                                      Integer fieldId)
    throws InvalidEntryAccessException {

    BigDecimal rv = null;
    try {
      rv = ValueUtil.getDecimal(getValue(entry, fieldId));
    } catch (InvalidValueAccessException ivae) {
      throw new InvalidEntryAccessException(entry, fieldId, ivae);
    }
    return rv;
  }


  //
  public static AttachmentValue getAttachmentValue(Entry entry,
                                                   Integer fieldId)
    throws InvalidEntryAccessException {

    AttachmentValue av = null;
    try {
      av = ValueUtil.getAttachmentValue(getValue(entry, fieldId));
    } catch (InvalidValueAccessException ivae) {
      throw new InvalidEntryAccessException(entry, fieldId, ivae);
    }
    return av;
  }
  
  //  
  public static CurrencyValue getCurrencyValue(Entry entry,
                                               Integer fieldId)
    throws InvalidEntryAccessException {

    CurrencyValue rv = null;
    try {
      rv = ValueUtil.getCurrencyValue(getValue(entry, fieldId));
    } catch (InvalidValueAccessException ivae) {
      throw new InvalidEntryAccessException(entry, fieldId, ivae);
    }
    return rv;
  }
  
  public static DateInfo getDateInfo(Entry entry,
                                     Integer fieldId)
    throws InvalidEntryAccessException {

    DateInfo rv = null;
    try {
      rv = ValueUtil.getDateInfo(getValue(entry, fieldId));
    } catch (InvalidValueAccessException ivae) {
      throw new InvalidEntryAccessException(entry, fieldId, ivae);
    }
    return rv;
  }
  
  public static Time getTime(Entry entry,
                             Integer fieldId)
    throws InvalidEntryAccessException {

    Time rv = null;
    try {
      rv = ValueUtil.getTime(getValue(entry, fieldId));
    } catch (InvalidValueAccessException ivae) {
      throw new InvalidEntryAccessException(entry, fieldId, ivae);
    }
    return rv;
  }
  
  
  
  
  
      
  
  
  
  /*
  public static Integer getIntegerValue(Entry entry,
                                        String fieldName,
                                        List<Field> fieldList)
    throws DataAccessException
  {
    int fieldId = FieldUtil.getFieldId(fieldList, fieldName);
    return getIntegerValue(entry, fieldId);
  }
  */
  /*
  public static Integer getIntegerValue(Entry entry,
                                        int fieldId)
    throws DataAccessException
  {
    Value v = getValue(entry, fieldId);
    DataType dt = v.getDataType();
    
    if (dt.equals(DataType.NULL)) {
      return null;
    }

    if (!dt.equals(DataType.INTEGER)) {
      throw new InvalidEntryAccessException(entry,
                                            "Field with id "
                                            + Strings.tickit(String.valueOf(fieldId))
                                            + " is not an INTEGER.");
    }
    
    Integer objValue = (Integer) v.getValue();
    return objValue;
  }
  */
  
  
  
  
  
  
  
  
  
  /*  
  public static Long getLongValue(Entry entry,
                                  String fieldName,
                                  List<Field> fieldList)
    throws DataAccessException
  {
    int fieldId = FieldUtil.getFieldId(fieldList, fieldName);
    return getLongValue(entry, fieldId);
  }
  public static Long getLongValue(Entry entry,
                                  int fieldId)
    throws DataAccessException
  {
    Value v = getValue(entry, fieldId);
    DataType dt = v.getDataType();
    
    if (dt.equals(DataType.NULL)) {
      return null;
    }

    if (!dt.equals(DataType.ULONG)) {
      throw new InvalidEntryAccessException(entry,
                                            "Field with id "
                                            + Strings.tickit(String.valueOf(fieldId))
                                            + " is not a ULONG.");
    }
    
    Long objVal = (Long) v.getValue();
    return objVal;
  }
  */

  
  
  
  
  
  
  
  /*  
  public static BigDecimal getDecimalValue(Entry entry,
                                           String fieldName,
                                           List<Field> fieldList)
    throws DataAccessException
  {
    int fieldId = FieldUtil.getFieldId(fieldList, fieldName);
    return getDecimalValue(entry, fieldId);
  }
  public static BigDecimal getDecimalValue(Entry entry,
                                           int fieldId)
    throws DataAccessException
  {
    Value v = getValue(entry, fieldId);
    DataType dt = v.getDataType();
    
    if (dt.equals(DataType.NULL)) {
      return null;
    }
    
    if (!dt.equals(DataType.DECIMAL)) {
      throw new InvalidEntryAccessException(entry,
                                            "Field with id "
                                            + Strings.tickit(String.valueOf(fieldId))
                                            + " is not a DECIMAL.");
    }
    
    BigDecimal objVal = (BigDecimal) v.getValue();
    return objVal;
  }
  */

  
  
  
  
  
  
  
  /*  
  public static Timestamp getTimestampValue(Entry entry,
                                            String fieldName,
                                            List<Field> fieldList)
    throws DataAccessException
  {
    int fieldId = FieldUtil.getFieldId(fieldList, fieldName);
    return getTimestampValue(entry, fieldId);
  }

  public static Timestamp getTimestampValue(Entry entry,
                                            int fieldId)
    throws DataAccessException
  {
    Value v = getValue(entry, fieldId);
    DataType dt = v.getDataType();
    
    if (dt.equals(DataType.NULL)) {
      return null;
    }
    
    if (!dt.equals(DataType.TIME)) {
      throw new InvalidEntryAccessException(entry,
                                            "Field with id "
                                            + Strings.tickit(String.valueOf(fieldId))
                                            + " is not a TIME.");
    }
    
    Timestamp objVal = (Timestamp) v.getValue();
    return objVal;
  }
  */  
  
  
  
  
  
  
  
  /*  
  public static DateInfo getDateInfoValue(Entry entry,
                                          String fieldName,
                                          List<Field> fieldList)
    throws DataAccessException
  {
    int fieldId = FieldUtil.getFieldId(fieldList, fieldName);
    return getDateInfoValue(entry, fieldId);
  }
  public static DateInfo getDateInfoValue(Entry entry,
                                          int fieldId)
    throws DataAccessException
  {
    Value v = getValue(entry, fieldId);
    DataType dt = v.getDataType();
    
    if (dt.equals(DataType.NULL)) {
      return null;
    }

    if (!dt.equals(DataType.DATE)) {
      throw new InvalidEntryAccessException(entry,
                                            "Field with id "
                                            + Strings.tickit(String.valueOf(fieldId))
                                            + " is not a DATE.");
    }
    
    DateInfo objVal = (DateInfo) v.getValue();
    return objVal;
  }
  */  
  
  
  
  
  
  /*  
  public static Time getTimeValue(Entry entry,
                                  String fieldName,
                                  List<Field> fieldList)
    throws DataAccessException
  {
    int fieldId = FieldUtil.getFieldId(fieldList, fieldName);
    return getTimeValue(entry, fieldId);
  }
  public static Time getTimeValue(Entry entry, int fieldId)
    throws DataAccessException
  {
    Value v = getValue(entry, fieldId);
    DataType dt = v.getDataType();

    if (dt.equals(DataType.NULL)) {
      return null;
    }

    if (!dt.equals(DataType.TIME_OF_DAY)) {
      throw new InvalidEntryAccessException(entry,
                                            "Field with id "
                                            + Strings.tickit(String.valueOf(fieldId))
                                            + " is not a TIME_OF_DAY.");
    }
    
    Time objVal = (Time) v.getValue();
    return objVal;
  }
  */  
  
  

  
  /*
  public static EnumItem getEnumItemValue(Entry entry,
                                          String fieldName,
                                          List<Field> fieldList)
    throws DataAccessException
  {
    int fieldId = FieldUtil.getFieldId(fieldList, fieldName);
    return getEnumItemValue(entry, fieldId, fieldList);
  }
  
  public static Integer getEnumItemValue(Entry entry,
                                         int fieldId,
                                         List<Field> fieldList)
    throws DataAccessException
  {
    Value v = getValue(entry, fieldId);
    DataType dt = v.getDataType();
    
    if (dt.equals(DataType.NULL)) {
      return null;
    }

    if (!dt.equals(DataType.ENUM)) {
      throw new InvalidEntryAccessException(entry,
                                            "Field with id "
                                            + Strings.tickit(String.valueOf(fieldId))
                                            + " is not an ENUM.");
    }
    
    Integer objValue = (Integer) v.getValue();
    return objValue;
  }
  */




  /*
  public static Integer getSelectionNumberValue(Entry entry,
                                                String fieldName,
                                                List<Field> fieldList)
    throws DataAccessException
  {
    int fieldId = FieldUtil.getFieldId(fieldList, fieldName);
    return getSelectionNumberValue(entry, fieldId);
  }
  public static Integer getSelectionNumberValue(Entry entry,
                                                int fieldId)
    throws DataAccessException
  {
    Value v = getValue(entry, fieldId);
    DataType dt = v.getDataType();
    
    if (dt.equals(DataType.NULL)) {
      return null;
    }

    if (!dt.equals(DataType.ENUM)) {
      throw new InvalidEntryAccessException(entry,
                                            "Field with id "
                                            + Strings.tickit(String.valueOf(fieldId))
                                            + " is not an ENUM.");
    }
    
    Integer objValue = (Integer) v.getValue();
    return objValue;
  }
  */









  /*
  public static AttachmentValue getAttachmentValue(Entry entry,
                                                   String fieldName,
                                                   List<Field> fieldList)
    throws DataAccessException
  {
    int fieldId = FieldUtil.getFieldId(fieldList, fieldName);
    return getAttachmentValue(entry, fieldId);
  }
  public static AttachmentValue getAttachmentValue(Entry entry,
                                                   int fieldId)
    throws DataAccessException
  {
    Value v = getValue(entry, fieldId);
    DataType dt = v.getDataType();
    
    if (dt.equals(DataType.NULL)) {
      return null;
    }

    if (!dt.equals(DataType.ATTACHMENT)) {
      throw new InvalidEntryAccessException(entry,
                                            "Field with id "
                                            + Strings.tickit(String.valueOf(fieldId))
                                            + " is not an ATTACHMENT.");
    }
    
    AttachmentValue objValue = (AttachmentValue) v.getValue();
    return objValue;
  }
  */







  /*
  public static String getSelectionNameValue(Entry entry,
                                             String fieldName,
                                             List<Field> fieldList)
    throws DataAccessException
  {
    int fieldId = FieldUtil.getFieldId(fieldList, fieldName);
    return getSelectionNameValue(entry, fieldId);
  }
  public static String getSelectionNameValue(Entry entry,
                                             int fieldId)
    throws DataAccessException
  {
    Integer numberValue = getSelectionNumberValue(entry, fieldId);
    
    if (numberValue == null) {
      return null;
    }
    
    
    Value v = getValue(entry, fieldId);
    DataType dt = v.getDataType();
    
    if (dt.equals(DataType.NULL)) {
      return null;
    }
    if (!dt.equals(DataType.ENUM)) {
      throw new InvalidEntryAccessException(entry,
                                            "Field with id "
                                            + Strings.tickit(String.valueOf(fieldId))
                                            + " is not an ENUM.");
    }
    
    Integer objValue = (Integer) v.getValue();
    return objValue;
  }
  */
  
  
  public static Entry newEntry(List<ArsField> arsFields,
                               List<Value> arsValues) {
    int fieldsSize = arsFields.size();
    checkArgument(arsValues.size() == fieldsSize,
                  "Number of values(%s) does not match number of fields (%s)",
                  String.valueOf(arsValues.size()),
                  String.valueOf(fieldsSize));
    
    Entry entry = new Entry();
    for (int i = 0; i < fieldsSize; i++) {
      entry.put(arsFields.get(i).getId(),
                arsValues.get(i));
    }
    
    return entry;
  }


  /*  
  public void nothing() {
    Iterator<Map.Entry<String,String>> iter = TestMap.entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry<String,String> entry = iter.next();
      if("Sample".equalsIgnoreCase(entry.getValue())){
        iter.remove();
      }
    }
  }
  */

  public static void removeNullValues(Entry entry) {
    checkNotNull(entry, "Need an entry");
    Iterator<Map.Entry<Integer, Value>> iterator = entry.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<Integer, Value> me = iterator.next();
      if (me.getValue().getValue() == null) {
        logger.trace("Removing Entry entry with field id '{}'", me.getKey());
        iterator.remove();
      }
    }
  }
  
  public static void removeNullValues(Entry entry, List<Integer> fieldIds) {
    checkNotNull(entry, "Need an entry");
    checkNotNull(fieldIds, "Need some field ids");
    
    Iterator<Map.Entry<Integer, Value>> iterator = entry.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<Integer, Value> me = iterator.next();
      if (fieldIds.contains(me.getKey())
          && me.getValue().getValue() == null) {
        logger.trace("Removing Entry entry with field id '{}'", me.getKey());
        iterator.remove();
      }
    }
  }
  
  
  public static void removeBlankCharValues(Entry entry) {
    checkNotNull(entry, "Need an entry");
    Iterator<Map.Entry<Integer, Value>> iterator = entry.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<Integer, Value> me = iterator.next();
      if (DataType.CHAR.equals(me.getValue().getDataType())) {
        String stringValue = (String) me.getValue().getValue();
        if (isBlank(stringValue)) {
          logger.trace("Removing Entry entry with field id '{}'", me.getKey());
          iterator.remove();
        }
      }
    }
  }
  
  public static void removeBlankCharValues(Entry entry, List<Integer> fieldIds) {
    checkNotNull(entry, "Need an entry");
    checkNotNull(fieldIds, "Need some field ids");
    
    Iterator<Map.Entry<Integer, Value>> iterator = entry.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<Integer, Value> me = iterator.next();
      if (DataType.CHAR.equals(me.getValue().getDataType())) {
        String stringValue = (String) me.getValue().getValue();
        if (fieldIds.contains(me.getKey())
            && isBlank(stringValue)) {
          logger.trace("Removing Entry entry with field id '{}'", me.getKey());
          iterator.remove();
        }
      }
    }
  }
  
  
  
  
  
}
