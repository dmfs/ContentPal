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

package org.dmfs.android.contactspal.batches;

import android.provider.ContactsContract;

import org.dmfs.android.contactspal.aggregation.Link;
import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.RowReference;
import org.dmfs.iterables.decorators.DelegatingIterable;
import org.dmfs.jems.iterable.decorators.Mapped;

import androidx.annotation.NonNull;


/**
 * An {@link Iterable} of {@link Operation}s which link a given RawContact to a number of other {@link ContactsContract.RawContacts}.
 *
 * @author Marten Gajda
 */
public final class BulkLinkBatch extends DelegatingIterable<Operation<?>>
{

    public BulkLinkBatch(@NonNull final RowReference<ContactsContract.RawContacts> rawContact, @NonNull Iterable<RowReference<ContactsContract.RawContacts>> linked)
    {
        super(new Mapped<>(rawContactsRowReference -> new Link(rawContact, rawContactsRowReference), linked));
    }

}
