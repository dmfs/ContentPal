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

package org.dmfs.android.calendarpal.events;

import android.content.ContentProviderOperation;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.rfc5545.DateTime;


/**
 * The base {@link RowData} of a non-recurring event.
 *
 * @author Marten Gajda
 */
public final class SingleEventData implements RowData<CalendarContract.Events>
{
    private final DateTime mStart;
    private final DateTime mEnd;
    private final CharSequence mTitle;


    public SingleEventData(@NonNull CharSequence title, @NonNull DateTime start, @NonNull DateTime end)
    {
        mStart = start;
        mEnd = end;
        mTitle = title;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder)
    {
        return builder.withValue(CalendarContract.Events.DTSTART, mStart.getTimestamp())
                .withValue(CalendarContract.Events.EVENT_TIMEZONE, mStart.isAllDay() ? "UTC" : mStart.getTimeZone().getID())
                .withValue(CalendarContract.Events.ALL_DAY, mStart.isAllDay() ? 1 : 0)
                .withValue(CalendarContract.Events.DTEND, mEnd.getTimestamp())
                .withValue(CalendarContract.Events.EVENT_END_TIMEZONE, mEnd.isAllDay() ? "UTC" : mEnd.getTimeZone().getID())
                .withValue(CalendarContract.Events.TITLE, mTitle.toString())
                // explicitly (re-)set all recurring event values to null
                .withValue(CalendarContract.Events.RRULE, null)
                .withValue(CalendarContract.Events.RDATE, null)
                .withValue(CalendarContract.Events.EXDATE, null)
                .withValue(CalendarContract.Events.DURATION, null);
    }
}
