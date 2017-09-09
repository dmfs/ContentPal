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

package org.dmfs.android.contentpal.tables;

import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.InsertOperation;
import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.UriParams;
import org.dmfs.android.contentpal.tools.uriparams.EmptyUriParams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;


/**
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/test/AndroidManifest.xml")
public class SyncedTest
{
    @Test
    public void testInsertOperation() throws Exception
    {
        final InsertOperation<Object> resultOperation = mock(InsertOperation.class);
        Table<Object> testTable = new Synced<>(new AbstractMockTable()
        {
            @NonNull
            @Override
            public InsertOperation<Object> insertOperation(@NonNull UriParams uriParams)
            {
                // TODO: how do we check if any other params are still present?
                assertThat(uriParams.withParam(new Uri.Builder()).build().getBooleanQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER, false), is(true));
                return resultOperation;
            }
        });

        assertThat(testTable.insertOperation(new EmptyUriParams()), sameInstance(resultOperation));
    }


    @Test
    public void testUpdateOperation() throws Exception
    {
        final Operation<Object> resultOperation = mock(Operation.class);
        final Predicate testPredicate = mock(Predicate.class);
        Table<Object> testTable = new Synced<>(new AbstractMockTable<Object>()
        {
            @NonNull
            @Override
            public Operation<Object> updateOperation(@NonNull UriParams uriParams, @NonNull Predicate predicate)
            {
                assertThat(predicate, sameInstance(testPredicate));
                // TODO: how do we check if any other params are still present?
                assertThat(uriParams.withParam(new Uri.Builder()).build().getBooleanQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER, false), is(true));
                return resultOperation;
            }
        });

        assertThat(testTable.updateOperation(new EmptyUriParams(), testPredicate), sameInstance(resultOperation));
    }


    @Test
    public void testDeleteOperation() throws Exception
    {
        final Operation<Object> resultOperation = mock(Operation.class);
        final Predicate testPredicate = mock(Predicate.class);
        Table<Object> testTable = new Synced<>(new AbstractMockTable<Object>()
        {
            @NonNull
            @Override
            public Operation<Object> deleteOperation(@NonNull UriParams uriParams, @NonNull Predicate predicate)
            {
                assertThat(predicate, sameInstance(testPredicate));
                // TODO: how do we check if any other params are still present?
                assertThat(uriParams.withParam(new Uri.Builder()).build().getBooleanQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER, false), is(true));
                return resultOperation;
            }
        });

        assertThat(testTable.deleteOperation(new EmptyUriParams(), testPredicate), sameInstance(resultOperation));
    }

    // TODO: test view();
}