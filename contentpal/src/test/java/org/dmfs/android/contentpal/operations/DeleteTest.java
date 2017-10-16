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

import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.TransactionContext;
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
import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


/**
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class DeleteTest
{
    @Test
    public void testVirtualReference() throws Exception
    {
        RowSnapshot<Object> dummyRowSnapshot = dummy(RowSnapshot.class);

        assertThat(new Delete<>(dummyRowSnapshot).reference(), AbsentMatcher.<SoftRowReference<Object>>isAbsent());
    }


    @Test
    public void testContentOperationBuilder() throws Exception
    {
        RowSnapshot<Object> mockRowSnapshot = failingMock(RowSnapshot.class);
        SoftRowReference<Object> rowReference = failingMock(SoftRowReference.class);
        doReturn(rowReference).when(mockRowSnapshot).reference();
        doReturn(ContentProviderOperation.newDelete(Uri.EMPTY)).when(rowReference).deleteOperationBuilder(any(TransactionContext.class));

        assertThat(
                new Delete<>(mockRowSnapshot),
                builds(
                        deleteOperation(),
                        withYieldNotAllowed(),
                        withoutExpectedCount(),
                        withoutValues()));
    }
}