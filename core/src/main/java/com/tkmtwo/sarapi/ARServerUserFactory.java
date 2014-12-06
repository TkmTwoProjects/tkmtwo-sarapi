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

import static com.google.common.base.TkmTwoStrings.isBlank;

import com.bmc.arsys.api.ARException;
import com.bmc.arsys.api.ARServerUser;
import com.tkmtwo.sarapi.support.ARExceptions;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.pool.BasePoolableObjectFactory; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 *
 */
public class ARServerUserFactory
  extends BasePoolableObjectFactory<ARServerUser> {
  
  private final Logger logger = LoggerFactory.getLogger(ARServerUserFactory.class);

  private ARExceptionTranslator exceptionTranslator =
    new MessageNumARExceptionTranslator();
  
  private String arsEnvironmentName;
  private List<ArsContext> arsContexts;
  
  private String userName;
  private String userPassword;

  private String locale;
  private String timeZone;
  private String customDateFormat;
  private String customTimeFormat;

  private String impersonatedUser;




  public ARServerUserFactory(String arsEnvironmentName,
                             List<ArsContext> arsContexts,
                             String userName,
                             String userPassword,
                             String locale,
                             String timeZone,
                             String customDateFormat,
                             String customTimeFormat,
                             String impersonatedUser) {
    setArsEnvironmentName(arsEnvironmentName);
    setArsContexts(arsContexts);
    setUserName(userName);
    setUserPassword(userPassword);
    setLocale(locale);
    setTimeZone(timeZone);
    setCustomDateFormat(customDateFormat);
    setCustomTimeFormat(customTimeFormat);
    setImpersonatedUser(impersonatedUser);
  }
  

  public ARExceptionTranslator getExceptionTranslator() {
    return this.exceptionTranslator;
  }
  public void setExceptionTranslator(ARExceptionTranslator exceptionTranslator) {
    this.exceptionTranslator = exceptionTranslator;
  }

  public String getArsEnvironmentName() { return arsEnvironmentName; }
  public void setArsEnvironmentName(String s) { arsEnvironmentName = s; }


  

  public List<ArsContext> getArsContexts() {
    if (arsContexts == null) {
      arsContexts = new ArrayList<ArsContext>();
    }
    
    return arsContexts;
  }
  public List<String> getArsContextHostNames() {
    List<String> l = new ArrayList<String>();
    for (ArsContext arsContext : getArsContexts()) {
      l.add(arsContext.getHostName());
    }
    return l;
  }
    
  public void setArsContexts(List<ArsContext> l) { arsContexts = l; }
  
  
  public String getUserName() { return this.userName; }
  public void setUserName(String s) { this.userName = s; }
  public void setUserPassword(String s) { this.userPassword = s; }


  public String getLocale() { return locale; }
  public void setLocale(String s) { locale = s; }
  public String getTimeZone() { return timeZone; }
  public void setTimeZone(String s) { timeZone = s; }
  public String getCustomDateFormat() { return customDateFormat; }
  public void setCustomDateFormat(String s) { customDateFormat = s; }

  public String getCustomTimeFormat() { return customTimeFormat; }
  public void setCustomTimeFormat(String s) { customTimeFormat = s; }
  
  
  
  public String getImpersonatedUser() { return this.impersonatedUser; }
  public void setImpersonatedUser(String s) { this.impersonatedUser = s; }

  


  public ARServerUser makeObject() {
    logger.trace("Making new ARSU.");
    
    ARServerUser arsu = null;
    int numContexts = getArsContexts().size();
    for (int i = 0; i < numContexts; i++) {
      ArsContext arsContext = getArsContexts().get(i);

      logger.trace("Attempting ARServerUser connect to {} with userName {}.",
                   arsContext.getConnectionString(),
                   getUserName());
      
        
      
      if (arsContext.getHostPort() > 0) {
        arsu = new ARServerUser(getUserName(),
                                this.userPassword,
                                null, //locale
                                arsContext.getHostName(),
                                arsContext.getHostPort());
      } else {
        arsu = new ARServerUser(getUserName(),
                                this.userPassword,
                                null,
                                arsContext.getHostName());
      }
      
      if (!isBlank(getLocale())) { arsu.setLocale(getLocale()); }
      if (!isBlank(getTimeZone())) { arsu.setTimeZone(getTimeZone()); }
      if (!isBlank(getCustomDateFormat())) { arsu.setCustomDateFormat(getCustomDateFormat()); }
      if (!isBlank(getCustomTimeFormat())) { arsu.setCustomTimeFormat(getCustomTimeFormat()); }
      
      try {
        arsu.login();
      } catch (ARException arex) {
        if (ARExceptions.hasStatusInfoMessageNum(arex, 90L)) {
          logger.warn("Error 90 from {}.", 
                      arsContext.getConnectionString());
          if (i < numContexts - 1) {
            continue;
          } else {
            throw getExceptionTranslator().translate(arex);
          }
        } else {
          throw getExceptionTranslator().translate(arex);
        }
      }
      
      if (getImpersonatedUser() != null) {
        logger.debug("Impersonating user {}.",
                     getImpersonatedUser());
        try {
          arsu.impersonateUser(getImpersonatedUser());
        } catch (ARException arex) {
          throw getExceptionTranslator().translate(arex);
        }          
      }
      
      return arsu;
    }
    
    return null;

  }
  
  public void activateObject(ARServerUser arsu) { logger.trace("Activating ARSU."); }
  public void desroyObject(ARServerUser arsu) {
    logger.trace("Destroying ARSU.");
    if (arsu != null) {
      arsu.logout();
    }
  }
  
  public void passivateObject(ARServerUser arsu) {
    logger.trace("Passivating ARSU");
  } 
  public boolean validateObject(ARServerUser arsu) {
    logger.trace("Validating ARSU.");
    return true;
  }

  
  
}
