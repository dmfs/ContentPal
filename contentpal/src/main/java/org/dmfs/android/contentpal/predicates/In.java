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
import org.dmfs.iterables.decorators.Mapped;
import org.dmfs.iterators.Function;


/**
 * The conditional {@code IN} operator. Validates to {@code true} if a given column has any of the given values (their string representation to be more
 * precise).
 * <p>
 * Note, {@code null} is not a supported value.
 *
 * @author Marten Gajda
 */
public final class In implements Predicate
{
    private final String mColumnName;
    private final Object[] mArguments;


    public In(@NonNull String columnName, @NonNull Object... arguments)
    {
        mColumnName = columnName;
        mArguments = arguments.clone();
    }


    @NonNull
    @Override
    public CharSequence selection()
    {
        StringBuilder sb = new StringBuilder(mColumnName.length() + mArguments.length * 3 + 9);
        sb.append(mColumnName);
        sb.append(" in (");
        if (mArguments.length > 0)
        {
            sb.append(" ?");
        }
        for (int i = 1, count = mArguments.length; i < count; ++i)
        {
            sb.append(", ?");
        }
        sb.append(" ) ");
        return sb;
    }


    @NonNull
    @Override
    public Iterable<String> arguments()
    {
        return new Mapped<>(
                new ArrayIterable<>(mArguments),
                new Function<Object, String>()
                {
                    @Override
                    public String apply(Object argument)
                    {
                        return argument.toString();
                    }
                });
    }
}
