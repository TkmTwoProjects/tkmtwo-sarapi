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
public class MappingQuery<T>
  extends EntryMappingOperation<T> {
  
  private List<SortInfo> sortInfos = ImmutableList.of();
  private QualifierInfoCreator qualifierInfoCreator =  new NoneQualifierInfoCreator();
  private int[] fieldIds = null;
  
  
  
  
  public QualifierInfoCreator getQualifierInfoCreator() { return qualifierInfoCreator; }
  public void setQualifierInfoCreator(QualifierInfoCreator qic) { qualifierInfoCreator = qic; }
  
  
  
  
  
  
  public List<SortInfo> getSortInfos() { return sortInfos; }
  public void setSortInfos(List<SortInfo> l) { sortInfos = ImmutableList.copyOf(l); }
  
  
  
  
  
  
  protected int[] getFieldIds() {
    if (fieldIds == null || fieldIds.length == 0) {
      fieldIds = getSchemaHelper().compileFieldIdsByName(getFormName(), getEntryMapper().getMappedFieldNames());
    }
    return fieldIds;
  }
  protected void setFieldIds(int[] is) { fieldIds = is; }
  
  
  
  
  
  
  
  
  
  
  
  protected QualifierInfo getQualifierInfo(List<Value> values) {
    return getQualifierInfoCreator().createQualifierInfo(values);
  }
  protected QualifierInfo getQualifierInfo(Map<String, Value> values) {
    return getQualifierInfoCreator().createQualifierInfo(values);
  }
  
  
  ///////////////////////
  ///// Any number...
  ///////////////////////
  public List<T> getObjects()
    throws DataAccessException {
    
    List<Value> l = ImmutableList.of();
    return getObjects(l);
  }
  
  
  public List<T> getObjects(int firstRetrieve, int maxRetrieve, OutputInteger numMatches)
    throws DataAccessException {
    
    List<Value> l = ImmutableList.of();
    return getObjects(l, firstRetrieve, maxRetrieve, false, numMatches);
  }
  
  
  //
  // Using positional values
  //  
  public List<T> getObjects(List<Value> values)
    throws DataAccessException {
    
    return getObjects(values,
                      Constants.AR_START_WITH_FIRST_ENTRY,
                      Constants.AR_NO_MAX_LIST_RETRIEVE,
                      false,
                      new OutputInteger(0));
  }
  public List<T> getObjects(List<Value> values,
                            int firstRetrieve,
                            int maxRetrieve,
                            boolean useLocale,
                            OutputInteger numMatches)
    throws DataAccessException {
    
    return getObjects(getQualifierInfo(values),
                      firstRetrieve,
                      maxRetrieve,
                      useLocale,
                      numMatches);
  }
  
  
  //
  // Using named params in a Map<String, Value>
  //
  public List<T> getObjects(Map<String, Value> values)
    throws DataAccessException {
    
    return getObjects(values,
                      Constants.AR_START_WITH_FIRST_ENTRY,
                      Constants.AR_NO_MAX_LIST_RETRIEVE,
                      false,
                      new OutputInteger(0));
  }
  public List<T> getObjects(Map<String, Value> values,
                            int firstRetrieve,
                            int maxRetrieve,
                            boolean useLocale,
                            OutputInteger numMatches)
    throws DataAccessException {
    
    return getObjects(getQualifierInfo(values),
                      firstRetrieve,
                      maxRetrieve,
                      useLocale,
                      numMatches);
  }
  
  
  
  
  
  
  
  
  
  
  public List<T> getObjects(QualifierInfo qi,
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
      os.add(getEntryMapper().mapEntry(e));
    }
    
    return os;
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
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
  
  
  
  
}
