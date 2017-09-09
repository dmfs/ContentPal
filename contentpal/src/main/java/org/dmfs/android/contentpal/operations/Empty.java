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

package org.dmfs.android.contentpal.operations;

import android.content.ContentProviderOperation;
import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.optional.Optional;


/**
 * An {@link Operation} which fails if the operation affects (updates, deletes or asserts) any rows at all.
 *
 * @author Marten Gajda
 */
public final class Empty<T> implements Operation<T>
{
    private final Operation<T> mDelegate;


    /**
     * Creates an operation which asserts no changes to the database.
     *
     * @param operation
     *         The decorated {@link Operation}.
     */
    public Empty(@NonNull Operation<T> operation)
    {
        mDelegate = new Counted<>(0, operation);
    }


    @NonNull
    @Override
    public Optional<SoftRowReference<T>> reference()
    {
        return mDelegate.reference();
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder contentOperationBuilder(@NonNull TransactionContext transactionContext)
    {
        return mDelegate.contentOperationBuilder(transactionContext);
    }
}
