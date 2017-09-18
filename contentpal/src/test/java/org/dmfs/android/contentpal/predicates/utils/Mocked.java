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

package org.dmfs.android.contentpal.predicates.utils;

import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.predicates.arguments.ValueArgument;
import org.dmfs.iterables.ArrayIterable;
import org.dmfs.iterables.decorators.Mapped;
import org.dmfs.iterators.Function;


/**
 * A mocked {@link Predicate} which returns exactly what has been passed
 *
 * @author Marten Gajda
 */
public final class Mocked implements Predicate
{

    private final CharSequence mSelection;
    private final Iterable<String> mArguments;


    public Mocked(CharSequence selection, String... arguments)
    {
        mSelection = selection;
        mArguments = new ArrayIterable<>(arguments);
    }


    @NonNull
    @Override
    public CharSequence selection(@NonNull TransactionContext transactionContext)
    {
        return mSelection;
    }


    @NonNull
    @Override
    public Iterable<Argument> arguments(@NonNull TransactionContext transactionContext)
    {
        return new Mapped<>(mArguments, new Function<String, Argument>()
        {
            @Override
            public Argument apply(String argument)
            {
                return new ValueArgument(argument);
            }
        });
    }
}
