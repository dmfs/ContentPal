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

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.TransactionContext;

import androidx.annotation.NonNull;

import static org.dmfs.jems2.iterable.EmptyIterable.emptyIterable;


/**
 * A {@link Predicate} which evaluates to {@code true}, if and only if the given column contains a {@code null} value.
 *
 * @author Marten Gajda
 */
public final class IsNull<Contract> implements Predicate<Contract>
{
    private final String mColumnName;


    public IsNull(@NonNull String columnName)
    {
        mColumnName = columnName;
    }


    @NonNull
    @Override
    public CharSequence selection(@NonNull TransactionContext transactionContext)
    {
        return new StringBuilder(mColumnName).append(" is null");
    }


    @NonNull
    @Override
    public Iterable<Argument> arguments(@NonNull TransactionContext transactionContext)
    {
        return emptyIterable();
    }
}
