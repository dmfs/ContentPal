/*
 * Copyright 2018 dmfs GmbH
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

package org.dmfs.android.calendarpal.attendees;

import android.content.ContentProviderOperation;
import android.provider.CalendarContract;

import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.TransactionContext;

import androidx.annotation.NonNull;


/**
 * {@link CalendarContract.Attendees} {@link RowData} which sets the {@link CalendarContract.Attendees#ATTENDEE_EMAIL} of an attendee.
 * <p>
 * This is meant for internal use only. Users of this library should not update the email of an attendee but instead delete the old one and insert a new one
 * with {@link AttendeeData}.
 *
 * @author Marten Gajda
 */
public final class EmailData implements RowData<CalendarContract.Attendees>
{
    private final CharSequence mEmail;


    public EmailData(@NonNull CharSequence email)
    {
        mEmail = email;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(@NonNull TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder)
    {
        return builder.withValue(CalendarContract.Attendees.ATTENDEE_EMAIL, mEmail.toString());
    }
}
