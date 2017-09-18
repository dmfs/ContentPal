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

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.lang.reflect.Field;
import java.util.Locale;


/**
 * A {@link ContentProviderOperation.Builder} {@link Matcher} which matches the "yield allowed" flag of an operation builder.
 *
 * @author Marten Gajda
 */
public final class WithYieldAllowed extends TypeSafeDiagnosingMatcher<ContentProviderOperation.Builder>
{

    private final boolean mYieldable;


    public static WithYieldAllowed withYieldAllowed()
    {
        return new WithYieldAllowed(true);
    }


    public static WithYieldAllowed withYieldNotAllowed()
    {
        return new WithYieldAllowed(false);
    }


    public WithYieldAllowed(boolean yieldable)
    {
        mYieldable = yieldable;
    }


    @Override
    protected boolean matchesSafely(ContentProviderOperation.Builder builder, Description mismatchDescription)
    {
        // create a builder an test the ContentValues
        try
        {
            Field valuesField = ContentProviderOperation.Builder.class.getDeclaredField("mYieldAllowed");
            valuesField.setAccessible(true);

            boolean yieldable = valuesField.getBoolean(builder);

            if (mYieldable != yieldable)
            {
                mismatchDescription.appendText(String.format(Locale.ENGLISH, "yieldable is %s", Boolean.toString(yieldable)));
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
        description.appendText(String.format(Locale.ENGLISH, "yieldable is %s", Boolean.toString(mYieldable)));
    }
}
