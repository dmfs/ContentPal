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

package org.dmfs.android.contentpal.predicates;

import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.RowSnapshot;


/**
 * {@link Predicate} for selecting rows which refer, through a foreign id column, to the given row from a different table.
 *
 * @author Gabor Keszthelyi
 */
public final class ReferringTo implements Predicate
{

    private final String mForeignIdColumnName;
    private final RowSnapshot<?> mRowSnapshot;

    private Predicate mCachedDelegate;


    public ReferringTo(String foreignIdColumnName, RowSnapshot<?> rowSnapshot)
    {
        mForeignIdColumnName = foreignIdColumnName;
        mRowSnapshot = rowSnapshot;
    }


    @NonNull
    @Override
    public CharSequence selection()
    {
        return delegate().selection();
    }


    @NonNull
    @Override
    public Iterable<String> arguments()
    {
        return delegate().arguments();
    }


    private Predicate delegate()
    {
        if (mCachedDelegate == null)
        {
            mCachedDelegate = new EqArg(mForeignIdColumnName, mRowSnapshot.values().charData(BaseColumns._ID).value("-1"));
        }
        return mCachedDelegate;
    }
}
