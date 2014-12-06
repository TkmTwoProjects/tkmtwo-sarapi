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
package com.tkmtwo.sarapi.security;

import com.bmc.arsys.api.ARException;
import com.bmc.arsys.api.ARServerUser;
import com.tkmtwo.sarapi.ArsContext;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;



/**
 * Describe class <code>ArsAuthenticationProvider</code> here.
 *
 * @author Tom Mahaffey
 * @version $Id$
 */
public class ArsAuthenticationProvider
  extends AbstractUserDetailsAuthenticationProvider {

  
  ArsContext arsContext = null;
  //UserDetailsService userDetailsService = null;
  
  public ArsContext getArsContext() {
    return arsContext;
  }
  public void setArsContext(ArsContext arscs) {
    arsContext = arscs;
  }

  /*  
  public UserDetailsService getUserDetailsService() {
    return userDetailsService;
  }
  public void setUserDetailsService(UserDetailsService uds) {
    userDetailsService = uds;
  }
  */




  protected final UserDetails retrieveUser(String username,
                                           UsernamePasswordAuthenticationToken authentication)
    throws AuthenticationException {

    List<GrantedAuthority> gauth = new ArrayList<GrantedAuthority>();
    gauth.add(new SimpleGrantedAuthority("ROLE_ARUSER"));

    
    User usr = new User(username,           //String username
                        "",                 //String password
                        true,               //boolean enabled,
                        true,               //boolean accountNonExpired,
                        true,               //boolean credentialsNonExpired,
                        true,               //boolean accountNonLocked,
                        gauth);             //GrantedAuthority[] authorities
    
    return usr;
  }
  
  
  
  
  
  
  protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                UsernamePasswordAuthenticationToken authentication)
    throws AuthenticationException {
    
    if (authentication.getCredentials() == null) {
      /*
      throw new BadCredentialsException(messages
                                        .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials",
                                                    "No credentials for AR."),
                                        userDetails);
      */
      throw new BadCredentialsException(messages
                                        .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials",
                                                    "No credentials for AR."));
    }
    
    ARServerUser arsu = null;
    String presentedHostName = getArsContext().getHostName();
    int presentedHostPort = getArsContext().getHostPort();
    String presentedPassword = authentication.getCredentials().toString();
    String presentedUserName = 
      (authentication.getPrincipal() == null) 
      ? "NONE_PROVIDED" 
      : authentication.getName();

    try {
      if (presentedHostPort > 0) {
        arsu = new ARServerUser(presentedUserName,
                                presentedPassword,
                                null,
                                presentedHostName,
                                presentedHostPort);
      } else {
        arsu = new ARServerUser(presentedUserName,
                                presentedPassword,
                                null,
                                presentedHostName);
      }
      
      arsu.login();
      //authentication.setDetails("HERE ARE SOME DETAILS TO LOOK AT");
      
    } catch (ARException arex) {
      //String arexMsg = arex.getMessage();
      //NOTE: arex error code 623 is "Authentication failed."
      
      /*
      throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials",
                                                            arex.getMessage()),
                                        userDetails);
      */
      throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials",
                                                            arex.getMessage()));

    }
    
  }
  
  
  
}

