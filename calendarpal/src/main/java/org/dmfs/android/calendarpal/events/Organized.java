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

import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.rowdata.EmptyRowData;

import androidx.annotation.NonNull;


/**
 * Decorator to add an organizer to an event.
 *
 * @author Marten Gajda
 */
public final class Organized implements RowData<CalendarContract.Events>
{
    private final RowData<CalendarContract.Events> mDelegate;
    private final CharSequence mOrganizer;


    public Organized(@NonNull CharSequence organizer)
    {
        this(organizer, EmptyRowData.instance());
    }


    public Organized(@NonNull CharSequence organizer, @NonNull RowData<CalendarContract.Events> delegate)
    {
        mDelegate = delegate;
        mOrganizer = organizer;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(@NonNull TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder)
    {
        return mDelegate.updatedBuilder(transactionContext, builder)
            .withValue(CalendarContract.Events.ORGANIZER, mOrganizer == null ? null : mOrganizer.toString());
    }
}
