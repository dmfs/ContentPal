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

package org.dmfs.android.contentpal.tools;

import android.database.Cursor;
import android.os.RemoteException;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.View;
import org.dmfs.android.contentpal.predicates.AnyOf;
import org.dmfs.android.contentpal.tools.uriparams.EmptyUriParams;
import org.dmfs.jems.single.Single;
import org.dmfs.optional.Absent;


/**
 * The number of rows in a {@link View} that matches the given {@link Predicate}.
 *
 * @author Gabor Keszthelyi
 */
public final class Count<T> implements Single<Integer>
{
    private final View<T> mView;
    private final Predicate mPredicate;


    public Count(View<T> view, Predicate predicate)
    {
        mView = view;
        mPredicate = predicate;
    }


    public Count(View<T> view)
    {
        this(view, new AnyOf());
    }


    @Override
    public Integer value()
    {
        try
        {
            Cursor cursor = mView.rows(EmptyUriParams.INSTANCE, mPredicate, Absent.<String>absent());
            int count = cursor.getCount();
            cursor.close();
            return count;
        }
        catch (RemoteException e)
        {
            throw new RuntimeException("Query failed", e);
        }
    }
}
