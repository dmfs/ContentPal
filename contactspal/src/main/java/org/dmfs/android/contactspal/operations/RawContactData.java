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

package org.dmfs.android.contactspal.operations;

import android.content.ContentProviderOperation;
import android.provider.ContactsContract;

import org.dmfs.android.contactspal.tables.Data;
import org.dmfs.android.contentpal.InsertOperation;
import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.operations.Insert;
import org.dmfs.android.contentpal.operations.Referring;
import org.dmfs.jems.optional.Optional;

import androidx.annotation.NonNull;


/**
 * {@link InsertOperation} of {@link ContactsContract.Data} rows that is related to a specific {@link ContactsContract.RawContacts} row.
 *
 * @author Marten Gajda
 */
public final class RawContactData implements InsertOperation<ContactsContract.Data>
{
    private final Operation<ContactsContract.Data> mDelegate;


    public RawContactData(@NonNull RowSnapshot<ContactsContract.RawContacts> rawContact)
    {
        this(rawContact, Data.INSTANCE);
    }


    public RawContactData(@NonNull RowSnapshot<ContactsContract.RawContacts> rawContact, @NonNull Table<ContactsContract.Data> dataTable)
    {
        this(rawContact, new Insert<>(dataTable));
    }


    public RawContactData(@NonNull RowSnapshot<ContactsContract.RawContacts> rawContact, @NonNull InsertOperation<ContactsContract.Data> delegate)
    {
        mDelegate = new Referring<>(rawContact, ContactsContract.Data.RAW_CONTACT_ID, delegate);
    }


    @NonNull
    @Override
    public Optional<SoftRowReference<ContactsContract.Data>> reference()
    {
        return mDelegate.reference();
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder contentOperationBuilder(@NonNull TransactionContext transactionContext) throws UnsupportedOperationException
    {
        return mDelegate.contentOperationBuilder(transactionContext);
    }
}
