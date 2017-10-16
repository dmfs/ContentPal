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

package org.dmfs.android.contentpal.operations;

import android.content.ContentProviderOperation;
import android.net.Uri;

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.tools.uriparams.EmptyUriParams;
import org.dmfs.jems.hamcrest.matchers.AbsentMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.android.contentpal.testing.contentoperationbuilder.OperationType.deleteOperation;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithExpectedCount.withoutExpectedCount;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withoutValues;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithYieldAllowed.withYieldNotAllowed;
import static org.dmfs.android.contentpal.testing.operations.OperationMatcher.builds;
import static org.dmfs.android.contentpal.testing.predicates.PredicateSelectionMatcher.predicateWithSelection;
import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


/**
 * Unit test for {@link BulkDelete}.
 *
 * @author Gabor Keszthelyi
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public final class BulkDeleteTest
{

    @Test
    public void testReference()
    {
        assertThat(new BulkDelete<Object>(dummy(Table.class)).reference(),
                AbsentMatcher.<SoftRowReference<Object>>isAbsent());

        assertThat(new BulkDelete<Object>(dummy(Table.class), dummy(Predicate.class)).reference(),
                AbsentMatcher.<SoftRowReference<Object>>isAbsent());
    }


    @Test
    public void testContentOperationBuilder_ctor_table()
    {
        Table<Object> mockTable = failingMock(Table.class);
        Operation<Object> mockOperation = failingMock(Operation.class);

        doReturn(mockOperation).when(mockTable).deleteOperation(same(EmptyUriParams.INSTANCE), predicateWithSelection("1"));

        doReturn(ContentProviderOperation.newDelete(Uri.EMPTY)).when(mockOperation).contentOperationBuilder(any(TransactionContext.class));

        assertThat(new BulkDelete<>(mockTable),
                builds(
                        deleteOperation(),
                        withoutExpectedCount(),
                        withYieldNotAllowed(),
                        withoutValues()));
    }


    @Test
    public void testContentOperationBuilder_ctor_table_predicate()
    {
        Table<Object> mockTable = failingMock(Table.class);
        Operation<Object> mockOperation = failingMock(Operation.class);
        Predicate dummyPredicate = mock(Predicate.class);

        doReturn(mockOperation).when(mockTable).deleteOperation(EmptyUriParams.INSTANCE, dummyPredicate);

        doReturn(ContentProviderOperation.newDelete(Uri.EMPTY)).when(mockOperation).contentOperationBuilder(any(TransactionContext.class));

        assertThat(new BulkDelete<>(mockTable, dummyPredicate),
                builds(
                        deleteOperation(),
                        withoutExpectedCount(),
                        withYieldNotAllowed(),
                        withoutValues()));
    }

}