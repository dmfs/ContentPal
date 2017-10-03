/*
 * Copyright 2017 dmfs GmbH
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
 */

package org.dmfs.android.contentpal.batches;

import org.dmfs.android.contentpal.Operation;
import org.junit.Test;

import java.util.Iterator;

import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;


/**
 * Unit test for {@link DelegatingOperationsBatch}.
 *
 * @author Gabor Keszthelyi
 */
public class DelegatingOperationsBatchTest
{
    @Test
    public void test() throws Exception
    {
        Iterable<Operation<?>> mockIterable = failingMock(Iterable.class);
        Iterator<Operation<?>> dummyIterator = dummy(Iterator.class);
        doReturn(dummyIterator).when(mockIterable).iterator();

        assertThat(new TestDelegatingOperationsBatch(mockIterable).iterator(), sameInstance(dummyIterator));
    }


    private static final class TestDelegatingOperationsBatch extends DelegatingOperationsBatch
    {

        private TestDelegatingOperationsBatch(Iterable<Operation<?>> iterableDelegate)
        {
            super(iterableDelegate);
        }
    }

}