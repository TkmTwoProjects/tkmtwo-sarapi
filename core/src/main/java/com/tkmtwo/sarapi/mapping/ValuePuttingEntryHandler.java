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
import com.google.common.collect.ImmutableMap;
import com.tkmtwo.sarapi.ArsField;
import java.util.Map;


/**
 *
 */
public class ValuePuttingEntryHandler
  extends FormMappingOperation
  implements EntryHandler {
  
  private Map<String, Value> staticValues = ImmutableMap.of();
  private Map<Integer, Value> compiledValues = ImmutableMap.of();
  
  
  
  public Map<String, Value> getStaticValues() { return staticValues; }
  public void setStaticValues(Map<String, Value> m) { staticValues = ImmutableMap.copyOf(m); }
  
  public void setStaticValueStrings(Map<String, String> m) {
    checkNotNull(m, "Map of values is null.");
    
    ImmutableMap.Builder<String, Value> vb = new ImmutableMap.Builder<String, Value>();
    for (Map.Entry<String, String> me : m.entrySet()) {
      ArsField af = getSchemaHelper().getField(getFormName(), me.getKey());
      vb.put(me.getKey(),
             af.getDataType().getArValue(me.getValue()));
    }
    
    staticValues = vb.build();
  }
  
  
  
  
  protected Map<Integer, Value> getCompiledValues() { return compiledValues; }
  protected void setCompiledValues(Map<Integer, Value> m) { compiledValues = ImmutableMap.copyOf(m); }
  
  
  
  protected void compileStaticValues() {
    ImmutableMap.Builder<Integer, Value> imb = new ImmutableMap.Builder<Integer, Value>();
    for (Map.Entry<String, Value> me : getStaticValues().entrySet()) {
      ArsField af = getSchemaHelper().getField(getFormName(), me.getKey());
      imb.put(af.getId(), me.getValue());
    }
    compiledValues = imb.build();
  }
  
  
  
  
  protected void applyStaticValues(Entry entry) {
    if (getStaticValues() == null || getStaticValues().isEmpty()) { return; }
    
    for (Map.Entry<Integer, Value> me : getCompiledValues().entrySet()) {
      logger.trace("Applying static value '{}' to field id '{}'", me.getKey(), me.getValue());
      entry.put(me.getKey(), me.getValue());
    }
  }
  
  
  
  public void handleEntry(Entry entry) {
    applyStaticValues(entry);
  }
  
  
  public void afterPropertiesSet() {
    super.afterPropertiesSet();
    compileStaticValues();
  }
  
  
  
}
