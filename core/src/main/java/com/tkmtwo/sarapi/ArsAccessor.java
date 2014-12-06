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
import org.springframework.beans.factory.InitializingBean;


/**
 *
 */
public abstract class ArsAccessor
  implements InitializingBean {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass());
  private ArsUserSource userSource;
  private ARExceptionTranslator exceptionTranslator =
    new MessageNumARExceptionTranslator();

  public ArsAccessor() { }
  
  public ArsUserSource getUserSource() { return userSource; }
  public void setUserSource(ArsUserSource aus) { userSource = aus; }
  
  public ARExceptionTranslator getExceptionTranslator() { return exceptionTranslator; }
  public void setExceptionTranslator(ARExceptionTranslator aet) { exceptionTranslator = aet; }

  
  public void afterPropertiesSet() {
    checkNotNull(getUserSource(), "Need an ArsUserSource.");
    
    if (getExceptionTranslator() == null) {
      logger.info("No ARExceptionTranslator...creating default MessageNumARExceptionTranslator.");
      exceptionTranslator = new MessageNumARExceptionTranslator();
    }
    checkNotNull(getExceptionTranslator(), "Need an exception translator.");
    
    logger.debug("Using user source {}", getUserSource());
    logger.debug("Using exception translator {}", getExceptionTranslator());
  }
  
}
