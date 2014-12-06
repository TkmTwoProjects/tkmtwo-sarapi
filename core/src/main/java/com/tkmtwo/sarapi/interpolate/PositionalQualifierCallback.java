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
import com.tkmtwo.interpolate.PositionalInterpolatorCallback;
import com.tkmtwo.sarapi.support.QueryStringValueFormatter;
import com.tkmtwo.sarapi.support.ValueFormatter;
import java.util.List;


/**
 *
 */
public class PositionalQualifierCallback
  extends PositionalInterpolatorCallback<Value> {
  
  
  ValueFormatter formatter;
  
  /**
   * Creates a new <code>PositionalInterpolatorCallback</code> instance.
   *
   * @param vs a List of <code>T</code> values
   */
  public PositionalQualifierCallback(List<Value> vs) {
    super(vs);
    setFormatter(new QueryStringValueFormatter());
  }



  /**
   * Creates a new <code>PositionalInterpolatorCallback</code> instance.
   *
   * @param tokenPrefix
   * @param vs a List of <code>T</code> values
   */
  public PositionalQualifierCallback(String tokenPrefix,
                                     List<Value> vs) {
    super(tokenPrefix, vs);
    setFormatter(new QueryStringValueFormatter());
  }

  public ValueFormatter getFormatter() { return formatter; }
  public void setFormatter(ValueFormatter vf) { formatter = vf; }

  protected String formatValue(Value value) {
    return getFormatter().formatValue(value);
  }
  
}
