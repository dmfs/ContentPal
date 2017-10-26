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

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.iterables.decorators.Mapped;
import org.dmfs.iterators.Function;
import org.dmfs.optional.Absent;
import org.dmfs.optional.Optional;
import org.dmfs.optional.Present;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import java.util.Arrays;

import static org.dmfs.jems.hamcrest.matchers.AbsentMatcher.isAbsent;
import static org.dmfs.jems.hamcrest.matchers.IterableMatcher.iteratesTo;
import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.hamcrest.collection.IsEmptyIterable.emptyIterable;
import static org.hamcrest.object.HasToString.hasToString;


/**
 * Factory methods for {@link Predicate} {@link Matcher}s.
 *
 * @author Gabor Keszthelyi
 */
public final class PredicateMatcher
{

    @SafeVarargs
    @Factory
    public static Matcher<Predicate> predicateWith(Matcher<Predicate>... matchers)
    {
        return CoreMatchers.allOf(matchers);
    }


    @Factory
    public static Matcher<Predicate> selection(CharSequence selection)
    {
        return new Selection(Absent.<TransactionContext>absent(), selection);
    }


    @Factory
    public static Matcher<Predicate> selection(TransactionContext tc, CharSequence selection)
    {
        return new Selection(new Present<>(tc), selection);
    }


    @Factory
    public static Matcher<Predicate> emptyArguments()
    {
        return new EmptyArgument(Absent.<TransactionContext>absent());
    }


    @Factory
    public static Matcher<Predicate> emptyArguments(TransactionContext tc)
    {
        return new EmptyArgument(new Present<>(tc));
    }


    @Factory
    public static Matcher<Predicate> argumentValues(String... values)
    {
        return new ArgumentValues(Absent.<TransactionContext>absent(), values);
    }


    @Factory
    public static Matcher<Predicate> argumentValues(TransactionContext tc, String... values)
    {
        return new ArgumentValues(new Present<>(tc), values);
    }


    @Factory
    @SafeVarargs
    public static Matcher<Predicate> backReferences(Matcher<Optional<Integer>>... backReferences)
    {
        return new ArgumentBackReferences(Absent.<TransactionContext>absent(), backReferences);
    }


    @Factory
    @SafeVarargs
    public static Matcher<Predicate> backReferences(TransactionContext tc, Matcher<Optional<Integer>>... backReferences)
    {
        return new ArgumentBackReferences(new Present<>(tc), backReferences);
    }


    @Factory
    public static Matcher<Predicate> absentBackReferences(int noOfPredicateArguments)
    {
        Matcher[] matchers = new Matcher[noOfPredicateArguments];
        Arrays.fill(matchers, isAbsent());
        return new ArgumentBackReferences(Absent.<TransactionContext>absent(), matchers);
    }


    @Factory
    public static Matcher<Predicate> absentBackReferences(TransactionContext tc, int noOfPredicateArguments)
    {
        Matcher[] matchers = new Matcher[noOfPredicateArguments];
        Arrays.fill(matchers, isAbsent());
        return new ArgumentBackReferences(new Present<>(tc), matchers);
    }


    private PredicateMatcher()
    {

    }


    static final class Selection extends FeatureMatcher<Predicate, CharSequence>
    {
        private final Optional<TransactionContext> mTc;


        Selection(Optional<TransactionContext> tc, CharSequence selection)
        {
            super(hasToString(selection.toString()), "Predicate.selection(tc)", "selection");
            mTc = tc;
        }


        @Override
        protected CharSequence featureValueOf(Predicate actual)
        {
            return actual.selection(mTc.value(dummy(TransactionContext.class)));
        }
    }


    private static final class ArgumentValues extends FeatureMatcher<Predicate, Iterable<String>>
    {

        private final Optional<TransactionContext> mTc;


        private ArgumentValues(Optional<TransactionContext> tc, String... argumentValues)
        {
            super(iteratesTo(argumentValues), "Predicate.arguments(tc).value()", "argument value");
            mTc = tc;
        }


        @Override
        protected Iterable<String> featureValueOf(Predicate predicate)
        {
            return new Mapped<>(predicate.arguments(mTc.value(dummy(TransactionContext.class))), new Function<Predicate.Argument, String>()
            {
                @Override
                public String apply(Predicate.Argument argument)
                {
                    return argument.value();
                }
            });
        }
    }


    private static final class EmptyArgument extends FeatureMatcher<Predicate, Iterable<Predicate.Argument>>
    {

        private final Optional<TransactionContext> mTc;


        private EmptyArgument(Optional<TransactionContext> tc)
        {
            super(emptyIterable(), "Predicate.arguments(tc)", "arguments");
            mTc = tc;
        }


        @Override
        protected Iterable<Predicate.Argument> featureValueOf(Predicate predicate)
        {
            return predicate.arguments(mTc.value(dummy(TransactionContext.class)));
        }
    }


    private static final class ArgumentBackReferences extends FeatureMatcher<Predicate, Iterable<Optional<Integer>>>
    {

        private final Optional<TransactionContext> mTc;


        @SafeVarargs
        private ArgumentBackReferences(Optional<TransactionContext> tc, Matcher<Optional<Integer>>... backReferences)
        {
            super(iteratesTo(backReferences), "Predicate.arguments(tc).backreference()s", "backreferences");
            mTc = tc;
        }


        @Override
        protected Iterable<Optional<Integer>> featureValueOf(Predicate predicate)
        {
            return new Mapped<>(predicate.arguments(mTc.value(dummy(TransactionContext.class))), new Function<Predicate.Argument, Optional<Integer>>()
            {
                @Override
                public Optional<Integer> apply(Predicate.Argument argument)
                {
                    return argument.backReference();
                }
            });
        }
    }

}
