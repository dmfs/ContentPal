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
import android.net.Uri;

import org.dmfs.android.contentpal.InsertOperation;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.operations.internal.RawInsert;
import org.dmfs.android.contentpal.testing.answers.FailAnswer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.android.contentpal.testing.contentoperationbuilder.OperationType.insertOperation;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithExpectedCount.withoutExpectedCount;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withoutValues;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithYieldAllowed.withYieldNotAllowed;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;


/**
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class VirtualRowReferenceTest
{
    @Test
    public void testPutOperationBuilder() throws Exception
    {
        assertThat(new VirtualRowReference<>(new RawInsert<>(mock(Uri.class, new FailAnswer()))).putOperationBuilder(
                mock(TransactionContext.class, new FailAnswer())),
                allOf(
                        insertOperation(),
                        withoutExpectedCount(),
                        withYieldNotAllowed(),
                        withoutValues()));
    }


    @Test(expected = UnsupportedOperationException.class)
    public void testDeleteOperationBuilder() throws Exception
    {
        new VirtualRowReference<>(mock(InsertOperation.class)).deleteOperationBuilder(mock(TransactionContext.class, new FailAnswer()));
    }


    @Test(expected = UnsupportedOperationException.class)
    public void testAssertOperationBuilder() throws Exception
    {
        new VirtualRowReference<>(mock(InsertOperation.class)).assertOperationBuilder(mock(TransactionContext.class, new FailAnswer()));
    }


    @Test(expected = UnsupportedOperationException.class)
    public void testBuilderWithReferenceData() throws Exception
    {
        new VirtualRowReference<>(mock(InsertOperation.class)).builderWithReferenceData(mock(TransactionContext.class, new FailAnswer()),
                mock(ContentProviderOperation.Builder.class), "_id");
    }


    @Test(expected = UnsupportedOperationException.class)
    public void testPredicate() throws Exception
    {
        new VirtualRowReference<>(mock(InsertOperation.class)).predicate(mock(TransactionContext.class, new FailAnswer()), "_id");
    }


    @Test
    public void testIsVirtual() throws Exception
    {
        assertThat(new VirtualRowReference<>(mock(InsertOperation.class)).isVirtual(), is(true));
    }

}