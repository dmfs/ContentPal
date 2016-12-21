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
import org.dmfs.iterators.decorators.Flattened;

import java.util.Iterator;


/**
 * An {@link OperationsBatch} decorator which joins the {@link Operation}s of other {@link OperationsBatch}es.
 *
 * @author Marten Gajda
 */
public final class Joined implements OperationsBatch
{
    private final OperationsBatch[] mOperationsBatches;


    /**
     * Creates an {@link OperationsBatch} which contains the {@link Operation}s of the given {@link OperationsBatch}es.
     *
     * @param operationsBatches
     *         The {@link OperationsBatch}es to join.
     */
    public Joined(@NonNull OperationsBatch... operationsBatches)
    {
        mOperationsBatches = operationsBatches.clone();
    }


    @NonNull
    @Override
    public Iterator<Operation<?>> iterator()
    {
        return new Flattened<>(mOperationsBatches);
    }

}
