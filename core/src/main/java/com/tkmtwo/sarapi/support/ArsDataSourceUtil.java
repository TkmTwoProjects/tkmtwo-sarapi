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
package com.tkmtwo.sarapi.support;

import static com.google.common.base.Preconditions.checkNotNull;

import com.bmc.arsys.api.ARServerUser;
import com.tkmtwo.sarapi.ArsUserSource;
import com.tkmtwo.sarapi.CannotGetARServerUserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Describe class <code>ARServerUserUtil</code> here.
 *
 * @author Tom Mahaffey
 * @version $Id$
 */
public final class ArsDataSourceUtil {

  private static Logger logger = LoggerFactory.getLogger(ArsDataSourceUtil.class);


  public static ARServerUser getARServerUser(ArsUserSource arsUserSource)
    throws CannotGetARServerUserException {
    checkNotNull(arsUserSource, "ArsUserSource is null.");
    
    return arsUserSource.getARServerUser();
    
  }
  
  public static void releaseARServerUser(ARServerUser arsUser,
                                         ArsUserSource arsUserSource) {

    if (arsUser == null) { return; }
    arsUserSource.releaseARServerUser(arsUser);
  }
  
  
}
