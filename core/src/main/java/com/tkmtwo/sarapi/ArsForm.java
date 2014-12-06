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
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 *
 */
public class ArsForm
  implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  private String name;
  private Set<ArsField> fields;

  public ArsForm(String s) {
    setName(s);
  }
  public ArsForm(String s, Set<ArsField> fs) {
    setName(s);
    setFields(fs);
  }
  
  
  public String getName() { return name; }
  private void setName(String s) {
    checkNotBlank(s, "Form name is blank.");
    /*
    if (Strings.isBlank(s)) {
      throw new IllegalArgumentException("Form name may not be blank.");
    }
    */
    name = s; 
  }
  


  private Set<ArsField> getFieldsInternal() {
    if (fields == null) {
      fields = new HashSet<ArsField>();
    }
    return fields;
  }
  
  public Set<ArsField> getFields() {
    return Collections.unmodifiableSet(getFieldsInternal());
  }
  
  public void setFields(Set<ArsField> s) {
    if (s == null || s.isEmpty()) {
      return;
    }
    fields = s;
  }
  
  public void addField(ArsField af) {
    if (af == null) { return; }
    getFieldsInternal().add(af);
  }
  public void addField(Integer i, String s, ArsDataType adt) {
    getFieldsInternal().add(new ArsField(i, s, adt));
  }
  
  
  
}
