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

package org.dmfs.android.contentpal.references;

import android.content.ContentProviderOperation;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.RowDataSnapshot;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.predicates.EqArg;
import org.dmfs.android.contentpal.tools.uriparams.EmptyUriParams;


/**
 * A {@link SoftRowReference} to an existing database row.
 *
 * @author Marten Gajda
 */
public final class AbsoluteRowReference<T> implements SoftRowReference<T>
{
    private final Table<T> mTable;
    private final RowDataSnapshot<T> mRowDataSnapshot;


    public AbsoluteRowReference(@NonNull Table<T> table, @NonNull RowDataSnapshot<T> rowDataSnapshot)
    {
        mTable = table;
        mRowDataSnapshot = rowDataSnapshot;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder putOperationBuilder(@NonNull TransactionContext transactionContext)
    {
        return mTable.updateOperation(EmptyUriParams.INSTANCE, new EqArg(BaseColumns._ID, rowId())).contentOperationBuilder(transactionContext);
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder deleteOperationBuilder(@NonNull TransactionContext transactionContext)
    {
        return mTable.deleteOperation(EmptyUriParams.INSTANCE, new EqArg(BaseColumns._ID, rowId())).contentOperationBuilder(transactionContext);
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder builderWithReferenceData(@NonNull TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder operationBuilder, @NonNull String foreignKeyColumn)
    {
        return operationBuilder.withValue(foreignKeyColumn, rowId());
    }


    @NonNull
    @Override
    public Predicate predicate(@NonNull String keyColumn)
    {
        return new EqArg(keyColumn, rowId());
    }


    private String rowId()
    {
        return mRowDataSnapshot.charData(BaseColumns._ID).value().toString();
    }


    @Override
    public boolean isVirtual()
    {
        return false;
    }

}
