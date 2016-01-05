/**
 * Copyright (C) 2014-2015 LinkedIn Corp. (pinot-core@linkedin.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.linkedin.pinot.core.operator.dociditerators;

import org.roaringbitmap.IntIterator;

import com.linkedin.pinot.core.common.BlockDocIdIterator;
import com.linkedin.pinot.core.common.Constants;

public class DocIdIteratorWrapper implements BlockDocIdIterator {

  private IntIterator iterator;
  int currentDocId = -1;

  public DocIdIteratorWrapper(IntIterator iterator) {
    this.iterator = iterator;
  }

  @Override
  public int currentDocId() {
    return currentDocId;
  }

  @Override
  public int next() {
    if (iterator.hasNext()) {
      return (currentDocId = iterator.next());
    }
    return (currentDocId = Constants.EOF);
  }

  @Override
  public int advance(int targetDocId) {
    int docId;
    while (iterator.hasNext()) {
      if((docId = iterator.next()) >= targetDocId){
        return ((currentDocId = docId));
      }
    }
    return (currentDocId = Constants.EOF);
  }

}
