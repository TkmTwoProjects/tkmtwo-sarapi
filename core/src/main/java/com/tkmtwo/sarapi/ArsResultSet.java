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
import java.math.BigDecimal;
import org.joda.time.DateTime;

/**
 *
 */
public interface ArsResultSet {
  boolean next();
  int size();
  
  
  String getEntryId()
    throws InvalidEntryAccessException;
  
  
  Entry getEntry()
    throws InvalidEntryAccessException;
  Value getValue(Integer fieldId)
    throws InvalidEntryAccessException;
  Value getValue(String fieldName)
    throws InvalidEntryAccessException;
  
  
  Integer getInteger(Integer fieldId)
    throws InvalidEntryAccessException;
  Integer getInteger(String fieldName)
    throws InvalidEntryAccessException;

  Double getReal(Integer fieldId)
    throws InvalidEntryAccessException;
  Double getReal(String fieldName)
    throws InvalidEntryAccessException;

  String getCharacter(Integer fieldId)
    throws InvalidEntryAccessException;
  String getCharacter(String fieldName)
    throws InvalidEntryAccessException;

  Integer getEnum(Integer fieldId)
    throws InvalidEntryAccessException;
  Integer getEnum(String fieldName)
    throws InvalidEntryAccessException;

  String getEnumName(Integer fieldId)
    throws InvalidEntryAccessException;
  String getEnumName(String fieldName)
    throws InvalidEntryAccessException;

  Timestamp getTimestamp(Integer fieldId)
    throws InvalidEntryAccessException;
  Timestamp getTimestamp(String fieldName)
    throws InvalidEntryAccessException;

  DateTime getTimestampAsDateTime(Integer fieldId)
    throws InvalidEntryAccessException;
  DateTime getTimestampAsDateTime(String fieldName)
    throws InvalidEntryAccessException;
  
  BigDecimal getDecimal(Integer fieldId)
    throws InvalidEntryAccessException;
  BigDecimal getDecimal(String fieldName)
    throws InvalidEntryAccessException;

  AttachmentValue getAttachmentValue(Integer fieldId)
    throws InvalidEntryAccessException;
  AttachmentValue getAttachmentValue(String fieldName)
    throws InvalidEntryAccessException;

  CurrencyValue getCurrencyValue(Integer fieldId)
    throws InvalidEntryAccessException;
  CurrencyValue getCurrencyValue(String fieldName)
    throws InvalidEntryAccessException;

  DateInfo getDateInfo(Integer fieldId)
    throws InvalidEntryAccessException;
  DateInfo getDateInfo(String fieldName)
    throws InvalidEntryAccessException;

  Time getTime(Integer fieldId)
    throws InvalidEntryAccessException;
  Time getTime(String fieldName)
    throws InvalidEntryAccessException;
  
  
}
