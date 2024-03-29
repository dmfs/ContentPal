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
import android.database.Cursor;
import android.os.RemoteException;
import android.provider.CalendarContract;

import org.dmfs.android.contentpal.InsertOperation;
import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.Projection;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.UriParams;
import org.dmfs.android.contentpal.View;
import org.dmfs.jems2.Optional;

import androidx.annotation.NonNull;


/**
 * A view onto {@link CalendarContract.Events} which contains only events of a specific calendar. `{@link InsertOperation}`s returned by the result of {@link
 * #table()} will insert into the given calendar by default. Note, if you pass a virtual {@link RowSnapshot}, the {@link View} will always be empty, even after
 * adding and committing rows.
 *
 * @author Marten Gajda
 */
public final class CalendarScoped implements View<CalendarContract.Events>
{
    private final View<CalendarContract.Events> mDelegate;
    private final RowSnapshot<CalendarContract.Calendars> mCalendarRow;


    /**
     * Creates an app view (without syncadapter "privileges") onto the events of a specific calendar.
     *
     * @param client
     *     A {@link ContentProviderClient}
     * @param calendarRow
     *     The {@link RowSnapshot} of the calendar.
     */
    public CalendarScoped(@NonNull ContentProviderClient client, @NonNull RowSnapshot<CalendarContract.Calendars> calendarRow)
    {
        this(calendarRow, new Events(client));
    }


    public CalendarScoped(@NonNull RowSnapshot<CalendarContract.Calendars> calendarRow, @NonNull View<CalendarContract.Events> delegate)
    {
        mDelegate = delegate;
        mCalendarRow = calendarRow;
    }


    @NonNull
    @Override
    public Cursor rows(
        @NonNull UriParams uriParams,
        @NonNull Projection<? super CalendarContract.Events> projection,
        @NonNull Predicate<? super CalendarContract.Events> predicate,
        @NonNull Optional<String> sorting) throws RemoteException
    {
        return mDelegate.rows(
            uriParams,
            projection,
            new org.dmfs.android.calendarpal.predicates.CalendarScoped(mCalendarRow, predicate), sorting);
    }


    @NonNull
    @Override
    public Table<CalendarContract.Events> table()
    {
        return new org.dmfs.android.calendarpal.tables.CalendarScoped(mCalendarRow, mDelegate.table());
    }
}
