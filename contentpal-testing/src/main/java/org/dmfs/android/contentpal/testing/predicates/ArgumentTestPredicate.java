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

package org.dmfs.android.contentpal.testing.predicates;

import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.iterables.ArrayIterable;


/**
 * A mocked {@link Predicate} which returns exactly what has been passed
 *
 * @author Marten Gajda
 */
public final class ArgumentTestPredicate implements Predicate
{

    private final TransactionContext mExpectedTransactionContext;
    private final Iterable<Argument> mArguments;


    public ArgumentTestPredicate(TransactionContext expectedTransactionContext1, Argument... arguments)
    {
        mExpectedTransactionContext = expectedTransactionContext1;
        mArguments = new ArrayIterable<>(arguments);
    }


    @NonNull
    @Override
    public CharSequence selection(@NonNull TransactionContext transactionContext)
    {
        throw new UnsupportedOperationException("operation not mocked");
    }


    @NonNull
    @Override
    public Iterable<Argument> arguments(@NonNull TransactionContext transactionContext)
    {
        if (mExpectedTransactionContext != transactionContext)
        {
            throw new AssertionError("wrong transaction context received");
        }
        return mArguments;
    }

}
