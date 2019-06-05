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

import org.dmfs.android.contactspal.operations.LocalAccountScoped;
import org.dmfs.android.contentpal.InsertOperation;
import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.UriParams;
import org.dmfs.android.contentpal.View;
import org.dmfs.android.contentpal.predicates.AllOf;
import org.dmfs.android.contentpal.predicates.IsNull;
import org.dmfs.android.contentpal.tables.AccountScoped;

import androidx.annotation.NonNull;


/**
 * A scoped view on the given {@link ContactsContract.RawContacts} {@link Table} which only contains "local" contacts, which means contacts that don't belong to
 * a specific account.
 * <p>
 * This can not be used with {@link AccountScoped}.
 *
 * @author Marten Gajda
 */
public final class Local implements Table<ContactsContract.RawContacts>
{
    private final Table<ContactsContract.RawContacts> mDelegate;


    public Local(@NonNull Table<ContactsContract.RawContacts> delegate)
    {
        mDelegate = delegate;
    }


    @NonNull
    @Override
    public InsertOperation<ContactsContract.RawContacts> insertOperation(@NonNull UriParams uriParams)
    {
        return new LocalAccountScoped(mDelegate.insertOperation(uriParams));
    }


    @NonNull
    @Override
    public Operation<ContactsContract.RawContacts> updateOperation(@NonNull UriParams uriParams, @NonNull Predicate predicate)
    {
        return mDelegate.updateOperation(uriParams, localAccountPredicate(predicate));
    }


    @NonNull
    @Override
    public Operation<ContactsContract.RawContacts> deleteOperation(@NonNull UriParams uriParams, @NonNull Predicate predicate)
    {
        return mDelegate.deleteOperation(uriParams, localAccountPredicate(predicate));
    }


    @NonNull
    @Override
    public Operation<ContactsContract.RawContacts> assertOperation(@NonNull UriParams uriParams, @NonNull Predicate predicate)
    {
        return mDelegate.assertOperation(uriParams, localAccountPredicate(predicate));
    }


    @NonNull
    @Override
    public View<ContactsContract.RawContacts> view(@NonNull ContentProviderClient client)
    {
        return new org.dmfs.android.contactspal.views.Local(mDelegate.view(client));
    }


    private Predicate localAccountPredicate(@NonNull Predicate predicate)
    {
        return new AllOf(predicate, new IsNull("account_name"), new IsNull("account_type"));
    }
}
