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
package com.tkmtwo.sarapi.convert;

import com.bmc.arsys.api.DataType;
import com.bmc.arsys.api.Time;
import com.bmc.arsys.api.Value;
import com.tkmtwo.sarapi.InvalidValueAccessException;
import org.joda.time.LocalTime;
import org.springframework.core.convert.converter.Converter;

/**
 *
 */
public final class ValueToLocalTimeConverter
  implements Converter<Value, LocalTime> {
  
  @Override
  public LocalTime convert(Value v) {
    if (v == null || v.getValue() == null || v.getDataType() == DataType.NULL) {
      return null;
    }
    
    if (!v.getDataType().equals(DataType.TIME_OF_DAY)) {
      throw new InvalidValueAccessException("Value is not a DataType.TIME_OF_DAY");
    }
    
    Object o = v.getValue();
    if (o instanceof Time == false) {
      throw new InvalidValueAccessException("Value is not an Time");
    }
    
    Time t = (Time) o;
    return LocalTime.fromMillisOfDay(t.getValue() * 1000L);
  }

}

    
    
