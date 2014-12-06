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

import com.bmc.arsys.api.Constants;
import com.bmc.arsys.api.Entry;
import com.bmc.arsys.api.OutputInteger;
import com.bmc.arsys.api.QualifierInfo;
import com.bmc.arsys.api.SortInfo;
import com.bmc.arsys.api.Value;
import com.google.common.collect.ImmutableList;
import com.tkmtwo.sarapi.NoneQualifierInfoCreator;
import com.tkmtwo.sarapi.QualifierInfoCreator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;


/**
 *
 * @param <T> object type
 */
public class ValueQuery<T>
  extends FormMappingOperation {
  
  private List<SortInfo> sortInfos = ImmutableList.of();
  private QualifierInfoCreator qualifierInfoCreator =  new NoneQualifierInfoCreator();
  private String fieldName;
  private int fieldId = -1;
  
  private int[] fieldIds = null;
  private ValueMapper<T> valueMapper;
  
  
  
  public ValueMapper<T> getValueMapper() { return valueMapper; }
  public void setValueMapper(ValueMapper<T> vm) { valueMapper = vm; }

  public QualifierInfoCreator getQualifierInfoCreator() { return qualifierInfoCreator; }
  public void setQualifierInfoCreator(QualifierInfoCreator qic) { qualifierInfoCreator = qic; }
  
  
  
  
  
  
  
  public List<SortInfo> getSortInfos() { return sortInfos; }
  public void setSortInfos(List<SortInfo> l) { sortInfos = ImmutableList.copyOf(l); }
  
  
  
  
  public String getFieldName() { return fieldName; }
  public void setFieldName(String s) { fieldName = s; }
  
  protected int getFieldId() {
    if (fieldId < 0) {
      fieldId = getSchemaHelper().getFieldId(getFormName(), getFieldName());
    }
    return fieldId;
  }
  public void setFieldId(int i) { fieldId = i; }

  private int[] getFieldIds() {
    if (fieldIds == null) {
      fieldIds = new int[] { getFieldId() };
    }
    
    return fieldIds;
  }
  
  
  
  public void afterPropertiesSet() {
    super.afterPropertiesSet();
    checkNotNull(getFieldName(), "Need a field name");
    checkNotNull(getValueMapper(), "Need a ValueMapper");
  }
  
  
  
  
  
  
  protected QualifierInfo getQualifierInfo(List<Value> values) {
    return getQualifierInfoCreator().createQualifierInfo(values);
  }
  protected QualifierInfo getQualifierInfo(Map<String, Value> values) {
    return getQualifierInfoCreator().createQualifierInfo(values);
  }
  
  
  ///////////////////////
  ///// Any number...
  ///////////////////////
  public List<T> getValues()
    throws DataAccessException {
    
    List<Value> l = ImmutableList.of();
    return getValues(l);
  }
  
  
  public List<T> getValues(int firstRetrieve, int maxRetrieve, OutputInteger numMatches)
    throws DataAccessException {
    
    List<Value> l = ImmutableList.of();
    return getValues(l, firstRetrieve, maxRetrieve, false, numMatches);
  }
  
  
  //
  // Using positional values
  //  
  public List<T> getValues(List<Value> values)
    throws DataAccessException {
    
    return getValues(values,
                     Constants.AR_START_WITH_FIRST_ENTRY,
                     Constants.AR_NO_MAX_LIST_RETRIEVE,
                     false,
                     new OutputInteger(0));
  }
  public List<T> getValues(List<Value> values,
                            int firstRetrieve,
                            int maxRetrieve,
                            boolean useLocale,
                            OutputInteger numMatches)
    throws DataAccessException {
    
    return getValues(getQualifierInfo(values),
                     firstRetrieve,
                     maxRetrieve,
                     useLocale,
                     numMatches);
  }
  
  
  //
  // Using named params in a Map<String, Value>
  //
  public List<T> getValues(Map<String, Value> values)
    throws DataAccessException {
    
    return getValues(values,
                     Constants.AR_START_WITH_FIRST_ENTRY,
                     Constants.AR_NO_MAX_LIST_RETRIEVE,
                     false,
                     new OutputInteger(0));
  }
  public List<T> getValues(Map<String, Value> values,
                           int firstRetrieve,
                           int maxRetrieve,
                           boolean useLocale,
                           OutputInteger numMatches)
    throws DataAccessException {
    
    return getValues(getQualifierInfo(values),
                     firstRetrieve,
                     maxRetrieve,
                     useLocale,
                     numMatches);
  }
  
  
  
  
  
  
  
  
  
  
  public List<T> getValues(QualifierInfo qi,
                           int firstRetrieve,
                           int maxRetrieve,
                           boolean useLocale,
                           OutputInteger numMatches)
    throws DataAccessException {
    
    List<Entry> es = getTemplate().getListEntryObjects(getFormName(),
                                                       qi,
                                                       firstRetrieve,
                                                       maxRetrieve,
                                                       getSortInfos(),
                                                       getFieldIds(),
                                                       useLocale,
                                                       numMatches);
    List<T> os = new ArrayList<>(es.size());
    for (Entry e : es) {
      os.add(getValueMapper().mapValue(e.get(getFieldId())));
    }
    
    return os;
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  /*  
  ///////////////////////
  ///// Exactly One...
  ///////////////////////
  public T getObject()
    throws DataAccessException {
    
    List<Value> l = ImmutableList.of();
    return getObject(l);
  }
  
  
  //
  // Using positional values
  //  
  public T getObject(List<Value> values)
    throws DataAccessException {
    
    return getObject(values,
                     Constants.AR_START_WITH_FIRST_ENTRY,
                     Constants.AR_NO_MAX_LIST_RETRIEVE,
                     false,
                     new OutputInteger(0));
  }
  public T getObject(List<Value> values,
                     int firstRetrieve,
                     int maxRetrieve,
                     boolean useLocale,
                     OutputInteger numMatches)
    throws DataAccessException {
    
    return getObject(getQualifierInfo(values),
                     firstRetrieve,
                     maxRetrieve,
                     useLocale,
                     numMatches);
  }
  
  
  //
  // Using named params in a Map<String, Value>
  //
  public T getObject(Map<String, Value> values)
    throws DataAccessException {
    
    return getObject(values,
                     Constants.AR_START_WITH_FIRST_ENTRY,
                     Constants.AR_NO_MAX_LIST_RETRIEVE,
                     false,
                     new OutputInteger(0));
  }
  public T getObject(Map<String, Value> values,
                     int firstRetrieve,
                     int maxRetrieve,
                     boolean useLocale,
                     OutputInteger numMatches)
    throws DataAccessException {
    
    return getObject(getQualifierInfo(values),
                     firstRetrieve,
                     maxRetrieve,
                     useLocale,
                     numMatches);
  }
  
  
  
  
  
  
  
  
  
  
  public T getObject(QualifierInfo qi,
                     int firstRetrieve,
                     int maxRetrieve,
                     boolean useLocale,
                     OutputInteger numMatches)
    throws DataAccessException {
    
    Entry e = getTemplate().getListEntryObject(getFormName(),
                                               qi,
                                               firstRetrieve,
                                               maxRetrieve,
                                               getSortInfos(),
                                               getFieldIds(),
                                               useLocale,
                                               numMatches);
    return getEntryMapper().mapEntry(e);
  }
  
  
  */  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  /*  
  ///////////////////////
  ///// One or null...
  ///////////////////////
  public T getObjectNullable()
    throws DataAccessException {
    
    List<Value> l = ImmutableList.of();
    return getObjectNullable(l);
  }
  
  
  //
  // Using positional values
  //  
  public T getObjectNullable(List<Value> values)
    throws DataAccessException {
    
    return getObjectNullable(values,
                             Constants.AR_START_WITH_FIRST_ENTRY,
                             Constants.AR_NO_MAX_LIST_RETRIEVE,
                             false,
                             new OutputInteger(0));
  }
  public T getObjectNullable(List<Value> values,
                             int firstRetrieve,
                             int maxRetrieve,
                             boolean useLocale,
                             OutputInteger numMatches)
    throws DataAccessException {
    
    return getObjectNullable(getQualifierInfo(values),
                             firstRetrieve,
                             maxRetrieve,
                             useLocale,
                             numMatches);
  }
  
  
  //
  // Using named params in a Map<String, Value>
  //
  public T getObjectNullable(Map<String, Value> values)
    throws DataAccessException {
    
    return getObjectNullable(values,
                             Constants.AR_START_WITH_FIRST_ENTRY,
                             Constants.AR_NO_MAX_LIST_RETRIEVE,
                             false,
                             new OutputInteger(0));
  }
  public T getObjectNullable(Map<String, Value> values,
                             int firstRetrieve,
                             int maxRetrieve,
                             boolean useLocale,
                             OutputInteger numMatches)
    throws DataAccessException {
    
    return getObjectNullable(getQualifierInfo(values),
                             firstRetrieve,
                             maxRetrieve,
                             useLocale,
                             numMatches);
  }
  
  
  
  
  
  
  
  
  
  
  public T getObjectNullable(QualifierInfo qi,
                             int firstRetrieve,
                             int maxRetrieve,
                             boolean useLocale,
                             OutputInteger numMatches)
    throws DataAccessException {
    
    Entry e = getTemplate().getListEntryObjectNullable(getFormName(),
                                                       qi,
                                                       firstRetrieve,
                                                       maxRetrieve,
                                                       getSortInfos(),
                                                       getFieldIds(),
                                                       useLocale,
                                                       numMatches);
    return getEntryMapper().mapEntry(e);
  }
  */  
  
  
}
