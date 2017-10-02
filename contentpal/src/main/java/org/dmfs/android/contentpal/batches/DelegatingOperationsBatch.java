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

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.OperationsBatch;

import java.util.Iterator;


/**
 * Base class for {@link OperationsBatch}s that delegate to an {@link Iterable} of {@link Operation}s.
 *
 * @author Gabor Keszthelyi
 */
public abstract class DelegatingOperationsBatch implements OperationsBatch
{
    private final Iterable<Operation<?>> mIterableDelegate;


    protected DelegatingOperationsBatch(Iterable<Operation<?>> iterableDelegate)
    {
        mIterableDelegate = iterableDelegate;
    }


    @Override
    public final Iterator<Operation<?>> iterator()
    {
        return mIterableDelegate.iterator();
    }
}
