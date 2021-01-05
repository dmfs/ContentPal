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

package org.dmfs.android.contentpal.rowdata;

import android.content.ContentProviderOperation;

import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.jems.iterable.adapters.PresentValues;
import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.jems.optional.Optional;

import androidx.annotation.NonNull;


/**
 * {@link RowData} composition decorator, i.e. {@link RowData} composed of other {@link RowData}.
 */
public final class Composite<T> implements RowData<T>
{
    private final Iterable<? extends RowData<? super T>> mDelegates;


    @SafeVarargs
    public Composite(@NonNull Optional<? extends RowData<? super T>>... optionalDelegates)
    {
        this(new PresentValues<>(optionalDelegates));
    }


    @SafeVarargs
    public Composite(@NonNull RowData<? super T>... delegates)
    {
        this(new Seq<>(delegates));
    }


    public Composite(@NonNull Iterable<? extends RowData<? super T>> delegates)
    {
        mDelegates = delegates;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(@NonNull TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder)
    {
        for (RowData<? super T> rowData : mDelegates)
        {
            rowData.updatedBuilder(transactionContext, builder);
        }
        return builder;
    }
}
