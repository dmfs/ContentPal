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

package org.dmfs.android.contentpal.testing.projection;

import org.dmfs.android.contentpal.Projection;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;


/**
 * @author Marten Gajda
 */
public final class ProjectionMatcher extends TypeSafeDiagnosingMatcher<Projection<?>>
{
    private final Iterable<String> mExpectedColumns;


    public static Matcher<Projection<?>> projects(Iterable<String> expectedColumns)
    {
        return new ProjectionMatcher(expectedColumns);
    }


    public ProjectionMatcher(Iterable<String> expectedColumns)
    {
        mExpectedColumns = expectedColumns;
    }


    @Override
    protected boolean matchesSafely(Projection<?> item, Description mismatchDescription)
    {
        Set<String> columns = new HashSet<>(Arrays.asList(item.toArray()));
        Set<String> expectedCols = new HashSet<>(columns.size());
        for (String col : mExpectedColumns)
        {
            if (!columns.contains(col))
            {
                mismatchDescription.appendText("Array didn't contain column '").appendText(col).appendText("'");
                return false;
            }
            expectedCols.add(col);
        }
        if (expectedCols.size() != item.toArray().length)
        {
            mismatchDescription.appendText(String.format(Locale.ENGLISH, "Column count didn't equal %d", expectedCols.size()));
            return false;
        }
        int count = 0;
        for (String col : item)
        {
            if (!expectedCols.contains(col))
            {
                mismatchDescription.appendText("Iteration didn't contain column '").appendText(col).appendText("'");
                return false;
            }
            count += 1;
        }
        if (expectedCols.size() != count)
        {
            mismatchDescription.appendText(String.format(Locale.ENGLISH, "Iterated column count didn't equal %d", expectedCols.size()));
            return false;
        }

        return true;
    }


    @Override
    public void describeTo(Description description)
    {
        description.appendText("A projection of the columns [");
        boolean first = true;
        for (String s : mExpectedColumns)
        {
            if (first)
            {
                first = false;
            }
            else
            {
                description.appendText(", ");
            }
            description.appendText("\"");
            description.appendText(s);
            description.appendText("\"");
        }
        description.appendText("]");
    }
}
