package com.tkmtwo.sarapi.mapping;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.bmc.arsys.api.Entry;
import com.bmc.arsys.api.Value;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.tkmtwo.sarapi.ArsDataType;
import com.tkmtwo.sarapi.ArsSchemaHelper;
import com.tkmtwo.sarapi.ArsTemplate;
import com.tkmtwo.sarapi.support.EntryUtil;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;



public final class PersonFormDao
  extends AbstractFormDao<Person> {
  
  
  MappingQuery<Person> personByFirstName = null;
  MappingQuery<String> testFieldsByCharacterField = null;
  
  public EntryMapper<Person> newEntryMapper(ArsTemplate at,
                                            ConversionService cs,
                                            ArsSchemaHelper ash,
                                            String fn) {
    PersonMapper pm = new PersonMapper();
    pm.setTemplate(getTemplate());
    pm.setConversionService(getConversionService());
    pm.setSchemaHelper(getSchemaHelper());
    pm.setFormName(getFormName());
    pm.afterPropertiesSet();
    return pm;
  }
  
    
  public void initDao()
    throws DataAccessException {
    logger.info("PersonFormDao coming up");
    
    personByFirstName = newMappingQuery("'First Name' LIKE ${firstName}");
    testFieldsByCharacterField = newStringMappingQuery("'Character Field' = ${charField}",
                                                       "SARAPI:TestFields",
                                                       "Character Field");
    
    
  }
  
  public List<String> testFieldsByCharacterField(String charField) {
    ImmutableMap<String, Value> vs = ImmutableMap.of("charField", ArsDataType.CHAR.getArValue(charField));
    return testFieldsByCharacterField.getObjects(vs);
  }
  public List<Person> personsByFirstNameLike(String firstNameLike) {
    return personByFirstName.getObjects(ImmutableMap.of("firstName",
                                                        ArsDataType.CHAR.getArValue(firstNameLike)));
  }
  public Person personByFirstNameLike(String firstNameLike) {
    return personByFirstName.getObject(ImmutableMap.of("firstName",
                                                       ArsDataType.CHAR.getArValue(firstNameLike)));
  }
  public Person personByFirstNameLikeNullable(String firstNameLike) {
    return personByFirstName.getObjectNullable(ImmutableMap.of("firstName",
                                                               ArsDataType.CHAR.getArValue(firstNameLike)));
  }

  
  
}
