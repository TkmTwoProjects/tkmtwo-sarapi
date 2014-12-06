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

import com.google.common.collect.ImmutableList;
import java.util.List;


/**
 *
 * @param <T> object type
 */
public class EntryMappingOperation<T>
  extends FormMappingOperation {
  
  private EntryMapper<T> entryMapper;
  private List<EntryHandler> entryHandlers = null;


  public EntryMapper<T> getEntryMapper() { return entryMapper; }
  public void setEntryMapper(EntryMapper<T> em) { entryMapper = em; }
  
  public List<EntryHandler> getEntryHandlers() { return entryHandlers; }
  public void setEntryHandlers(List<EntryHandler> l) { entryHandlers = ImmutableList.copyOf(l); }
  public void addEntryHandler(EntryHandler eh) {
    ImmutableList.Builder<EntryHandler> ilb = new ImmutableList.Builder<EntryHandler>()
      .addAll(getEntryHandlers())
      .add(eh);
    entryHandlers = ilb.build();
  }
  
  
  public void afterPropertiesSet() {
    super.afterPropertiesSet();
    checkNotNull(getEntryMapper(), "Need an EntryMapper");
  }
  

  
}
