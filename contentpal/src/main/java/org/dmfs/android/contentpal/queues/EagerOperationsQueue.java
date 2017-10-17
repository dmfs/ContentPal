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

package org.dmfs.android.contentpal.queues;

import android.content.ContentProviderClient;
import android.content.Context;
import android.content.OperationApplicationException;
import android.os.Build;
import android.os.RemoteException;
import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.OperationsBatch;
import org.dmfs.android.contentpal.OperationsQueue;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.transactions.BaseTransaction;
import org.dmfs.android.contentpal.transactions.contexts.EmptyTransactionContext;


/**
 * An {@link OperationsQueue} which commits every {@link OperationsBatch} instantly.
 * <p>
 * To use this in concurrent environments add a {@link ThreadSafe} decorator.
 *
 * @author Marten Gajda
 */
public final class EagerOperationsQueue implements OperationsQueue
{
    private final Context mContext;
    private final String mAuthority;
    private TransactionContext mTransactionContext = EmptyTransactionContext.INSTANCE;


    public EagerOperationsQueue(Context context, String authority)
    {
        mContext = context;
        mAuthority = authority;
    }


    public void enqueue(@NonNull OperationsBatch operationsBatch) throws RemoteException, OperationApplicationException
    {
        ContentProviderClient providerClient = mContext.getContentResolver().acquireContentProviderClient(mAuthority);
        if (providerClient == null)
        {
            throw new IllegalArgumentException(String.format("Unknown authority %s", mAuthority));
        }
        try
        {
            mTransactionContext = new BaseTransaction(mTransactionContext).with(operationsBatch).commit(providerClient);
        }
        finally
        {
            if (Build.VERSION.SDK_INT >= 24)
            {
                providerClient.close();
            }
            else
            {
                providerClient.release();
            }
        }
    }


    @Override
    public void flush()
    {
        // nothing to do
    }
}
