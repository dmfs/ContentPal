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

package org.dmfs.android.calendarpal.attendees;

import android.content.ContentProviderOperation;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.RowData;


/**
 * The {@link RowData} of an attendee.
 *
 * @author Marten Gajda
 */
public final class AttendeeData implements RowData<CalendarContract.Attendees>
{
    private final CharSequence mAttendeeEmail;
    private final int mStatus;
    private final int mType;
    private final int mRelationShip;


    public AttendeeData(@NonNull CharSequence attendeeEmail)
    {
        this(attendeeEmail, CalendarContract.Attendees.ATTENDEE_STATUS_NONE);
    }


    public AttendeeData(@NonNull CharSequence attendeeEmail, int status)
    {
        this(attendeeEmail, status, CalendarContract.Attendees.TYPE_REQUIRED);
    }


    public AttendeeData(@NonNull CharSequence attendeeEmail, int status, int type)
    {
        this(attendeeEmail, status, type, CalendarContract.Attendees.RELATIONSHIP_ORGANIZER);
    }


    public AttendeeData(@NonNull CharSequence attendeeEmail, int status, int type, int relationShip)
    {
        mAttendeeEmail = attendeeEmail;
        mStatus = status;
        mType = type;
        mRelationShip = relationShip;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(@NonNull ContentProviderOperation.Builder builder)
    {
        return builder.withValue(CalendarContract.Attendees.ATTENDEE_EMAIL, mAttendeeEmail.toString())
                .withValue(CalendarContract.Attendees.ATTENDEE_TYPE, mType)
                .withValue(CalendarContract.Attendees.ATTENDEE_STATUS, mStatus)
                .withValue(CalendarContract.Attendees.ATTENDEE_RELATIONSHIP, mRelationShip);
    }
}
