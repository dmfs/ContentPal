/*
 * Copyright 2018 dmfs GmbH
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

package org.dmfs.android.contentpal.testing.android.uri;

import android.net.Uri;

import org.dmfs.jems.function.Function;
import org.dmfs.jems.iterable.decorators.Mapped;
import org.dmfs.jems.pair.Pair;
import org.dmfs.jems.pair.elementary.ValuePair;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import static org.hamcrest.CoreMatchers.is;


/**
 * Matcher for parts of a {@link Uri}.
 * <p>
 * TODO: move to Bolts project
 *
 * @author Marten Gajda
 */
public final class UriMatcher
{
    public static Matcher<Uri> hasParamSet(Matcher<Iterable<? extends Pair<String, String>>> paramNamesMatcher)
    {
        return new UriFunctionMatcher<>(
                uri -> new Mapped<>(name -> new ValuePair<>(name, uri.getQueryParameter(name)), uri.getQueryParameterNames()),
                paramNamesMatcher, "parameters");
    }


    public static Matcher<Uri> hasParam(String name, String value)
    {
        return hasParam(name, is(value));
    }


    public static Matcher<Uri> hasParam(String name, Matcher<String> valueMatcher)
    {
        return new UriFunctionMatcher<>(uri -> uri.getQueryParameter(name), valueMatcher, String.format("parameter \"%s\"", name));
    }


    public static Matcher<Uri> encodedPath(String path)
    {
        return new UriFunctionMatcher<>(Uri::getEncodedPath, is(path), "path");
    }


    public static Matcher<Uri> hierarchical()
    {
        return new UriFunctionMatcher<>(Uri::isHierarchical, is(true), "hierarchical");
    }


    public static Matcher<Uri> absolute()
    {
        return new UriFunctionMatcher<>(Uri::isAbsolute, is(true), "absolute");
    }


    // TODO: remove once the new FeatureMatcher is available in jems, see https://github.com/dmfs/jems/issues/169
    private final static class UriFunctionMatcher<T> extends FeatureMatcher<Uri, T>
    {
        private final Function<Uri, T> mFunction;


        public UriFunctionMatcher(Function<Uri, T> function, Matcher<T> matcher, String name)
        {
            super(matcher, name, name);
            mFunction = function;
        }


        @Override
        protected T featureValueOf(Uri actual)
        {
            return mFunction.value(actual);
        }
    }
}
