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
import android.support.annotation.Nullable;

import org.dmfs.android.contentpal.TransactionContext;


/**
 * The Country of a {@link ContactsContract.CommonDataKinds.StructuredPostal} row.
 *
 * @author Marten Gajda
 */
public final class CountryData implements StructuredPostalData
{
    private final StructuredPostalData mDelegate;
    private final CharSequence mCountry;


    public CountryData(@Nullable CharSequence country)
    {
        this(country, EmptyPostalData.INSTANCE);
    }


    public CountryData(@Nullable CharSequence country, @NonNull StructuredPostalData delegate)
    {
        mDelegate = delegate;
        mCountry = country;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder)
    {
        return mDelegate.updatedBuilder(transactionContext, builder)
                .withValue(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY, mCountry == null ? null : mCountry.toString());
    }
}
