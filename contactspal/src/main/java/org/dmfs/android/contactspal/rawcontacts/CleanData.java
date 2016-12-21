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

package org.dmfs.android.contactspal.rawcontacts;

import android.content.ContentProviderOperation;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.RowData;


/**
 * {@link RowData} of a {@link ContactsContract.RawContacts} row, which sets the "dirty" flag to 0.
 *
 * @author Marten Gajda
 */
public final class CleanData implements RowData<ContactsContract.RawContacts>
{
    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(@NonNull ContentProviderOperation.Builder builder)
    {
        return builder.withValue(ContactsContract.RawContacts.DIRTY, 0);
    }
}
