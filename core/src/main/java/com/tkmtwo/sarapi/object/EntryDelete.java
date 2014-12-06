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

import com.bmc.arsys.api.Entry;
import com.google.common.base.Objects;
import com.tkmtwo.sarapi.ArsTemplate;
import org.springframework.dao.DataAccessException;



/**
 *
 */
public class EntryDelete
  extends ArsFormOperation {

  
  public EntryDelete() { }
  public EntryDelete(ArsTemplate at, String fn) {
    setTemplate(at);
    setFormName(fn);
  }

  public void delete(Entry entry)
    throws DataAccessException {
    
    delete(checkNotNull(entry).getEntryId());
  }

        
  public void delete(String entryId)
    throws DataAccessException {
    
    getTemplate().deleteEntry(getFormName(), checkNotNull(entryId));
  }
  
  
  public String toString() {
    return Objects.toStringHelper(this)
      .add("arsUserSource", getTemplate().getUserSource())
      .add("formName", getFormName())
      .toString();
  }


  
}
