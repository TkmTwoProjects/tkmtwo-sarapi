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


import com.bmc.arsys.api.ARException;
import org.springframework.dao.DataAccessResourceFailureException;




/**
 * Fatal exception thrown when we can not impersonate an AR user.
 *
 * @author Tom Mahaffey
 * @version $Id$
 */
public class CannotImpersonateARServerUserException
  extends DataAccessResourceFailureException {
  
  
  /**
   * Creates a new <code>CannotImpersonateARServerUserException</code>
   *  instance.
   *
   * @param msg The detailed message
   * @param arex an <code>ARException</code> root cause
   */
  public CannotImpersonateARServerUserException(String msg, ARException arex) {
    super(msg, arex);
  }
  
}

