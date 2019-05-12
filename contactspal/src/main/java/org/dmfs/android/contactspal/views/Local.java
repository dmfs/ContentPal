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

package org.dmfs.android.contactspal.views;

import android.database.Cursor;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.Projection;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.UriParams;
import org.dmfs.android.contentpal.View;
import org.dmfs.android.contentpal.predicates.AllOf;
import org.dmfs.android.contentpal.predicates.IsNull;
import org.dmfs.android.contentpal.tables.AccountScoped;
import org.dmfs.jems.optional.Optional;


/**
 * A scoped view on the given {@link ContactsContract.RawContacts} {@link Table} which only contains "local" contacts, which means contacts that don't belong to
 * a specific account.
 * <p>
 * This can not be used with {@link AccountScoped}.
 *
 * @author Marten Gajda
 */
public final class Local implements View<ContactsContract.RawContacts>
{
    private final View<ContactsContract.RawContacts> mDelegate;


    public Local(@NonNull View<ContactsContract.RawContacts> delegate)
    {
        mDelegate = delegate;
    }


    @NonNull
    @Override
    public Cursor rows(@NonNull UriParams uriParams, @NonNull Projection<? super ContactsContract.RawContacts> projection, @NonNull Predicate predicate, @NonNull Optional<String> sorting) throws RemoteException
    {
        return mDelegate.rows(uriParams,
                projection,
                new AllOf(predicate, new IsNull(ContactsContract.RawContacts.ACCOUNT_NAME), new IsNull(ContactsContract.RawContacts.ACCOUNT_TYPE)), sorting);
    }


    @NonNull
    @Override
    public Table<ContactsContract.RawContacts> table()
    {
        return new org.dmfs.android.contactspal.tables.Local(mDelegate.table());
    }
}
