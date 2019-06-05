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

package org.dmfs.android.contactspal.data.name;

import android.content.ContentProviderOperation;
import android.provider.ContactsContract;

import org.dmfs.android.contentpal.TransactionContext;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * Display name of a {@link ContactsContract.CommonDataKinds.StructuredName} row.
 *
 * @author Marten Gajda
 */
public final class DisplayNameData implements StructuredNameData
{
    private final StructuredNameData mDelegate;
    private final CharSequence mDisplayName;


    public DisplayNameData(@Nullable CharSequence displayName)
    {
        this(displayName, EmptyNameData.INSTANCE);
    }


    public DisplayNameData(@Nullable CharSequence displayName, @NonNull StructuredNameData delegate)
    {
        mDelegate = delegate;
        mDisplayName = displayName;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(@NonNull TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder)
    {
        return mDelegate.updatedBuilder(transactionContext, builder)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, mDisplayName == null ? null : mDisplayName.toString());
    }
}
