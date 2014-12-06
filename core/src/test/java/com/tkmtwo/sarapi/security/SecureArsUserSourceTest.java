package com.tkmtwo.sarapi.security;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;


import com.bmc.arsys.api.Value;
import com.bmc.arsys.api.ARServerUser;
import com.bmc.arsys.api.Form;
import com.bmc.arsys.api.ARException;

import com.tkmtwo.sarapi.ArsUserSource;
import com.tkmtwo.sarapi.ArsTemplate;
import com.tkmtwo.sarapi.ArsUserSource;
import com.tkmtwo.sarapi.support.ArsDataSourceUtil;

@ContextConfiguration
public final class SecureArsUserSourceTest
  extends AbstractJUnit4SpringContextTests
{

  private @Resource(name="arsSecureSource") ArsUserSource arsSecureSource;
  
  private @Resource(name="arsAuthenticationProvider") AuthenticationProvider authenticationProvider;
  
  private @Resource(name="authenticationManager") AuthenticationManager authenticationManager;
  
  
  private void login(String uname, String passwd)
  {
    Authentication auth =
      new UsernamePasswordAuthenticationToken(uname, passwd);
    //auth = authenticationProvider.authenticate(auth);
    auth = authenticationManager.authenticate(auth);
    
    SecurityContext securityContext = SecurityContextHolder.getContext();
    securityContext.setAuthentication(auth);
  }

  
  private void logout()
  {
    SecurityContextHolder.clearContext();
  }
  
  
  @Test
  public void testSuccess()
  {
    login("thomas.k.mahaffey", "tom");
    
    ARServerUser arsUser = 
      ArsDataSourceUtil.getARServerUser(arsSecureSource);
    Form form = null;
    
    try {
      form = arsUser.getForm("User");
    } catch (ARException arex) {
      fail(arex.getMessage());
    } finally {
      ArsDataSourceUtil.releaseARServerUser(arsUser, arsSecureSource);
    }
    
    assertNotNull(form);
  }
  
  @Test(expected = org.springframework.security.authentication.BadCredentialsException.class)
  public void testFail()
  {
    login("Demo", "noSuchPassword");
    
    ARServerUser arsUser = 
      ArsDataSourceUtil.getARServerUser(arsSecureSource);
    Form form = null;
    
    try {
      form = arsUser.getForm("User");
    } catch (ARException arex) {
      fail(arex.getMessage());
    } finally {
      ArsDataSourceUtil.releaseARServerUser(arsUser, arsSecureSource);
    }
    
    assertNotNull(form);
  }
  
  
  
}

