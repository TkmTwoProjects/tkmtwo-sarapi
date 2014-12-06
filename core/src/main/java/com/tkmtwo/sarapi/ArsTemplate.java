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
import com.bmc.arsys.api.Constants;
import com.bmc.arsys.api.Entry;
import com.bmc.arsys.api.Field;
import com.bmc.arsys.api.OutputInteger;
import com.bmc.arsys.api.QualifierInfo;
import com.bmc.arsys.api.SortInfo;
import com.bmc.arsys.api.StatusInfo;
import com.bmc.arsys.api.Timestamp;
import com.google.common.base.Stopwatch;
import com.tkmtwo.sarapi.support.ArsDataSourceUtil;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;


/**
 *
 */
public class ArsTemplate
  extends ArsAccessor
  implements ArsOperations {
  
  
  
  public ArsTemplate() { }

  /*
  public static ArsTemplate newInstance(ArsUserSource aus) {
    ARExceptionTranslator aret = null;
    return newInstance(aus, aret);
  }
  */
  public static ArsTemplate newInstance(ArsUserSource aus, ARExceptionTranslator aret) {
    ArsTemplate at = new ArsTemplate();
    at.setUserSource(aus);
    at.setExceptionTranslator(aret);
    at.afterPropertiesSet();
    return at;
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  public <T> T execute(ARServerUserCallback<T> action, String description)
    throws DataAccessException {

    Stopwatch sw = Stopwatch.createStarted();
    T result = execute(action);
    sw.stop();

    logger.trace("Executed callback {} in {}",
                 description, sw.toString());
    return result;
  }
  
  
  
  public <T> T execute(ARServerUserCallback<T> action)
    throws DataAccessException {

    ARServerUser arsUser = 
      ArsDataSourceUtil.getARServerUser(getUserSource());
    
    logger.trace("Using uname {} impersonating {}",
                 arsUser.getUser(), arsUser.getImpersonatedUser());
    try {
      T resultObject = action.doInARServerUser(arsUser);
      
      
      List<StatusInfo> lastStatus = arsUser.getLastStatus();
      if (lastStatus != null
          && !lastStatus.isEmpty()) {
        for (StatusInfo statusInfo : lastStatus) {
          //TODO(mahaffey): maybe add something here to skip "OK" messages?
          logger.warn("Status Message: " + statusInfo.toString());
        }
      }
      
      return resultObject;
      
    } catch (ARException arex) {
      throw getExceptionTranslator().translate(arex);
    } finally {
      ArsDataSourceUtil.releaseARServerUser(arsUser, getUserSource());
    }
  }
  
  
  
  
  
  

































  public List<Field> getAllFields(final String formName)
    throws DataAccessException {
    return getListFieldObjects(formName, Constants.AR_FIELD_TYPE_ALL);
  }

  public List<Field> getDataFields(final String formName)
    throws DataAccessException {
    return getListFieldObjects(formName, Constants.AR_FIELD_TYPE_DATA);
  }


  /*
   *
   * AR_FIELD_TYPE_ALL           Retrieves and stores all field types.
   * AR_FIELD_TYPE_ATTACH        Retrieve or store attachment field type.
   * AR_FIELD_TYPE_ATTACH_POOL   Retrieve or store attachment pool type.
   * AR_FIELD_TYPE_COLUMN        Retrieve or store column fields.
   * AR_FIELD_TYPE_CONTROL       Retrieve or store control fields.
   * AR_FIELD_TYPE_DATA          Retrieve or store data fields.
   * AR_FIELD_TYPE_PAGE          Retrieve or store page fields.
   * AR_FIELD_TYPE_PAGE_HOLDER   Retrieve or store page holder fields.
   * AR_FIELD_TYPE_TABLE         Retrieve or store table fields.
   * AR_FIELD_TYPE_TRIM          Retrieve or store trim fields.
   *
   */

  public List<Field> getListFieldObjects(final String formName, final int fieldTypeMask)
    throws DataAccessException {

    String cbDescr = "Get fields from form " + formName + " field type " + String.valueOf(fieldTypeMask);
    List<Field> result =
      execute(new ARServerUserCallback<List<Field>>() {
          public List<Field> doInARServerUser(ARServerUser arsu)
            throws ARException {
            return arsu.getListFieldObjects(formName, fieldTypeMask);
          }
        }, cbDescr);
    return result;
  }
  
  
  
  
  
  

  public String createEntry(final String formName,
                            final Entry entry)
    throws DataAccessException {

    String result =
      execute(new ARServerUserCallback<String>() {
          public String doInARServerUser(ARServerUser arsu)
            throws ARException {
            return arsu.createEntry(formName, entry);
          }
        });
    return result;
  }    

  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  public QualifierInfo parseQualification(final String formName,
                                          final String qs)
    throws DataAccessException {

    QualifierInfo result =
      execute(new ARServerUserCallback<QualifierInfo>() {
          public QualifierInfo doInARServerUser(ARServerUser arsu)
            throws ARException {
            
            return arsu.parseQualification(formName, qs);
            
          }
        });
    return result;
  }
  
  
  /*
   * Get exactly one by id.  Throws exception ARERR 302 if not loaded
   *
   */
  public Entry getEntry(final String formName,
                        final String entryId,
                        final int[] fieldIds)
    throws DataAccessException {
    Entry result =
      execute(new ARServerUserCallback<Entry>() {
          public Entry doInARServerUser(ARServerUser arsu)
          throws ARException {
            Entry entry =
            arsu.getEntry(formName,
                          entryId,
                          fieldIds);
            return entry;
          }
        });
    return result;
  }
  
  
  
  /*
   * Query for one Entry.
   *
   */
  public Entry getListEntryObject(String formName,
                                  QualifierInfo qualifierInfo,
                                  int firstRetrieve,
                                  int maxRetrieve,
                                  List<SortInfo> sortList,
                                  int[] fieldIds,
                                  boolean useLocale,
                                  OutputInteger numMatches)
    throws DataAccessException {

    List<Entry> entries = getListEntryObjects(formName, qualifierInfo,
                                              firstRetrieve, maxRetrieve,
                                              sortList, fieldIds,
                                              useLocale, numMatches);
    if (entries.size() != 1) {
      throw new IncorrectResultSizeDataAccessException("Expected exactly one in result set.",
                                                       1, entries.size());
    }
    return entries.get(0);
  }


  /*
   * Query for zero or one Entry.
   *
   */
  public Entry getListEntryObjectNullable(String formName,
                                          QualifierInfo qualifierInfo,
                                          int firstRetrieve,
                                          int maxRetrieve,
                                          List<SortInfo> sortList,
                                          int[] fieldIds,
                                          boolean useLocale,
                                          OutputInteger numMatches)
    throws DataAccessException {

    List<Entry> entries = getListEntryObjects(formName, qualifierInfo,
                                              firstRetrieve, maxRetrieve,
                                              sortList, fieldIds,
                                              useLocale, numMatches);
    
    if (entries.size() > 1) {
      throw new IncorrectResultSizeDataAccessException("Expected one or none in results set.",
                                                       1, entries.size());
    }

    return (entries.size() == 1) ? entries.get(0) : null;
  }

  
  /*
   * Query for List of Entry objects.
   *
   */
  public List<Entry> getListEntryObjects(final String formName,
                                         final QualifierInfo qualifierInfo,
                                         final int firstRetrieve,
                                         final int maxRetrieve,
                                         final List<SortInfo> sortList,
                                         final int[] fieldIds,
                                         final boolean useLocale,
                                         final OutputInteger numMatches)
    throws DataAccessException {
    
    List<Entry> result =
      execute(new ARServerUserCallback<List<Entry>>() {
          public List<Entry> doInARServerUser(ARServerUser arsu) throws ARException {
            List<Entry> entries =
            arsu.getListEntryObjects(formName,
                                     qualifierInfo,
                                     firstRetrieve,
                                     maxRetrieve,
                                     sortList,
                                     fieldIds,
                                     useLocale,
                                     numMatches);
            return entries;
          }
        });
    return result;
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  public void setEntry(final String formName,
                       final String entryId,
                       final Entry entry)
    throws DataAccessException {
    
    setEntry(formName,
             entryId,
             entry,
             new Timestamp(0),
             Constants.AR_JOIN_SETOPTION_NONE);
  }
  public void setEntry(final String formName,
                       final String entryId,
                       final Entry entry,
                       final Timestamp ts,
                       final int nOption)
    throws DataAccessException {

    Boolean result =
      execute(new ARServerUserCallback<Boolean>() {
          public Boolean doInARServerUser(ARServerUser arsu)
            throws ARException {
            
            arsu.setEntry(formName,
                          entryId,
                          entry,
                          ts,
                          nOption);
            return Boolean.TRUE;
          }
        });
  }

  /*
    nMergeType - A value indicating the action to take if the Entry ID already exists in the target form.
    This parameter is ignored if you do not specify the Entry ID or the ID specified does not conflict
    with existing entry IDs.

    Generate an error (AR_MERGE_ENTRY_DUP_ERROR) = 1
    Create a new entry with a new ID (AR_MERGE_ENTRY_DUP_NEW_ID) = 2
    Delete the existing entry and create a new one in its place ( AR_MERGE_ENTRY_DUP_OVERWRITE) = 3
    Update the fields specified in fieldList in the existing entry ( AR_MERGE_ENTRY_DUP_MERGE) = 4
    To omit some field validation steps, add the appropriate increments to the merge type.
    
    Allow NULL in required fields (not applicable for the Submitter, Status, or Short-Description core fields)
    (AR_MERGE_NO_REQUIRED_INCREMENT) = 1024
    Skip field pattern checking (including $MENU$) ( AR_MERGE_NO_PATTERNS_INCREMENT) = 2048 
  */
  public String mergeEntry(final String formName,
                           final Entry entry) {
    return mergeEntry(formName, entry,
                      Constants.AR_MERGE_ENTRY_DUP_MERGE);
  }
  public String mergeEntry(final String formName,
                           final Entry entry,
                           final int nMergeType) {
    String result =
      execute(new ARServerUserCallback<String>() {
          public String doInARServerUser(ARServerUser arsu)
            throws ARException {
            return arsu.mergeEntry(formName,
                                   entry,
                                   nMergeType);
          }
        });
    return result;
  }

  
  
  








      

  public void deleteEntry(final String formName,
                         final String entryId)
    throws DataAccessException {

    deleteEntry(formName, entryId, 0);
  }

  public void deleteEntry(final String formName,
                          final String entryId,
                          final int deleteOption)
    throws DataAccessException {

    String cbDescr = "Delete entry from " + formName + ": ";
    Boolean result =
      execute(new ARServerUserCallback<Boolean>() {
          public Boolean doInARServerUser(ARServerUser arsu)
            throws ARException {

            logger.trace("Deleting {}.{}", formName, entryId);
            arsu.deleteEntry(formName,
                             entryId,
                             deleteOption);
            return Boolean.TRUE;
          }
        }, cbDescr);

  }
  
  
  
}

