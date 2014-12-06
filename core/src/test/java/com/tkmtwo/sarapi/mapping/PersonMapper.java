package com.tkmtwo.sarapi.mapping;


import com.bmc.arsys.api.Entry;
import com.bmc.arsys.api.Value;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.Map;
import java.util.Set;
import com.tkmtwo.sarapi.InvalidEntryAccessException;
import org.springframework.beans.BeanWrapper;



public final class PersonMapper
  extends AbstractEntryMapper<Person> {
  
  
  private static final Map<String, String> PROPERTY_TO_FIELDNAME = new ImmutableMap.Builder<String, String>()
    .put("status", "Status")
    .put("createDateTime", "Create Date")
    .put("modifyDateTime", "Modified Date")
    .put("firstName", "First Name")
    .put("lastName", "Last Name")
    .put("age", "Age")
    .put("gender", "Gender")
    .put("workPhone.fullNumber", "WorkFullNumber")
    .put("workPhone.countryCode", "WorkCcNumber")
    .put("workPhone.areaCode", "WorkAcNumber")
    .put("workPhone.localNumber", "WorkLocalNumber")
    .put("mobilePhone.fullNumber", "MobileFullNumber")
    .put("mobilePhone.countryCode", "MobileCcNumber")
    .put("mobilePhone.areaCode", "MobileAcNumber")
    .put("mobilePhone.localNumber", "MobileLocalNumber")
    .build();
  
  
  
  
  private static final Set<String> FIELD_NAMES = new ImmutableSet.Builder<String>()
    .addAll(PROPERTY_TO_FIELDNAME.values())
    .build();
  
  
  protected Map<String, String> propertyToFieldNames() { return PROPERTY_TO_FIELDNAME; }
  public Set<String> getMappedFieldNames() { return FIELD_NAMES; }
  
  
  
  
  
  
  
  
  
  public Entry mapObject(Person source)
    throws InvalidEntryAccessException {
    
    if (source == null) { return null; }
    Entry entry = new Entry();
    
    if (source.getId() != null) {
      entry.setEntryId(source.getId());
    }
    
    BeanWrapper bw = newBeanWrapper(source);
    setEntryValues(bw, entry, propertyToFieldNames());
    
    return entry;
  }
  
  
  
  
  
  
  
  public Person mapEntry(Entry entry)
    throws InvalidEntryAccessException {
    
    if (entry == null) { return null; }
    
    Person p = new Person();
    p.setId(entry.getEntryId());
    
    BeanWrapper bw = newBeanWrapper(p);
    setPropertyValues(bw, entry, propertyToFieldNames());
    
    return p;
  }
  
  
  
}
