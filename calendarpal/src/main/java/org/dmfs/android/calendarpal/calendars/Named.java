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

package org.dmfs.android.calendarpal.calendars;

import android.content.ContentProviderOperation;
import android.provider.CalendarContract;

import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.TransactionContext;

import androidx.annotation.NonNull;


/**
 * {@link CalendarContract.Calendars} {@link RowData} to set the name of a calendar.
 * <p>
 * Note: at present this sets {@link CalendarContract.Calendars#CALENDAR_DISPLAY_NAME} and {@link CalendarContract.Calendars#NAME} to the same value.
 *
 * @author Marten Gajda
 */
public final class Named implements RowData<CalendarContract.Calendars>
{
    private final CharSequence mName;


    public Named(@NonNull CharSequence name)
    {
        mName = name;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(@NonNull TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder)
    {
        return builder.withValue(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, mName.toString())
            .withValue(CalendarContract.Calendars.NAME, mName.toString());
    }
}
