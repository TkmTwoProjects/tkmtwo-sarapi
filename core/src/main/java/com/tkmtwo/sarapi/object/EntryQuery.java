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
package com.tkmtwo.sarapi.object;

import static com.google.common.base.Preconditions.checkNotNull;

import com.bmc.arsys.api.Constants;
import com.bmc.arsys.api.Entry;
import com.bmc.arsys.api.OutputInteger;
import com.bmc.arsys.api.SortInfo;
import com.bmc.arsys.api.Value;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
//import com.google.common.collect.ImmutableMap;
import com.tkmtwo.sarapi.ArsField;
import com.tkmtwo.sarapi.NoneQualifierInfoCreator;
import com.tkmtwo.sarapi.QualifierInfoCreator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;



/**
 *
 */
public class EntryQuery
  extends ArsFormOperation {

  public static final List<Value> EMPTY_VALUE_LIST = ImmutableList.of();
  //public static Map<String, Value> EMPTY_VALUE_MAP = ImmutableMap.of();
  
  private QualifierInfoCreator qualifierInfoCreator = 
    new NoneQualifierInfoCreator();
  
  private List<SortInfo> sortInfos = new ArrayList<SortInfo>();
  private List<SortInfo> compiledSortInfos;


  private List<String> fieldNames = ImmutableList.of();
  private List<ArsField> fields = ImmutableList.of();
  private int[] fieldIds = new int[0];


  public List<String> getFieldNames() { return fieldNames; }
  public void setFieldNames(List<String> l) { fieldNames = ImmutableList.copyOf(l); }
  
  protected List<ArsField> getFields() { return fields; }
  protected void setFields(List<ArsField> l) { fields = ImmutableList.copyOf(l); }

  private int[] getFieldIds() { return fieldIds; }
  private void setFieldIds(int[] is) { fieldIds = is; }
  
  
  public void setFieldNameSource(FieldNameSource fns) {
    fieldNames = ImmutableList.copyOf(fns.getFieldNames());
  }
  
  
  
  public EntryQuery() { }
  
  public QualifierInfoCreator getQualifierInfoCreator() {
    return qualifierInfoCreator;
  }
  public void setQualifierInfoCreator(QualifierInfoCreator qic) {
    if (qic == null) {
      throw new InvalidDataAccessApiUsageException("QualifierInfoCreator "
                                                   + "may not be null.");
    }
    
    qualifierInfoCreator = qic;
  }

  protected List<SortInfo> getCompiledSortInfos() {
    return compiledSortInfos;
  }
  
  
  
  
  
  
  
  public List<SortInfo> getSortInfos() {
    if (sortInfos == null) {
      sortInfos = new ArrayList<SortInfo>();
    }
    return sortInfos;
  }
  public void setSortInfos(List<SortInfo> sis) {
    //assertNotCompiled();
    this.sortInfos = sis;
  }
  public void addSortInfo(SortInfo si) {
    //assertNotCompiled();
    
    this.sortInfos.add(si);
  }
  
  
  
  private int evalSortDirection(String s) {
    checkNotNull(s, "Sort direction is null.");
    /*
      FYI...
      Constants.AR_SORT_ASCENDING        1
      Constants.AR_SORT_DESCENDING       2
    */

    //if (StringUtils.startsWithIgnoreCase(s, "desc")) {
    if (s.startsWith("desc")) {
      return Constants.AR_SORT_DESCENDING;
    }
    
    return Constants.AR_SORT_ASCENDING;
  }
  
  //give me map of field names  -> sort directions (asc, desc)
  public void setSortSpecs(Map<String, String> sortSpecs) {
    //assertHasHelper();
    List<SortInfo> sis = ImmutableList.of();  //Collections.emptyList();
    
    //if (MapUtils.isEmpty(sortSpecs)) {
    if (sortSpecs == null || sortSpecs.isEmpty()) {
      logger.warn("sortSpecs map is empty.");
      setSortInfos(sis);
    }
    
    sis = new ArrayList<SortInfo>();

    /*
    for (String fieldName : sortSpecs.keySet()) {
      int fieldId = getArsSchemaHelper().getFieldId(getFormName(),
                                                    fieldName);
      sis.add(new SortInfo(fieldId,
                           evalSortDirection(sortSpecs.get(fieldName))));



    }
    */
    for (Map.Entry<String, String> me : sortSpecs.entrySet()) {
      int fieldId = getSchemaHelper().getFieldId(getFormName(),
                                                    me.getKey());
      sis.add(new SortInfo(fieldId,
                           evalSortDirection(me.getValue())));
    }
    setSortInfos(sis);
  }

  public void addSortSpec(String fieldName, String sortDirName) {
    //assertHasHelper();

    /*
    Field field = getArsDefinitionHelper().getField(getFormName(),
                                                    fieldName);
    addSortInfo(new SortInfo(field.getFieldID(),
                             evalSortDirection(sortDirName)));
    */

    int fieldId = getSchemaHelper().getFieldId(getFormName(),
                                                  fieldName);
    addSortInfo(new SortInfo(fieldId,
                             evalSortDirection(sortDirName)));


  }


  public void addSortSpec(Integer fieldId, String sortDirName) {
    if (fieldId == null) {
      return;
    }
    
    addSortInfo(new SortInfo(fieldId,
                             evalSortDirection(sortDirName)));
  }
  
  
  




    
  


  
  
  
  
  protected void compileSortInfos() {
    logger.debug("Sort Info being compiled.");

    if (getSortInfos().isEmpty()) {
      logger.debug("SortInfo list is empty, no sorting will be performed.");
      compiledSortInfos = Collections.emptyList();
      return;
    }
    
    int sortInfosSize = getSortInfos().size();
    List<SortInfo> l = new ArrayList<SortInfo>(sortInfosSize);
    for (int i = 0; i < sortInfosSize; i++) {
      SortInfo si = getSortInfos().get(i);
      if (si == null) {
        logger.warn("SortInfo at position {} is null.", i);
        continue;
      }
      l.add(si);
    }
    
    logger.debug("compileSortInfo complete with {} SortInfo objects.", l.size());
 
    compiledSortInfos = ImmutableList.copyOf(l);
    //compiledSortInfos = Collections.unmodifiableList(l);
  }

  
  
  
  
  
  public void afterPropertiesSet() {
    super.afterPropertiesSet();

    setFields(getSchemaHelper().compileFieldsByName(getFormName(), getFieldNames()));
    setFieldIds(getSchemaHelper().compileFieldIds(getFields()));

    compileSortInfos();
  }
  
  
  
  
  
  
  
  









  public List<Entry> getEntries()
    throws DataAccessException {

    return getEntries(EMPTY_VALUE_LIST);
  }
  public List<Entry> getEntries(List<Value> values)
    throws DataAccessException {

    return getEntries(values,
                      Constants.AR_START_WITH_FIRST_ENTRY,
                      Constants.AR_NO_MAX_LIST_RETRIEVE,
                      false,
                      null);
  }
  public List<Entry> getEntries(List<Value> values,
                                int firstRetrieve,
                                int maxRetrieve,
                                boolean useLocale,
                                OutputInteger numMatches)
    throws DataAccessException {

    //assertIsCompiled();
    
    
    return getTemplate()
      .getListEntryObjects(getFormName(),
                           qualifierInfoCreator.createQualifierInfo(values),
                           firstRetrieve,
                           maxRetrieve,
                           compiledSortInfos,
                           getFieldIds(),
                           useLocale,
                           numMatches);
  }
  







  ///
  /// one exactly
  public Entry getEntry()
    throws DataAccessException {

    return getEntry(EMPTY_VALUE_LIST);
  }
  public Entry getEntry(List<Value> values)
    throws DataAccessException {

    return getEntry(values,
                      Constants.AR_START_WITH_FIRST_ENTRY,
                      Constants.AR_NO_MAX_LIST_RETRIEVE,
                      false,
                      null);
  }
  public Entry getEntry(List<Value> values,
                        int firstRetrieve,
                        int maxRetrieve,
                        boolean useLocale,
                        OutputInteger numMatches)
    throws DataAccessException {

    //assertIsCompiled();

    return getTemplate()
      .getListEntryObject(getFormName(),
                          qualifierInfoCreator.createQualifierInfo(values),
                          firstRetrieve,
                          maxRetrieve,
                          compiledSortInfos,
                          getFieldIds(),
                          useLocale,
                          numMatches);


    /*    
    List<Entry> entries = 
      getTemplate()
      .getListEntryObject(getFormName(),
                          qualifierInfoCreator.createQualifierInfo(values),
                          firstRetrieve,
                          maxRetrieve,
                          compiledSortInfos,
                          getFieldIds(),
                          useLocale,
                          numMatches);
    return entries.get(0);
    */
  }




  public Entry getEntryNullable()
    throws DataAccessException {

    return getEntryNullable(EMPTY_VALUE_LIST);
  }
  public Entry getEntryNullable(List<Value> values)
    throws DataAccessException {

    return getEntryNullable(values,
                      Constants.AR_START_WITH_FIRST_ENTRY,
                      Constants.AR_NO_MAX_LIST_RETRIEVE,
                      false,
                      null);
  }
  public Entry getEntryNullable(List<Value> values,
                                int firstRetrieve,
                                int maxRetrieve,
                                boolean useLocale,
                                OutputInteger numMatches)
    throws DataAccessException {

    //assertIsCompiled();
    


    return getTemplate()
      .getListEntryObjectNullable(getFormName(),
                                  qualifierInfoCreator.createQualifierInfo(values),
                                  firstRetrieve,
                                  maxRetrieve,
                                  compiledSortInfos,
                                  getFieldIds(),
                                  useLocale,
                                  numMatches);
    /*
    List<Entry> entries = 
      getTemplate()
      .getListEntryObjectNullable(getFormName(),
                                  qualifierInfoCreator.createQualifierInfo(values),
                                  firstRetrieve,
                                  maxRetrieve,
                                  compiledSortInfos,
                                  getFieldIds(),
                                  useLocale,
                                  numMatches);
    if (entries.isEmpty()) {
      return null;
    }

    return entries.get(0);
    */
  }
  
  
  public String toString() {
    return Objects.toStringHelper(this)
      .add("arsUserSource", getTemplate().getUserSource())
      .add("formName", getFormName())
      .add("qualifierInfoCreator", getQualifierInfoCreator())
      .toString();
  }


  
}

