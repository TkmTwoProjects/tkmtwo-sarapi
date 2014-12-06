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

import com.bmc.arsys.api.Constants;
import com.bmc.arsys.api.DataType;
import com.bmc.arsys.api.Value;


/**
 *
 */
public final class QueryStringValueFormatter
  implements ValueFormatter {
  
  
  public String formatValue(Value value) {
    
    String replString = null;
    
    DataType dataType = value.getDataType();
    switch(dataType.toInt()) {
      
    case Constants.AR_DATA_TYPE_NULL:
      replString = "$NULL$";
      break;
      
    case Constants.AR_DATA_TYPE_INTEGER:
      replString = ValueUtil.getInteger(value).toString();
      break;
      
    case Constants.AR_DATA_TYPE_REAL:
      replString = ValueUtil.getReal(value).toString();
      break;
      
    case Constants.AR_DATA_TYPE_CHAR:
      replString = "\"" + ValueUtil.getCharacter(value) + "\"";
      break;
      
    case Constants.AR_DATA_TYPE_ENUM:
      replString = ValueUtil.getEnum(value).toString();
      break;
      
    case Constants.AR_DATA_TYPE_TIME:
      replString = String.valueOf(ValueUtil.getTimestamp(value).getValue());
      break;
      
    case Constants.AR_DATA_TYPE_DECIMAL:
      replString = ValueUtil.getDecimal(value).toString();
      break;
      
    case Constants.AR_DATA_TYPE_CURRENCY:
      replString = String.valueOf(ValueUtil.getCurrencyValue(value).getValue());
      break;
      
    case Constants.AR_DATA_TYPE_DATE:
      replString = String.valueOf(ValueUtil.getDateInfo(value).getValue());
      break;
      
    case Constants.AR_DATA_TYPE_TIME_OF_DAY:
      replString = String.valueOf(ValueUtil.getTime(value).getValue());
      break;
      
    default:
      replString = "\"" + value.toString() + "\"";
      break;
    }
    
    return replString;
  }
  
  
}

