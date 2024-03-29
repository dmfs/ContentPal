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

import org.dmfs.android.contentpal.InsertOperation;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.jems2.Optional;

import androidx.annotation.NonNull;


/**
 * A {@link InsertOperation} decorator which adds {@code account_type} and {@code account_name} values to the inserted {@link ContactsContract.RawContacts}
 * row.
 *
 * @author Marten Gajda
 */
public final class LocalAccountScoped implements InsertOperation<ContactsContract.RawContacts>
{
    private final InsertOperation<ContactsContract.RawContacts> mDelegate;


    public LocalAccountScoped(@NonNull InsertOperation<ContactsContract.RawContacts> delegate)
    {
        mDelegate = delegate;
    }


    @NonNull
    @Override
    public Optional<SoftRowReference<ContactsContract.RawContacts>> reference()
    {
        return mDelegate.reference();
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder contentOperationBuilder(@NonNull TransactionContext transactionContext) throws UnsupportedOperationException
    {
        return mDelegate.contentOperationBuilder(transactionContext).withValue("account_type", null).withValue("account_name", null);
    }
}
