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
import android.support.annotation.Nullable;

import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.rowdata.EmptyRowData;


/**
 * Decorator to add a location to an event.
 *
 * @author Marten Gajda
 */
public final class Located implements RowData<CalendarContract.Events>
{
    private final RowData<CalendarContract.Events> mDelegate;
    private final CharSequence mLocation;


    public Located(@Nullable CharSequence location)
    {
        this(location, EmptyRowData.<CalendarContract.Events>instance());
    }


    public Located(@Nullable CharSequence location, @NonNull RowData<CalendarContract.Events> delegate)
    {
        mDelegate = delegate;
        mLocation = location;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder)
    {
        return mDelegate.updatedBuilder(transactionContext, builder)
                .withValue(CalendarContract.Events.EVENT_LOCATION, mLocation == null ? null : mLocation.toString());
    }
}
