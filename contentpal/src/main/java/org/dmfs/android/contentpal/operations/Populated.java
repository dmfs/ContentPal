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

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.jems2.Optional;

import androidx.annotation.NonNull;


/**
 * An {@link Operation} which populates another {@link Operation} with a given {@link RowData}.
 *
 * @author Marten Gajda
 */
public final class Populated<T> implements Operation<T>
{
    private final Operation<T> mOperation;
    private final RowData<T> mRowData;


    public Populated(@NonNull RowData<T> rowData, @NonNull Operation<T> putOperation)
    {
        mOperation = putOperation;
        mRowData = rowData;
    }


    @NonNull
    @Override
    public Optional<SoftRowReference<T>> reference()
    {
        return mOperation.reference();
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder contentOperationBuilder(@NonNull TransactionContext transactionContext)
    {
        return mRowData.updatedBuilder(transactionContext, mOperation.contentOperationBuilder(transactionContext));
    }
}
