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

import android.accounts.Account;
import android.provider.ContactsContract;

import org.dmfs.android.contactspal.operations.RawContactData;
import org.dmfs.android.contactspal.tables.RawContacts;
import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.batches.MultiInsertBatch;
import org.dmfs.android.contentpal.operations.Put;
import org.dmfs.android.contentpal.rowsnapshots.VirtualRowSnapshot;
import org.dmfs.android.contentpal.tables.AccountScoped;
import org.dmfs.iterables.ArrayIterable;
import org.dmfs.iterables.SingletonIterable;
import org.dmfs.iterables.decorators.DelegatingIterable;
import org.dmfs.iterables.decorators.Flattened;


/**
 * An {@link Iterable} of {@link Operation}s which insert a new contact.
 *
 * @author Marten Gajda
 */
public final class InsertRawContactBatch extends DelegatingIterable<Operation<?>>
{

    /**
     * Inserts a new contact with the given data into the given {@link Account}.
     *
     * @param account
     * @param contactData
     */
    public InsertRawContactBatch(Account account, RowData<ContactsContract.Data>... contactData)
    {
        this(account, new ArrayIterable<>(contactData));
    }


    /**
     * Inserts a new contact with the given data into the given {@link Account}.
     *
     * @param account
     * @param contactData
     */
    public InsertRawContactBatch(Account account, Iterable<RowData<ContactsContract.Data>> contactData)
    {
        this(new AccountScoped<>(account, new RawContacts()), contactData);
    }


    /**
     * Inserts a new contact with the given data into the given {@link Table}.
     *
     * @param rawContacts
     * @param contactData
     */
    public InsertRawContactBatch(Table<ContactsContract.RawContacts> rawContacts, RowData<ContactsContract.Data>... contactData)
    {
        this(rawContacts, new ArrayIterable<>(contactData));
    }


    /**
     * Inserts a new contact with the given data into the given {@link Table}.
     *
     * @param rawContacts
     * @param contactData
     */
    public InsertRawContactBatch(Table<ContactsContract.RawContacts> rawContacts, Iterable<RowData<ContactsContract.Data>> contactData)
    {
        this(new VirtualRowSnapshot<>(rawContacts), contactData);
    }


    /**
     * Inserts the given rawContact with the given data.
     *
     * @param rawContact
     * @param contactData
     */
    public InsertRawContactBatch(RowSnapshot<ContactsContract.RawContacts> rawContact, RowData<ContactsContract.Data>... contactData)
    {
        this(rawContact, new ArrayIterable<>(contactData));
    }


    /**
     * Inserts the given rawContact with the given data.
     *
     * @param rawContact
     * @param contactData
     */
    public InsertRawContactBatch(RowSnapshot<ContactsContract.RawContacts> rawContact, Iterable<RowData<ContactsContract.Data>> contactData)
    {
        super(new Flattened<>(
                new SingletonIterable<Operation<?>>(
                        new Put<>(rawContact)),
                new MultiInsertBatch<>(
                        new RawContactData(rawContact),
                        contactData)));
    }

}
