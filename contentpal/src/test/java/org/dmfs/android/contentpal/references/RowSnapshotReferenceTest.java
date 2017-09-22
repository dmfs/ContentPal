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

package org.dmfs.android.contentpal.references;

import android.content.ContentProviderOperation;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.RowReference;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.testing.answers.FailAnswer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


/**
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class RowSnapshotReferenceTest
{
    @Test
    public void testPutOperationBuilder() throws Exception
    {
        ContentProviderOperation.Builder resultBuilder = mock(ContentProviderOperation.Builder.class, new FailAnswer());
        SoftRowReference<Object> originalReference = mock(SoftRowReference.class, new FailAnswer());
        RowReference<Object> resolvedReference = mock(RowReference.class, new FailAnswer());
        RowSnapshot<Object> mockSnapshot = mock(RowSnapshot.class, new FailAnswer());
        TransactionContext mockTransactionContext = mock(TransactionContext.class, new FailAnswer());
        doReturn(originalReference).when(mockSnapshot).reference();
        doReturn(resolvedReference).when(mockTransactionContext).resolved(originalReference);

        doReturn(resultBuilder).when(resolvedReference).putOperationBuilder(mockTransactionContext);

        assertThat(new RowSnapshotReference<>(mockSnapshot).putOperationBuilder(mockTransactionContext), sameInstance(resultBuilder));
    }


    @Test
    public void testDeleteOperationBuilder() throws Exception
    {
        ContentProviderOperation.Builder resultBuilder = mock(ContentProviderOperation.Builder.class, new FailAnswer());
        SoftRowReference<Object> originalReference = mock(SoftRowReference.class, new FailAnswer());
        RowReference<Object> resolvedReference = mock(RowReference.class, new FailAnswer());
        RowSnapshot<Object> mockSnapshot = mock(RowSnapshot.class, new FailAnswer());
        TransactionContext mockTransactionContext = mock(TransactionContext.class, new FailAnswer());
        doReturn(originalReference).when(mockSnapshot).reference();
        doReturn(resolvedReference).when(mockTransactionContext).resolved(originalReference);

        doReturn(resultBuilder).when(resolvedReference).deleteOperationBuilder(mockTransactionContext);

        assertThat(new RowSnapshotReference<>(mockSnapshot).deleteOperationBuilder(mockTransactionContext), sameInstance(resultBuilder));

    }


    @Test
    public void testAssertOperationBuilder() throws Exception
    {
        ContentProviderOperation.Builder resultBuilder = mock(ContentProviderOperation.Builder.class, new FailAnswer());
        SoftRowReference<Object> originalReference = mock(SoftRowReference.class, new FailAnswer());
        RowReference<Object> resolvedReference = mock(RowReference.class, new FailAnswer());
        RowSnapshot<Object> mockSnapshot = mock(RowSnapshot.class, new FailAnswer());
        TransactionContext mockTransactionContext = mock(TransactionContext.class, new FailAnswer());
        doReturn(originalReference).when(mockSnapshot).reference();
        doReturn(resolvedReference).when(mockTransactionContext).resolved(originalReference);

        doReturn(resultBuilder).when(resolvedReference).assertOperationBuilder(mockTransactionContext);

        assertThat(new RowSnapshotReference<>(mockSnapshot).assertOperationBuilder(mockTransactionContext), sameInstance(resultBuilder));

    }


    @Test
    public void testBuilderWithReferenceData() throws Exception
    {
        ContentProviderOperation.Builder resultBuilder = mock(ContentProviderOperation.Builder.class, new FailAnswer());
        SoftRowReference<Object> originalReference = mock(SoftRowReference.class, new FailAnswer());
        RowReference<Object> resolvedReference = mock(RowReference.class, new FailAnswer());
        RowSnapshot<Object> mockSnapshot = mock(RowSnapshot.class, new FailAnswer());
        TransactionContext mockTransactionContext = mock(TransactionContext.class, new FailAnswer());
        doReturn(originalReference).when(mockSnapshot).reference();
        doReturn(resolvedReference).when(mockTransactionContext).resolved(originalReference);

        doReturn(resultBuilder).when(resolvedReference).builderWithReferenceData(mockTransactionContext, resultBuilder, "column");

        assertThat(new RowSnapshotReference<>(mockSnapshot).builderWithReferenceData(mockTransactionContext, resultBuilder, "column"),
                sameInstance(resultBuilder));
    }


    @Test
    public void testPredicate() throws Exception
    {
        Predicate resultPredicate = mock(Predicate.class, new FailAnswer());
        SoftRowReference<Object> originalReference = mock(SoftRowReference.class, new FailAnswer());
        RowReference<Object> resolvedReference = mock(RowReference.class, new FailAnswer());
        RowSnapshot<Object> mockSnapshot = mock(RowSnapshot.class, new FailAnswer());
        TransactionContext mockTransactionContext = mock(TransactionContext.class, new FailAnswer());
        doReturn(originalReference).when(mockSnapshot).reference();
        doReturn(resolvedReference).when(mockTransactionContext).resolved(originalReference);

        doReturn(resultPredicate).when(resolvedReference).predicate(mockTransactionContext, "column");

        assertThat(new RowSnapshotReference<>(mockSnapshot).predicate(mockTransactionContext, "column"),
                sameInstance(resultPredicate));
    }
}