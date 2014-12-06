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
package com.tkmtwo.sarapi.support;

import com.bmc.arsys.api.Field;
import com.google.common.collect.ComparisonChain;
import java.io.Serializable;
import java.util.Comparator;
//import org.apache.commons.lang.builder.CompareToBuilder;



/**
 *
 */
public class FieldIdComparator
  implements Serializable, Comparator<Field> {
  
  private static final long serialVersionUID = 1L;
  
  public int compare(Field fieldOne, Field fieldTwo) {
    if (fieldOne == null || fieldTwo == null) { return 0; }

    return ComparisonChain.start()
      .compare(fieldOne.getFieldID(), fieldTwo.getFieldID())
      .result();
  }

  /*  
  public int compare(Field fieldOne, Field fieldTwo) {
    if (fieldOne == null || fieldTwo == null) {
      return 0;
    }
    
    return new CompareToBuilder()
      .append(fieldOne.getFieldID(), fieldTwo.getFieldID())
      .toComparison();
  }
  */
  
  
  
}

