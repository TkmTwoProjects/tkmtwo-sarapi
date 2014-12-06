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
package com.tkmtwo.sarapi.support;




import com.bmc.arsys.api.ARException;
import com.bmc.arsys.api.StatusInfo;
import com.google.common.base.Objects;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class ARExceptions {

  public static final List<StatusInfo> EMPTY_STATUS_INFO = Collections.emptyList();
  
  public static String toString(ARException arex) {
    Objects.ToStringHelper tsh = Objects.toStringHelper(arex);
    tsh.add("message", arex.getMessage());
    List<StatusInfo> sis = arex.getLastStatus();
    if (sis != null) {
      for (StatusInfo si : sis) {
        tsh.add("statusInfo", toString(si));
      }
    }
    return tsh.toString();
  }
  
  public static String toString(StatusInfo si) {
    return Objects.toStringHelper(si)
      .add("messageType", si.getMessageType())
      .add("messageNum", si.getMessageNum())
      .add("messageText", si.getMessageText())
      .add("appendedText", si.getAppendedText())
      .toString();
   }


  public static boolean hasStatusInfoMessageNum(ARException arex,
                                                long messageNum) {
    if (arex == null) { return false; }
    if (arex.getLastStatus() == null) { return false; }

    for (StatusInfo statusInfo : arex.getLastStatus()) {
      if (statusInfo.getMessageNum() == messageNum) {
        return true;
      }
    }
    return false;
  }
  
  
  public static StatusInfo getRootStatusInfo(ARException arex) {
    if (arex == null) { return null; }
    
    List<StatusInfo> sis = arex.getLastStatus();
    if (sis.isEmpty()) { return null; }
    
    return sis.get(0);
  }
  
  
  public static List<StatusInfo> getStatusInfos(ARException arex) {
    if (arex == null) {
      return EMPTY_STATUS_INFO;
    }
    return (arex.getLastStatus() == null) ? EMPTY_STATUS_INFO : arex.getLastStatus();
  }
  public static int getStatusInfoCount(ARException arex) {
    return getStatusInfos(arex).size();
  }
  public static boolean hasStatusInfos(ARException arex) {
    return getStatusInfoCount(arex) > 0;
  }
  public static StatusInfo getFirstStatusInfo(ARException arex) {
    List<StatusInfo> sis = getStatusInfos(arex);
    if (sis.size() > 0) {
      return sis.get(0);
    }
    return null;
  }
  
  
  
  
  public static StatusInfo getCauseStatusInfo(ARException arex) {
    return getFirstStatusInfo(arex);
  }



  public static String getStackTrace(Throwable throwable) {
    Writer writer = new StringWriter();
    PrintWriter printWriter = new PrintWriter(writer);
    throwable.printStackTrace(printWriter);
    return writer.toString();
  }
  
  
  
}
