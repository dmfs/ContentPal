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


/**
 * @author Marten Gajda
 */
public final class Not implements Predicate
{
    private final Predicate mPredicate;


    public Not(@NonNull Predicate predicate)
    {
        mPredicate = predicate;
    }


    @NonNull
    @Override
    public CharSequence selection(@NonNull TransactionContext transactionContext)
    {
        CharSequence subSelection = mPredicate.selection(transactionContext);
        return new StringBuilder(subSelection.length() + 10).append("not ( ").append(subSelection).append(" )");
    }


    @NonNull
    @Override
    public Iterable<String> arguments(@NonNull TransactionContext transactionContext)
    {
        return mPredicate.arguments(transactionContext);
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(@NonNull TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder, int argOffset)
    {
        return mPredicate.updatedBuilder(transactionContext, builder, argOffset);
    }
}
