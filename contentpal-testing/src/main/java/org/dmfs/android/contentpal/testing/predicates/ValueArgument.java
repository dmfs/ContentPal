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

package org.dmfs.android.contentpal.testing.predicates;

import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.optional.Absent;
import org.dmfs.optional.Optional;


/**
 * A simple constant argument with no back reference.
 *
 * @author Marten Gajda
 */
public final class ValueArgument implements Predicate.Argument
{
    private final Object mValue;


    public ValueArgument(@NonNull Object value)
    {

        mValue = value;
    }


    @NonNull
    @Override
    public String value()
    {
        return mValue.toString();
    }


    @NonNull
    @Override
    public Optional<Integer> backReference()
    {
        return Absent.absent();
    }
}
