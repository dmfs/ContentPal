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

package org.dmfs.android.contentpal.batches;

import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.InsertOperation;
import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.OperationsBatch;
import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.operations.Populated;
import org.dmfs.iterables.ArrayIterable;
import org.dmfs.iterators.Function;
import org.dmfs.iterators.decorators.Mapped;
import org.dmfs.optional.Optional;
import org.dmfs.optional.iterable.PresentValues;

import java.util.Iterator;


/**
 * An {@link OperationsBatch} to insert multiple rows of a specific {@link InsertOperation}, populated with given {@link RowData}.
 * <p>
 * Example use case:
 * <pre>{@code
 * // insert a number of data rows for a given rawContact
 * new MultiInsertBatch<ContactsContract.Data>(
 *    // prototype is a data row which is related to a specific contact
 *    new RawContactData(rawContact),
 *    // the data entries to insert:
 *    new DisplayName(name),
 *    new Phone(phoneType, phone),
 *    new Email(emailType, email)
 * )
 * }</pre>
 *
 * @param <T>
 *         The contract of the rows to insert.
 *
 * @author Marten Gajda
 */
public final class MultiInsertBatch<T> implements OperationsBatch
{
    private final InsertOperation<T> mInsertOperation;
    private final Iterable<RowData<T>> mData;


    /**
     * Creates an {@link OperationsBatch} to insert rows with the given {@link Optional} {@link RowData}, based on the given {@link InsertOperation}.
     * Only present {@link RowData} will generate a new row.
     *
     * @param insertOperation
     *         The {@link InsertOperation} to use for each row.
     * @param data
     *         The {@link Optional} {@link RowData} of the rows to insert (one {@link RowData} per row)
     */
    @SafeVarargs
    public MultiInsertBatch(@NonNull InsertOperation<T> insertOperation, @NonNull Optional<RowData<T>>... data)
    {
        this(insertOperation, new PresentValues<RowData<T>>(new ArrayIterable<>(data)));
    }


    /**
     * Creates an {@link OperationsBatch} to insert rows with the given {@link RowData}, based on the given {@link InsertOperation}.
     *
     * @param insertOperation
     *         The {@link InsertOperation} to use for each row.
     * @param data
     *         The {@link RowData} of the rows to insert (one {@link RowData} per row)
     */
    @SafeVarargs
    public MultiInsertBatch(@NonNull InsertOperation<T> insertOperation, @NonNull RowData<T>... data)
    {
        this(insertOperation, new ArrayIterable<>(data));
    }


    /**
     * Creates an {@link OperationsBatch} to insert rows with the given {@link RowData}, based on the given {@link InsertOperation}.
     *
     * @param insertOperation
     *         The {@link InsertOperation} to use for each row.
     * @param data
     *         An {@link Iterable} of {@link RowData} of the rows to insert (one {@link RowData} per row)
     */
    public MultiInsertBatch(@NonNull InsertOperation<T> insertOperation, @NonNull Iterable<RowData<T>> data)
    {
        mInsertOperation = insertOperation;
        mData = data;
    }


    @NonNull
    @Override
    public Iterator<Operation<?>> iterator()
    {
        return new Mapped<>(mData.iterator(), new Function<RowData<T>, Operation<?>>()
        {
            @Override
            public Operation<?> apply(RowData<T> argument)
            {
                return new Populated<>(argument, mInsertOperation);
            }
        });
    }
}
