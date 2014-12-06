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

import com.bmc.arsys.api.Constants;
import com.bmc.arsys.api.DataType;
import com.bmc.arsys.api.Value;
import com.tkmtwo.interpolate.AbstractInterpolatorCallback;
import com.tkmtwo.sarapi.support.ValueUtil;


/**
 *
 */
public abstract class ArsInterpolatorCallback
  extends AbstractInterpolatorCallback {


  protected String formatValue(Value value, int tokenNumber) {

    if (value == null) {
      logger.trace("Parameter at {} is null.  Inserting $NULL$ keyword.", String.valueOf(tokenNumber));
      return "$NULL$";
    }
    
    
    String replString = null;
    DataType dataType = value.getDataType();

    switch(dataType.toInt()) {
    case Constants.AR_DATA_TYPE_NULL:
      logger.trace("Parameter at {} is a AR_DATA_TYPE_NULL.", String.valueOf(tokenNumber));
      replString = "$NULL$";
      break;
      
    case Constants.AR_DATA_TYPE_INTEGER:
      logger.trace("Parameter at {} is a AR_DATA_TYPE_INTEGER.", String.valueOf(tokenNumber));
      replString = ValueUtil.getInteger(value).toString();
      break;
      
    case Constants.AR_DATA_TYPE_REAL:
      logger.trace("Parameter at {} is a AR_DATA_TYPE_REAL.", String.valueOf(tokenNumber));
      replString = ValueUtil.getReal(value).toString();
      break;
      
    case Constants.AR_DATA_TYPE_CHAR:
      logger.trace("Parameter at {} is a AR_DATA_TYPE_CHAR.", String.valueOf(tokenNumber));
      replString = "\"" + ValueUtil.getCharacter(value) + "\"";
      break;
      
    case Constants.AR_DATA_TYPE_ENUM:
      logger.trace("Parameter at {} is a AR_DATA_TYPE_ENUM.", String.valueOf(tokenNumber));
      replString = ValueUtil.getEnum(value).toString();
      break;
      
    case Constants.AR_DATA_TYPE_TIME:
      logger.trace("Parameter at {} is a AR_DATA_TYPE_TIME.", String.valueOf(tokenNumber));
      replString = String.valueOf(ValueUtil.getTimestamp(value).getValue());
      break;
      
    case Constants.AR_DATA_TYPE_DECIMAL:
      logger.trace("Parameter at {} is a AR_DATA_TYPE_DECIMAL.", String.valueOf(tokenNumber));
      replString = ValueUtil.getDecimal(value).toString();
      break;
      
    case Constants.AR_DATA_TYPE_CURRENCY:
      logger.trace("Parameter at {} is a AR_DATA_TYPE_CURRENCY.", String.valueOf(tokenNumber));
      replString = String.valueOf(ValueUtil.getCurrencyValue(value).getValue());
      break;
      
    case Constants.AR_DATA_TYPE_DATE:
      logger.trace("Parameter at {} is a AR_DATA_TYPE_DATE.", String.valueOf(tokenNumber));
      replString = String.valueOf(ValueUtil.getDateInfo(value).getValue());
      break;
      
    case Constants.AR_DATA_TYPE_TIME_OF_DAY:
      logger.trace("Parameter at {} is a AR_DATA_TYPE_TIME_OF_DAY.", String.valueOf(tokenNumber));
      replString = String.valueOf(ValueUtil.getTime(value).getValue());
      break;
      
    default:
      logger.trace("Parameter at {} is being evaluated with default dbl-quotes.", String.valueOf(tokenNumber));
      replString = "\"" + value.toString() + "\"";
      break;
    }
    
    return replString;
  }

  
}

