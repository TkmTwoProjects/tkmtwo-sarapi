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
package com.tkmtwo.sarapi.interpolate;

import com.bmc.arsys.api.Value;
import com.tkmtwo.interpolate.MappingInterpolatorCallback;
import com.tkmtwo.sarapi.support.QueryStringValueFormatter;
import com.tkmtwo.sarapi.support.ValueFormatter;
import java.util.Map;
//import org.joda.time.format.DateTimeFormat;
//import org.joda.time.format.DateTimeFormatter;
//import org.springframework.dao.InvalidDataAccessApiUsageException;

/**
 *
 */
public class MappingQualifierCallback
  extends MappingInterpolatorCallback<Value> {
  
  
  
  ValueFormatter formatter;
  
  
  
  /**
   * Creates a new interpolator with an empty map.
   *
   */  
  public MappingQualifierCallback() {
    super();
    setFormatter(new QueryStringValueFormatter());
  }
  
  
  /**
   * Creates a new interpolator callback using the 
   * supplied mappings.
   *
   * @param m a map from which to copy entries
   */
  public MappingQualifierCallback(Map<String, Value> m) {
    super(m);
    setFormatter(new QueryStringValueFormatter());
  }
  
  
  /**
   * Creates a new interpolator callback using the 
   * supplied mappings in the supplied prefix.
   *
   * @param tokenPrefix
   * @param m a map from which to copy entries
   */
  public MappingQualifierCallback(String tokenPrefix,
                                  Map<String, Value> m) {
    super(tokenPrefix, m);
    setFormatter(new QueryStringValueFormatter());
  }
  
  
  public ValueFormatter getFormatter() { return formatter; }
  public void setFormatter(ValueFormatter vf) { formatter = vf; }
  
  protected String formatValue(Value value) {
    return getFormatter().formatValue(value);
  }
  
}
