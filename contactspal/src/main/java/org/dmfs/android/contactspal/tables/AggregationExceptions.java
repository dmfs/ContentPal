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

package org.dmfs.android.contactspal.tables;

import android.content.ContentProviderClient;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.InsertOperation;
import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.UriParams;
import org.dmfs.android.contentpal.View;
import org.dmfs.android.contentpal.tables.BaseTable;


/**
 * The ContactsProvider {@link ContactsContract.AggregationExceptions} {@link Table}. Note that this table supports neither insert nor delete operations.
 *
 * @author Marten Gajda
 */
public final class AggregationExceptions implements Table<ContactsContract.AggregationExceptions>
{
    public final static AggregationExceptions INSTANCE = new AggregationExceptions();

    private final Table<ContactsContract.AggregationExceptions> mDelegate;


    public AggregationExceptions()
    {
        mDelegate = new BaseTable<>(ContactsContract.AggregationExceptions.CONTENT_URI);
    }


    @NonNull
    @Override
    public InsertOperation<ContactsContract.AggregationExceptions> insertOperation(@NonNull UriParams uriParams)
    {
        throw new UnsupportedOperationException("AggregationExceptions doesn't support inserts");
    }


    @NonNull
    @Override
    public Operation<ContactsContract.AggregationExceptions> updateOperation(@NonNull UriParams uriParams, @NonNull Predicate predicate)
    {
        return mDelegate.updateOperation(uriParams, predicate);
    }


    @NonNull
    @Override
    public Operation<ContactsContract.AggregationExceptions> deleteOperation(@NonNull UriParams uriParams, @NonNull Predicate predicate)
    {
        throw new UnsupportedOperationException("AggregationExceptions doesn't support deletes");
    }


    @NonNull
    @Override
    public Operation<ContactsContract.AggregationExceptions> assertOperation(@NonNull UriParams uriParams, @NonNull Predicate predicate)
    {
        return mDelegate.assertOperation(uriParams, predicate);
    }


    @NonNull
    @Override
    public View<ContactsContract.AggregationExceptions> view(@NonNull ContentProviderClient client)
    {
        return mDelegate.view(client);
    }
}
