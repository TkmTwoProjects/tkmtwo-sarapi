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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.bmc.arsys.api.Entry;
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
public class EntryCreate
  extends ArsFormOperation {

  //Field Names in play...  
  private List<String> fieldNames = ImmutableList.of();
  //Compiled Field Names in play...  
  private List<ArsField> fields = ImmutableList.of();

  private List<String> staticValues = ImmutableList.of();
  private List<Value> compiledStaticValues = ImmutableList.of();
  
  private static final List<Value> EMPTY_VALUE_LIST = ImmutableList.of();
  

  public EntryCreate() { }
  
  
  
  
  
  
  
  
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
  
  
  
  
  
  
  public String create() {
    return create(EMPTY_VALUE_LIST);
  }
  
  public String create(Value[] values) {
    checkArgument(values != null, "Need a Value[]");
    List<Value> l = ImmutableList.copyOf(values);
    return create(l);
  }
  
  public String create(List<Value> values)
    throws DataAccessException {
    
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
    return create(entry);
  }
  
  
  /* TODO(mahaffey): perhaps provide a create that does checking (already has id, all fields are coorect, etc */  
  public String create(Entry entry)
    throws DataAccessException {
    
    checkNotNull(entry, "Entry is null.");
    return getTemplate().createEntry(getFormName(), entry);
  }
  
  
  
  public String toString() {
    return Objects.toStringHelper(this)
      .add("arsUserSource", getTemplate().getUserSource())
      .add("formName", getFormName())
      .add("fieldNames", getFieldNames())
      .toString();
  }


  
}
