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
import org.dmfs.iterables.ArrayIterable;
import org.dmfs.iterables.decorators.Flattened;
import org.dmfs.iterables.decorators.Mapped;
import org.dmfs.iterators.Function;


/**
 * A {@link Predicate} which evaluates to {@code true}, if and only if at least one of the given {@link Predicate}s evaluates to {@code true}. This corresponds
 * to the Boolean "or" operation.
 *
 * @author Marten Gajda
 */
public final class AnyOf implements Predicate
{
    private final Predicate[] mPredicates;


    public AnyOf(@NonNull Predicate... predicates)
    {
        mPredicates = predicates.clone();
    }


    @NonNull
    @Override
    public CharSequence selection()
    {
        if (mPredicates.length == 0)
        {
            // if no predicates are present, all predicates are satisfied
            return "1";
        }
        if (mPredicates.length == 1)
        {
            return mPredicates[0].selection();
        }
        StringBuilder result = new StringBuilder(mPredicates.length * 24);
        result.append("( ");
        result.append(mPredicates[0].selection());
        for (int i = 1, count = mPredicates.length; i < count; ++i)
        {
            result.append(" ) or ( ");
            result.append(mPredicates[i].selection());
        }
        result.append(" )");
        return result;
    }


    @NonNull
    @Override
    public Iterable<String> arguments()
    {
        return new Flattened<>(new Mapped<>(new ArrayIterable<>(mPredicates), new Function<Predicate, Iterable<String>>()
        {
            @Override
            public Iterable<String> apply(Predicate argument)
            {
                return argument.arguments();
            }
        }));
    }
}
