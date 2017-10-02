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
import org.dmfs.iterables.ArrayIterable;
import org.dmfs.optional.Optional;
import org.dmfs.optional.iterable.PresentValues;


/**
 * An {@link OperationsBatch} that contains multiple {@link Operation}s.
 *
 * @author Marten Gajda
 */
public final class MultiBatch extends DelegatingOperationsBatch
{

    @SafeVarargs
    public MultiBatch(@NonNull Optional<Operation<?>>... operations)
    {
        this(new PresentValues<>(new ArrayIterable<>(operations)));
    }


    /**
     * Creates an {@link OperationsBatch} which contains the given {@link Operation}s.
     *
     * @param operations
     *         The {@link Operation}s in this {@link OperationsBatch}.
     */
    public MultiBatch(@NonNull Operation<?>... operations)
    {
        this(new ArrayIterable<>(operations));
    }


    public MultiBatch(@NonNull Iterable<Operation<?>> operations)
    {
        super(operations);
    }

}
