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

import android.content.ContentProviderOperation;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import org.dmfs.android.contactspal.tables.AggregationExceptions;
import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.RowReference;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.predicates.AnyOf;
import org.dmfs.android.contentpal.tools.uriparams.EmptyUriParams;
import org.dmfs.optional.Absent;
import org.dmfs.optional.Optional;


/**
 * An {@link Operation} which updates the aggregation exception of two specific raw contacts.
 *
 * @author Marten Gajda
 */
final class AggregationException implements Operation<ContactsContract.AggregationExceptions>
{
    private final RowReference<ContactsContract.RawContacts> mRawContact1;
    private final RowReference<ContactsContract.RawContacts> mRawContact2;


    AggregationException(@NonNull RowReference<ContactsContract.RawContacts> rawContact1, @NonNull RowReference<ContactsContract.RawContacts> rawContact2)
    {
        mRawContact1 = rawContact1;
        mRawContact2 = rawContact2;
    }


    @NonNull
    @Override
    public Optional<SoftRowReference<ContactsContract.AggregationExceptions>> reference()
    {
        // you can't refer to a specific row in the aggregation table
        return Absent.absent();
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder contentOperationBuilder(@NonNull TransactionContext transactionContext) throws UnsupportedOperationException
    {
        // updating Aggregation exceptions works a bit strange. Instead of selecting a specific entry, we have to refer to them in the values.
        return mRawContact2.builderWithReferenceData(
                transactionContext,
                mRawContact1.builderWithReferenceData(
                        transactionContext,
                        AggregationExceptions.INSTANCE.updateOperation(
                                EmptyUriParams.INSTANCE,
                                new AnyOf()
                        ).contentOperationBuilder(transactionContext),
                        ContactsContract.AggregationExceptions.RAW_CONTACT_ID1), ContactsContract.AggregationExceptions.RAW_CONTACT_ID2);

    }
}
