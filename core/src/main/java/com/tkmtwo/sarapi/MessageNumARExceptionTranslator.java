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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;


/**
 *
 */
public class MessageNumARExceptionTranslator
  implements ARExceptionTranslator {

  //TODO(mahaffey): make this return exceptions from properties

  private static final Logger logger = LoggerFactory.getLogger(MessageNumARExceptionTranslator.class);


  public DataAccessException translate(ARException arex) {
      return new UncategorizedArsException(arex);
  }
}
