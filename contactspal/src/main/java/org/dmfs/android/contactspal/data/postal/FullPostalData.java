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

import android.provider.ContactsContract;

import androidx.annotation.Nullable;

import org.dmfs.android.contactspal.data.Custom;
import org.dmfs.android.contactspal.data.Typed;
import org.dmfs.android.contentpal.rowdata.Composite;
import org.dmfs.android.contentpal.rowdata.DelegatingRowData;


/**
 * Full postal data.
 * <p>
 * Use {@link Typed} or {@link Custom} to add a type.
 */
public final class FullPostalData extends DelegatingRowData<ContactsContract.Data> implements StructuredPostalData
{

    public FullPostalData(
        @Nullable CharSequence street,
        @Nullable CharSequence neighborhood,
        @Nullable CharSequence region,
        @Nullable CharSequence postcode,
        @Nullable CharSequence poBox,
        @Nullable CharSequence city,
        @Nullable CharSequence country)
    {
        super(new Composite<>(
            new StreetData(street),
            new NeighborhoodData(neighborhood),
            new RegionData(region),
            new PostcodeData(postcode),
            new PoBoxData(poBox),
            new CityData(city),
            new CountryData(country)
        ));
    }
}
