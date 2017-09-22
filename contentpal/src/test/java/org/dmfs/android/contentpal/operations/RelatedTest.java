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

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.operations.internal.RawInsert;
import org.dmfs.android.contentpal.references.RowUriReference;
import org.dmfs.android.contentpal.testing.answers.FailAnswer;
import org.dmfs.optional.hamcrest.AbsentMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.android.contentpal.testing.contentoperationbuilder.OperationType.insertOperation;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithExpectedCount.withoutExpectedCount;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withValuesOnly;
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
public class RelatedTest
{
    @Test
    public void testReference() throws Exception
    {
        RowSnapshot<Object> mockSnapshot = mock(RowSnapshot.class, new FailAnswer());

        assertThat(new Related<>(mockSnapshot, "column", mock(Operation.class, new FailAnswer())).reference(),
                AbsentMatcher.<SoftRowReference<Object>>isAbsent(mock(SoftRowReference.class)));
    }


    @Test
    public void testContentOperationBuilder() throws Exception
    {
        RowSnapshot<Object> mockSnapshot = mock(RowSnapshot.class, new FailAnswer());
        doReturn(new RowUriReference<>(Uri.parse("content://abc/xyz/123"))).when(mockSnapshot).reference();

        assertThat(new Related<>(mockSnapshot, "column", new RawInsert<>(mock(Uri.class, new FailAnswer()))),
                builds(
                        insertOperation(),
                        withoutExpectedCount(),
                        withYieldNotAllowed(),
                        withValuesOnly(
                                containing("column", 123L))));
    }
}