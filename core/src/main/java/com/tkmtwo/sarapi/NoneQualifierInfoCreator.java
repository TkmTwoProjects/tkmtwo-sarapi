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
package com.tkmtwo.sarapi;

import com.bmc.arsys.api.Constants;
import com.bmc.arsys.api.QualifierInfo;
import com.bmc.arsys.api.Value;
import com.google.common.base.Objects;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;

/**
 * QualifierInfoCreator which always returns no-op.
 *
 */
public final class NoneQualifierInfoCreator
  implements QualifierInfoCreator {

  public QualifierInfo createQualifierInfo(List<Value> values)
    throws DataAccessException {
    
    return createQualifierInfo();
  }
  public QualifierInfo createQualifierInfo(Map<String, Value> values)
    throws DataAccessException {
    
    return createQualifierInfo();
  }
  
  private static QualifierInfo createQualifierInfo() {
    QualifierInfo qi = new QualifierInfo();
    qi.setOperation(Constants.AR_COND_OP_NONE);
    return qi;
  }
    

  public String toString() {
    return Objects.toStringHelper(this)
      .add("qualifierString", "AR_COND_OP_NONE")
      .toString();
  }



}
