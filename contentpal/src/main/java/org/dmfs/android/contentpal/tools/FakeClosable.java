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

import org.dmfs.android.contentpal.ClosableIterator;

import java.io.IOException;
import java.util.Iterator;


/**
 * An adapter which makes any {@link Iterator} a {@link ClosableIterator}.
 *
 * @author Marten Gajda
 */
public final class FakeClosable<T> implements Iterator<T>, ClosableIterator<T>
{
    private final Iterator<T> mDelegate;


    public FakeClosable(Iterator<T> delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public void close() throws IOException
    {
        // nothing to do
    }


    @Override
    public boolean hasNext()
    {
        return mDelegate.hasNext();
    }


    @Override
    public T next()
    {
        return mDelegate.next();
    }
}
