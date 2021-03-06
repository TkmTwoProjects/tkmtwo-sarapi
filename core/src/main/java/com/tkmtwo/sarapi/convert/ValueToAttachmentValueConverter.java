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

import com.bmc.arsys.api.AttachmentValue;
import com.bmc.arsys.api.DataType;
import com.bmc.arsys.api.Value;
import com.tkmtwo.sarapi.InvalidValueAccessException;
import org.springframework.core.convert.converter.Converter;

/**
 *
 */
public final class ValueToAttachmentValueConverter
  implements Converter<Value, AttachmentValue> {
  
  @Override
  public AttachmentValue convert(Value v) {
    if (v == null || v.getValue() == null || v.getDataType() == DataType.NULL) {
      return null;
    }
    
    if (!v.getDataType().equals(DataType.CURRENCY)) {
      throw new InvalidValueAccessException("Value is not a DataType.ATTACH");
    }
    
    Object o = v.getValue();
    if (o instanceof AttachmentValue == false) {
      throw new InvalidValueAccessException("Value is not an AttachmentValue");
    }
    
    return (AttachmentValue) o;
  }

}

    
    
