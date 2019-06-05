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

package org.dmfs.android.contactspal.data;

import android.content.ContentProviderOperation;
import android.provider.ContactsContract;

import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.TransactionContext;

import androidx.annotation.NonNull;


/**
 * Marks the decorated {@link RowData} as the super primary element of its kind.
 * <p>
 * Note by definition this also makes an element the primary of its kind.
 *
 * @author Marten Gajda
 */
public final class SuperPrimary implements RowData<ContactsContract.Data>
{
    private final RowData<ContactsContract.Data> mDelegate;


    public SuperPrimary(@NonNull RowData<ContactsContract.Data> delegate)
    {
        mDelegate = delegate;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(@NonNull TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder)
    {
        return mDelegate.updatedBuilder(transactionContext, builder)
                .withValue(ContactsContract.Data.IS_SUPER_PRIMARY, 1)
                .withValue(ContactsContract.Data.IS_PRIMARY, 1);
    }
}
