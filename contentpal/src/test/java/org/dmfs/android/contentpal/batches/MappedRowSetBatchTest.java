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
import org.dmfs.android.contentpal.RowSet;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.testing.rowset.TestRowSet;
import org.dmfs.android.contentpal.testing.table.Contract;
import org.dmfs.iterators.Function;
import org.junit.Test;

import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;


/**
 * Test for {@link MappedRowSetBatch}.
 *
 * @author Gabor Keszthelyi
 */
public final class MappedRowSetBatchTest
{

    @Test
    public void test()
    {
        RowSnapshot<Contract> dummyRowSnapshot1 = dummy(RowSnapshot.class);
        RowSnapshot<Contract> dummyRowSnapshot2 = dummy(RowSnapshot.class);
        RowSnapshot<Contract> dummyRowSnapshot3 = dummy(RowSnapshot.class);
        RowSet<Contract> rowSet = new TestRowSet<>(dummyRowSnapshot1, dummyRowSnapshot2, dummyRowSnapshot3);

        Operation<?> dummyOperation1 = dummy(Operation.class);
        Operation<?> dummyOperation2 = dummy(Operation.class);
        Operation<?> dummyOperation3 = dummy(Operation.class);

        Function<RowSnapshot<Contract>, Operation<?>> mockFunction = failingMock(Function.class);
        doReturn(dummyOperation1).when(mockFunction).apply(dummyRowSnapshot1);
        doReturn(dummyOperation2).when(mockFunction).apply(dummyRowSnapshot2);
        doReturn(dummyOperation3).when(mockFunction).apply(dummyRowSnapshot3);

        assertThat(new MappedRowSetBatch<>(rowSet, mockFunction), contains(dummyOperation1, dummyOperation2, dummyOperation3));
    }

}