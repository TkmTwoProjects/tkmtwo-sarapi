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

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import java.io.Serializable;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;




/**
 * Bean-based implementation of an <code>ArsContext</code>.
 *
 * @author Tom Mahaffey
 * @version 1.0
 */
public final class ArsContext
  implements Comparable<ArsContext>, Serializable, FactoryBean, InitializingBean, DisposableBean {

  private static final long serialVersionUID = 1L;

  private String hostName;
  private Integer hostPort;


  public String getHostName() { return hostName; }
  public void setHostName(String s) { hostName = s; }

  
  public Integer getHostPort() { return hostPort; }
  public void setHostPort(Integer i) { hostPort = i; }
  

  public String getConnectionString() {
    return String.format("%s:%s", getHostName(), String.valueOf(getHostPort()));
  }
  
  
  
  
  
  
  
  
  
  
  /**
   * @see InitializingBean#afterPropertiesSet()
   */
  @Override
  public void afterPropertiesSet() {
    checkState(getHostName() != null, "Need a host name.");
    checkState(getHostPort() != null, "Need a port.");
    checkState(getHostPort().intValue() > 0, "Need a non-negative port.");
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
    return ArsContext.class;
  }
  
  /**
   * @see FactoryBean#isSingleton()
   */
  @Override
  public boolean isSingleton() {
    return true;
  }
  
  
  
  
  
  
  
  
  
  /**
   * @see DisposableBean#destroy()
   */
  @Override
  public void destroy() {
  }
  
  
  
  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (!(o instanceof ArsContext)) { return false; }
      
    ArsContext impl = (ArsContext) o;
    
    return
      Objects.equal(getHostName(), impl.getHostName())
      && Objects.equal(getHostPort(), impl.getHostPort());
  }
  
  
  
  @Override
  public int hashCode() {
    return Objects.hashCode(getHostName(), getHostPort());
  }
  
  
  
  @Override
  public int compareTo(ArsContext impl) {
    return ComparisonChain.start()
      .compare(getHostName(), impl.getHostName())
      .compare(getHostPort(), impl.getHostPort())
      .result();
  }
  
  
  
  @Override
  public String toString() {
    return
      Objects.toStringHelper(this)
      .add("hostName", this.hostName)
      .add("hostPort", this.hostPort)
      .toString();
  }
}

