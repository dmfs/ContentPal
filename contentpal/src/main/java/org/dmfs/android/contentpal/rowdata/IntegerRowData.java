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

package org.dmfs.android.contentpal.rowdata;

import android.content.ContentProviderOperation;

import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.jems2.Single;

import androidx.annotation.NonNull;


/**
 * {@link RowData} for a key and an Integer value.
 */
public final class IntegerRowData<T> implements RowData<T>
{
    private final String mKey;
    private final Single<Integer> mValue;


    public IntegerRowData(@NonNull String key, int value)
    {
        this(key, () -> value);
    }


    public IntegerRowData(@NonNull String key, @NonNull Single<Integer> value)
    {
        mKey = key;
        mValue = value;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(@NonNull TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder)
    {
        return builder.withValue(mKey, mValue.value());
    }
}
