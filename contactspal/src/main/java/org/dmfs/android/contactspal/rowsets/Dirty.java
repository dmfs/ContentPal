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

import org.dmfs.android.contactspal.tables.RawContacts;
import org.dmfs.android.contentpal.RowSet;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.View;
import org.dmfs.android.contentpal.predicates.AnyOf;
import org.dmfs.android.contentpal.predicates.EqArg;
import org.dmfs.android.contentpal.rowsets.QueryRowSet;
import org.dmfs.android.contentpal.tools.ClosableIterator;


/**
 * A {@link RowSet} of all {@link RawContacts} rows of dirty and deleted entries.
 *
 * @author Marten Gajda
 */
public final class Dirty implements RowSet<ContactsContract.RawContacts>
{
    private final RowSet<ContactsContract.RawContacts> mDelegate;


    public Dirty(@NonNull View<ContactsContract.RawContacts> mRawContacts)
    {
        mDelegate = new QueryRowSet<>(mRawContacts,
                new AnyOf(
                        new EqArg(ContactsContract.RawContacts.DIRTY, 1),
                        new EqArg(ContactsContract.RawContacts.DELETED, 1)));
    }


    @NonNull
    @Override
    public ClosableIterator<RowSnapshot<ContactsContract.RawContacts>> iterator()
    {
        return mDelegate.iterator();
    }
}
