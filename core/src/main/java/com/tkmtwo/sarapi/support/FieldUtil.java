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

import com.bmc.arsys.api.EnumItem;
import com.bmc.arsys.api.Field;
import com.bmc.arsys.api.SelectionField;
import com.bmc.arsys.api.SelectionFieldLimit;
import com.tkmtwo.sarapi.InvalidFieldAccessException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;


/**
 *
 */
public final class FieldUtil {

  private static final Integer ZERO = Integer.valueOf(0);
  private static final Integer ONE = Integer.valueOf(1);
  private static final Long ZEROL = Long.valueOf(0);
  private static final Long ONEL = Long.valueOf(1);

  private static final int[] EMPTYINTS = new int[0];

  private static final Logger logger = LoggerFactory.getLogger(FieldUtil.class);


  //may return null...
  public static Field findField(List<Field> fieldList,
                                int fieldId) {
    if (fieldList == null || fieldList.isEmpty()) {
      return null;
    }

    for (Field f : fieldList) {
      if (f.getFieldID() == fieldId) {
        return f;
      }
    }
    return null;
  }

  //throw exception if null...  
  public static Field getField(List<Field> fieldList,
                               int fieldId)
    throws DataAccessException {

    Field f = findField(fieldList, fieldId);
    if (f == null) {
      throw new InvalidFieldAccessException("getField(List<Field>, int)",
                                            String.format("Field with id %s not found in list.",
                                                          String.valueOf(fieldId)));
    }
    return f;
  }

  //may return null...
  public static Field findField(List<Field> fieldList,
                                String fieldName) {

    if (fieldList == null || fieldList.isEmpty()) {
      return null;
    }

    for (Field f : fieldList) {
      if (f.getName().equals(fieldName)) {
        return f;
      }
    }
    
    return null;
  }

  public static Field getField(List<Field> fieldList,
                               String fieldName)
    throws DataAccessException {

    Field f = findField(fieldList, fieldName);
    if (f == null) {
      throw new InvalidFieldAccessException("getField(List<Field>, String)",
                                            String.format("Field with name '%s' not found.",
                                                          fieldName));
    }
    
    return f;
  }
  
  
  
  public static String getFieldName(List<Field> fieldList,
                                        int fieldId)
    throws DataAccessException {

    Field f = getField(fieldList, fieldId);
    return f.getName();
  }
  public static int getFieldId(List<Field> fieldList,
                               String fieldName)
    throws DataAccessException {

    Field f = getField(fieldList, fieldName);
    return f.getFieldID();
  }
  
  
  
  
  
  public static List<EnumItem> getEnums(List<Field> fieldList,
                                        String fieldName)
    throws DataAccessException {

    Field f = findField(fieldList, fieldName);
    return getEnums(f);
  }
  public static List<EnumItem> getEnums(List<Field> fieldList,
                                        int fieldId)
    throws DataAccessException {

    Field f = findField(fieldList, fieldId);
    return getEnums(f);
  }
  public static List<EnumItem> getEnums(Field field)
    throws DataAccessException {

    if (field == null) {
      throw new InvalidFieldAccessException("getEnums",
                                            "Field can not be null.");
    }
    
    if (!(field instanceof SelectionField)) {
      throw new InvalidFieldAccessException(field,
                                            "is not a SelectionField.");
    }
    SelectionFieldLimit sfl = (SelectionFieldLimit) field.getFieldLimit();
    return sfl.getValues();
  }


  
  



  public static String getEnumName(List<Field> fieldList,
                                   String fieldName,
                                   int enumNumber)
    throws DataAccessException {

    Field f = findField(fieldList, fieldName);
    return getEnumName(f, enumNumber);
  }
  public static String getEnumName(List<Field> fieldList,
                                   int fieldId,
                                   int enumNumber)
    throws DataAccessException {

    Field f = findField(fieldList, fieldId);
    return getEnumName(f, enumNumber);
  }
  public static String getEnumName(Field field,
                                   int enumNumber)
    throws DataAccessException {

    List<EnumItem> l = getEnums(field);
    
    for (EnumItem ei : l) {
      if (ei.getEnumItemNumber() == enumNumber) {
        return ei.getEnumItemName();
      }
    }
    
    throw new InvalidFieldAccessException(field,
                                          String.format("No enum item with number '%s'.",
                                                        String.valueOf(enumNumber)));

  }
  
  
  
  
  
  
  
  public static int getEnumNumber(List<Field> fieldList,
                                  String fieldName,
                                  String enumName)
    throws DataAccessException {

    Field f = findField(fieldList, fieldName);
    return getEnumNumber(f, enumName);
  }
  public static int getEnumNumber(List<Field> fieldList,
                                  int fieldId,
                                  String enumName)
    throws DataAccessException {

    Field f = findField(fieldList, fieldId);
    return getEnumNumber(f, enumName);
  }
  public static int getEnumNumber(Field field,
                                  String enumName)
    throws DataAccessException {

    List<EnumItem> l = getEnums(field);
    
    for (EnumItem ei : l) {
      if (ei.getEnumItemName().equals(enumName)) {
        return ei.getEnumItemNumber();
      }
    }
    
    throw new InvalidFieldAccessException(field,
                                          String.format("No enum item with name '%s'.", enumName));
  }
  
  
  
  
  
  
  
}

