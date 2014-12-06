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


import static com.google.common.base.Preconditions.checkNotNull;

import com.bmc.arsys.api.CharacterFieldLimit;
import com.bmc.arsys.api.Constants;
import com.bmc.arsys.api.EnumItem;
import com.bmc.arsys.api.Field;
import com.bmc.arsys.api.FieldLimit;
import com.bmc.arsys.api.QualifierInfo;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.tkmtwo.sarapi.support.FieldUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;


/**
 *
 */
public class InMemorySchemaHelper
  extends ArsDaoSupport
  implements ArsSchemaHelper {

  private Map<String, ArsForm> arsForms = new HashMap<String, ArsForm>();


  private ArsForm refreshForm(String formName)
    throws DataAccessException {
    checkNotNull(formName, "Form name is null");
    
    logger.info("Refresh start data fields on form " + formName);
    ArsForm arsForm = new ArsForm(formName);

    
    List<Field> arFields = getTemplate().getDataFields(formName);
    if (arFields == null || arFields.isEmpty()) {
      throw new InvalidDataAccessResourceUsageException(String.format("Form %s not found or has no fields", formName));
    }
    logger.trace("Found {} fields on form '{}'", String.valueOf(arFields.size()), formName);


    for (Field arField : arFields) {
      logger.trace("Adding field '{}' with fieldtype '{}' and datatype '{}'",
                   arField.getName(), arField.getFieldType(), arField.getDataType());
      
      
      if (arField.getFieldType() != Constants.AR_FIELD_TYPE_DATA
          && arField.getFieldType() != Constants.AR_FIELD_TYPE_ATTACH) {

        logger.warn("Skipping non-data, non-attach-field '{}' with field type '{}' and datatype '{}'",
                    arField.getName(), arField.getFieldType(), arField.getDataType());
        continue;
      }


      
      ArsField arsField = new ArsField(Integer.valueOf(arField.getFieldID()),
                                       arField.getName(),
                                       arField.getDataType());
      arsField.setArField(arField);
      
      
      arsForm.addField(arsField);
      
      if (arsField.getDataType().equals(ArsDataType.ENUM)) {
        for (EnumItem enumItem : FieldUtil.getEnums(arField)) {
          arsField.addEnum(Integer.valueOf(enumItem.getEnumItemNumber()),
                           enumItem.getEnumItemName());
        }
      }


      logger.trace("Added field {}", arsField.toString());
    }
    
    arsForms.put(formName, arsForm);

    logger.info("Refresh stop data fields on form '{}'", formName);

    return arsForm;
  }




  public ArsForm getForm(String formName)
    throws DataAccessException {

    checkNotNull(formName, "Form name is null");
    
    ArsForm arsForm = arsForms.get(formName);
    if (arsForm == null) {
      arsForm = refreshForm(formName);
    }
    return arsForm;
  }
  
  
  public Set<ArsField> getFields(String formName)
    throws DataAccessException {

    ArsForm arsForm = getForm(formName);
    return arsForm.getFields();
  }
  

  private Integer parseInteger(String s) {
    Integer i = null;
    try {
      i = new Integer(s);
    } catch (Exception ex) {
      i = null;
    }
    return i;
  }
  
  
  
  
  
  public ArsField getField(String formName,
                           String fieldName,
                           ArsDataType requiredType)
    throws DataAccessException {
    
    checkNotNull(requiredType, "Need a required ArsDataType");
    ArsField arsField = getField(formName, fieldName);
    if (requiredType != arsField.getDataType()) {
      throw new InvalidDataAccessResourceUsageException(String.format("Field %s.%s is not a %s field.",
                                                                      formName, fieldName,
                                                                      requiredType.toString()));
    }
    return arsField;
  }


  public ArsField getField(String formName,
                           Integer fieldId,
                           ArsDataType requiredType)
    throws DataAccessException {
    
    checkNotNull(requiredType, "Need a required ArsDataType");
    ArsField arsField = getField(formName, fieldId);
    if (requiredType != arsField.getDataType()) {
      throw new InvalidDataAccessResourceUsageException(String.format("Field %s.%s is not a %s field.",
                                                                      formName, String.valueOf(fieldId),
                                                                      requiredType.toString()));
    }
    return arsField;
  }


  public ArsField getField(String formName,
                           String fieldName)
    throws DataAccessException {
    
    checkNotNull(fieldName, "Field name is null");
    ArsForm arsForm = getForm(formName);
    for (ArsField arsField : arsForm.getFields()) {
      if (fieldName.equals(arsField.getName())) {
        return arsField;
      }
    }
    
    Integer nameAsInteger = parseInteger(fieldName);
    if (nameAsInteger != null) {
      return getField(formName, nameAsInteger);
    }
      
    
    throw new InvalidDataAccessResourceUsageException(String.format("Field %s.%s not found",
                                                                    formName, fieldName));
  }
  
  
  public ArsField getField(String formName,
                           Integer fieldId)
    throws DataAccessException {

    
    checkNotNull(fieldId, "Field id is null");
    
    ArsForm arsForm = getForm(formName);
    for (ArsField arsField : arsForm.getFields()) {
      if (fieldId.equals(arsField.getId())) {
        return arsField;
      }
    }
    throw new InvalidDataAccessResourceUsageException(String.format("Field %s.%s not found",
                                                                    formName, fieldId.toString()));
  }
  
  
  
  
  
  public List<ArsField> compileFieldsByName(String formName,
                                            Iterable<String> fieldNames)
    throws DataAccessException {
    
    ImmutableList.Builder<ArsField> ilb = new ImmutableList.Builder<ArsField>();
    for (String fieldName : fieldNames) {
      ilb.add(getField(formName, fieldName));
    }

    return ilb.build();
  }

  public List<ArsField> compileFieldsById(String formName,
                                          Iterable<Integer> fieldIds)
    throws DataAccessException {
    
    ImmutableList.Builder<ArsField> ilb = new ImmutableList.Builder<ArsField>();
    for (Integer fieldId : fieldIds) {
      ilb.add(getField(formName, fieldId));
    }

    return ilb.build();
  }
  
  
  public int[] compileFieldIdsByName(String formName, Iterable<String> fieldNames)
    throws DataAccessException {
    return compileFieldIds(compileFieldsByName(formName, fieldNames));
  }
  public int[] compileFieldIdsById(String formName, Iterable<Integer> fieldIds)
    throws DataAccessException {
    return compileFieldIds(compileFieldsById(formName, fieldIds));
  }
  
  public int[] compileFieldIds(Iterable<ArsField> fields) {
    if (fields == null) { return new int[0]; }
    
    ImmutableList.Builder<Integer> ilb = new ImmutableList.Builder<Integer>();
    for (ArsField field : fields) {
      ilb.add(field.getId());
    }
    List<Integer> fieldIds = ilb.build();

    

    int numFields = fieldIds.size();
    int[] ids = new int[numFields];
    for (int i = 0; i < numFields; i++) {
      ids[i] = fieldIds.get(i).intValue();
    }
    return ids;
  }
  

  
  
  
  
  public Integer getFieldId(String formName,
                            String fieldName)
    throws DataAccessException {

    return getField(formName, fieldName).getId();
  }

  public String getFieldName(String formName,
                             Integer fieldId)
    throws DataAccessException {

    return getField(formName, fieldId).getName();
  }
  
  

  
  
  public ArsDataType getDataType(String formName,
                                String fieldName)
    throws DataAccessException {

    return getField(formName, fieldName).getDataType();
  }
  
  public ArsDataType getDataType(String formName,
                                Integer fieldId)
    throws DataAccessException {

    return getField(formName, fieldId).getDataType();
  }
  
  
  public Map<Integer, String> getEnums(String formName,
                                      String fieldName)
    throws DataAccessException {

    ArsField arsField = getField(formName, fieldName);
    if (!arsField.getDataType().equals(ArsDataType.ENUM)) {
      throw new InvalidDataAccessResourceUsageException(String.format("Field %s.%s is not an ENUM type",
                                                                      formName, fieldName));
      
    }
    return arsField.getEnums();
  }
  
  
  public Map<Integer, String> getEnums(String formName,
                                      Integer fieldId)
    throws DataAccessException {

    ArsField arsField = getField(formName, fieldId);
    if (!arsField.getDataType().equals(ArsDataType.ENUM)) {
      throw new InvalidDataAccessResourceUsageException(String.format("Field %s.%s is not an ENUM type",
                                                                      formName, fieldId.toString()));
    }
    return arsField.getEnums();
  }

  
  
  public String getEnumName(String formName,
                            Integer fieldId,
                            Integer enumNumber)
    throws DataAccessException {
    
    ArsField arsField = getField(formName, fieldId);
    if (enumNumber == null) {
      throw new InvalidFieldAccessException(arsField.getArField(),
                                            "Can not find enum name for null enum number");
    }

    Map<Integer, String> enums = getEnums(formName, fieldId);

    String enumName = enums.get(enumNumber);
    if (enumName == null) {
      throw new InvalidDataAccessResourceUsageException(String.format("Enum name for %s.%s.%s not found",
                                                                      formName, fieldId.toString(),
                                                                      enumNumber.toString()));
      
    }
    return enumName;
  }

  public String getEnumName(String formName,
                            String fieldName,
                            Integer enumNumber)
    throws DataAccessException {
    
    return getEnumName(formName, getField(formName, fieldName).getId(), enumNumber);
  }


  public Integer getEnumNumber(String formName,
                               String fieldName,
                               String enumName)
    throws DataAccessException {

    checkNotNull(enumName, "Enum name is null");
    Map<Integer, String> enums = getEnums(formName, fieldName);
    
    for (Map.Entry<Integer, String> mapEntry : enums.entrySet()) {
      if (Objects.equal(enumName, mapEntry.getValue())) {
        return mapEntry.getKey();
      }
    }

    throw new InvalidDataAccessResourceUsageException(String.format("Enum number for %s.%s.%s not found",
                                                                    formName, fieldName, enumName));
  }
  
  public Integer getEnumNumber(String formName,
                               Integer fieldId,
                               String enumName)
    throws DataAccessException {

    checkNotNull(enumName, "Enum name is null");
    Map<Integer, String> enums = getEnums(formName, fieldId);
    
    for (Map.Entry<Integer, String> mapEntry : enums.entrySet()) {
      if (Objects.equal(enumName, mapEntry.getValue())) {
        return mapEntry.getKey();
      }
    }

    throw new InvalidDataAccessResourceUsageException(String.format("Enum number for %s.%s.%s not found",
                                                                    formName, fieldId.toString(), enumName));
  }
  
  
  
  
  public QualifierInfo parseQualification(String formName,
                                          String qs)
    throws DataAccessException {
    return getTemplate().parseQualification(formName, qs);
  }
  
  
  
  
  
  public int getMaxLength(String formName,
                          String fieldName)
    throws DataAccessException {
    
    ArsField arsField = getField(formName, fieldName, ArsDataType.CHAR);
    return getMaxLengthCharacter(arsField.getArField());
  }
  
  public int getMaxLength(String formName,
                          Integer fieldId)
  throws DataAccessException {
    ArsField arsField = getField(formName, fieldId, ArsDataType.CHAR);
    return getMaxLengthCharacter(arsField.getArField());
  }

  private int getMaxLengthCharacter(Field field) {
    FieldLimit fl = field.getFieldLimit();
    if (!(fl instanceof CharacterFieldLimit)) {
      throw new InvalidDataAccessResourceUsageException("FieldLimit is not a CharacterFieldLimit");
    }

    CharacterFieldLimit cfl = (CharacterFieldLimit) fl;
    return cfl.getMaxLength();
  }
  
  
  
  
  

  public List<String> getEnumNames(String formName,
                                   String fieldName)
    throws DataAccessException {
    
    return getEnumNames(formName, getField(formName, fieldName).getId());
  }

    
  public List<String> getEnumNames(String formName,
                                   Integer fieldId)
    throws DataAccessException {
    
    ArsField arsField = getField(formName, fieldId);
    if (!arsField.getDataType().equals(ArsDataType.ENUM)) {
      throw new InvalidDataAccessResourceUsageException(String.format("Field %s.%s is not an ENUM type",
                                                                      formName, fieldId.toString()));
    }
    
    List<String> enumNames = ImmutableList.copyOf(arsField.getEnums().values());
    return enumNames;
  }



  
  
}
