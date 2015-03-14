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

import static com.google.common.base.TkmTwoConditions.checkNotEmpty;
import static com.google.common.base.TkmTwoJointers.COMMA_JOINER;

import com.bmc.arsys.api.ARException;
import com.bmc.arsys.api.ARServerUser;
import com.google.common.collect.ImmutableList;
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

  
  //ArsContext arsContext = null;
  List<ArsContext> arsContexts;
  
  public List<ArsContext> getArsContexts() {
    //return arsContext;
    return arsContexts;
  }
  public void setArsContext(ArsContext arscs) {
    //arsContext = arscs;
    arsContexts = ImmutableList.of(arscs);
  }
  public void setArsContexts(List<ArsContext> l) {
    arsContexts = ImmutableList.copyOf(l);
  }



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
    
    checkNotEmpty(getArsContexts());
    
    if (authentication.getCredentials() == null) {
      throw new BadCredentialsException(messages
                                        .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials",
                                                    "No credentials for AR."));
    }
    
    ARServerUser arsu = null;
    String presentedPassword = authentication.getCredentials().toString();
    String presentedUserName = 
      (authentication.getPrincipal() == null) 
      ? "NONE_PROVIDED" 
      : authentication.getName();
    
    for (ArsContext ac : getArsContexts()) {
      String presentedHostName = ac.getHostName();
      int presentedHostPort = ac.getHostPort();
      
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
        return;
      } catch (ARException arex) {
        logger.warn("Login attempt failed: " + arex.getMessage());
      }
    }
    
    //String arexMsg = arex.getMessage();
    //NOTE: arex error code 623 is "Authentication failed."
    throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials",
                                                          "Could not log in to any of "
                                                          + COMMA_JOINER.join(getArsContexts())));
    
    
  }
  
  
}

