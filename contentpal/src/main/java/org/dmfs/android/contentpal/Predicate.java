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

import android.support.annotation.NonNull;

import org.dmfs.optional.Optional;


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
     * Returns an Iterable of optional back references. Each present value returned will replace the argument at the same position with the result of the
     * operation at the index.
     *
     * @param transactionContext
     *         The current {@link TransactionContext}
     *
     * @return An Iterable of optional back references. This must iterate exactly the same number of elements as {@link #arguments(TransactionContext)}.
     */
    @NonNull
    Iterable<Optional<Integer>> backReferences(@NonNull TransactionContext transactionContext);
}
