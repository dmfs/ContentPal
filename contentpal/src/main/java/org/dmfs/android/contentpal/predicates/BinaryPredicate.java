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

import android.content.ContentProviderOperation;
import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.tools.Length;
import org.dmfs.iterables.ArrayIterable;
import org.dmfs.iterables.decorators.Flattened;
import org.dmfs.iterables.decorators.Mapped;
import org.dmfs.iterators.Function;


/**
 * A {@link Predicate} which connects a number of given predicates with the given binary operator. Like this:
 * <pre>{@code
 * (predicate_1) operator (predicate_2) operator (predicate_3) … operator (predicate_n)
 * }</pre>
 * <p>
 * If no predicates are given this always evaluates to "1".
 *
 * @author Marten Gajda
 */
public final class BinaryPredicate implements Predicate
{
    private final Predicate[] mPredicates;
    private final String mOperator;


    public BinaryPredicate(@NonNull String operator, @NonNull Predicate... predicates)
    {
        mOperator = operator;
        mPredicates = predicates;
    }


    @NonNull
    @Override
    public CharSequence selection(@NonNull TransactionContext transactionContext)
    {
        if (mPredicates.length == 0)
        {
            // if no predicates are present, all predicates are satisfied
            return "1";
        }
        if (mPredicates.length == 1)
        {
            return mPredicates[0].selection(transactionContext);
        }
        StringBuilder result = new StringBuilder(mPredicates.length * 24);
        result.append("( ");
        result.append(mPredicates[0].selection(transactionContext));
        for (int i = 1, count = mPredicates.length; i < count; ++i)
        {
            result.append(" ) ");
            result.append(mOperator);
            result.append(" ( ");
            result.append(mPredicates[i].selection(transactionContext));
        }
        result.append(" )");
        return result;

    }


    @NonNull
    @Override
    public Iterable<String> arguments(@NonNull final TransactionContext transactionContext)
    {
        return new Flattened<>(new Mapped<>(new ArrayIterable<>(mPredicates), new Function<Predicate, Iterable<String>>()
        {
            @Override
            public Iterable<String> apply(Predicate argument)
            {
                return argument.arguments(transactionContext);
            }
        }));
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(@NonNull TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder, int argOffset)
    {
        int offset = argOffset;
        for (Predicate predicate : mPredicates)
        {
            predicate.updatedBuilder(transactionContext, builder, offset);
            offset += new Length(predicate.arguments(transactionContext)).intValue();
        }

        return builder;
    }
}
