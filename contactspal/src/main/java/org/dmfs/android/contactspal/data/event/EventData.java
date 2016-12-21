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

package org.dmfs.android.contactspal.data.event;

import android.content.ContentProviderOperation;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import org.dmfs.android.contactspal.data.Custom;
import org.dmfs.android.contactspal.data.Typed;
import org.dmfs.android.contentpal.RowData;


/**
 * Data of a {@link ContactsContract.CommonDataKinds.Event} row of {@link ContactsContract.CommonDataKinds.Event#TYPE_OTHER}.
 * <p>
 * Use {@link Typed} or {@link Custom} to add a type.
 * <p>
 * TODO: better use a DateTime parameter instead of a CharSequence
 * <p>
 * TODO: does it make sense to create dedicated classes for Birthday and Anniversary?
 *
 * @author Marten Gajda
 */
public final class EventData implements RowData<ContactsContract.Data>
{
    private final CharSequence mDate;


    public EventData(@NonNull CharSequence date)
    {
        mDate = date;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(@NonNull ContentProviderOperation.Builder builder)
    {
        return builder
                .withValue(ContactsContract.CommonDataKinds.Event.MIMETYPE, ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Event.START_DATE, mDate.toString());
    }
}
