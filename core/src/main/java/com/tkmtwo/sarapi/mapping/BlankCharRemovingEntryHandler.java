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
import com.google.common.collect.ImmutableList;
import com.tkmtwo.sarapi.ArsSchemaHelper;
import com.tkmtwo.sarapi.ArsTemplate;
import com.tkmtwo.sarapi.support.EntryUtil;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;


/**
 * Remove Entry entries where the Value is $NULL$.
 *
 *
 */
public class BlankCharRemovingEntryHandler
  extends FormMappingOperation
  implements EntryHandler {

  protected final Logger logger = LoggerFactory.getLogger(getClass());  

  private List<String> fieldNames;
  private List<Integer> fieldIds;
  
  public static final BlankCharRemovingEntryHandler ALL = BlankCharRemovingEntryHandler.of();
  
  public BlankCharRemovingEntryHandler() { }
  
  public BlankCharRemovingEntryHandler(FormMappingOperation fmo) {
    setTemplate(fmo.getTemplate());
    setConversionService(fmo.getConversionService());
    setSchemaHelper(fmo.getSchemaHelper());
    setFormName(fmo.getFormName());
  }
  public BlankCharRemovingEntryHandler(FormMappingOperation fmo,
                                  List<String> fns) {
    this(fmo);
    setFieldNames(fns);
  }
  
  public BlankCharRemovingEntryHandler(ArsTemplate at,
                                  ConversionService cs,
                                  ArsSchemaHelper ash,
                                  String fn) {
    setTemplate(at);
    setConversionService(cs);
    setSchemaHelper(ash);
    setFormName(fn);
  }
  public BlankCharRemovingEntryHandler(ArsTemplate at,
                                  ConversionService cs,
                                  ArsSchemaHelper ash,
                                  String fn,
                                  List<String> fns) {
    this(at, cs, ash, fn);
    setFieldNames(fns);
  }
  
  public static BlankCharRemovingEntryHandler of() {
    BlankCharRemovingEntryHandler nreh = new BlankCharRemovingEntryHandler();
    nreh.afterPropertiesSet();
    return nreh;
  }
  public static BlankCharRemovingEntryHandler of(List<Integer> fids) {
    BlankCharRemovingEntryHandler nreh = new BlankCharRemovingEntryHandler();
    nreh.setFieldIds(fids);
    nreh.afterPropertiesSet();
    return nreh;
  }
  
  public static BlankCharRemovingEntryHandler of(FormMappingOperation fmo) {
    BlankCharRemovingEntryHandler nreh = new BlankCharRemovingEntryHandler(fmo);
    nreh.afterPropertiesSet();
    return nreh;
  }
  
  public static BlankCharRemovingEntryHandler of(FormMappingOperation fmo,
                                            List<String> fns) {
    BlankCharRemovingEntryHandler nreh = new BlankCharRemovingEntryHandler(fmo, fns);
    nreh.afterPropertiesSet();
    return nreh;
  }
  
  public static BlankCharRemovingEntryHandler of(ArsTemplate at,
                                            ConversionService cs,
                                            ArsSchemaHelper ash,
                                            String fn) {
    BlankCharRemovingEntryHandler nreh = new BlankCharRemovingEntryHandler(at, cs, ash, fn);
    nreh.afterPropertiesSet();
    return nreh;
  }
  public static BlankCharRemovingEntryHandler of(ArsTemplate at,
                                            ConversionService cs,
                                            ArsSchemaHelper ash,
                                            String fn,
                                            List<String> fns) {
    BlankCharRemovingEntryHandler nreh = new BlankCharRemovingEntryHandler(at, cs, ash, fn, fns);
    nreh.afterPropertiesSet();
    return nreh;
  }
    
    

  
  public List<String> getFieldNames() {
    if (fieldNames == null) {
      fieldNames = new ArrayList<String>();
    }
    return fieldNames;
  }
  public void setFieldNames(List<String> l) { fieldNames = l; }
  
  
  
  
  private List<Integer> getFieldIds() {
    if (fieldIds == null) {
      fieldIds = ImmutableList.of();
    }
    return fieldIds;
  }
  private void setFieldIds(List<Integer> l) {
    if (l != null) {
      fieldIds = ImmutableList.copyOf(l);
    }
  }
  
  
  
  public void handleEntry(Entry entry) {
    checkNotNull(entry, "Need an entry");
    int sizeBefore = entry.size();
    
    if (getFieldIds().isEmpty()) {
      EntryUtil.removeBlankCharValues(entry);
    } else {
      EntryUtil.removeBlankCharValues(entry, getFieldIds());
    }
    
    int sizeAfter = entry.size();
    logger.trace("Removed {} entries from value map", String.valueOf(sizeBefore - sizeAfter));
  }
  

  
  public void afterPropertiesSet() {
    if (getFieldNames().isEmpty()) {
      return;
    }

    super.afterPropertiesSet();
    
    ImmutableList.Builder<Integer> ilb = new ImmutableList.Builder<Integer>();
    for (String fieldName : getFieldNames()) {
      if (fieldName == null) { continue; }
      ilb.add(getSchemaHelper().getFieldId(getFormName(), fieldName));
    }
    fieldIds = ilb.build();
    
  }
  
  
  
}
