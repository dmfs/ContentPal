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
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.iterables.SingletonIterable;
import org.dmfs.optional.Absent;
import org.dmfs.optional.Optional;


/**
 * A {@link Predicate} which compares a column to an argument using the {@code like} operator.
 *
 * @author Marten Gajda
 */
public final class LikeArg implements Predicate
{
    private final String mColumnName;
    private final Object mArgument;


    public LikeArg(@NonNull String columnName, @NonNull Object argument)
    {
        mColumnName = columnName;
        mArgument = argument;
    }


    @NonNull
    @Override
    public CharSequence selection(@NonNull TransactionContext transactionContext)
    {
        return new StringBuilder(mColumnName).append(" like ?");
    }


    @NonNull
    @Override
    public Iterable<String> arguments(@NonNull TransactionContext transactionContext)
    {
        return new SingletonIterable<>(mArgument.toString());
    }


    @NonNull
    @Override
    public Iterable<Optional<Integer>> backReferences(@NonNull TransactionContext transactionContext)
    {
        return new SingletonIterable<>((Optional<Integer>) Absent.<Integer>absent());
    }
}
