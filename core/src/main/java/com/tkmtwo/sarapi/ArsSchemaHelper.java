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

import com.bmc.arsys.api.QualifierInfo;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.dao.DataAccessException;


/**
 *
 */
public interface ArsSchemaHelper{
  
  ArsForm getForm(String formName)
    throws DataAccessException;

  
  Set<ArsField> getFields(String formName)
    throws DataAccessException;
  

  ArsField getField(String formName,
                    String fieldName)
    throws DataAccessException;
  ArsField getField(String formName,
                    Integer fieldId)
    throws DataAccessException;
  
  
  
  List<ArsField> compileFieldsByName(String formName,
                                     Iterable<String> fieldNames)
    throws DataAccessException;
  
  List<ArsField> compileFieldsById(String formName,
                                   Iterable<Integer> fieldIds)
    throws DataAccessException;
  
  int[] compileFieldIds(Iterable<ArsField> fields);
  int[] compileFieldIdsByName(String formName, Iterable<String> fieldNames)
    throws DataAccessException;
  int[] compileFieldIdsById(String formName, Iterable<Integer> fieldIds)
    throws DataAccessException;
  
  
  
  
  Integer getFieldId(String formName,
                     String fieldName)
    throws DataAccessException;
  String getFieldName(String formName,
                      Integer fieldId)
    throws DataAccessException;
  
  
  
  
  ArsDataType getDataType(String formName,
                          String fieldName)
    throws DataAccessException;
  ArsDataType getDataType(String formName,
                          Integer fieldId)
    throws DataAccessException;
  
  
  Map<Integer, String> getEnums(String formName,
                                String fieldName)
    throws DataAccessException;
  Map<Integer, String> getEnums(String formName,
                                Integer fieldId)
    throws DataAccessException;
  
  
  String getEnumName(String formName,
                     String fieldName,
                     Integer enumNumber)
    throws DataAccessException;
  String getEnumName(String formName,
                     Integer fieldId,
                     Integer enumNumber)
    throws DataAccessException;
  
  
  Integer getEnumNumber(String formName,
                        String fieldName,
                        String enumName)
    throws DataAccessException;
  Integer getEnumNumber(String formName,
                        Integer fieldId,
                        String enumName)
    throws DataAccessException;
  
  
  QualifierInfo parseQualification(String formName,
                                   String qs)
    throws DataAccessException;
  
  
  
  int getMaxLength(String formName,
                   String fieldName)
    throws DataAccessException;
  int getMaxLength(String formName,
                   Integer fieldId)
    throws DataAccessException;
  
  
  
  List<String> getEnumNames(String formName,
                            String fieldName)
    throws DataAccessException;
  List<String> getEnumNames(String formName,
                            Integer fieldId)
    throws DataAccessException;
  
  
  
  
}
