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
import org.dmfs.iterables.elementary.Seq;
import org.dmfs.jems.function.BiFunction;
import org.dmfs.jems.single.elementary.Reduced;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
        List<String> projection = new Reduced<>(
                new ArrayList<String>(32),
                new BiFunction<ArrayList<String>, Projection<T>, ArrayList<String>>()
                {
                    @Override
                    public ArrayList<String> value(ArrayList<String> strings, Projection<T> projection)
                    {
                        // add the strings of all projections
                        strings.addAll(Arrays.asList(projection.toArray()));
                        return strings;
                    }
                },
                mDelegates).value();
        return projection.toArray(new String[projection.size()]);
    }
}
