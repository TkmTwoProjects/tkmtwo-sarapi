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

import com.bmc.arsys.api.Value;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.core.convert.support.GenericConversionService;

/**
 *
 */
public class ArsConversionService
  extends GenericConversionService {
  
  public ArsConversionService() {
    addArsConverters(this);
  }
    
    

  public static void addArsConverters(ConverterRegistry converterRegistry) {

    converterRegistry.addConverter(new IntegerToValueConverter());
    converterRegistry.addConverter(new ValueToIntegerConverter());

    converterRegistry.addConverter(new DoubleToValueConverter());
    converterRegistry.addConverter(new ValueToDoubleConverter());

    converterRegistry.addConverter(new StringToValueConverter());
    converterRegistry.addConverter(new ValueToStringConverter());

    converterRegistry.addConverter(new DiaryListValueToValueConverter());
    converterRegistry.addConverter(new ValueToDiaryListValueConverter());

    converterRegistry.addConverter(new TimestampToValueConverter());
    converterRegistry.addConverter(new ValueToTimestampConverter());

    converterRegistry.addConverter(new DateTimeToValueConverter());
    converterRegistry.addConverter(new ValueToDateTimeConverter());

    converterRegistry.addConverter(new BigDecimalToValueConverter());
    converterRegistry.addConverter(new ValueToBigDecimalConverter());

    converterRegistry.addConverter(new CurrencyValueToValueConverter());
    converterRegistry.addConverter(new ValueToCurrencyValueConverter());

    converterRegistry.addConverter(new DateInfoToValueConverter());
    converterRegistry.addConverter(new ValueToDateInfoConverter());

    converterRegistry.addConverter(new LocalDateToValueConverter());
    converterRegistry.addConverter(new ValueToLocalDateConverter());

    converterRegistry.addConverter(new TimeToValueConverter());
    converterRegistry.addConverter(new ValueToTimeConverter());

    converterRegistry.addConverter(new LocalTimeToValueConverter());
    converterRegistry.addConverter(new ValueToLocalTimeConverter());

    converterRegistry.addConverter(new WallClockToValueConverter());
    converterRegistry.addConverter(new ValueToWallClockConverter());

    converterRegistry.addConverter(new AttachmentValueToValueConverter());
    converterRegistry.addConverter(new ValueToAttachmentValueConverter());
  }
  

  protected Object convertNullSource(TypeDescriptor sourceType, TypeDescriptor targetType) {
    if (targetType.getType().equals(Value.class)) {
      return new Value();
    }
    return null;
  }


}

    
    
