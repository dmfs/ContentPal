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
import android.support.annotation.NonNull;


/**
 * {@link CalendarRowData} decorator to set a calendar to be synced.
 *
 * @author Marten Gajda
 */
public final class Synced implements CalendarRowData
{
    private final CalendarRowData mDelegate;
    private final boolean mSynced;


    public Synced(@NonNull CalendarRowData delegate)
    {
        this(true, delegate);
    }


    public Synced(boolean synced, @NonNull CalendarRowData delegate)
    {
        mDelegate = delegate;
        mSynced = synced;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(@NonNull ContentProviderOperation.Builder builder)
    {
        return mDelegate.updatedBuilder(builder).withValue(CalendarContract.Calendars.SYNC_EVENTS, mSynced ? 1 : 0);
    }
}
