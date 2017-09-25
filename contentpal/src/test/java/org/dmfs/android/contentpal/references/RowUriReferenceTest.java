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

import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.predicates.utils.BackReferences;
import org.dmfs.android.contentpal.predicates.utils.Values;
import org.dmfs.android.contentpal.testing.answers.FailAnswer;
import org.dmfs.optional.iterable.PresentValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.android.contentpal.testing.contentoperationbuilder.OperationType.assertOperation;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.OperationType.deleteOperation;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.OperationType.insertOperation;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.OperationType.updateOperation;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithExpectedCount.withoutExpectedCount;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withValuesOnly;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withoutValues;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithYieldAllowed.withYieldNotAllowed;
import static org.dmfs.android.contentpal.testing.contentvalues.Containing.containing;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;


/**
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class RowUriReferenceTest
{
    @Test
    public void testPutOperationBuilder() throws Exception
    {
        assertThat(new RowUriReference<>(Uri.parse("content://authority/path/123")).putOperationBuilder(
                mock(TransactionContext.class, new FailAnswer())),
                allOf(
                        updateOperation(),
                        withoutExpectedCount(),
                        withYieldNotAllowed(),
                        withoutValues()));
    }


    @Test
    public void testDeleteOperationBuilder() throws Exception
    {
        assertThat(new RowUriReference<>(Uri.parse("content://authority/path/123")).deleteOperationBuilder(
                mock(TransactionContext.class, new FailAnswer())),
                allOf(
                        deleteOperation(),
                        withoutExpectedCount(),
                        withYieldNotAllowed(),
                        withoutValues()));
    }


    @Test
    public void testAssertOperationBuilder() throws Exception
    {
        assertThat(new RowUriReference<>(Uri.parse("content://authority/path/123")).assertOperationBuilder(
                mock(TransactionContext.class, new FailAnswer())),
                allOf(
                        assertOperation(),
                        withoutExpectedCount(),
                        withYieldNotAllowed(),
                        withoutValues()));
    }


    @Test
    public void testBuilderWithReferenceData() throws Exception
    {
        assertThat(new RowUriReference<>(Uri.parse("content://authority/path/123")).builderWithReferenceData(
                mock(TransactionContext.class, new FailAnswer()), ContentProviderOperation.newInsert(mock(Uri.class, new FailAnswer())), "column"),
                allOf(
                        insertOperation(),
                        withoutExpectedCount(),
                        withYieldNotAllowed(),
                        withValuesOnly(
                                containing("column", 123L))));
    }


    @Test
    public void testPredicate() throws Exception
    {
        assertThat(new RowUriReference<>(Uri.parse("content://authority/path/123"))
                        .predicate(mock(TransactionContext.class, new FailAnswer()), "column")
                        .selection(mock(TransactionContext.class, new FailAnswer())).toString(),
                is("column = ?"));

        assertThat(new Values(new RowUriReference<>(Uri.parse("content://authority/path/123"))
                        .predicate(mock(TransactionContext.class, new FailAnswer()), "column")
                        .arguments(mock(TransactionContext.class, new FailAnswer()))),
                contains("123"));

        assertThat(new PresentValues<>(new BackReferences(new RowUriReference<>(Uri.parse("content://authority/path/123"))
                        .predicate(mock(TransactionContext.class, new FailAnswer()), "column")
                        .arguments(mock(TransactionContext.class, new FailAnswer())))),
                emptyIterable());
    }


    @Test
    public void testIsVirtual() throws Exception
    {
        assertThat(new RowUriReference<>(Uri.parse("content://authority/path/123")).isVirtual(),
                is(false));
    }

}