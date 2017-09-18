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

package org.dmfs.android.contentpal.predicates;

import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.RowReference;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.references.RowSnapshotReference;
import org.dmfs.optional.Optional;


/**
 * Predicate which matches rows which have a foreign key to another row.
 *
 * @author Marten Gajda
 */
public final class ReferringTo<T> implements Predicate
{
    private final String mColumnName;
    private final RowReference<T> mRowReference;


    public ReferringTo(@NonNull String columnName, @NonNull RowSnapshot<T> row)
    {
        mColumnName = columnName;
        mRowReference = new RowSnapshotReference<>(row);
    }


    @NonNull
    @Override
    public CharSequence selection(@NonNull TransactionContext transactionContext)
    {
        return mRowReference.predicate(transactionContext, mColumnName).selection(transactionContext);
    }


    @NonNull
    @Override
    public Iterable<String> arguments(@NonNull TransactionContext transactionContext)
    {
        return mRowReference.predicate(transactionContext, mColumnName).arguments(transactionContext);
    }


    @NonNull
    @Override
    public Iterable<Optional<Integer>> backReferences(@NonNull TransactionContext transactionContext)
    {
        return mRowReference.predicate(transactionContext, mColumnName).backReferences(transactionContext);
    }
}
