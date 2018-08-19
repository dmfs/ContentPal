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

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.operations.BulkDelete;
import org.dmfs.android.contentpal.predicates.AllOf;
import org.dmfs.android.contentpal.predicates.EqArg;
import org.dmfs.android.contentpal.predicates.IsNull;
import org.dmfs.jems.optional.Optional;


/**
 * An {@link Operation} which removes all events which have been deleted before they have been synced.
 *
 * @author Marten Gajda
 */
public final class TransientEventsCleanup implements Operation<CalendarContract.Events>
{
    private final Operation<CalendarContract.Events> mDelegate;


    public TransientEventsCleanup(@NonNull Table<CalendarContract.Events> eventsTable)
    {
        // wipe all events without sync sync id which have been deleted
        mDelegate = new BulkDelete<>(eventsTable, new AllOf(
                new IsNull(CalendarContract.Events.ORIGINAL_SYNC_ID),
                new IsNull(CalendarContract.Events._SYNC_ID),
                new EqArg(CalendarContract.Events.DELETED, 1)));
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
