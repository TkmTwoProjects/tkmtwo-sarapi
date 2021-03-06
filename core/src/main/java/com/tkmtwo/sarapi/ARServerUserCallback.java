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
import com.bmc.arsys.api.ARServerUser;
import org.springframework.dao.DataAccessException;



/**
 * Describe interface <code>ARServerUserCallback</code> here.
 *
 * @author Tom Mahaffey
 * @version $Id$
 * @param <T> return type
 */
public interface ARServerUserCallback<T> {

  /**
   * Describe <code>doInARServerUser</code> method here.
   *
   * @param arsUser an <code>ARServerUser</code> value
   * @return an <code>Object</code> value
   * @exception ARException if an error occurs
   * @exception DataAccessException if an error occurs
   */
  T doInARServerUser(ARServerUser arsUser)
    throws ARException, DataAccessException;

}
