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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;


/**
 *
 */
public abstract class ArsDaoSupport 
  implements InitializingBean {

  protected final Logger logger = LoggerFactory.getLogger(getClass());

  private ArsTemplate template;
  private ArsSchemaHelper schemaHelper;


  /*  
  public final void setUserSource(ArsUserSource aus) {
    template = createTemplate(aus);
    initTemplateConfig();
  }
  
  protected ArsTemplate createTemplate(ArsUserSource aus) {
    return new ArsTemplate(aus);
  }

  
  public final ArsUserSource getUserSource() {
    return
      (getTemplate() != null)
      ? getTemplate().getUserSource()
      : null;
  }
  */



  public final ArsTemplate getTemplate() { return template; }
  public final void setTemplate(ArsTemplate at) { template = at; }
  //initTemplateConfig();
  //}
  
  
  public ArsSchemaHelper getSchemaHelper() { return schemaHelper; }
  public void setSchemaHelper(ArsSchemaHelper ash) { schemaHelper = ash; }

  
  //protected void initTemplateConfig() { ; }

  /*  
  protected void checkDaoConfig() {
    if (getTemplate() == null) {
      throw new IllegalArgumentException("'arsTemplate' is required");
    }
  }
  */

  /*  
  protected final ARExceptionTranslator getExceptionTranslator() {
    return getTemplate().getExceptionTranslator();
  }
  */

  /*
  //OOOPS  
  protected final ARServerUser getARServerUser()
    throws CannotGetARServerUserException {
    return ArsDataSourceUtil.getARServerUser(getArsUserSource());
  }
  
  protected final void releaseARServerUser(ARServerUser arsUser) {
    ArsDataSourceUtil.releaseARServerUser(arsUser,
                                          getArsUserSource());
  }
  */

  
  public final void afterPropertiesSet()
    throws IllegalArgumentException, BeanInitializationException {
    
    checkNotNull(getTemplate(), "Need an ArsTemplate");
    ////checkNotNull(getSchemaHelper(), "Need an ArsSchemaHelper");

    // Let abstract subclasses check their configuration.
    ////checkDaoConfig();
    
    // Let concrete implementations initialize themselves.

    /*
    try {
      initDao();
    } catch (Exception ex) {
      throw new BeanInitializationException("Initialization of DAO failed", ex);
    }
    */
  }
  
  
  //protected void initDao() throws Exception { }
  
  
  
}
