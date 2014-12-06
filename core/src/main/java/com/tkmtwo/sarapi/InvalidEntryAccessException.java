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
package com.tkmtwo.sarapi;


import com.bmc.arsys.api.Entry;
import com.bmc.arsys.api.Field;
import org.springframework.dao.InvalidDataAccessResourceUsageException;


/**
 *
 */
public class InvalidEntryAccessException
  extends InvalidDataAccessResourceUsageException {
  
  public InvalidEntryAccessException(Entry entry,
                                     Integer fieldId,
                                     InvalidValueAccessException ivae) {
    super(String.format("Invalid value access for entry with id '%s' and field id '%s'",
                        entry.getEntryId(),
                        fieldId.toString()),
          ivae);
  }
  
  

  public InvalidEntryAccessException(Entry  entry,
                                     Field field,
                                     String message) {

    super(String.format("Entry with id %s and field %s/%s: %s",
                        entry.getEntryId(),
                        String.valueOf(field.getFieldID()),
                        field.getName(),
                        message),
          new IllegalArgumentException(message));
    

  }
  
  
  
  public InvalidEntryAccessException(Entry  entry,
                                     String message) {
    super(String.format("Entry with id %s: %s.",
                        entry.getEntryId(),
                        message));
  }


  public InvalidEntryAccessException(String task,
                                     String message) {
    super(task
          + ": "
          + message,
          new IllegalArgumentException(message));
  }
  
  
  public InvalidEntryAccessException(String task,
                                     Field field,
                                     String message) {
    super(task
          + ": "
          + "Field "
          + String.valueOf(field.getFieldID())
          + field.getName()
          + ": "
          + message,
          new IllegalArgumentException(message));
  }
  
  

  
}

