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

package org.dmfs.android.contentpal.projections;

import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.Projection;
import org.dmfs.iterables.decorators.Mapped;
import org.dmfs.iterables.elementary.Seq;
import org.dmfs.iterators.Function;
import org.dmfs.iterators.decorators.Filtered;
import org.dmfs.iterators.decorators.Serialized;
import org.dmfs.iterators.filters.Distinct;

import java.util.Iterator;
import java.util.TreeSet;


/**
 * @author Marten Gajda
 */
public final class Composite<T> implements Projection<T>
{
    private final Iterable<Projection<T>> mDelegates;


    public Composite(Projection<T>... delegates)
    {
        this(new Seq<>(delegates));
    }


    public Composite(Iterable<Projection<T>> delegates)
    {
        mDelegates = delegates;
    }


    @NonNull
    @Override
    public String[] toArray()
    {
        TreeSet<String> set = new TreeSet<>();
        for (String s : this)
        {
            set.add(s);
        }
        return set.toArray(new String[set.size()]);
    }


    @NonNull
    @Override
    public Iterator<String> iterator()
    {
        return new Filtered<>(new Serialized<>(new Mapped<>(mDelegates, new Function<Projection<T>, Iterator<String>>()
        {
            @Override
            public Iterator<String> apply(Projection<T> argument)
            {
                return argument.iterator();
            }
        }).iterator()), new Distinct<String>());
    }
}
