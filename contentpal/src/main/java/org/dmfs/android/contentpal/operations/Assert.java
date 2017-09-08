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

package org.dmfs.android.contentpal.operations;

import android.content.ContentProviderOperation;
import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.predicates.AnyOf;
import org.dmfs.android.contentpal.rowdata.EmptyRowData;
import org.dmfs.android.contentpal.tools.uriparams.EmptyUriParams;
import org.dmfs.optional.Absent;
import org.dmfs.optional.Optional;


/**
 * An {@link Operation} for an assert query which checks that the given {@link RowData} is present in the given {@link Table}
 * filtered by the given {@link Predicate}.
 *
 * @author Gabor Keszthelyi
 */
public final class Assert<T> implements Operation<T>
{
    private final Table<T> mTable;
    private final RowData<T> mRowData;
    private final Predicate mPredicate;


    public Assert(Table<T> table, RowData<T> rowData, Predicate predicate)
    {
        mTable = table;
        mRowData = rowData;
        mPredicate = predicate;
    }


    public Assert(Table<T> table, RowData<T> rowData)
    {
        this(table, rowData, new AnyOf());
    }


    public Assert(Table<T> table, Predicate predicate)
    {
        this(table, new EmptyRowData<T>(), predicate);
    }


    public Assert(Table<T> table)
    {
        this(table, new EmptyRowData<T>(), new AnyOf());
    }


    @NonNull
    @Override
    public Optional<SoftRowReference<T>> reference()
    {
        return Absent.absent();
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder contentOperationBuilder(@NonNull TransactionContext transactionContext) throws UnsupportedOperationException
    {
        return mRowData.updatedBuilder(transactionContext,
                mTable.assertOperation(EmptyUriParams.INSTANCE, mPredicate).contentOperationBuilder(transactionContext));
    }
}
