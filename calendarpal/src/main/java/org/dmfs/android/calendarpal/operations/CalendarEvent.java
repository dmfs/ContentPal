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

package org.dmfs.android.calendarpal.operations;

import android.content.ContentProviderOperation;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;

import org.dmfs.android.calendarpal.tables.Events;
import org.dmfs.android.contentpal.InsertOperation;
import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.operations.Insert;
import org.dmfs.android.contentpal.operations.Related;
import org.dmfs.android.contentpal.tables.Synced;
import org.dmfs.optional.Optional;


/**
 * {@link InsertOperation} decorator which relates a new event to a calendar row.
 *
 * @author Marten Gajda
 */
public final class CalendarEvent implements InsertOperation<CalendarContract.Events>
{
    private final Operation<CalendarContract.Events> mDelegate;


    /**
     * Creates a new event {@link InsertOperation} into the given calendar. The event is inserted as an app. To insert the event as a sync adapter use {@link
     * #CalendarEvent(RowSnapshot, InsertOperation)} and pass an {@link InsertOperation} created by a {@link Synced} {@link Table}.
     *
     * @param calendar
     *         The calendar to add the event to.
     */
    public CalendarEvent(@NonNull RowSnapshot<CalendarContract.Calendars> calendar)
    {
        this(calendar, new Insert<>(Events.INSTANCE));
    }


    public CalendarEvent(@NonNull RowSnapshot<CalendarContract.Calendars> calendar, @NonNull Table<CalendarContract.Events> eventsTable)
    {
        this(calendar, new Insert<>(eventsTable));
    }


    public CalendarEvent(@NonNull RowSnapshot<CalendarContract.Calendars> calendar, @NonNull InsertOperation<CalendarContract.Events> delegate)
    {
        mDelegate = new Related<>(calendar, CalendarContract.Events.CALENDAR_ID, delegate);
    }


    @NonNull
    @Override
    public Optional<SoftRowReference<CalendarContract.Events>> reference()
    {
        return mDelegate.reference();
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder contentOperationBuilder(@NonNull TransactionContext transactionContext) throws UnsupportedOperationException
    {
        return mDelegate.contentOperationBuilder(transactionContext);
    }
}
