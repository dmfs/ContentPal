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

package org.dmfs.android.contentpal.batches;

import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.OperationsBatch;
import org.dmfs.iterators.SingletonIterator;

import java.util.Iterator;


/**
 * An {@link OperationsBatch} that contains exactly one {@link Operation}.
 *
 * @author Marten Gajda
 */
public final class SingletonBatch implements OperationsBatch
{
    private final Operation<?> mOperation;


    /**
     * Creates an {@link OperationsBatch} which contains the given {@link Operation} only.
     *
     * @param operation
     *         The sole {@link Operation} in this {@link OperationsBatch}.
     */
    public SingletonBatch(@NonNull Operation<?> operation)
    {
        mOperation = operation;
    }


    @NonNull
    @Override
    public Iterator<Operation<?>> iterator()
    {
        return new SingletonIterator<Operation<?>>(mOperation);
    }
}
