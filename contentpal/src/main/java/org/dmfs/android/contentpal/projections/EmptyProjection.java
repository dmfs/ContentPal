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
import org.dmfs.iterators.EmptyIterator;

import java.util.Iterator;


/**
 * A {@link Projection} which doesn't specify any specific columns.
 *
 * @author Marten Gajda
 */
public final class EmptyProjection<T> implements Projection<T>
{
    @NonNull
    @Override
    public String[] toArray()
    {
        return new String[0];
    }


    @NonNull
    @Override
    public Iterator<String> iterator()
    {
        return EmptyIterator.instance();
    }
}
