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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.dmfs.android.contentpal.TransactionContext;


/**
 * Phonetic name component of a {@link StructuredNameData}.
 *
 * @author Marten Gajda
 */
public final class PhoneticNameData implements StructuredNameData
{
    private final StructuredNameData mDelegate;
    private final CharSequence mFirstName;
    private final CharSequence mMiddleName;
    private final CharSequence mLastName;


    public PhoneticNameData(@Nullable CharSequence firstName, @Nullable CharSequence middleName, @Nullable CharSequence lastName)
    {
        this(firstName, middleName, lastName, EmptyNameData.INSTANCE);
    }


    public PhoneticNameData(@Nullable CharSequence firstName, @Nullable CharSequence middleName, @Nullable CharSequence lastName, @NonNull StructuredNameData delegate)
    {
        mDelegate = delegate;
        mFirstName = firstName;
        mMiddleName = middleName;
        mLastName = lastName;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder)
    {
        return mDelegate.updatedBuilder(transactionContext, builder)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.PHONETIC_GIVEN_NAME, mFirstName == null ? null : mFirstName.toString())
                .withValue(ContactsContract.CommonDataKinds.StructuredName.PHONETIC_MIDDLE_NAME, mMiddleName == null ? null : mMiddleName.toString())
                .withValue(ContactsContract.CommonDataKinds.StructuredName.PHONETIC_FAMILY_NAME, mLastName == null ? null : mLastName.toString());
    }
}
