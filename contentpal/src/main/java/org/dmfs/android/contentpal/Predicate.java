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

package org.dmfs.android.contentpal;

import android.content.ContentProviderOperation;
import android.support.annotation.NonNull;


/**
 * A predicate.
 *
 * @author Marten Gajda
 */
public interface Predicate
{
    /**
     * Returns an SQL selection CharSequence which selects elements matching this predicate.
     *
     * @param transactionContext
     *         The {@link TransactionContext} of the Transaction this is executed in.
     *
     * @return
     */
    @NonNull
    CharSequence selection(@NonNull TransactionContext transactionContext);

    /**
     * An {@link Iterable} of all arguments of this predicate.
     *
     * @param transactionContext
     *         The {@link TransactionContext} of the Transaction this is executed in.
     */
    @NonNull
    Iterable<String> arguments(@NonNull TransactionContext transactionContext);

    /**
     * Updates the selection of the given {@link ContentProviderOperation.Builder} with any back references.
     *
     * @param transactionContext
     *         The current {@link TransactionContext}
     * @param builder
     *         The {@link ContentProviderOperation.Builder} to update.
     * @param argOffset
     *         The offset of the argument of this Predicate in the arguments array.
     *
     * @return The given {@link ContentProviderOperation.Builder}.
     */
    @NonNull
    ContentProviderOperation.Builder updatedBuilder(@NonNull TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder, int argOffset);
}
