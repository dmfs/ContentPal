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

package org.dmfs.android.contentpal.testing.contentoperationbuilder;

import android.content.ContentProviderOperation;
import android.content.ContentValues;

import org.dmfs.android.contentpal.testing.tools.Field;
import org.dmfs.optional.NullSafe;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static org.dmfs.android.contentpal.testing.contentvalues.Size.withValueCount;
import static org.hamcrest.Matchers.allOf;


/**
 * A {@link Matcher} which matches a {@link ContentProviderOperation.Builder}.
 *
 * @author Marten Gajda
 */
public final class WithValues extends TypeSafeDiagnosingMatcher<ContentProviderOperation.Builder>
{
    private final Matcher<ContentValues> mValueMatcher;


    @SafeVarargs
    public static WithValues withValuesOnly(Matcher<ContentValues>... valueMatchers)
    {
        return new WithValues(allOf(allOf(valueMatchers), withValueCount(valueMatchers.length)));
    }


    public static WithValues withoutValues()
    {
        return new WithValues(withValueCount(0));
    }


    public WithValues(Matcher<ContentValues> valueMatchers)
    {
        mValueMatcher = valueMatchers;
    }


    @Override
    protected boolean matchesSafely(ContentProviderOperation.Builder builder, Description mismatchDescription)
    {
        ContentValues values = new NullSafe<>(new Field<ContentValues>(builder, "mValues").value()).value(new ContentValues());

        if (!mValueMatcher.matches(values))
        {
            mValueMatcher.describeMismatch(values, mismatchDescription);
            return false;
        }
        return true;
    }


    @Override
    public void describeTo(Description description)
    {
        mValueMatcher.describeTo(description);
    }
}
