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

import org.dmfs.android.contentpal.InsertOperation;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.optional.Absent;
import org.dmfs.optional.Optional;


/**
 * A {@link InsertOperation} decorator that a foreign key to another row of type {@code <V>}.
 *
 * @author Marten Gajda
 */
public final class Related<T, V> implements InsertOperation<T>
{
    private final InsertOperation<T> mDelegate;
    private final RowSnapshot<V> mRowSnapshot;
    private final String mForeignKeyColumn;


    public Related(@NonNull RowSnapshot<V> rowSnapshot, @NonNull String foreignKeyColumn, @NonNull InsertOperation<T> delegate)
    {
        mDelegate = delegate;
        mRowSnapshot = rowSnapshot;
        mForeignKeyColumn = foreignKeyColumn;
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
        return transactionContext.resolved(mRowSnapshot.reference())
                .builderWithReferenceData(mDelegate.contentOperationBuilder(transactionContext), mForeignKeyColumn);
    }
}
