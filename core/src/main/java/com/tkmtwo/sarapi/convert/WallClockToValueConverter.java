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
package com.tkmtwo.sarapi.convert;

import com.bmc.arsys.api.Time;
import com.bmc.arsys.api.Value;
import com.tkmtwo.timex.WallClock;
import org.springframework.core.convert.converter.Converter;

/**
 *
 */
public final class WallClockToValueConverter
  implements Converter<WallClock, Value> {
  
  @Override
  public Value convert(WallClock wc) {
    if (wc == null) { return new Value(); }
    Time t = new Time(wc.getSeconds());
    return new Value(t);
  }
}

    
    
