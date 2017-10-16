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

import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.RowSet;
import org.dmfs.android.contentpal.View;
import org.dmfs.android.contentpal.predicates.IsNull;
import org.dmfs.android.contentpal.rowsets.DelegatingRowSet;
import org.dmfs.android.contentpal.rowsets.QueryRowSet;


/**
 * A {@link RowSet} of all {@link ContactsContract.RawContacts} rows of unsynced contacts.
 *
 * @author Marten Gajda
 */
public final class Unsynced extends DelegatingRowSet<ContactsContract.RawContacts>
{

    public Unsynced(@NonNull View<ContactsContract.RawContacts> mRawContacts)
    {
        super(new QueryRowSet<>(mRawContacts, new IsNull(ContactsContract.RawContacts.SOURCE_ID)));
    }

}
