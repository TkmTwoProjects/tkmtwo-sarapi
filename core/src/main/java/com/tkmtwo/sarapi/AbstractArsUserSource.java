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

import static com.google.common.base.Preconditions.checkState;

import com.bmc.arsys.api.ARServerUser;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.StackObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;



/**
 * Bean-based implementation of an <code>AbstractArsUserSource</code>.
 *
 * @author Tom Mahaffey
 */
public abstract class AbstractArsUserSource
  implements ArsUserSource, FactoryBean, InitializingBean, DisposableBean {

  protected final Logger logger = LoggerFactory.getLogger(getClass());

  private ObjectPool<ARServerUser> userPool;
  
  private String arsEnvironmentName;
  private List<ArsContext> arsContexts;
  
  private String userName;
  private String userPassword;

  private String locale;
  private String timeZone;
  private String customDateFormat;
  private String customTimeFormat;

  private String impersonatedUser;

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

  /*
  public void setArsContextsConnectionStrings(Iterable<String> i) {
    ImmutableList.Builder<String> ilb = new ImmutableList.Builder<String>();
  */
  
  
  public String getUserName() { return userName; }
  public void setUserName(String s) { userName = s; }
  private String getUserPassword() { return userPassword; }
  public void setUserPassword(String s) { userPassword = s; }


  public String getLocale() { return locale; }
  public void setLocale(String s) { locale = s; }
  public String getTimeZone() { return timeZone; }
  public void setTimeZone(String s) { timeZone = s; }
  public String getCustomDateFormat() { return customDateFormat; }
  public void setCustomDateFormat(String s) { customDateFormat = s; }
  
  public String getCustomTimeFormat() { return customTimeFormat; }
  public void setCustomTimeFormat(String s) { customTimeFormat = s; }
  
  
  
  public String getImpersonatedUser() { return impersonatedUser; }
  public void setImpersonatedUser(String s) { impersonatedUser = s; }
  
  
  protected ObjectPool<ARServerUser> getUserPool() { return userPool; }
  
  public abstract ARServerUser getARServerUser();
  public abstract void releaseARServerUser(ARServerUser arsu);
  

  
  
  
  

  /**
   * @see InitializingBean#afterPropertiesSet()
   */
  @Override
  public void afterPropertiesSet() {
    //Assert.isTrue(!getArsContexts().isEmpty(), "Need some ArsContexts.");
    checkState(!getArsContexts().isEmpty(), "Need some ArsContexts.");

    if (getArsEnvironmentName() == null) {
      setArsEnvironmentName(Joiner.on(",").join(getArsContextHostNames()));
    }

    logger.debug("Creating ARServerUserFactory.");

    userPool =
      new StackObjectPool<ARServerUser>(new ARServerUserFactory(getArsEnvironmentName(),
                                                                getArsContexts(),
                                                                getUserName(),
                                                                getUserPassword(),
                                                                getLocale(),
                                                                getTimeZone(),
                                                                getCustomDateFormat(),
                                                                getCustomTimeFormat(),
                                                                getImpersonatedUser()));
  }
  

  /**
   * @see FactoryBean#getObject()
   */
  @Override
  public Object getObject() {
    return this;
  }
  
  
  
  /**
   * @see FactoryBean#getObjectType()
   */
  @Override
  public Class getObjectType() {
    return ArsUserSource.class;
  }
  
  
  
  /**
   * @see FactoryBean#isSingleton()
   */
  public boolean isSingleton() {
    return true;
  }
  
  
  
  
  
  
  
  /**
   * @see DisposableBean#destroy()
   */
  @Override
  public void destroy() {
    logger.debug("Closing ArsUserSource.");
  }


  @Override
  public String toString() {
    return Objects.toStringHelper(this)
      .add("arsEnvironmentName", getArsEnvironmentName())
      .add("userName", getUserName())
      .add("impersonatedUser", getImpersonatedUser())
      .add("locale", getLocale())
      .add("timeZone", getTimeZone())
      .add("customDateFormat", getCustomDateFormat())
      .add("customTimeFormat", getCustomTimeFormat())
      .toString();
  }


}


