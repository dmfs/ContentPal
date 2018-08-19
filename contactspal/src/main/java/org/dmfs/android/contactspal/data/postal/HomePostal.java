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
import android.support.annotation.NonNull;

import org.dmfs.android.contactspal.data.Typed;
import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.rowdata.Composite;
import org.dmfs.iterables.elementary.Seq;


/**
 * Decorator for home postal addresses.
 *
 * @author Marten Gajda
 */
public final class HomePostal implements StructuredPostalData
{
    private final RowData<ContactsContract.Data> mDelegate;


    public HomePostal()
    {
        this(EmptyPostalData.INSTANCE);
    }


    public HomePostal(@NonNull StructuredPostalData... data)
    {
        this(new Seq<>(data));
    }


    public HomePostal(@NonNull Iterable<StructuredPostalData> data)
    {
        this(new Composite<>(data));
    }


    public HomePostal(@NonNull StructuredPostalData delegate)
    {
        this((RowData<ContactsContract.Data>) delegate);
    }


    private HomePostal(@NonNull RowData<ContactsContract.Data> delegate)
    {
        mDelegate = new Typed(ContactsContract.CommonDataKinds.StructuredPostal.TYPE_HOME, delegate);
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder)
    {
        return mDelegate.updatedBuilder(transactionContext, builder);
    }
}
