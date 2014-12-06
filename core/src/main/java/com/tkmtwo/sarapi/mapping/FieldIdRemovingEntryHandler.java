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
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;


/**
 *
 */
public class FieldIdRemovingEntryHandler
  implements EntryHandler, InitializingBean {

  protected final Logger logger = LoggerFactory.getLogger(getClass());  


  public static final FieldIdRemovingEntryHandler CORE = of(new int[] {
      Constants.AR_CORE_ENTRY_ID,
      Constants.AR_CORE_SUBMITTER,
      Constants.AR_CORE_CREATE_DATE,
      Constants.AR_CORE_ASSIGNED_TO,
      Constants.AR_CORE_LAST_MODIFIED_BY,
      Constants.AR_CORE_MODIFIED_DATE,
      Constants.AR_CORE_STATUS,
      Constants.AR_CORE_SHORT_DESCRIPTION
    });
  
  public static final FieldIdRemovingEntryHandler CORE_CREATE_ENTRY = of(new int[] {
      Constants.AR_CORE_ENTRY_ID,
      //Constants.AR_CORE_SUBMITTER,
      Constants.AR_CORE_CREATE_DATE,
      //Constants.AR_CORE_ASSIGNED_TO,
      Constants.AR_CORE_LAST_MODIFIED_BY,
      Constants.AR_CORE_MODIFIED_DATE,
      //Constants.AR_CORE_STATUS,
      //Constants.AR_CORE_SHORT_DESCRIPTION
    });
  
  public static final FieldIdRemovingEntryHandler CORE_SET_ENTRY = of(new int[] {
      Constants.AR_CORE_ENTRY_ID,
      Constants.AR_CORE_SUBMITTER,
      Constants.AR_CORE_CREATE_DATE,
      //Constants.AR_CORE_ASSIGNED_TO,
      Constants.AR_CORE_LAST_MODIFIED_BY,
      Constants.AR_CORE_MODIFIED_DATE,
      //Constants.AR_CORE_STATUS,
      //Constants.AR_CORE_SHORT_DESCRIPTION
    });
  
  public static final FieldIdRemovingEntryHandler CORE_MERGE_ENTRY = of(new int[] {
      //Constants.AR_CORE_ENTRY_ID,
      Constants.AR_CORE_SUBMITTER,
      Constants.AR_CORE_CREATE_DATE,
      //Constants.AR_CORE_ASSIGNED_TO,
      Constants.AR_CORE_LAST_MODIFIED_BY,
      Constants.AR_CORE_MODIFIED_DATE,
      //Constants.AR_CORE_STATUS,
      //Constants.AR_CORE_SHORT_DESCRIPTION
    });





  
  private List<Integer> fieldIds;
  
  public List<Integer> getFieldIds() { return fieldIds; }
  public void setFieldIds(List<Integer> l) { fieldIds = ImmutableList.copyOf(l); }
  
  
  ///
  ///
  ///

  public static FieldIdRemovingEntryHandler of(int[] is) {
    checkNotNull(is, "Need an array of fields ids");
    FieldIdRemovingEntryHandler fireh = new FieldIdRemovingEntryHandler();
    List<Integer> l = new ArrayList<>();
    for (int i : is) {
      l.add(Integer.valueOf(i));
    }
    fireh.setFieldIds(l);
    return fireh;
  }
      
  
  public void handleEntry(Entry entry) {
    for (Integer i : getFieldIds()) {
      if (entry.containsKey(i)) {
        logger.trace("Removing map entry with id {} and value {}", i, entry.get(i));
        entry.remove(i);
      }
    }
  }


  public void afterPropertiesSet() {
    checkNotNull(getFieldIds(), "Need a list of field ids");
  }
  
}
