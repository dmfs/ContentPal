/*
 * Copyright 2019 dmfs GmbH
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
import org.dmfs.jems2.Procedure;
import org.dmfs.jems2.iterable.DelegatingIterable;
import org.dmfs.jems2.iterable.Mapped;

import androidx.annotation.NonNull;


/**
 * An {@link Operation}s batch which logs every operation it builds.
 *
 * @author Marten Gajda
 */
public final class Logging<T> extends DelegatingIterable<Operation<T>>
{

    public Logging(@NonNull Procedure<String> logProcedure, @NonNull Iterable<Operation<T>> delegate)
    {
        super(new Mapped<>(op -> new org.dmfs.android.contentpal.operations.Logging<>(logProcedure, op), delegate));
    }
}
