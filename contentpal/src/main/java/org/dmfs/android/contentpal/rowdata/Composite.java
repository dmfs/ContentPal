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
import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.iterables.ArrayIterable;
import org.dmfs.optional.Optional;
import org.dmfs.optional.iterable.PresentValues;


/**
 * {@link RowData} composition decorator, i.e. {@link RowData} composed of other {@link RowData}.
 *
 * @author Marten Gajda
 */
public final class Composite<T> implements RowData<T>
{
    private final Iterable<? extends RowData<T>> mDelegates;


    @SafeVarargs
    public Composite(Optional<RowData<T>>... optionalDelegates)
    {
        this(new PresentValues<>(new ArrayIterable<>(optionalDelegates)));
    }


    @SafeVarargs
    public Composite(RowData<T>... delegates)
    {
        this(new ArrayIterable<>(delegates));
    }


    public Composite(Iterable<? extends RowData<T>> delegates)
    {
        mDelegates = delegates;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder)
    {
        for (RowData<T> rowData : mDelegates)
        {
            rowData.updatedBuilder(transactionContext, builder);
        }
        return builder;
    }
}
