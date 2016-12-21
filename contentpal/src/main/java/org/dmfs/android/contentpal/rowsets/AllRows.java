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

package org.dmfs.android.contentpal.rowsets;

import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.RowSet;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.View;
import org.dmfs.android.contentpal.predicates.AnyOf;
import org.dmfs.android.contentpal.tools.ClosableIterator;


/**
 * A {@link RowSet} to retrieve all rows of a given {@link View}.
 *
 * @author Marten Gajda
 */
public final class AllRows<T> implements RowSet<T>
{
    private final RowSet<T> mDelegate;


    public AllRows(@NonNull View<T> view)
    {
        mDelegate = new QueryRowSet<>(view, new AnyOf());
    }


    @NonNull
    @Override
    public ClosableIterator<RowSnapshot<T>> iterator()
    {
        return mDelegate.iterator();
    }
}
