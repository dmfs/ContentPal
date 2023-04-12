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

package org.dmfs.android.contentpal.testing.contentvalues;

import android.util.ArrayMap;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import androidx.annotation.NonNull;


/**
 * A {@link Matcher} which matches if a value in a {@link ArrayMap} is {@code null}.
 */
public final class NullValue extends TypeSafeDiagnosingMatcher<ArrayMap<String, Object>>
{
    private final String mExpectedKey;


    @NonNull
    public static NullValue withNullValue(@NonNull String expectedKey)
    {
        return new NullValue(expectedKey);
    }


    public NullValue(String expectedKey)
    {
        mExpectedKey = expectedKey;
    }


    @Override
    protected boolean matchesSafely(@NonNull ArrayMap<String, Object> values, @NonNull Description mismatchDescription)
    {
        if (values.get(mExpectedKey) != null)
        {
            mismatchDescription.appendText(String.format("value of key \"%s\" was \"%s\"", mExpectedKey, values.get(mExpectedKey)));
            return false;
        }
        return true;
    }


    @Override
    public void describeTo(@NonNull Description description)
    {
        description.appendText(String.format("value of key \"%s\" is null", mExpectedKey));
    }
}
