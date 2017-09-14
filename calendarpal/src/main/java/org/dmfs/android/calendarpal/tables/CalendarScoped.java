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

package org.dmfs.android.calendarpal.tables;

import android.content.ContentProviderClient;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;

import org.dmfs.android.calendarpal.operations.CalendarEvent;
import org.dmfs.android.contentpal.InsertOperation;
import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.UriParams;
import org.dmfs.android.contentpal.View;
import org.dmfs.android.contentpal.predicates.AllOf;
import org.dmfs.android.contentpal.predicates.EqArg;


/**
 * A view onto the {@link CalendarContract.Events} table which contains only events of a specific calendar. Events created with {@link
 * #insertOperation(UriParams)} will automatically be added to this calendar.
 * <p>
 * Note, if you create a {@link View} (using {@link #view(ContentProviderClient, String...)}) with a virtual {@link RowSnapshot}, the {@link View} will always
 * be empty, even after adding and committing rows.
 *
 * @author Marten Gajda
 */
public final class CalendarScoped implements Table<CalendarContract.Events>
{
    private final Table<CalendarContract.Events> mDelegate;
    private final RowSnapshot<CalendarContract.Calendars> mCalendarRow;


    public CalendarScoped(@NonNull RowSnapshot<CalendarContract.Calendars> calendarRow)
    {
        this(calendarRow, Events.INSTANCE);
    }


    public CalendarScoped(@NonNull RowSnapshot<CalendarContract.Calendars> calendarRow, @NonNull Table<CalendarContract.Events> delegate)
    {
        mDelegate = delegate;
        mCalendarRow = calendarRow;
    }


    @NonNull
    @Override
    public InsertOperation<CalendarContract.Events> insertOperation(@NonNull UriParams uriParams)
    {
        return new CalendarEvent(mCalendarRow, mDelegate.insertOperation(uriParams));
    }


    @NonNull
    @Override
    public Operation<CalendarContract.Events> updateOperation(@NonNull UriParams uriParams, @NonNull Predicate predicate)
    {
        return mDelegate.updateOperation(uriParams, calendarScoped(predicate));
    }


    @NonNull
    @Override
    public Operation<CalendarContract.Events> deleteOperation(@NonNull UriParams uriParams, @NonNull Predicate predicate)
    {
        return mDelegate.deleteOperation(uriParams, calendarScoped(predicate));
    }


    @NonNull
    @Override
    public Operation<CalendarContract.Events> assertOperation(@NonNull UriParams uriParams, @NonNull Predicate predicate)
    {
        return mDelegate.assertOperation(uriParams, calendarScoped(predicate));
    }


    @NonNull
    @Override
    public View<CalendarContract.Events> view(@NonNull ContentProviderClient client, @NonNull String... projection)
    {
        return new org.dmfs.android.calendarpal.views.CalendarScoped(mCalendarRow, mDelegate.view(client, projection));
    }


    private Predicate calendarScoped(@NonNull Predicate predicate)
    {
        return new AllOf(predicate, new EqArg(CalendarContract.Events.CALENDAR_ID,
                mCalendarRow.values().charData(CalendarContract.Calendars._ID).value("-1")));
    }
}