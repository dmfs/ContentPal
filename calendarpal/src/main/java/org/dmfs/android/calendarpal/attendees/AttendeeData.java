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

import android.provider.CalendarContract;
import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.rowdata.Composite;
import org.dmfs.android.contentpal.rowdata.DelegatingRowData;
import org.dmfs.android.contentpal.rowdata.EmptyRowData;


/**
 * The {@link RowData} of an attendee.
 * <p>
 * This sets the {@link CalendarContract.Attendees#ATTENDEE_EMAIL} field and defaults for {@link
 * CalendarContract.Attendees#ATTENDEE_STATUS},{@link CalendarContract.Attendees#ATTENDEE_RELATIONSHIP} and {@link CalendarContract.Attendees#ATTENDEE_TYPE},
 * unless overridden by one of the attributes.
 * <p>
 * Be careful when using this with an update operation. This will reset status, relationship and type to the respective {@code *_NONE} values.
 *
 * @author Marten Gajda
 */
public final class AttendeeData extends DelegatingRowData<CalendarContract.Attendees>
{

    public AttendeeData(@NonNull CharSequence attendeeEmail)
    {
        this(attendeeEmail, new EmptyRowData<>());
    }


    public AttendeeData(@NonNull CharSequence attendeeEmail, @NonNull RowData<CalendarContract.Attendees> attributes)
    {
        super(new Composite<>(
                new EmailData(attendeeEmail),
                // set the required defaults
                new RelationData(CalendarContract.Attendees.RELATIONSHIP_NONE),
                new StateData(CalendarContract.Attendees.ATTENDEE_STATUS_NONE),
                new TypeData(CalendarContract.Attendees.TYPE_NONE),
                // append the attributes, allowing to override the defaults
                attributes));
    }
}
