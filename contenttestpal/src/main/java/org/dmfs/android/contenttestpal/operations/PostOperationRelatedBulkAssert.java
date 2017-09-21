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

package org.dmfs.android.contenttestpal.operations;

import android.content.ContentProviderOperation;
import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.RowReference;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.predicates.AllOf;
import org.dmfs.android.contentpal.predicates.AnyOf;
import org.dmfs.android.contentpal.rowdata.EmptyRowData;
import org.dmfs.android.contentpal.tools.uriparams.EmptyUriParams;
import org.dmfs.optional.Absent;
import org.dmfs.optional.Optional;


/**
 * An assert {@link Operation} that can be used to check rows in a table <code>T</code> that refer to a row from
 * another table <code>V</code> through the given foreign key column.
 * <p>
 * IMPORTANT: This assert only works if the referred {@link RowSnapshot} had already been committed in a previous transaction.
 *
 * @author Gabor Keszthelyi
 */
public final class PostOperationRelatedBulkAssert<T> implements Operation<T>
{
    private final Table<T> mTable;
    private final RowSnapshot<?> mRowSnapshot;
    private final String mForeignKeyColumn;
    private final RowData<T> mRowData;
    private final Predicate mPredicate;


    public PostOperationRelatedBulkAssert(Table<T> table, RowSnapshot<?> rowSnapshot, String foreignKeyColumn, RowData<T> rowData, Predicate predicate)
    {
        mTable = table;
        mRowSnapshot = rowSnapshot;
        mForeignKeyColumn = foreignKeyColumn;
        mRowData = rowData;
        mPredicate = predicate;
    }


    public PostOperationRelatedBulkAssert(Table<T> table, RowSnapshot<?> rowSnapshot, String foreignKeyColumn, RowData<T> rowData)
    {
        this(table, rowSnapshot, foreignKeyColumn, rowData, new AnyOf());
    }


    public PostOperationRelatedBulkAssert(Table<T> table, RowSnapshot<?> rowSnapshot, String foreignKeyColumn, Predicate predicate)
    {
        this(table, rowSnapshot, foreignKeyColumn, new EmptyRowData<T>(), predicate);
    }


    public PostOperationRelatedBulkAssert(Table<T> table, RowSnapshot<?> rowSnapshot, String foreignKeyColumn)
    {
        this(table, rowSnapshot, foreignKeyColumn, new EmptyRowData<T>(), new AnyOf());
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
        RowReference<?> resolvedRowReference = transactionContext.resolved(mRowSnapshot.reference());

        // Note: foreign id has to be added to data as well here (not only as predicate) to avoid exception because of empty data
        return resolvedRowReference.builderWithReferenceData(transactionContext,
                mRowData.updatedBuilder(transactionContext,
                        mTable.assertOperation(EmptyUriParams.INSTANCE, new AllOf(mPredicate, resolvedRowReference.predicate(mForeignKeyColumn)))
                                .contentOperationBuilder(transactionContext)), mForeignKeyColumn);
    }
}
