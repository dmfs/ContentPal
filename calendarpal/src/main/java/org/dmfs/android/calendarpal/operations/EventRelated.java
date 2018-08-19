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

import org.dmfs.android.contentpal.InsertOperation;
import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.operations.Referring;
import org.dmfs.jems.optional.Optional;


/**
 * {@link InsertOperation} decorator which relates an element of a table &lt;T&gt; to a given event row. The respective table must use {@code "event_id"} as the
 * name of the foreign key column to the event. This is true for {@link CalendarContract.Attendees} and {@link CalendarContract.Reminders}.
 *
 * @author Marten Gajda
 */
public final class EventRelated<T> implements InsertOperation<T>
{

    private final Operation<T> mDelegate;


    public EventRelated(@NonNull RowSnapshot<CalendarContract.Events> event, @NonNull InsertOperation<T> delegate)
    {
        mDelegate = new Referring<>(event, "event_id"/* all supported tables use this name as the foreign key column name */, delegate);
    }


    @NonNull
    @Override
    public Optional<SoftRowReference<T>> reference()
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
