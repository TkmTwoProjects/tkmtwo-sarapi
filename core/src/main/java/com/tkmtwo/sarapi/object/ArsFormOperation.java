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
package com.tkmtwo.sarapi.object;

import static com.google.common.base.Preconditions.checkNotNull;

import com.tkmtwo.sarapi.ArsSchemaHelper;
import org.springframework.dao.DataAccessException;



/**
 *
 */
public abstract class ArsFormOperation
  extends ArsOperation {

  private String formName;
  private ArsSchemaHelper schemaHelper;
  
  public String getFormName() { return formName; }
  public void setFormName(String s)
    throws DataAccessException {

    //assertNotCompiled();
    
    formName = s;
  }
  
  
  public ArsSchemaHelper getSchemaHelper() { return schemaHelper; }
  public void setSchemaHelper(ArsSchemaHelper ash)
    throws DataAccessException {

    //assertNotCompiled();
    schemaHelper = ash;
  }

  /*  
  protected void assertHasHelper()
    throws DataAccessException {

    if (getArsSchemaHelper() == null) {
      throw new InvalidDataAccessApiUsageException("Need a ArsSchemaHelper.");
    }
  }
  */

  /*  
  protected void compileFormName()
    throws DataAccessException {

    String fn = getFormName();
    if (fn == null || fn.isEmpty() || fn.trim().isEmpty()) {
      throw new InvalidDataAccessApiUsageException("Need a form name");
    }
  }
  */
  
  public void aferPropertiesSet() {
    super.afterPropertiesSet();
    checkNotNull(getFormName(), "Need a form name.");
    
    if (getSchemaHelper() == null) {
      logger.warn("No ArsSchemaHelper found.  Field IDs must be used.");
    }
    
  }
  
  
  
  
  
  /*
  protected static List<Value> combineValueLists(List<Value> staticValues,
                                                 List<Value> dynamicValues) {
    List<Value> l = Lists.newArrayList();
    l.addAll(staticValues);
    l.addAll(dynamicValues);
    return l;
  }
  */
  


  
}
