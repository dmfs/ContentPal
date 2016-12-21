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


/**
 * The City of a {@link ContactsContract.CommonDataKinds.StructuredPostal} row.
 *
 * @author Marten Gajda
 */
public final class CityData implements StructuredPostalData
{
    private final StructuredPostalData mDelegate;
    private final CharSequence mCity;


    public CityData(@Nullable CharSequence city)
    {
        this(city, EmptyPostalData.INSTANCE);
    }


    public CityData(@Nullable CharSequence city, @NonNull StructuredPostalData delegate)
    {
        mDelegate = delegate;
        mCity = city;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(@NonNull ContentProviderOperation.Builder builder)
    {
        return mDelegate.updatedBuilder(builder)
                .withValue(ContactsContract.CommonDataKinds.StructuredPostal.CITY, mCity == null ? null : mCity.toString());
    }
}
