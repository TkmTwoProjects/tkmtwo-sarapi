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

import com.bmc.arsys.api.Value;
import com.tkmtwo.sarapi.convert.ArsConversionService;
import org.springframework.core.convert.ConversionService;



/**
 *
 * @param <T> object type
 */

public class ConversionValueMapper<T>
  implements ValueMapper<T> {
  
  private Class<T> requiredType;
  private ConversionService conversionService;


  private Class<T> getRequiredType() { return requiredType; }
  private void setRequiredType(Class<T> rt) { requiredType = rt; }

  private ConversionService getConversionService() {
    if (conversionService == null) {
      conversionService = new ArsConversionService();
    }
    return conversionService;
  }
  private void setConversionService(ConversionService cs) {
    conversionService = cs;
  }
  
  private ConversionValueMapper() { }
  public ConversionValueMapper(Class<T> rt) {
    requiredType = rt;
  }
  public ConversionValueMapper(Class<T> rt, ConversionService cs) {
    setRequiredType(rt);
    setConversionService(cs);
  }
  
  public T mapValue(Value v) {
    return getConversionService().convert(v, requiredType);
  }
  
}
