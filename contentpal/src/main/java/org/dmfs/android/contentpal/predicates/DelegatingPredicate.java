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

import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.Predicate;


/**
 * Abstract convenience base class for {@link Predicate}s which only need to compose a {@link Predicate} to delegate to in the constructor.
 *
 * @author Gabor Keszthelyi
 */
public abstract class DelegatingPredicate implements Predicate
{
    private final Predicate mDelegate;


    public DelegatingPredicate(Predicate predicate)
    {
        mDelegate = predicate;
    }


    @NonNull
    @Override
    public final CharSequence selection()
    {
        return mDelegate.selection();
    }


    @NonNull
    @Override
    public final Iterable<String> arguments()
    {
        return mDelegate.arguments();
    }
}
