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

import android.provider.ContactsContract;

import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.rowdata.Composite;
import org.dmfs.android.contentpal.rowdata.DelegatingRowData;
import org.dmfs.android.contentpal.rowdata.IntegerRowData;

import androidx.annotation.NonNull;


/**
 * Marks the decorated {@link RowData} as the super primary element of its kind.
 * <p>
 * Note by definition this also makes an element the primary of its kind.
 */
public final class SuperPrimary extends DelegatingRowData<ContactsContract.Data>
{

    public SuperPrimary(@NonNull RowData<ContactsContract.Data> delegate)
    {
        super(new Composite<>(
            new Primary(delegate),
            new IntegerRowData<>(ContactsContract.Data.IS_SUPER_PRIMARY, 1)));
    }
}
