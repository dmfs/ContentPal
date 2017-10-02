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

import android.net.Uri;

import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.operations.internal.RawInsert;
import org.dmfs.android.contentpal.rowdata.CharSequenceRowData;
import org.dmfs.android.contentpal.testing.answers.FailAnswer;
import org.dmfs.android.contentpal.tools.uriparams.EmptyUriParams;
import org.dmfs.optional.hamcrest.AbsentMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.android.contentpal.testing.contentoperationbuilder.OperationType.insertOperation;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithExpectedCount.withoutExpectedCount;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withValuesOnly;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withoutValues;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithYieldAllowed.withYieldNotAllowed;
import static org.dmfs.android.contentpal.testing.contentvalues.Containing.containing;
import static org.dmfs.android.contentpal.testing.operations.OperationMatcher.builds;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


/**
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class InsertTest
{
    @Test
    public void testReference() throws Exception
    {
        Table<Object> mockTable = mock(Table.class, new FailAnswer());

        assertThat(new Insert<Object>(mockTable).reference(),
                AbsentMatcher.<SoftRowReference<Object>>isAbsent());

        assertThat(new Insert<Object>(mockTable, mock(RowData.class, new FailAnswer())).reference(),
                AbsentMatcher.<SoftRowReference<Object>>isAbsent());
    }


    @Test
    public void testContentOperationBuilder() throws Exception
    {
        Table<Object> mockTable = mock(Table.class, new FailAnswer());
        doReturn(new RawInsert<>(mock(Uri.class, new FailAnswer()))).when(mockTable).insertOperation(EmptyUriParams.INSTANCE);

        assertThat(new Insert<>(mockTable),
                builds(
                        insertOperation(),
                        withoutExpectedCount(),
                        withYieldNotAllowed(),
                        withoutValues()));

        assertThat(new Insert<>(mockTable, new CharSequenceRowData<>("key", "value")),
                builds(
                        insertOperation(),
                        withoutExpectedCount(),
                        withYieldNotAllowed(),
                        withValuesOnly(
                                containing("key", "value"))));
    }
}