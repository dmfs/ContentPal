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

import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.support.annotation.NonNull;


/**
 * The interface of a queue that takes {@link OperationsBatch}es for execution.
 *
 * @author Marten Gajda
 */
public interface OperationsQueue
{
    /**
     * Adds another {@link OperationsBatch} for execution. The {@link OperationsBatch} is added to a {@link Transaction} unless the transaction would exceed the
     * size limit, in which case the transaction is committed before adding the new {@link OperationsBatch} to a new transaction.
     *
     * @param operationsBatch
     *         The {@link OperationsBatch} to enqueue for execution.
     *
     * @throws RemoteException
     * @throws OperationApplicationException
     */
    void enqueue(@NonNull OperationsBatch operationsBatch) throws RemoteException, OperationApplicationException;

    /**
     * Commit all {@link OperationsBatch}es that have not been committed yet.
     *
     * @throws RemoteException
     * @throws OperationApplicationException
     */
    void flush() throws RemoteException, OperationApplicationException;
}
