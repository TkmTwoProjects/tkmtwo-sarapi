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

import static com.google.common.base.TkmTwoConditions.checkNotBlank;

//import com.google.common.base.Splitter.MapSplitter;
//import com.google.common.base.Splitter;
import com.google.common.base.TkmTwoJointers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.tkmtwo.sarapi.security.SecureArsUserSource;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public final class ArsSource
  implements InitializingBean {

  protected final Logger logger = LoggerFactory.getLogger(getClass());

  private String environmentName;
  private String connectionString;
  private Map<String,String> configurationMap;
  
  private List<ArsContext> contexts;
  
  private ArsUserSource userSource;
  private ArsUserSource secureUserSource;
  
  private ArsTemplate template;
  private ArsTemplate secureTemplate;
  
  private ArsSchemaHelper schemaHelper;
  
  public ArsSource(String s) { setConnectionString(s); }
  
  public String getConnectionString() { return connectionString; }
  public void setConnectionString(String s) { connectionString = s; }
  
  public Map<String,String> getConfigurationMap() { return configurationMap; }
  private void setConfigurationMap(Map<String,String> m) { configurationMap = m; }
  
  public String getEnvironmentName() { return environmentName; }
  private void setEnvironmentName(String s) { environmentName = s; }
  
  public List<ArsContext> getContexts() { return contexts; }
  private void setContexts(List<ArsContext> l) { contexts = l; }
  
  public ArsUserSource getUserSource() { return userSource; }
  private void setUserSource(ArsUserSource aus) { userSource = aus; }
  
  public ArsUserSource getSecureUserSource() { return secureUserSource; }
  private void setSecureUserSource(ArsUserSource aus) { secureUserSource = aus; }
  
  public ArsTemplate getTemplate() { return template; }
  private void setTemplate(ArsTemplate at) { template = at; }
  
  public ArsTemplate getSecureTemplate() { return secureTemplate; }
  private void setSecureTemplate(ArsTemplate at) { secureTemplate = at; }
  
  public ArsSchemaHelper getSchemaHelper() { return schemaHelper; }
  private void setSchemaHelper(ArsSchemaHelper ash) { schemaHelper = ash; }
  
  
  public boolean isSecure() {
    return Boolean.valueOf(getConfigurationMap().get("secure")).booleanValue();
  }
  
  
  
  
  
  
  
  private String buildEnvironmentName() {
    Map<String,String> m = getConfigurationMap();
    return checkNotBlank(m.get("environmentName"), "Need an environment name.");
  }
  
  private List<ArsContext> buildContexts() {
    Map<String,String> m = getConfigurationMap();
    Iterable<String> i =
      TkmTwoJointers.COMMA_SPLITTER.split(checkNotBlank(m.get("contexts"), "Need some ars contexts."));
    
    ImmutableList.Builder<ArsContext> ilb = new ImmutableList.Builder<ArsContext>();
    for (String s : i) {
      ArsContext ac = new ArsContext(s);
      ac.afterPropertiesSet();
      ilb.add(ac);
    }
    return ilb.build();
  }

  /*  
  private ArsUserSource buildUserSource() {
    if (Boolean.valueOf(getConfigurationMap().get("secure"))) {
      return buildSecureUserSource();
    }
    return buildBasicUserSource();
  }
  */
  
  private ArsUserSource buildUserSource() {
    logger.info("Basic user source has been requested.");
    
    Map<String,String> m = getConfigurationMap();
    BasicArsUserSource aus = new BasicArsUserSource();
    aus.setUserName(checkNotBlank(m.get("userName"), "Need a user name."));
    aus.setUserPassword(checkNotBlank(m.get("userPassword"), "Need a password."));
    aus.setArsContexts(getContexts());
    
    if (m.containsKey("impersonatedUser")) { aus.setImpersonatedUser(m.get("impersonatedUser")); }
    if (m.containsKey("locale")) { aus.setLocale(m.get("locale")); }
    if (m.containsKey("timeZone")) { aus.setTimeZone(m.get("timeZone")); }
    if (m.containsKey("customDateFormat")) { aus.setCustomDateFormat(m.get("customDateFormat")); }
    if (m.containsKey("customTimeFormat")) { aus.setCustomTimeFormat(m.get("customTimeFormat")); }
    
    aus.afterPropertiesSet();
    return aus;
  }
    
  private ArsUserSource buildSecureUserSource() {
    if (!isSecure()) { return null; }
    
    Map<String,String> m = getConfigurationMap();
    SecureArsUserSource sus = new SecureArsUserSource();
    sus.setUserName(checkNotBlank(m.get("userName"), "Need a user name."));
    sus.setUserPassword(checkNotBlank(m.get("userPassword"), "Need a password."));
    sus.setArsContexts(getContexts());
    
    if (m.containsKey("locale")) { sus.setLocale(m.get("locale")); }
    if (m.containsKey("timeZone")) { sus.setTimeZone(m.get("timeZone")); }
    if (m.containsKey("customDateFormat")) { sus.setCustomDateFormat(m.get("customDateFormat")); }
    if (m.containsKey("customTimeFormat")) { sus.setCustomTimeFormat(m.get("customTimeFormat")); }
    
    sus.afterPropertiesSet();
    return sus;
  }
  
  private ArsTemplate buildTemplate() {
    ArsTemplate at = new ArsTemplate();
    at.setUserSource(getUserSource());
    at.afterPropertiesSet();
    return at;
  }
  
  private ArsTemplate buildSecureTemplate() {
    if (!isSecure()) { return null; }
    ArsTemplate at = new ArsTemplate();
    at.setUserSource(getSecureUserSource());
    at.afterPropertiesSet();
    return at;
  }
  
  private ArsSchemaHelper buildSchemaHelper() {
    InMemorySchemaHelper imsh = new InMemorySchemaHelper();
    imsh.setTemplate(getTemplate());
    imsh.afterPropertiesSet();
    return imsh;
  }
  


  public void afterPropertiesSet() {

    //checkNotBlank(getConnectionString(), "Need a connection string.");
    setConfigurationMap(TkmTwoJointers.MAP_SPLITTER.split(checkNotBlank(getConnectionString(),
                                                                        "Need a connection string.")));
    //Map<String,String> cm = MAP_SPLITTER.split(getConnectionString());

    //build the environment name
    setEnvironmentName(buildEnvironmentName());
    
    //build the contexts
    //setContexts(buildContexts(cm));
    setContexts(buildContexts());
    
    //build the usersource
    //setUserSource(buildUserSource(cm));
    setUserSource(buildUserSource());
    setSecureUserSource(buildSecureUserSource());
    
    
    //build the template
    setTemplate(buildTemplate());
    setSecureTemplate(buildTemplate());

    //build the schemahelper
    setSchemaHelper(buildSchemaHelper());
  }
  
  
}
