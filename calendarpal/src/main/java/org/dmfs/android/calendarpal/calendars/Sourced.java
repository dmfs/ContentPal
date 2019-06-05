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

package org.dmfs.android.calendarpal.calendars;

import android.content.ContentProviderOperation;
import android.provider.CalendarContract;

import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.TransactionContext;

import androidx.annotation.NonNull;


/**
 * {@link CalendarContract.Calendars} {@link RowData} to set the {@code _SYNC_ID} of a calendar.
 * <p>
 * Note, this class is called {@code Sourced} rather than {@code Synced} (reflecting the name {@code _SYNC_ID}) because it is assumed that this field identifies
 * the source of this calendar (either directly or indirectly).
 *
 * @author Marten Gajda
 */
public final class Sourced implements RowData<CalendarContract.Calendars>
{
    private final CharSequence mSource;


    public Sourced(@NonNull CharSequence source)
    {
        mSource = source;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(@NonNull TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder)
    {
        return builder.withValue(CalendarContract.Calendars._SYNC_ID, mSource.toString());
    }
}
