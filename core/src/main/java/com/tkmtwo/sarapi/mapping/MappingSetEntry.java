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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


import com.bmc.arsys.api.Constants;
import com.bmc.arsys.api.Entry;
import com.bmc.arsys.api.Timestamp;
import com.google.common.collect.ImmutableList;
import java.util.List;
import org.joda.time.DateTime;


/**
 *
 * @param <T> object type
 */
public class MappingSetEntry<T>
  extends EntryMappingOperation<T> {

  
  
  public void setEntry(T objectSource) {
    setEntry(objectSource, new DateTime(0L), Constants.AR_JOIN_SETOPTION_NONE);
  }
  
  public void setEntry(Entry entry) {
    setEntry(entry, new DateTime(0L), Constants.AR_JOIN_SETOPTION_NONE);
  }
  
  
  public void setEntry(T objectSource,
                       DateTime dt,
                       int nOption) {
    
    
    Entry entry = getEntryMapper().mapObject(checkNotNull(objectSource, "Input object is null."));
    setEntry(entry, dt, nOption);
  }
  
  
  
  
  public void setEntry(Entry entry,
                       DateTime dt,
                       int nOption) {
    
    
    checkArgument(entry != null, "Entry is null");
    checkArgument(entry.getEntryId() != null, "Entry ID is null");
    
    //
    // When setting and entry, if we allow column 1 in the entry, the API will complain with:
    // WARNING (52): The field is a core system field and cannot be changed; 1
    //
    // So...we remove it here after we pull it out into a String for use later
    //
    
    String entryId = entry.getEntryId();
    entry.remove(Integer.valueOf(1));
    
    for (EntryHandler eh : getEntryHandlers()) {
      eh.handleEntry(entry);
    }
    
    getTemplate().setEntry(getFormName(),
                           entryId,
                           entry,
                           new Timestamp(dt.getMillis() / 1000L),
                           nOption);
  }
  
  public void afterPropertiesSet() {
    super.afterPropertiesSet();
    if (getEntryHandlers() == null) {
      logger.info("No EntryHandlers found, adding default handlers.");
      List<EntryHandler> l = ImmutableList.of((EntryHandler) FieldIdRemovingEntryHandler.CORE_SET_ENTRY,
                                              (EntryHandler) BlankCharRemovingEntryHandler.ALL,
                                              (EntryHandler) NullRemovingEntryHandler.ALL);
      setEntryHandlers(l);
    }
  }
  
  
}
