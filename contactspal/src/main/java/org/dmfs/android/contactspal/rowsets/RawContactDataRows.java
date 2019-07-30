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

package org.dmfs.android.contactspal.rowsets;

import android.provider.BaseColumns;
import android.provider.ContactsContract;

import org.dmfs.android.contactspal.tables.Data;
import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.Projection;
import org.dmfs.android.contentpal.RowReference;
import org.dmfs.android.contentpal.RowSet;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.View;
import org.dmfs.android.contentpal.predicates.AllOf;
import org.dmfs.android.contentpal.predicates.AnyOf;
import org.dmfs.android.contentpal.predicates.ReferringTo;
import org.dmfs.android.contentpal.references.RowSnapshotReference;
import org.dmfs.android.contentpal.rowsets.DelegatingRowSet;
import org.dmfs.android.contentpal.rowsets.Frozen;
import org.dmfs.android.contentpal.rowsets.QueryRowSet;

import androidx.annotation.NonNull;


/**
 * The {@link RowSet} of the {@link ContactsContract.Data} rows of a specific raw contact.
 * <p>
 * Note, the result is not cached. Iterating this multiple times, will query the content provider each time. Use {@link Frozen} to retain the result.
 *
 * @author Marten Gajda
 */
public final class RawContactDataRows extends DelegatingRowSet<ContactsContract.Data>
{
    /**
     * Creates a {@link RowSet} of all {@link ContactsContract.Data} rows of the given raw contact.
     *
     * @param dataView
     *         A {@link View} onto the {@link ContactsContract.Data} table, like {@link Data}.
     * @param rawContact
     *         The {@link RowSnapshot} of a RawContact.
     */
    public RawContactDataRows(@NonNull View<ContactsContract.Data> dataView, @NonNull Projection<ContactsContract.Data> projection, @NonNull RowSnapshot<ContactsContract.RawContacts> rawContact)
    {
        this(dataView, projection, rawContact, new AnyOf<>());
    }


    /**
     * Creates a {@link RowSet} of all {@link ContactsContract.Data} rows of the given raw contact.
     *
     * @param dataView
     *         A {@link View} onto the {@link ContactsContract.Data} table, like {@link Data}.
     * @param rawContact
     *         The {@link RowReference} of a RawContact.
     */
    public RawContactDataRows(@NonNull View<ContactsContract.Data> dataView, @NonNull Projection<ContactsContract.Data> projection, @NonNull RowReference<ContactsContract.RawContacts> rawContact)
    {
        this(dataView, projection, rawContact, new AnyOf<>());
    }


    /**
     * Creates a {@link RowSet} of all {@link ContactsContract.Data} rows of the given raw contact.
     *
     * @param dataView
     *         A {@link View} onto the {@link ContactsContract.Data} table, like {@link Data}.
     * @param rawContact
     *         The {@link RowSnapshot} of a RawContact.
     * @param predicate
     *         A {@link Predicate} to filter the data rows to return.
     */
    public RawContactDataRows(@NonNull View<ContactsContract.Data> dataView, @NonNull Projection<ContactsContract.Data> projection, @NonNull RowSnapshot<ContactsContract.RawContacts> rawContact, @NonNull Predicate<? super ContactsContract.Data> predicate)
    {
        this(dataView, projection, new RowSnapshotReference<>(rawContact), predicate);
    }


    /**
     * Creates a {@link RowSet} of all {@link ContactsContract.Data} rows of the given raw contact.
     *
     * @param dataView
     *         A {@link View} onto the {@link ContactsContract.Data} table, like {@link Data}.
     * @param rawContactReference
     *         The {@link RowReference} of a RawContact.
     * @param predicate
     *         A {@link Predicate} to filter the data rows to return.
     */
    public RawContactDataRows(@NonNull View<ContactsContract.Data> dataView, @NonNull Projection<ContactsContract.Data> projection, @NonNull RowReference<ContactsContract.RawContacts> rawContactReference, @NonNull Predicate<? super ContactsContract.Data> predicate)
    {
        this(dataView, projection, new AllOf<>(new ReferringTo<>(BaseColumns._ID, rawContactReference), predicate));
    }


    private RawContactDataRows(@NonNull View<ContactsContract.Data> dataView, @NonNull Projection<ContactsContract.Data> projection, @NonNull Predicate<? super ContactsContract.Data> predicate)
    {
        super(new QueryRowSet<>(dataView, projection, predicate));
    }

}
