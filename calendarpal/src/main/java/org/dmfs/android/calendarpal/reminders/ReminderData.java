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

package org.dmfs.android.calendarpal.reminders;

import android.content.ContentProviderOperation;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.TransactionContext;


/**
 * The {@link RowData} of a reminder.
 *
 * @author Marten Gajda
 */
public final class ReminderData implements RowData<CalendarContract.Reminders>
{
    private final int mMinutes;
    private final int mMethod;


    public ReminderData()
    {
        this(CalendarContract.Reminders.MINUTES_DEFAULT);
    }


    public ReminderData(int minutes)
    {
        this(minutes, CalendarContract.Reminders.METHOD_ALERT);
    }


    public ReminderData(int minutes, int method)
    {
        mMinutes = minutes;
        mMethod = method;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder)
    {
        return builder.withValue(CalendarContract.Reminders.MINUTES, mMinutes)
                .withValue(CalendarContract.Reminders.METHOD, mMethod);
    }
}
