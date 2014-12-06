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
package com.tkmtwo.sarapi.mapping;

import com.bmc.arsys.api.Entry;
import com.bmc.arsys.api.Value;
import com.tkmtwo.sarapi.ArsDataType;
import com.tkmtwo.sarapi.ArsField;
import com.tkmtwo.sarapi.InvalidEntryAccessException;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;



/**
 *
 * @param <T> object type
 */
public abstract class AbstractEntryMapper<T>
  extends FormMappingOperation
  implements EntryMapper<T> {
  
  public abstract Entry mapObject(T source)
    throws InvalidEntryAccessException;
  public abstract T mapEntry(Entry entry)
    throws InvalidEntryAccessException;

  public abstract Set<String> getMappedFieldNames();
  protected abstract Map<String, String> propertyToFieldNames();
  

  protected BeanWrapper newBeanWrapper(Object target) {
    BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(target);
    bw.setConversionService(getConversionService());
    return bw;
  }
  
  
  
  
  protected ArsField getField(String fieldName) {
    return getSchemaHelper().getField(getFormName(), fieldName);
  }
  protected Integer getFieldId(String fieldName) {
    return getField(fieldName).getId();
  }


  protected void setPropertyValues(BeanWrapper bw,
                                   Entry entry,
                                   Map<String, String> m) {
    for (Map.Entry<String, String> me : m.entrySet()) {
      String propName = me.getKey();
      String fieldName = me.getValue();
      
      ArsField field = getField(fieldName);
      Value value = entry.get(field.getId());
      Class propClass = bw.getPropertyType(propName);
      
      if (field.getDataType().equals(ArsDataType.ENUM)) {
        
        if (propClass.equals(String.class)) {
          logger.trace("Performing num2name swap for field {}", field.getName());
          Integer enumNumber = getConversionService().convert(value, Integer.class);
          String enumName = null;
          
          if (enumNumber == null) {
            bw.setPropertyValue(propName, new Value());
          } else {
            enumName = getSchemaHelper().getEnumName(getFormName(), field.getName(), enumNumber);
            bw.setPropertyValue(propName, enumName);
          }
          logger.trace("Performed num2name swap for field '{}' number '{}' to name '{}'",
                       field.getName(), enumNumber, enumName);
          continue;
        }
        
      }
      
      bw.setPropertyValue(propName, value);
    }
  }

  protected void setEntryValues(BeanWrapper bw,
                                Entry entry,
                                Map<String, String> m) {
    for (Map.Entry<String, String> me : m.entrySet()) {
      String propName = me.getKey();
      String fieldName = me.getValue();
      
      ArsField field = getField(fieldName);
      Value value = bw.getConversionService().convert(bw.getPropertyValue(propName),
                                                      Value.class);
      Class propClass = bw.getPropertyType(propName);

      if (field.getDataType().equals(ArsDataType.ENUM)) {
        
        if (propClass.equals(String.class)) {
          logger.trace("Performing name2num swap for field {}", field.getName());
          Integer enumNumber = null;
          String enumName = getConversionService().convert(value, String.class);
          
          if (enumName == null) {
            entry.put(field.getId(), new Value());
          } else {
            enumNumber = getSchemaHelper().getEnumNumber(getFormName(), field.getName(), enumName);
            entry.put(field.getId(), new Value(enumNumber));
          }
          logger.trace("Performed name2num swap for field '{}' name '{}' to number '{}'",
                       field.getName(), enumName, enumNumber);
          continue;
        }
        
      }
      
      entry.put(field.getId(), (value == null ? new Value() : value));
    }
    
  }
  
    
  
                        
  
  public void afterPropertiesSet() {
    super.afterPropertiesSet();
  }
  
}
