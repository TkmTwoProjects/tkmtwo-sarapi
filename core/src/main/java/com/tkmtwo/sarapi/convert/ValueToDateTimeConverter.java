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
import com.bmc.arsys.api.Timestamp;
import com.bmc.arsys.api.Value;
import com.tkmtwo.sarapi.InvalidValueAccessException;
import org.joda.time.DateTime;
import org.springframework.core.convert.converter.Converter;

/**
 *
 */
public final class ValueToDateTimeConverter
  implements Converter<Value, DateTime> {
  
  @Override
  public DateTime convert(Value v) {
    if (v == null || v.getValue() == null || v.getDataType() == DataType.NULL) {
      return null;
    }
    
    if (!v.getDataType().equals(DataType.TIME)) {
      throw new InvalidValueAccessException("Value is not a DataType.TIME");
    }
    
    Object o = v.getValue();
    if (o instanceof Timestamp == false) {
      throw new InvalidValueAccessException("Value is not a Timestamp");
    }
    
    Timestamp t = (Timestamp) o;
    return new DateTime(t.getValue() * 1000L);
  }

}

    
    
