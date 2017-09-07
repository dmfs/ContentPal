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

package org.dmfs.android.calendarpal.views;

import android.content.ContentProviderClient;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.views.BaseView;
import org.dmfs.android.contentpal.views.DelegatingView;


/**
 * The CalendarProvider.Attendees view.
 *
 * @author Marten Gajda
 */
public final class Attendees extends DelegatingView<CalendarContract.Attendees>
{
    public Attendees(@NonNull ContentProviderClient client)
    {
        this(client, CalendarContract.Attendees._ID,
                CalendarContract.Attendees.ATTENDEE_EMAIL,
                CalendarContract.Attendees.ATTENDEE_NAME,
                CalendarContract.Attendees.ATTENDEE_RELATIONSHIP,
                CalendarContract.Attendees.ATTENDEE_STATUS,
                CalendarContract.Attendees.ATTENDEE_TYPE);
    }


    public Attendees(@NonNull ContentProviderClient client, @NonNull String... projection)
    {
        super(new BaseView<CalendarContract.Attendees>(client, CalendarContract.Attendees.CONTENT_URI, projection));
    }
}
