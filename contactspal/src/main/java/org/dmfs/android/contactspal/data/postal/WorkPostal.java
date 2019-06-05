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

package org.dmfs.android.contactspal.data.postal;

import android.content.ContentProviderOperation;
import android.provider.ContactsContract;

import org.dmfs.android.contactspal.data.Typed;
import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.rowdata.Composite;
import org.dmfs.iterables.elementary.Seq;

import androidx.annotation.NonNull;


/**
 * Decorator for work postal addresses.
 *
 * @author Marten Gajda
 */
public final class WorkPostal implements StructuredPostalData
{
    private final RowData<ContactsContract.Data> mDelegate;


    public WorkPostal()
    {
        this(EmptyPostalData.INSTANCE);
    }


    public WorkPostal(@NonNull StructuredPostalData... data)
    {
        this(new Seq<>(data));
    }


    public WorkPostal(@NonNull Iterable<StructuredPostalData> data)
    {
        this(new Composite<>(data));
    }


    public WorkPostal(@NonNull StructuredPostalData delegate)
    {
        this((RowData<ContactsContract.Data>) delegate);
    }


    private WorkPostal(@NonNull RowData<ContactsContract.Data> delegate)
    {
        mDelegate = new Typed(ContactsContract.CommonDataKinds.StructuredPostal.TYPE_WORK, delegate);
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(@NonNull TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder)
    {
        return mDelegate.updatedBuilder(transactionContext, builder);
    }
}
