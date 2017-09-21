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
import org.dmfs.android.contentpal.rowdata.CharSequenceRowData;
import org.dmfs.android.contentpal.testing.answers.FailAnswer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.android.contentpal.testing.contentoperationbuilder.OperationType.updateOperation;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithExpectedCount.withoutExpectedCount;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.only;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withoutValues;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithYieldAllowed.withYieldNotAllowed;
import static org.dmfs.android.contentpal.testing.contentvalues.WithValue.withValue;
import static org.dmfs.android.contentpal.testing.operations.OperationMatcher.builds;
import static org.dmfs.optional.hamcrest.PresentMatcher.isPresent;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


/**
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class PutTest
{

    @Test
    public void testVirtualReference() throws Exception
    {
        SoftRowReference<Object> reference = mock(SoftRowReference.class, new FailAnswer());
        RowSnapshot<Object> rowSnapshot = mock(RowSnapshot.class, new FailAnswer());

        doReturn(reference).when(rowSnapshot).reference();
        doReturn(ContentProviderOperation.newUpdate(Uri.EMPTY)).when(reference).putOperationBuilder(any(TransactionContext.class));

        assertThat(new Put<>(rowSnapshot).reference(), isPresent(sameInstance(reference)));
    }


    @Test
    public void testContentOperationBuilder() throws Exception
    {
        RowSnapshot<Object> rowSnapshot = mock(RowSnapshot.class, new FailAnswer());
        SoftRowReference<Object> rowReference = mock(SoftRowReference.class, new FailAnswer());

        doReturn(rowReference).when(rowSnapshot).reference();
        doReturn(ContentProviderOperation.newUpdate(Uri.EMPTY)).when(rowReference).putOperationBuilder(any(TransactionContext.class));

        assertThat(new Put<>(rowSnapshot),
                builds(
                        updateOperation(),
                        withYieldNotAllowed(),
                        withoutExpectedCount(),
                        withoutValues()
                )
        );
    }


    @Test
    public void testContentOperationBuilderWithData() throws Exception
    {
        RowSnapshot<Object> rowSnapshot = mock(RowSnapshot.class, new FailAnswer());
        SoftRowReference<Object> rowReference = mock(SoftRowReference.class, new FailAnswer());

        doReturn(rowReference).when(rowSnapshot).reference();
        doReturn(ContentProviderOperation.newUpdate(Uri.EMPTY)).when(rowReference).putOperationBuilder(any(TransactionContext.class));

        assertThat(new Put<>(rowSnapshot, new CharSequenceRowData<>("x", "y")),
                builds(
                        updateOperation(),
                        withYieldNotAllowed(),
                        withoutExpectedCount(),
                        only(
                                withValue("x", "y"))));
    }
}