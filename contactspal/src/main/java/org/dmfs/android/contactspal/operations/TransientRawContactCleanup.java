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

package org.dmfs.android.contactspal.operations;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import org.dmfs.android.contactspal.predicates.TransientRawContact;
import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.operations.BulkDelete;
import org.dmfs.android.contentpal.operations.DelegatingOperation;


/**
 * An {@link Operation} which removes all RawContacts which have been deleted before they have been synced.
 *
 * @author Marten Gajda
 */
public final class TransientRawContactCleanup extends DelegatingOperation<ContactsContract.RawContacts>
{
    public TransientRawContactCleanup(@NonNull Table<ContactsContract.RawContacts> rawContactsTable)
    {
        // delete all RawContacts without source id which have been deleted
        super(new BulkDelete<>(rawContactsTable, new TransientRawContact()));
    }
}
