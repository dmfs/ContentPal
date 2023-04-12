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
import org.dmfs.jems2.Optional;
import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.optional.Absent;
import org.dmfs.jems2.optional.Present;
import org.dmfs.jems2.single.Backed;
import org.hamcrest.CoreMatchers;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import java.util.Arrays;

import androidx.annotation.NonNull;

import static org.dmfs.jems2.hamcrest.matchers.iterable.IterableMatcher.iteratesTo;
import static org.dmfs.jems2.hamcrest.matchers.optional.AbsentMatcher.absent;
import static org.dmfs.jems2.mockito.doubles.TestDoubles.dummy;
import static org.hamcrest.collection.IsEmptyIterable.emptyIterable;
import static org.hamcrest.object.HasToString.hasToString;


/**
 * Factory methods for {@link Predicate} {@link Matcher}s.
 *
 * @author Gabor Keszthelyi
 */
public final class PredicateMatcher
{
    @NonNull
    @SafeVarargs
    public static <T> Matcher<Predicate<? super T>> predicateWith(@NonNull Matcher<Predicate<? super T>>... matchers)
    {
        return CoreMatchers.allOf(matchers);
    }


    @NonNull
    public static <T> Matcher<Predicate<? super T>> selection(@NonNull CharSequence selection)
    {
        return new Selection<>(Absent.absent(), selection);
    }


    @NonNull
    public static <T> Matcher<Predicate<? super T>> selection(@NonNull TransactionContext tc, @NonNull CharSequence selection)
    {
        return new Selection<>(new Present<>(tc), selection);
    }


    @NonNull
    public static <T> Matcher<Predicate<? super T>> emptyArguments()
    {
        return new EmptyArgument<>(Absent.absent());
    }


    @NonNull
    public static <T> Matcher<Predicate<? super T>> emptyArguments(@NonNull TransactionContext tc)
    {
        return new EmptyArgument<>(new Present<>(tc));
    }


    @NonNull
    public static <T> Matcher<Predicate<? super T>> argumentValues(@NonNull String... values)
    {
        return new ArgumentValues<>(Absent.absent(), values);
    }


    @NonNull
    public static <T> Matcher<Predicate<? super T>> argumentValues(@NonNull TransactionContext tc, @NonNull String... values)
    {
        return new ArgumentValues<>(new Present<>(tc), values);
    }


    @NonNull
    @SafeVarargs
    public static <T> Matcher<Predicate<? super T>> backReferences(@NonNull Matcher<? super Optional<Integer>>... backReferences)
    {
        return new ArgumentBackReferences<>(Absent.absent(), backReferences);
    }


    @NonNull
    @SafeVarargs
    public static <T> Matcher<Predicate<? super T>> backReferences(TransactionContext tc, Matcher<? super Optional<Integer>>... backReferences)
    {
        return new ArgumentBackReferences<>(new Present<>(tc), backReferences);
    }


    @NonNull
    public static <T> Matcher<Predicate<? super T>> absentBackReferences(int noOfPredicateArguments)
    {
        Matcher[] matchers = new Matcher[noOfPredicateArguments];
        Arrays.fill(matchers, absent());
        return new ArgumentBackReferences<>(Absent.absent(), matchers);
    }


    @NonNull
    public static <T> Matcher<Predicate<? super T>> absentBackReferences(@NonNull TransactionContext tc, int noOfPredicateArguments)
    {
        Matcher[] matchers = new Matcher[noOfPredicateArguments];
        Arrays.fill(matchers, absent());
        return new ArgumentBackReferences<>(new Present<>(tc), matchers);
    }


    private PredicateMatcher()
    {

    }


    static final class Selection<T> extends FeatureMatcher<Predicate<? super T>, CharSequence>
    {
        private final Optional<TransactionContext> mTc;


        Selection(@NonNull Optional<TransactionContext> tc, @NonNull CharSequence selection)
        {
            super(hasToString(selection.toString()), "Predicate.selection(tc)", "selection");
            mTc = tc;
        }


        @Override
        protected CharSequence featureValueOf(@NonNull Predicate<? super T> actual)
        {
            return actual.selection(new Backed<>(mTc, dummy(TransactionContext.class)).value());
        }
    }


    private static final class ArgumentValues<T> extends FeatureMatcher<Predicate<? super T>, Iterable<String>>
    {

        private final Optional<TransactionContext> mTc;


        private ArgumentValues(Optional<TransactionContext> tc, String... argumentValues)
        {
            super(iteratesTo(argumentValues), "Predicate.arguments(tc).value()", "argument value");
            mTc = tc;
        }


        @Override
        protected Iterable<String> featureValueOf(Predicate<? super T> predicate)
        {
            return new Mapped<>(Predicate.Argument::value, predicate.arguments(new Backed<>(mTc, dummy(TransactionContext.class)).value()));
        }
    }


    private static final class EmptyArgument<T> extends FeatureMatcher<Predicate<? super T>, Iterable<Predicate.Argument>>
    {

        private final Optional<TransactionContext> mTc;


        private EmptyArgument(Optional<TransactionContext> tc)
        {
            super(emptyIterable(), "Predicate.arguments(tc)", "arguments");
            mTc = tc;
        }


        @Override
        protected Iterable<Predicate.Argument> featureValueOf(Predicate<? super T> predicate)
        {
            return predicate.arguments(new Backed<>(mTc, dummy(TransactionContext.class)).value());
        }
    }


    private static final class ArgumentBackReferences<T> extends FeatureMatcher<Predicate<? super T>, Iterable<Optional<Integer>>>
    {

        private final Optional<TransactionContext> mTc;


        @SafeVarargs
        private ArgumentBackReferences(Optional<TransactionContext> tc, Matcher<? super Optional<Integer>>... backReferences)
        {
            super(iteratesTo(backReferences), "Predicate.arguments(tc).backreference()s", "backreferences");
            mTc = tc;
        }


        @Override
        protected Iterable<Optional<Integer>> featureValueOf(Predicate<? super T> predicate)
        {
            return new Mapped<>(Predicate.Argument::backReference, predicate.arguments(new Backed<>(mTc, dummy(TransactionContext.class)).value()));
        }
    }

}
