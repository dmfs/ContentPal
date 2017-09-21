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

import android.content.ContentValues;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Locale;

import static org.hamcrest.Matchers.is;


/**
 * A {@link Matcher} which matches expected values in {@link ContentValues}.
 *
 * @author Marten Gajda
 */
public final class WithValue<T> extends TypeSafeDiagnosingMatcher<ContentValues>
{
    private final String mExpectedKey;
    private final Matcher<T> mExpectedValueMatcher;


    public static <T> WithValue<T> withValue(String expectedKey, T expectedValue)
    {
        return new WithValue<>(expectedKey, is(expectedValue));
    }


    public static <T> WithValue<T> withValue(String expectedKey, Matcher<T> expectedValueMatcher)
    {
        return new WithValue<>(expectedKey, expectedValueMatcher);
    }


    public WithValue(String expectedKey, Matcher<T> expectedValueMatcher)
    {
        mExpectedKey = expectedKey;
        mExpectedValueMatcher = expectedValueMatcher;
    }


    @Override
    protected boolean matchesSafely(ContentValues values, Description mismatchDescription)
    {
        if (!mExpectedValueMatcher.matches(values.get(mExpectedKey)))
        {
            mismatchDescription.appendText(String.format(Locale.ENGLISH, "has value of key \"%s\" ", mExpectedKey));
            mExpectedValueMatcher.describeMismatch(values.get(mExpectedKey), mismatchDescription);
            return false;
        }
        return true;
    }


    @Override
    public void describeTo(Description description)
    {
        description.appendText(String.format(Locale.ENGLISH, "has value of key \"%s\" ", mExpectedKey));
        mExpectedValueMatcher.describeTo(description);
    }
}
