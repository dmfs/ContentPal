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

import org.dmfs.android.contentpal.testing.tools.Field;
import org.dmfs.optional.Absent;
import org.dmfs.optional.NullSafe;
import org.dmfs.optional.Optional;
import org.dmfs.optional.Present;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Locale;


/**
 * A {@link ContentProviderOperation.Builder} {@link Matcher} which matches the expected count of affected rows of the operation.
 *
 * @author Marten Gajda
 */
public final class WithExpectedCount extends TypeSafeDiagnosingMatcher<ContentProviderOperation.Builder>
{

    private final Optional<Integer> mExpectedCount;


    public static WithExpectedCount withExpectedCount(int expectedCount)
    {
        return new WithExpectedCount(expectedCount);
    }


    public static WithExpectedCount withoutExpectedCount()
    {
        return new WithExpectedCount();
    }


    public WithExpectedCount()
    {
        this(Absent.<Integer>absent());
    }


    public WithExpectedCount(int expectedCount)
    {
        this(new Present<Integer>(expectedCount));
    }


    public WithExpectedCount(Optional<Integer> expectedCount)
    {
        mExpectedCount = expectedCount;
    }


    @Override
    protected boolean matchesSafely(ContentProviderOperation.Builder builder, Description mismatchDescription)
    {
        Optional<Integer> expectedCount = new NullSafe<>(new Field<Integer>(builder, "mExpectedCount").value());

        if (!mExpectedCount.isPresent() && expectedCount.isPresent()
                || mExpectedCount.isPresent() && expectedCount.isPresent() && !mExpectedCount.value().equals(expectedCount.value()))
        {
            mismatchDescription.appendText(String.format(Locale.ENGLISH, "expected %d results", expectedCount.value()));
            return false;
        }
        else if (mExpectedCount.isPresent() && !expectedCount.isPresent())
        {
            mismatchDescription.appendText("expected no specific number of results");
            return false;
        }
        return true;
    }


    @Override
    public void describeTo(Description description)
    {
        description.appendText(mExpectedCount.isPresent()
                ? String.format(Locale.ENGLISH, "expects %d results", mExpectedCount.value())
                : "expects no specific number of results");
    }
}
