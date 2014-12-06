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


import com.bmc.arsys.api.AttachmentValue;
import com.bmc.arsys.api.CurrencyValue;
import com.bmc.arsys.api.DateInfo;
import com.bmc.arsys.api.Entry;
import com.bmc.arsys.api.Time;
import com.bmc.arsys.api.Timestamp;
import com.bmc.arsys.api.Value;
import com.tkmtwo.sarapi.support.EntryUtil;
import java.math.BigDecimal;
import java.util.List;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;


/**
 *
 */
public class EntryResultSet
  implements ArsResultSet {

  private static final Logger logger = LoggerFactory.getLogger(EntryResultSet.class);

  private ArsSchemaHelper arsSchemaHelper;
  private String formName;
  
  private List<Entry> entries;
  private int currentIndex = -1;


  public EntryResultSet(List<Entry> entries) {
    setEntries(entries);
  }
  public EntryResultSet(ArsSchemaHelper ash,
                        String formName,
                        List<Entry> entries) {
    setArsSchemaHelper(ash);
    setFormName(formName);
    setEntries(entries);
  }
  
  private ArsSchemaHelper getArsSchemaHelper() { return arsSchemaHelper; }
  private void setArsSchemaHelper(ArsSchemaHelper ash) { arsSchemaHelper = ash; }
  
  private String getFormName() { return formName; }
  private void setFormName(String s) { formName = s; }
  
  private void setEntries(List<Entry> entries) {
    this.entries = entries;
  }

  
  

  public boolean next() {
    if ((currentIndex + 1) < entries.size()) {
      currentIndex++;

      logger.debug("Moving current Entry to EntryID {}.",
                   entries.get(currentIndex).getEntryId());
      
      return true;
    }
    
    return false;

  }

    
  public int size() {
    if (entries != null) {
      return entries.size();
    }
    
    return -1;
  }
  public int currentIndex() {
    return currentIndex;
  }
  

  private void assertHelperAndForm() {
    if (getArsSchemaHelper() == null) {
      throw new InvalidDataAccessResourceUsageException("Need a schema helper.");
    }
    if (getFormName() == null) {
      throw new InvalidDataAccessResourceUsageException("Need a schema helper.");
    }
  }


  
  
  public Entry getEntry()
    throws InvalidEntryAccessException {

    return entries.get(currentIndex);
  }
  
  public String getEntryId()
    throws InvalidEntryAccessException {

    return getEntry().getEntryId();
  }
  
  public Value getValue(Integer fieldId)
    throws InvalidEntryAccessException {

    return EntryUtil.getValue(getEntry(), fieldId);
  }
  public Value getValue(String fieldName)
    throws InvalidEntryAccessException {

    assertHelperAndForm();
    return getValue(getArsSchemaHelper().getFieldId(getFormName(), fieldName));
  }
  
  
  
  public Integer getInteger(Integer fieldId)
    throws InvalidEntryAccessException {

    return EntryUtil.getInteger(getEntry(), fieldId);
  }

  public Integer getInteger(String fieldName)
    throws InvalidEntryAccessException {

    assertHelperAndForm();
    return getInteger(getArsSchemaHelper().getFieldId(getFormName(), fieldName));
  }
  
  
  
  public Double getReal(Integer fieldId)
    throws InvalidEntryAccessException {

    return EntryUtil.getReal(getEntry(), fieldId);
  }
  public Double getReal(String fieldName)
    throws InvalidEntryAccessException {

    assertHelperAndForm();
    return getReal(getArsSchemaHelper().getFieldId(getFormName(), fieldName));
  }
  
  
  
  
  public String getCharacter(Integer fieldId)
    throws DataAccessException {

    return EntryUtil.getCharacter(getEntry(), fieldId);
  }

  public String getCharacter(String fieldName)
    throws DataAccessException {

    assertHelperAndForm();
    return getCharacter(getArsSchemaHelper().getFieldId(getFormName(), fieldName));
  }
  
  
  
  
  
  public Integer getEnum(Integer fieldId)
    throws DataAccessException {

    return EntryUtil.getEnum(getEntry(), fieldId);
  }
  public Integer getEnum(String fieldName)
    throws DataAccessException {

    assertHelperAndForm();
    return getEnum(getArsSchemaHelper().getFieldId(getFormName(), fieldName));
  }


  /*
  public EnumPair getEnumPair(Integer fieldId)
    throws InvalidEntryAccessException {

    Integer enumNumber = getEnum(fieldId);
    String enumName = null;
    
    if (getArsSchemaHelper() != null
        && getFormName() != null
        && enumNumber != null) {
      enumName = getArsSchemaHelper().getEnumName(getFormName(),
                                                  fieldId,
                                                  enumNumber);
    }
    return new EnumPair(enumNumber, enumName);
  }
  
  public EnumPair getEnumPair(String fieldName)
    throws InvalidEntryAccessException {

    assertHelperAndForm();
    return getEnumPair(getArsSchemaHelper().getFieldId(getFormName(), fieldName));
  }
  */



  //
  //TODO(mahaffey): these two are dodgy...
  //
  public String getEnumName(Integer fieldId)
    throws InvalidEntryAccessException {
    
    assertHelperAndForm();
    return getArsSchemaHelper().getEnumName(getFormName(),
                                            fieldId,
                                            getEnum(fieldId));
  }
  //
  //TODO(mahaffey): these two are dodgy...
  //
  public String getEnumName(String fieldName) {
    assertHelperAndForm();
    return getEnumName(getArsSchemaHelper().getFieldId(getFormName(), fieldName));
  }
  
  
  
  
  
  public Timestamp getTimestamp(Integer fieldId)
    throws DataAccessException {

    return EntryUtil.getTimestamp(getEntry(), fieldId);
  }
  public Timestamp getTimestamp(String fieldName)
    throws DataAccessException {

    assertHelperAndForm();
    return getTimestamp(getArsSchemaHelper().getFieldId(getFormName(), fieldName));
    
  }

  public DateTime getTimestampAsDateTime(Integer fieldId)
    throws DataAccessException {

    return EntryUtil.getTimestampAsDateTime(getEntry(), fieldId);
  }

  public DateTime getTimestampAsDateTime(String fieldName)
    throws DataAccessException {

    assertHelperAndForm();
    return getTimestampAsDateTime(getArsSchemaHelper().getFieldId(getFormName(), fieldName));
  }
  
  
  
  
  
  public BigDecimal getDecimal(Integer fieldId)
    throws DataAccessException {

    return EntryUtil.getDecimal(getEntry(), fieldId);
  }

  public BigDecimal getDecimal(String fieldName)
    throws DataAccessException {

    assertHelperAndForm();
    return getDecimal(getArsSchemaHelper().getFieldId(getFormName(), fieldName));
  }



  public AttachmentValue getAttachmentValue(Integer fieldId)
    throws DataAccessException {

    return EntryUtil.getAttachmentValue(getEntry(), fieldId);
  }

  public AttachmentValue getAttachmentValue(String fieldName)
    throws DataAccessException {

    assertHelperAndForm();
    return getAttachmentValue(getArsSchemaHelper().getFieldId(getFormName(), fieldName));
  }





  public CurrencyValue getCurrencyValue(Integer fieldId)
    throws DataAccessException {

    return EntryUtil.getCurrencyValue(getEntry(), fieldId);
  }

  public CurrencyValue getCurrencyValue(String fieldName)
    throws DataAccessException {

    assertHelperAndForm();
    return getCurrencyValue(getArsSchemaHelper().getFieldId(getFormName(), fieldName));
  }






  public DateInfo getDateInfo(Integer fieldId)
    throws DataAccessException {

    return EntryUtil.getDateInfo(getEntry(), fieldId);
  }

  public DateInfo getDateInfo(String fieldName)
    throws DataAccessException {

    assertHelperAndForm();
    return getDateInfo(getArsSchemaHelper().getFieldId(getFormName(), fieldName));
  }






  public Time getTime(Integer fieldId)
    throws DataAccessException {

    return EntryUtil.getTime(getEntry(), fieldId);
  }
  public Time getTime(String fieldName)
    throws DataAccessException {

    assertHelperAndForm();
    return getTime(getArsSchemaHelper().getFieldId(getFormName(), fieldName));
  }
  
  
}

