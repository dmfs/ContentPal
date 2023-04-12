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
 * Decorator to add a suffix to {@link StructuredNameData}.
 *
 * @author Marten Gajda
 */
@Deprecated
public final class Suffixed implements StructuredNameData
{
    private final StructuredNameData mDelegate;
    private final CharSequence mSuffix;


    public Suffixed(@Nullable CharSequence suffix)
    {
        this(suffix, EmptyNameData.INSTANCE);
    }


    public Suffixed(@Nullable CharSequence suffix, @NonNull StructuredNameData delegate)
    {
        mDelegate = delegate;
        mSuffix = suffix;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(@NonNull TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder)
    {
        return mDelegate.updatedBuilder(transactionContext, builder)
            .withValue(ContactsContract.CommonDataKinds.StructuredName.SUFFIX, mSuffix == null ? null : mSuffix.toString());
    }
}
