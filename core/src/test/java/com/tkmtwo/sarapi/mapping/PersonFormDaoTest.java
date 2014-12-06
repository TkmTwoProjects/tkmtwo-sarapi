package com.tkmtwo.sarapi.mapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;


import com.bmc.arsys.api.Entry;
import com.bmc.arsys.api.Value;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.tkmtwo.sarapi.ArsDataType;
import com.tkmtwo.sarapi.support.EntryUtil;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;


@ContextConfiguration
public final class PersonFormDaoTest
  extends AbstractJUnit4SpringContextTests
{
  
  @Autowired
  protected PersonFormDao personDao;
  
  
  @Test
  public void testCrud() {
    String id = null;
    String firstName = "FormDaoFirstName";
    String lastName = "FormDaoLastName";
    Integer age = Integer.valueOf(73);
    String gender = "Male";


    Person p = new Person();
    p.setFirstName(firstName);
    p.setLastName(lastName);

    System.out.println("Begin  : " + p.toString());
    p = personDao.save(p);
    System.out.println("Create : " + p.toString());


    assertNotNull(p);
    assertNotNull(p.getId());
    id = p.getId();
    assertEquals(firstName, p.getFirstName());
    assertEquals(lastName, p.getLastName());
    assertNull(p.getAge());
    assertNull(p.getGender());

    p.setAge(age);
    p = personDao.createOrSet(p);
    System.out.println("Set    : " + p.toString());
    assertNotNull(p);
    assertEquals(id, p.getId());
    assertEquals(firstName, p.getFirstName());
    assertEquals(lastName, p.getLastName());
    assertEquals(age, p.getAge());
    assertNull(p.getGender());

    p.setGender(gender);
    p = personDao.createOrMerge(p);
    System.out.println("Merge  : " + p.toString());
    assertNotNull(p);
    assertEquals(id, p.getId());
    assertEquals(firstName, p.getFirstName());
    assertEquals(lastName, p.getLastName());
    assertEquals(age, p.getAge());
    assertEquals(gender, p.getGender());
    
    p.setId(null);
    p = personDao.save(p);
    System.out.println("Dup    : " + p.toString());
    
  }


  @Test
  public void testPersonsByFirstNameLike() {
    List<Person> persons = personDao.personsByFirstNameLike("SetFirst%");
    assertTrue(!persons.isEmpty());
    for (Person person : persons) {
      assertTrue(person.getFirstName().startsWith("SetFirst"));
    }
  }

  @Test
  public void testPersonByFirstNameLike() {
    Person person = personDao.personByFirstNameLike("%Isaac%");
    assertNotNull(person);
    assertEquals("Newton", person.getLastName());
  }
  
  @Test(expected = IncorrectResultSizeDataAccessException.class)
  public void testPersonByFirstNameLikeFailTooMany() {
    Person person = personDao.personByFirstNameLike("Create%");
    assertNotNull(person);
    assertEquals("Newton", person.getLastName());
  }
  
  @Test(expected = IncorrectResultSizeDataAccessException.class)
  public void testPersonByFirstNameLikeFailNotEnough() {
    Person person = personDao.personByFirstNameLike("%NoSuchName%");
    assertNotNull(person);
    assertEquals("Newton", person.getLastName());
  }
  
  @Test
  public void testPersonByFirstNameLikeNullable() {
    Person person = personDao.personByFirstNameLikeNullable("%NoSuchName%");
    assertNull(person);
  }
  
  @Test
  public void testTestFieldsByCharacterField() {
    List<String> cfs = personDao.testFieldsByCharacterField("AAA");
    System.out.println("There are " + cfs.size() + " of them");
    for (String cf : cfs) {
      System.out.println("I see " + cf);
    }
  }
  
}
