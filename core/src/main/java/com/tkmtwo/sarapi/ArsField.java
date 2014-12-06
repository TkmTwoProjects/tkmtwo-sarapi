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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.TkmTwoConditions.checkNotBlank;

import com.bmc.arsys.api.Field;
import com.google.common.base.Objects;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




/*
AR_FIELD_TYPE_DATA          1  per record stored data field type 
AR_FIELD_TYPE_TRIM          2  visual trim field type
AR_FIELD_TYPE_CONTROL       4  GUI control field type
AR_FIELD_TYPE_PAGE          8  page field type 
AR_FIELD_TYPE_PAGE_HOLDER  16  page holder field type
AR_FIELD_TYPE_TABLE        32  table field type
AR_FIELD_TYPE_COLUMN       64  column field type
AR_FIELD_TYPE_ATTACH      128  attachment field type
AR_FIELD_TYPE_ATTACH_POOL 256  attachment pool type

AR_FIELD_TYPE_ALL   (AR_FIELD_TYPE_DATA | AR_FIELD_TYPE_TRIM | \
                     AR_FIELD_TYPE_CONTROL | AR_FIELD_TYPE_PAGE | \
                     AR_FIELD_TYPE_PAGE_HOLDER | AR_FIELD_TYPE_TABLE | \
                     AR_FIELD_TYPE_COLUMN | AR_FIELD_TYPE_ATTACH | \
                     AR_FIELD_TYPE_ATTACH_POOL)

 */
/**
 *
 */
public class ArsField
  implements Serializable {

  private static final long serialVersionUID = 1L;
  
  private Integer id;
  private String name;
  private ArsDataType dataType;
  private Field arField;
  
  private Map<Integer, String> enums;
  
  public ArsField(Integer i, String s, ArsDataType adt) {
    setId(i);
    setName(s);
    setDataType(adt);
  }
  public ArsField(Integer i, String s, Integer adt) {
    setId(i);
    setName(s);
    setDataType(adt);
  }
  
  public Field getArField() { return arField; }
  public void setArField(Field f) { arField = f; }
  public Integer getId() { return id; }
  private void setId(Integer i) {
    checkArgument((checkNotNull(i, "Integer is null.").intValue() > 0),
                  "Field id must be a positive integer.");
    id = i;
  }
  
  public String getName() { return name; }
  private void setName(String s) {
    name = checkNotBlank(s, "Field name is blank.");
  }
  
  public ArsDataType getDataType() { return dataType; }
  private void setDataType(ArsDataType adt) {
    dataType = checkNotNull(adt, "Data type may not be null.");
  }
  private void setDataType(Integer i) {
    setDataType(ArsDataType.valueOfArInt((checkNotNull(i, "Data type may not be null.").intValue())));
  }


  private Map<Integer, String> getEnumsInternal() {
    if (enums == null) {
      enums = new HashMap<Integer, String>();
    }
    return enums;
  }
  public Map<Integer, String> getEnums() {
    return Collections.unmodifiableMap(getEnumsInternal());
  }
  public void addEnum(Integer i, String s) {
    
    if (i == null) {
      throw new IllegalArgumentException("Enumerated integer may not be null.");
    }
    if (i.intValue() < 0) {
      throw new IllegalArgumentException("Enumerated integer may not be negative.");
    }

    getEnumsInternal().put(i, s);
    
                         
  }


  
  /*
    TODO:
    
    Maybe equals and hashcode should be just id, or just name...

  */
  @Override
  public boolean equals(Object obj) {
    if (obj == this) { return true; }
    if (!(obj instanceof ArsField)) { return false; }
    ArsField impl = (ArsField) obj;
    
    return
      Objects.equal(id, impl.id)
      && Objects.equal(name, impl.name)
      && Objects.equal(dataType, impl.dataType);
  }



  @Override
  public int hashCode() {
    return Objects.hashCode(id, name, dataType);
  }

  private String mapToString(Map<Integer, String> m) {
    List<String> sbChunks = new ArrayList<String>();
    for (Map.Entry<Integer, String> mapEntry : m.entrySet()) {
      sbChunks.add(mapEntry.getKey().toString() + "=" + mapEntry.getValue());
    }

    StringBuffer sb = new StringBuffer();
    for (String s : sbChunks) {
      sb.append(s).append(',');
    }
    return sb.toString();
    

  }

  @Override
  public String toString() {
    Objects.ToStringHelper tsh =
      Objects.toStringHelper(this)
      .add("id", id)
      .add("name", name)
      .add("dataType", dataType);
    if (ArsDataType.ENUM == getDataType()) {
      tsh.add("enums", mapToString(getEnums()));
    }
    return tsh.toString();
  }
  
}
