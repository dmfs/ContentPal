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
 * The Region of a {@link ContactsContract.CommonDataKinds.StructuredPostal} row.
 *
 * @author Marten Gajda
 */
public final class RegionData implements StructuredPostalData
{
    private final StructuredPostalData mDelegate;
    private final CharSequence mRegion;


    public RegionData(@Nullable CharSequence region)
    {
        this(region, EmptyPostalData.INSTANCE);
    }


    public RegionData(@Nullable CharSequence region, @NonNull StructuredPostalData delegate)
    {
        mDelegate = delegate;
        mRegion = region;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(@NonNull ContentProviderOperation.Builder builder)
    {
        return mDelegate.updatedBuilder(builder)
                .withValue(ContactsContract.CommonDataKinds.StructuredPostal.REGION, mRegion == null ? null : mRegion.toString());
    }
}
