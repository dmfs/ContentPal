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

package org.dmfs.android.contactspal.aggregation;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.RowReference;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.operations.DelegatingOperation;
import org.dmfs.android.contentpal.operations.Populated;
import org.dmfs.android.contentpal.references.RowSnapshotReference;


/**
 * An {@link Operation} which let's Android decide whether two raw contacts should be aggregated.
 *
 * @author Marten Gajda
 */
public final class AutoAggregate extends DelegatingOperation<ContactsContract.AggregationExceptions>
{

    public AutoAggregate(@NonNull RowSnapshot<ContactsContract.RawContacts> rawContact1, @NonNull RowSnapshot<ContactsContract.RawContacts> rawContact2)
    {
        this(new RowSnapshotReference<>(rawContact1), new RowSnapshotReference<>(rawContact2));
    }


    public AutoAggregate(@NonNull RowReference<ContactsContract.RawContacts> rawContact1, @NonNull RowSnapshot<ContactsContract.RawContacts> rawContact2)
    {
        this(rawContact1, new RowSnapshotReference<>(rawContact2));
    }


    public AutoAggregate(@NonNull RowReference<ContactsContract.RawContacts> rawContact1, @NonNull RowReference<ContactsContract.RawContacts> rawContact2)
    {
        super(new Populated<>(
                new AggregationTypeData(ContactsContract.AggregationExceptions.TYPE_AUTOMATIC),
                new AggregationException(rawContact1, rawContact2)));
    }
}
