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

import org.dmfs.optional.Absent;
import org.dmfs.optional.NullSafe;
import org.dmfs.optional.Optional;
import org.dmfs.optional.Present;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.lang.reflect.Field;
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
        // create a builder an test the ContentValues
        try
        {
            Field valuesField = ContentProviderOperation.Builder.class.getDeclaredField("mExpectedCount");
            valuesField.setAccessible(true);

            Optional<Integer> expectedCount = new NullSafe<>((Integer) valuesField.get(builder));

            if (mExpectedCount.isPresent() != expectedCount.isPresent())
            {
                if (expectedCount.isPresent())
                {
                    mismatchDescription.appendText(String.format(Locale.ENGLISH, "expects %d results", expectedCount.value()));
                }
                else
                {
                    mismatchDescription.appendText("expects no specific number of results");
                }
                return false;
            }
            if (mExpectedCount.isPresent() && !mExpectedCount.value().equals(expectedCount.value()))
            {
                mismatchDescription.appendText(String.format(Locale.ENGLISH, "expects %d results", expectedCount.value()));
                return false;
            }
            return true;
        }
        catch (NoSuchFieldException e)
        {
            throw new RuntimeException("Could not read builder values", e);
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException("Could not read builder values", e);
        }
    }


    @Override
    public void describeTo(Description description)
    {
        if (mExpectedCount.isPresent())
        {
            description.appendText(String.format(Locale.ENGLISH, "expects %d results", mExpectedCount.value()));
        }
        else
        {
            description.appendText("expects no specific number of results");
        }
    }
}
