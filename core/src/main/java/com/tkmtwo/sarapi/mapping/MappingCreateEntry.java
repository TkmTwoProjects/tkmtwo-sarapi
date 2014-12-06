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
import com.google.common.collect.ImmutableList;
import com.tkmtwo.sarapi.support.EntryUtil;
import org.springframework.dao.DataAccessException;


/**
 *
 * @param <T> object type
 */
public class MappingCreateEntry<T>
  extends EntryMappingOperation<T> {
  

  public String createEntry(Entry entry) {
    for (EntryHandler eh : getEntryHandlers()) {
      eh.handleEntry(entry);
    }

    logger.trace("Creating entry for {}", EntryUtil.entryToString(entry, getSchemaHelper(), getFormName()));
    String id = getTemplate().createEntry(getFormName(), entry);
    return id;
  }
  public String createEntry(T objectSource)
    throws DataAccessException {
    
    Entry entry = getEntryMapper().mapObject(objectSource);
    return createEntry(entry);
  }
  
  
  
  public void afterPropertiesSet() {
    super.afterPropertiesSet();
    if (getEntryHandlers() == null) {
      logger.info("No EntryHandlers found, adding default handlers.");
      setEntryHandlers(ImmutableList.of((EntryHandler) FieldIdRemovingEntryHandler.CORE_CREATE_ENTRY,
                                        (EntryHandler) BlankCharRemovingEntryHandler.ALL,
                                        (EntryHandler) NullRemovingEntryHandler.ALL));

    }
  }
  
  

  
}
