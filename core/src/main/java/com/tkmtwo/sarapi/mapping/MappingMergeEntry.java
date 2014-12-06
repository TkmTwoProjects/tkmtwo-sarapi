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
import com.google.common.collect.ImmutableList;
import java.util.List;


/**
 *
 * @param <T> object type
 */
public class MappingMergeEntry<T>
  extends EntryMappingOperation<T> {
  
  
  
  public String mergeEntry(T objectSource) {
    return mergeEntry(objectSource, Constants.AR_MERGE_ENTRY_DUP_MERGE);
  }
  
  public String mergeEntry(Entry entry) {
    return mergeEntry(entry, Constants.AR_MERGE_ENTRY_DUP_MERGE);
  }
  
  public String mergeEntry(T objectSource,
                           int mergeType) {
    Entry entry = getEntryMapper().mapObject(objectSource);
    return mergeEntry(entry, mergeType);
  }


  public String mergeEntry(Entry entry,
                           int mergeType) {
    
    for (EntryHandler eh : getEntryHandlers()) {
      eh.handleEntry(entry);
    }
    
    return getTemplate().mergeEntry(getFormName(),
                                    entry,
                                    mergeType);
  }
  
  
  
  
  public void afterPropertiesSet() {
    super.afterPropertiesSet();
    if (getEntryHandlers() == null) {
      logger.info("No EntryHandlers found, adding default handlers.");
      List<EntryHandler> l = ImmutableList.of((EntryHandler) FieldIdRemovingEntryHandler.CORE_MERGE_ENTRY,
                                              (EntryHandler) BlankCharRemovingEntryHandler.ALL,
                                              (EntryHandler) NullRemovingEntryHandler.ALL);
      setEntryHandlers(l);
    }
  }
  
  
  
}
