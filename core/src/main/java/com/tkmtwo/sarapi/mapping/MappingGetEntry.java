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
import org.springframework.dao.DataAccessException;


/**
 *
 * @param <T> object type
 */
public class MappingGetEntry<T>
  extends EntryMappingOperation<T> {
  
  private int[] fieldIds = null;
  
  
  
  
  protected int[] getFieldIds() {
    if (fieldIds == null || fieldIds.length == 0) {
      fieldIds = getSchemaHelper().compileFieldIdsByName(getFormName(), getEntryMapper().getMappedFieldNames());
    }
    return fieldIds;
  }
  protected void setFieldIds(int[] is) { fieldIds = is; }
  
  
  
  
  public T getEntry(String entryId)
    throws DataAccessException {
    Entry entry = getTemplate().getEntry(getFormName(), entryId, getFieldIds());
    return getEntryMapper().mapEntry(entry);
  }

}
