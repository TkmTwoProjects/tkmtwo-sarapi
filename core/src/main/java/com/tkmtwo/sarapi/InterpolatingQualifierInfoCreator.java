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

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.TkmTwoConditions.checkNotBlank;

import com.bmc.arsys.api.QualifierInfo;
import com.bmc.arsys.api.Value;
import com.google.common.base.Objects;
import com.tkmtwo.interpolate.Interpolator;
import com.tkmtwo.interpolate.InterpolatorCallback;
import com.tkmtwo.sarapi.interpolate.MappingQualifierCallback;
import com.tkmtwo.sarapi.interpolate.PositionalQualifierCallback;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;



/**
 *
 */
public class InterpolatingQualifierInfoCreator
  implements InitializingBean, QualifierInfoCreator {

  protected final Logger logger = LoggerFactory.getLogger(getClass());
  
  private ArsSchemaHelper schemaHelper;
  private String formName;
  private String qualifierString;
  //private InterpolatorCallback interpolatorCallback;
  


  private boolean compiled = false;
  
  
  public InterpolatingQualifierInfoCreator() { ; }

  public InterpolatingQualifierInfoCreator(ArsSchemaHelper ash,
                                           String fn,
                                           String qs) {
    setSchemaHelper(ash);
    setFormName(fn);
    setQualifierString(qs);
  }


  public ArsSchemaHelper getSchemaHelper() {
    return schemaHelper;
  }
  public void setSchemaHelper(ArsSchemaHelper ash) {
    assertNotCompiled();
    schemaHelper = ash;
  }

  
  
  

  public String getFormName() {
    return formName;
  }
  public void setFormName(String s) {
    assertNotCompiled();
    formName = s;
  }

  
  
  public String getQualifierString() {
    return qualifierString;
  }
  
  public void setQualifierString(String qs) {
    assertNotCompiled();
    if (qs != null) {
      qualifierString = qs.trim();
    }
  }



  private boolean isCompiled() {
    return compiled;
  }
  private void assertNotCompiled() {
    if (isCompiled()) {
      throw new InvalidDataAccessApiUsageException("Fields have already been compiled.");
    }
  }
  private void assertIsCompiled() {
    if (!isCompiled()) {
      throw new InvalidDataAccessApiUsageException("Fields have not been compiled.");
    }
  }

  
  public void afterPropertiesSet() {
    if (isCompiled()) { return; }
    
    checkNotNull(getSchemaHelper(), "ArsSchemaHelper is null.");
    checkNotBlank(getFormName(), "Form name is blank.");
    checkNotBlank(getQualifierString(), "Qualifier is blank.");

    compiled = true;
  }
  
  
  
  public QualifierInfo createQualifierInfo(InterpolatorCallback ic)
    throws DataAccessException {
    
    String qs = Interpolator.interpolate(getQualifierString(), ic);

    logger.trace("Qualifier string was {}.", getQualifierString());
    logger.trace("Qualifier string is now {}.", qs);

    QualifierInfo qi =
      getSchemaHelper().parseQualification(getFormName(), qs);
    
    return qi;
  }
  
    
  
  
  public QualifierInfo createQualifierInfo(List<Value> values)
    throws DataAccessException {
    
    assertIsCompiled();
    checkNotBlank(getQualifierString(), "Qualifier string is blank.");
    
    return createQualifierInfo(new PositionalQualifierCallback(values));
  }

  public QualifierInfo createQualifierInfo(Map<String, Value> values)
    throws DataAccessException {
    
    assertIsCompiled();
    checkNotBlank(getQualifierString(), "Qualifier string is blank.");
    
    return createQualifierInfo(new MappingQualifierCallback(values));
  }




  public String toString() {
    return Objects.toStringHelper(this)
      .add("formName", getFormName())
      .add("qualifierString", getQualifierString())
      .toString();
  }

  
  
}
