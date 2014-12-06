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
package com.tkmtwo.sarapi.mapping;

import static com.google.common.base.Preconditions.checkNotNull;


import com.bmc.arsys.api.Entry;
import com.tkmtwo.sarapi.ArsSchemaHelper;
import com.tkmtwo.sarapi.ArsTemplate;
import com.tkmtwo.sarapi.InterpolatingQualifierInfoCreator;
import com.tkmtwo.sarapi.convert.ArsConversionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataAccessException;


/**
 *
 * @param <T> object type
 */
public abstract class AbstractFormDao<T>
  implements InitializingBean {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass());  
  
  private ArsTemplate template;
  private ConversionService conversionService;
  private ArsSchemaHelper schemaHelper;
  private String formName;
  private EntryMapper<T> entryMapper;
  
  
  
  private MappingGetEntry<T> getEntry;
  private MappingCreateEntry<T> createEntry;
  private MappingDeleteEntry<T> deleteEntry;
  private MappingSetEntry<T> setEntry;
  private MappingMergeEntry<T> mergeEntry;
  


  
  public ArsTemplate getTemplate() { return template; }
  public void setTemplate(ArsTemplate at) { template = at; }
  
  public ConversionService getConversionService() { return conversionService; }
  public void setConversionService(ConversionService cs) { conversionService = cs; }
  
  public ArsSchemaHelper getSchemaHelper() { return schemaHelper; }
  public void setSchemaHelper(ArsSchemaHelper ash) { schemaHelper = ash; }
  
  public String getFormName() { return formName; }
  public void setFormName(String s) { formName = s; }
  
  public EntryMapper<T> getEntryMapper() { return entryMapper; }
  public void setEntryMapper(EntryMapper<T> em) { entryMapper = em; }
  
  
  public MappingGetEntry<T> getGetEntry() { return getEntry; }
  public void setGetEntry(MappingGetEntry<T> mge) { getEntry = mge; }

  public MappingCreateEntry<T> getCreateEntry() { return createEntry; }
  public void setCreateEntry(MappingCreateEntry<T> mce) { createEntry = mce; }

  public MappingDeleteEntry<T> getDeleteEntry() { return deleteEntry; }
  public void setDeleteEntry(MappingDeleteEntry<T> mde) { deleteEntry = mde; }

  public MappingSetEntry<T> getSetEntry() { return setEntry; }
  public void setSetEntry(MappingSetEntry<T> mse) { setEntry = mse; }

  public MappingMergeEntry<T> getMergeEntry() { return mergeEntry; }
  public void setMergeEntry(MappingMergeEntry<T> mme) { mergeEntry = mme; }
  
  
  public T getById(String id)
    throws DataAccessException {
    return getGetEntry().getEntry(id);
  }
  public void delete(String id)
    throws DataAccessException {
    getDeleteEntry().deleteEntry(id);
  }
  public void delete(T objectSource)
    throws DataAccessException {
    Entry entry = getEntryMapper().mapObject(objectSource);
    String entryId = entry.getEntryId();
    if (entryId != null) {
      getDeleteEntry().deleteEntry(entryId);
    }
  }
  
  public T save(T objectSource)
    throws DataAccessException {
    return createOrSet(objectSource);
  }
  
  public T createOrMerge(T objectSource)
    throws DataAccessException {
    
    Entry entry = getEntryMapper().mapObject(objectSource);
    String entryId = entry.getEntryId();
    if (entryId == null) {
      entryId = getCreateEntry().createEntry(entry);
    } else {
      entryId = getMergeEntry().mergeEntry(entry);
    }
    return getGetEntry().getEntry(entryId);
  }

      
  public T createOrSet(T objectSource)
    throws DataAccessException {
    
    Entry entry = getEntryMapper().mapObject(objectSource);
    String entryId = entry.getEntryId();
    if (entryId == null) {
      entryId = getCreateEntry().createEntry(entry);
    } else {
      getSetEntry().setEntry(entry);
    }
    
    return getGetEntry().getEntry(entryId);
  }
  
  public T mergeOrSet(T objectSource)
    throws DataAccessException {
    Entry entry = getEntryMapper().mapObject(objectSource);
    String entryId = entry.getEntryId();
    if (entryId == null) {
      entryId = getMergeEntry().mergeEntry(entry);
    } else {
      getSetEntry().setEntry(entry);
    }
    return getGetEntry().getEntry(entryId);
  }
  
  public T merge(T objectSource)
    throws DataAccessException {
    Entry entry = getEntryMapper().mapObject(objectSource);
    String entryId = getMergeEntry().mergeEntry(entry);
    return getGetEntry().getEntry(entryId);
  }

  public MappingQuery<T> newMappingQuery(String qs) {
    InterpolatingQualifierInfoCreator iqic = new InterpolatingQualifierInfoCreator();
    iqic.setQualifierString(qs);
    iqic.setSchemaHelper(getSchemaHelper());
    iqic.setFormName(getFormName());
    iqic.afterPropertiesSet();
    
    MappingQuery<T> mq = new MappingQuery<T>();
    mq.setQualifierInfoCreator(iqic);
    mq.setTemplate(getTemplate());
    mq.setSchemaHelper(getSchemaHelper());
    mq.setFormName(getFormName());
    mq.setEntryMapper(getEntryMapper());
    mq.afterPropertiesSet();
    
    return mq;
  }
  

  public MappingQuery<String> newStringMappingQuery(String qs,
                                                    String formName,
                                                    String fieldName) {
    InterpolatingQualifierInfoCreator iqic = new InterpolatingQualifierInfoCreator();
    iqic.setQualifierString(qs);
    iqic.setSchemaHelper(getSchemaHelper());
    iqic.setFormName(formName);
    iqic.afterPropertiesSet();
    
    MappingQuery<String> mq = new MappingQuery<String>();
    mq.setQualifierInfoCreator(iqic);
    mq.setTemplate(getTemplate());
    mq.setSchemaHelper(getSchemaHelper());
    mq.setFormName(formName);
    mq.setEntryMapper(SingleValueEntryMapper.forString(getTemplate(),
                                                       getConversionService(),
                                                       getSchemaHelper(),
                                                       formName,
                                                       fieldName));
    mq.afterPropertiesSet();
    
    return mq;
  }
  


  public abstract EntryMapper<T> newEntryMapper(ArsTemplate at,
                                                ConversionService cs,
                                                ArsSchemaHelper ash,
                                                String fn);
  public abstract void initDao()
    throws DataAccessException;

  public void afterPropertiesSet() {
    checkNotNull(getTemplate(), "Need an ArsTemplate");
    
    if (getConversionService() == null) {
      logger.info("No ConversionService found.  Creating a new ArsConversionService.");
      conversionService = new ArsConversionService();
    }
    
    checkNotNull(getSchemaHelper(), "Need an ArsSchemaHelper");
    checkNotNull(getFormName(), "Need a form name");

    if (getEntryMapper() == null) {
      logger.info("No EntryMapper, creating new with newEntryMapper()");
      setEntryMapper(newEntryMapper(getTemplate(), getConversionService(),
                                    getSchemaHelper(), getFormName()));
      logger.info("Created EntryMapper<T> {}", getEntryMapper().getClass().getName());
    }
    
    if (getGetEntry() == null) {
      logger.info("No MappingGetEntry<T> found, creating with {}", getEntryMapper().getClass().getName());
      MappingGetEntry<T> mge = new MappingGetEntry<T>();
      mge.setTemplate(getTemplate());
      mge.setConversionService(getConversionService());
      mge.setSchemaHelper(getSchemaHelper());
      mge.setFormName(getFormName());
      mge.setEntryMapper(getEntryMapper());
      mge.afterPropertiesSet();
      setGetEntry(mge);
    }
    if (getCreateEntry() == null) {
      logger.info("No MappingCreateEntry<T> found, creating with {}", getEntryMapper().getClass().getName());
      MappingCreateEntry<T> mce = new MappingCreateEntry<T>();
      mce.setTemplate(getTemplate());
      mce.setConversionService(getConversionService());
      mce.setSchemaHelper(getSchemaHelper());
      mce.setFormName(getFormName());
      mce.setEntryMapper(getEntryMapper());
      mce.afterPropertiesSet();
      setCreateEntry(mce);
    }
    if (getDeleteEntry() == null) {
      logger.info("No MappingDeleteEntry<T> found, creating with {}", getEntryMapper().getClass().getName());
      MappingDeleteEntry<T> mde = new MappingDeleteEntry<T>();
      mde.setTemplate(getTemplate());
      mde.setConversionService(getConversionService());
      mde.setSchemaHelper(getSchemaHelper());
      mde.setFormName(getFormName());
      mde.setEntryMapper(getEntryMapper());
      mde.afterPropertiesSet();
      setDeleteEntry(mde);
    }
    if (getSetEntry() == null) {
      logger.info("No MappingSetEntry<T> found, creating with {}", getEntryMapper().getClass().getName());
      MappingSetEntry<T> mse = new MappingSetEntry<T>();
      mse.setTemplate(getTemplate());
      mse.setConversionService(getConversionService());
      mse.setSchemaHelper(getSchemaHelper());
      mse.setFormName(getFormName());
      mse.setEntryMapper(getEntryMapper());
      mse.afterPropertiesSet();
      setSetEntry(mse);
    }
    if (getMergeEntry() == null) {
      logger.info("No MappingMergeEntry<T> found, creating with {}", getEntryMapper().getClass().getName());
      MappingMergeEntry<T> mme = new MappingMergeEntry<T>();
      mme.setTemplate(getTemplate());
      mme.setConversionService(getConversionService());
      mme.setSchemaHelper(getSchemaHelper());
      mme.setFormName(getFormName());
      mme.setEntryMapper(getEntryMapper());
      mme.afterPropertiesSet();
      setMergeEntry(mme);
    }
    
    initDao();

  }
  
  
  
}
