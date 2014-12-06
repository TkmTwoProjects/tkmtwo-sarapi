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

import static com.google.common.base.Preconditions.checkNotNull;

import com.bmc.arsys.api.Entry;
import com.bmc.arsys.api.Value;
import com.google.common.collect.ImmutableSet;
import com.tkmtwo.sarapi.ArsSchemaHelper;
import com.tkmtwo.sarapi.ArsTemplate;
import com.tkmtwo.sarapi.InvalidEntryAccessException;
import java.util.Set;
import org.springframework.core.convert.ConversionService;



/**
 *
 * @param <T> object type
 */
public class SingleValueEntryMapper<T>
  extends FormMappingOperation
  implements EntryMapper<T> {
  
  
  
    
  
  
  private Class<T> requiredType;
  private String fieldName;
  private Integer fieldId;
  
  private Set<String> mappedFieldNames;
  
  public static <T> EntryMapper<T> forClass(ArsTemplate at,
                                            ConversionService cs,
                                            ArsSchemaHelper ash,
                                            String formName,
                                            String fieldName,
                                            Class<T> rt) {
    SingleValueEntryMapper<T> svem = new SingleValueEntryMapper<T>();
    svem.setTemplate(at);
    svem.setConversionService(cs);
    svem.setSchemaHelper(ash);
    svem.setFormName(formName);
    svem.setFieldName(fieldName);
    svem.setRequiredType(rt);
    svem.afterPropertiesSet();
    return svem;
  }
  
  
  
  
  public static EntryMapper<String> forString(ArsTemplate at,
                                              ConversionService cs,
                                              ArsSchemaHelper ash,
                                              String formName,
                                              String fieldName) {
    SingleValueEntryMapper<String> svem = new SingleValueEntryMapper<String>();
    svem.setTemplate(at);
    svem.setConversionService(cs);
    svem.setSchemaHelper(ash);
    svem.setFormName(formName);
    svem.setFieldName(fieldName);
    svem.setRequiredType(String.class);
    svem.afterPropertiesSet();
    return svem;
  }
  
  
  
  
  public void afterPropertiesSet() {
    super.afterPropertiesSet();
    checkNotNull(getFieldName(), "Need a field name");
    checkNotNull(getRequiredType(), "Need a required type");
    checkNotNull(getFieldId(), "Need a field id");
    checkNotNull(getMappedFieldNames(), "Need some mapped field names");
  }
  
  
  public Class<T> getRequiredType() { return requiredType; }
  public void setRequiredType(Class<T> rt) { requiredType = rt; }

  public String getFieldName() { return fieldName; }
  public void setFieldName(String s) { fieldName = s; }
  
  private Integer getFieldId() {
    if (fieldId == null) {
      fieldId = getSchemaHelper().getField(getFormName(), getFieldName()).getId();
    }
    return fieldId;
  }

  
  
  
  public Entry mapObject(T source)
    throws InvalidEntryAccessException {
    Entry entry = new Entry();
    entry.put(getFieldId(),
              getConversionService().convert(source, Value.class));
    return entry;
  }

  public T mapEntry(Entry entry)
    throws InvalidEntryAccessException {
    return getConversionService().convert(entry.get(getFieldId()),
                                          requiredType);
  }
    

  public Set<String> getMappedFieldNames() {
    if (mappedFieldNames == null) {
      mappedFieldNames = ImmutableSet.of(getFieldName());
    }
    return mappedFieldNames;
  }
  
  
                        
  
  
}
