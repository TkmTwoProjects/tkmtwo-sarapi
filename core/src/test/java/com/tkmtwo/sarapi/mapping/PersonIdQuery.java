package com.tkmtwo.sarapi.mapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.bmc.arsys.api.Entry;
import com.bmc.arsys.api.Value;
import java.util.List;
import com.tkmtwo.sarapi.ArsDataType;
import com.tkmtwo.sarapi.support.EntryUtil;



public final class PersonIdQuery
  extends ValueQuery<String> {
  
  
  public void afterPropertiesSet() {
    if (getValueMapper() == null) {
      setValueMapper(new ConversionValueMapper<String>(String.class, getConversionService()));
    }

    if (getFieldName() == null) {
      setFieldName("First Name");
    }

    super.afterPropertiesSet();
  }
        
}

