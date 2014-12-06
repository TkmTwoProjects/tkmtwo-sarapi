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
package com.tkmtwo.sarapi.mapping;

import static com.google.common.base.Preconditions.checkNotNull;


import com.tkmtwo.sarapi.ArsTemplate;
import com.tkmtwo.sarapi.convert.ArsConversionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.convert.ConversionService;

/**
 *
 */
public abstract class MappingOperation
  implements InitializingBean {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass());  
  
  private ArsTemplate template;
  private ConversionService conversionService;
  
  public ArsTemplate getTemplate() { return template; }
  public void setTemplate(ArsTemplate at) { template = at; }
  
  public ConversionService getConversionService() { return conversionService; }
  public void setConversionService(ConversionService cs) { conversionService = cs; }
  
  
  public void afterPropertiesSet() {
    checkNotNull(getTemplate(), "Need an ArsTemplate");
    
    if (getConversionService() == null) {
      logger.info("No ConversionService found.  Creating a new ArsConversionService.");
      conversionService = new ArsConversionService();
    }
    
  }
  
  
  
}