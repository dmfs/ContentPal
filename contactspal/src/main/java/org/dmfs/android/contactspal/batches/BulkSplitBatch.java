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
import android.support.annotation.NonNull;

import org.dmfs.android.contactspal.aggregation.Split;
import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.OperationsBatch;
import org.dmfs.android.contentpal.RowReference;
import org.dmfs.iterators.Function;
import org.dmfs.iterators.decorators.Mapped;

import java.util.Iterator;


/**
 * An {@link OperationsBatch} which splits a given RawContact from a number of other {@link ContactsContract.RawContacts}.
 *
 * @author Marten Gajda
 */
public final class BulkSplitBatch implements OperationsBatch
{
    private final RowReference<ContactsContract.RawContacts> mRawContact;
    private final Iterable<RowReference<ContactsContract.RawContacts>> mLinked;


    public BulkSplitBatch(@NonNull RowReference<ContactsContract.RawContacts> rawContact, @NonNull Iterable<RowReference<ContactsContract.RawContacts>> linked)
    {
        mRawContact = rawContact;
        mLinked = linked;
    }


    @Override
    public Iterator<Operation<?>> iterator()
    {
        return new Mapped<>(mLinked.iterator(), new Function<RowReference<ContactsContract.RawContacts>, Operation<?>>()
        {

            @Override
            public Operation<?> apply(RowReference<ContactsContract.RawContacts> rawContactsRowReference)
            {
                return new Split(mRawContact, rawContactsRowReference);
            }
        });
    }
}
