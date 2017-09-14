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
import org.dmfs.iterables.EmptyIterable;
import org.dmfs.optional.Optional;


/**
 * @author Marten Gajda
 */
public final class EqCol implements Predicate
{
    private final String mColumn1Name;
    private final String mColumn2Name;


    public EqCol(@NonNull String column1Name, @NonNull String column2Name)
    {
        mColumn1Name = column1Name;
        mColumn2Name = column2Name;
    }


    @NonNull
    @Override
    public CharSequence selection(@NonNull TransactionContext transactionContext)
    {
        return new StringBuilder(mColumn1Name.length() + 3 + mColumn2Name.length()).append(mColumn1Name).append(" = ").append(mColumn2Name);
    }


    @NonNull
    @Override
    public Iterable<String> arguments(@NonNull TransactionContext transactionContext)
    {
        return EmptyIterable.instance();
    }


    @NonNull
    @Override
    public Iterable<Optional<Integer>> backReferences(@NonNull TransactionContext transactionContext)
    {
        return EmptyIterable.instance();
    }
}
