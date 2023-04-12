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

package org.dmfs.android.contentpal.operations.internal;

import android.content.ContentProviderOperation;
import android.net.Uri;

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.operations.BulkUpdate;
import org.dmfs.jems2.Optional;

import androidx.annotation.NonNull;

import static org.dmfs.jems2.optional.Absent.absent;


/**
 * An {@link Operation} to update all rows of a given {@link Uri}.
 * <p>
 * This is for internal purposes and should not be instantiated directly. Use {@link BulkUpdate} instead.
 *
 * @author Marten Gajda
 */
public final class RawUpdate<T> implements Operation<T>
{

    private final Uri mUri;


    public RawUpdate(@NonNull Uri uri)
    {
        mUri = uri;
    }


    @NonNull
    @Override
    public Optional<SoftRowReference<T>> reference()
    {
        return absent();
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder contentOperationBuilder(@NonNull TransactionContext transactionContext) throws UnsupportedOperationException
    {
        return ContentProviderOperation.newUpdate(mUri);
    }
}
