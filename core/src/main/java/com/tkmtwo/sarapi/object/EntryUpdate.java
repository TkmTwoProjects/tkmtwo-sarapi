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
package com.tkmtwo.sarapi.object;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.bmc.arsys.api.Constants;
import com.bmc.arsys.api.Entry;
import com.bmc.arsys.api.Timestamp;
import com.bmc.arsys.api.Value;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.tkmtwo.sarapi.ArsField;
import com.tkmtwo.sarapi.support.EntryUtil;
import com.tkmtwo.sarapi.support.ValueUtil;
import java.util.List;
import org.springframework.dao.DataAccessException;


/**
 *
 */
public class EntryUpdate
  extends ArsFormOperation {
  
  //private List<String> staticStringValues;
  //private Value[] compiledStaticValues = new Value[0];

  private List<String> fieldNames = ImmutableList.of();
  private List<ArsField> fields = ImmutableList.of();
  private List<String> staticValues = ImmutableList.of();
  private List<Value> compiledStaticValues = ImmutableList.of();
  
  private static final List<Value> EMPTY_VALUE_LIST = ImmutableList.of();

  


  
  
  
  
  public EntryUpdate() { ; }

  
  
  public List<String> getFieldNames() { return fieldNames; }
  public void setFieldNames(List<String> l) { fieldNames = ImmutableList.copyOf(l); }
  
  protected List<ArsField> getFields() { return fields; }
  protected void setFields(List<ArsField> l) { fields = ImmutableList.copyOf(l); }

  public List<String> getStaticValues() { return staticValues; }
  public void setStaticValues(List<String> l) { staticValues = ImmutableList.copyOf(l); }
  
  private List<Value> getCompiledStaticValues() { return compiledStaticValues; }
  private void setCompiledStaticValues(List<Value> l) { compiledStaticValues = ImmutableList.copyOf(l); }
  
  
  
  
  
  
  
  
  
  
  
  
  public void afterPropertiesSet() {
    super.afterPropertiesSet();
    
    checkState(!getFieldNames().isEmpty(), "Need some field names.");
    
    setFields(getSchemaHelper().compileFieldsByName(getFormName(), getFieldNames()));
    setCompiledStaticValues(ValueUtil.compileValues(getFields(),
                                                    getStaticValues()));
    
  }
  
  
  
  
  
  
  
  
  
  
  
  
  public void setEntry(String entryId) {
    setEntry(entryId, EMPTY_VALUE_LIST);
  }
  public void setEntry(String entryId,
                       Value[] values)
    throws DataAccessException {

    List<Value> l = ImmutableList.copyOf(values);
    setEntry(entryId, l);
  }


  public void setEntry(String entryId,
                       List<Value> values) {
    checkNotNull(values, "Need a List<Value>.");
    
    List<Value> allValues = null;
    
    if (getStaticValues().isEmpty()) {
      logger.trace("No static values.");
      allValues = values;
    } else {
      logger.trace("Adding {} static values.", String.valueOf(getCompiledStaticValues().size()));
      allValues = Lists.newArrayList();
      allValues.addAll(getCompiledStaticValues());
      logger.trace("Adding {} dynamic values", String.valueOf(values.size()));
      allValues.addAll(values);
    }
    
    Entry entry = EntryUtil.newEntry(getFields(), allValues);
    setEntry(entryId, entry);
  }


  public void setEntry(String entryId,
                       Entry entry)
    throws DataAccessException {
    
    setEntry(entryId,
             entry,
             new Timestamp(0),
             Constants.AR_JOIN_SETOPTION_NONE);
  }
  public void setEntry(String entryId,
                       Entry entry,
                       Timestamp timeStamp,
                       int nOption) {
    getTemplate().setEntry(getFormName(),
                           entryId,
                           entry,
                           timeStamp,
                           nOption);
  }
  
  
  
  
  
  
  
  
  
  
  
  
  public void mergeEntry(String entryId) {
    mergeEntry(entryId, EMPTY_VALUE_LIST);
  }
  public void mergeEntry(String entryId,
                         Value[] values)
    throws DataAccessException {

    List<Value> l = ImmutableList.copyOf(values);
    mergeEntry(entryId, l);
  }


  public void mergeEntry(String entryId,
                         List<Value> values) {
    checkNotNull(values, "Need a List<Value>.");
    
    List<Value> allValues = null;
    
    if (getStaticValues().isEmpty()) {
      logger.trace("No static values.");
      allValues = values;
    } else {
      logger.trace("Adding {} static values.", String.valueOf(getCompiledStaticValues().size()));
      allValues = Lists.newArrayList();
      allValues.addAll(getCompiledStaticValues());
      logger.trace("Adding {} dynamic values", String.valueOf(values.size()));
      allValues.addAll(values);
    }
    
    Entry entry = EntryUtil.newEntry(getFields(), allValues);
    mergeEntry(entryId, entry);
  }
  
  
  
  
  public String mergeEntry(String entryId,
                           Entry entry)
    throws DataAccessException {
    
    return mergeEntry(entryId,
                      entry,
                      Constants.AR_MERGE_ENTRY_DUP_MERGE);
  }
  
  public String mergeEntry(String entryId,
                           Entry entry,
                           int mergeType) {
    if (entryId != null && !entryId.isEmpty() && !entryId.trim().isEmpty()) {
      entry.setEntryId(entryId);
    }
    return getTemplate().mergeEntry(getFormName(),
                                    entry,
                                    mergeType);
  }
  
  
  
  
  
  
  
  
  
  public String toString() {
    return Objects.toStringHelper(this)
      .add("arsUserSource", getTemplate().getUserSource())
      .add("formName", getFormName())
      .add("fieldNames", getFieldNames())
      .toString();
  }
  
  
}
