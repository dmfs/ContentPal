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
import org.dmfs.iterables.ArrayIterable;
import org.dmfs.iterables.decorators.DelegatingIterable;
import org.dmfs.iterables.decorators.Mapped;
import org.dmfs.iterators.Function;
import org.dmfs.optional.Absent;
import org.dmfs.optional.Optional;
import org.dmfs.optional.Present;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Arrays;

import static org.dmfs.jems.hamcrest.matchers.AbsentMatcher.isAbsent;
import static org.dmfs.jems.hamcrest.matchers.IterableMatcher.iteratesTo;
import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.hamcrest.collection.IsEmptyIterable.emptyIterable;


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
    public static Matcher<Predicate> selection(TransactionContext tc, CharSequence selection)
    {
        return new SelectionMatcher(selection, tc);
    }


    @Factory
    public static Matcher<Predicate> selection(CharSequence selection)
    {
        return new SelectionMatcher(selection);
    }


    @Factory
    public static Matcher<Predicate> emptyArguments()
    {
        return new EmptyArgumentMatcher();
    }


    @Factory
    public static Matcher<Predicate> argumentValues(TransactionContext tc, String... values)
    {
        return new ArgumentValuesMatcher(new ArrayIterable<>(values), tc);
    }


    @Factory
    public static Matcher<Predicate> argumentValues(String... values)
    {
        return new ArgumentValuesMatcher(new ArrayIterable<>(values));
    }


    @Factory
    public static Matcher<Predicate> argumentValues(TransactionContext tc, Iterable<String> values)
    {
        return new ArgumentValuesMatcher(values, tc);
    }


    @Factory
    public static Matcher<Predicate> argumentValues(Iterable<String> values)
    {
        return new ArgumentValuesMatcher(values);
    }


    @Factory
    @SafeVarargs
    public static Matcher<Predicate> backReferences(Matcher<Optional<Integer>>... backReferences)
    {
        return new ArgumentBackReferencesMatcher(new ArrayIterable<>(backReferences));
    }


    @Factory
    @SafeVarargs
    public static Matcher<Predicate> backReferences(TransactionContext tc, Matcher<Optional<Integer>>... backReferences)
    {
        return new ArgumentBackReferencesMatcher(new ArrayIterable<>(backReferences), tc);
    }


    @Factory
    public static Matcher<Predicate> backReferences(TransactionContext tc, Iterable<Matcher<Optional<Integer>>> backReferences)
    {
        return new ArgumentBackReferencesMatcher(backReferences, tc);
    }


    @Factory
    public static Matcher<Predicate> backReferences(Iterable<Matcher<Optional<Integer>>> backReferences)
    {
        return new ArgumentBackReferencesMatcher(backReferences);
    }


    @Factory
    public static Matcher<Predicate> absentBackReferences(int noOfPredicateArguments)
    {
        Matcher[] matchers = new Matcher[noOfPredicateArguments];
        Arrays.fill(matchers, isAbsent());
        return new ArgumentBackReferencesMatcher(new ArrayIterable<Matcher<Optional<Integer>>>(matchers));
    }


    private PredicateMatcher()
    {

    }


    /**
     * {@link Matcher} that matches a {@link Predicate}s {@link Predicate.Argument#arguments(TransactionContext)} values.
     *
     * @author Gabor Keszthelyi
     */
    private static final class ArgumentValuesMatcher extends TypeSafeDiagnosingMatcher<Predicate>
    {
        private final Iterable<String> mExpectedArgumentValues;

        // Note: The mock cannot be created in ctor time, Mockito throws in that case, that's why Optional is used
        private final Optional<TransactionContext> mOptTransactionContext;


        private ArgumentValuesMatcher(Iterable<String> expectedArgumentValues, Optional<TransactionContext> optTransactionContext)
        {
            mExpectedArgumentValues = expectedArgumentValues;
            mOptTransactionContext = optTransactionContext;
        }


        private ArgumentValuesMatcher(Iterable<String> expectedArgumentValues, TransactionContext transactionContext)
        {
            this(expectedArgumentValues, new Present<>(transactionContext));
        }


        private ArgumentValuesMatcher(Iterable<String> expectedArgumentValues)
        {
            this(expectedArgumentValues, Absent.<TransactionContext>absent());
        }


        @Override
        protected boolean matchesSafely(Predicate predicate, Description mismatchDescription)
        {
            TransactionContext tContext = mOptTransactionContext.value(dummy(TransactionContext.class));

            Iterable<String> actualValues = new Values(predicate.arguments(tContext));

            Matcher<Iterable<? extends String>> expectedValuesMatcher = iteratesTo(
                    // TODO Use Function from jems:test-utils when available
                    new Mapped<>(mExpectedArgumentValues, new Function<String, Matcher<String>>()
                    {
                        @Override
                        public Matcher<String> apply(String argument)
                        {
                            return CoreMatchers.equalTo(argument);
                        }
                    }));

            if (!expectedValuesMatcher.matches(actualValues))
            {
                mismatchDescription.appendText("Predicate Argument values mismatch: ");
                expectedValuesMatcher.describeMismatch(actualValues, mismatchDescription);
                return false;
            }
            return true;
        }


        @Override
        public void describeTo(Description description)
        {
            description.appendText("Predicate has Argument values: ");
            description.appendValueList("[", ", ", "]", mExpectedArgumentValues);
        }

    }


    /**
     * {@link Matcher} that checks that a {@link Predicate}s {@link Predicate#arguments(TransactionContext)} value is empty.
     *
     * @author Gabor Keszthelyi
     */
    private static final class EmptyArgumentMatcher extends TypeSafeDiagnosingMatcher<Predicate>
    {
        // Note: The mock cannot be created in ctor time, Mockito throws in that case, that's why Optional is used
        private final Optional<TransactionContext> mOptTransactionContext;


        private EmptyArgumentMatcher(Optional<TransactionContext> optTransactionContext)
        {
            mOptTransactionContext = optTransactionContext;
        }


        private EmptyArgumentMatcher(TransactionContext transactionContext)
        {
            this(new Present<>(transactionContext));
        }


        private EmptyArgumentMatcher()
        {
            this(Absent.<TransactionContext>absent());
        }


        @Override
        protected boolean matchesSafely(Predicate predicate, Description mismatchDescription)
        {
            TransactionContext tContext = mOptTransactionContext.value(dummy(TransactionContext.class));
            Iterable<Predicate.Argument> arguments = predicate.arguments(tContext);

            if (!emptyIterable().matches(arguments))
            {
                mismatchDescription.appendText("Predicate Arguments was not empty");
                return false;
            }
            return true;
        }


        @Override
        public void describeTo(Description description)
        {
            description.appendText("Predicate with empty Arguments");
        }

    }


    /**
     * {@link Matcher} that matches a {@link Predicate}s {@link Predicate.Argument#backReference()} values.
     *
     * @author Gabor Keszthelyi
     */
    private static final class ArgumentBackReferencesMatcher extends TypeSafeDiagnosingMatcher<Predicate>
    {
        private final Iterable<Matcher<Optional<Integer>>> mExpectedBackReferences;

        // Note: The mock cannot be created in ctor time, Mockito throws in that case, that's why Optional is used
        private final Optional<TransactionContext> mOptTransactionContext;


        private ArgumentBackReferencesMatcher(Iterable<Matcher<Optional<Integer>>> expectedBackReferences, Optional<TransactionContext> optTransactionContext)
        {
            mExpectedBackReferences = expectedBackReferences;
            mOptTransactionContext = optTransactionContext;
        }


        private ArgumentBackReferencesMatcher(Iterable<Matcher<Optional<Integer>>> expectedBackReferences, TransactionContext transactionContext)
        {
            this(expectedBackReferences, new Present<>(transactionContext));
        }


        private ArgumentBackReferencesMatcher(Iterable<Matcher<Optional<Integer>>> expectedBackReferences)
        {
            this(expectedBackReferences, Absent.<TransactionContext>absent());
        }


        @Override
        protected boolean matchesSafely(Predicate predicate, Description mismatchDescription)
        {
            TransactionContext tContext = mOptTransactionContext.value(dummy(TransactionContext.class));

            Iterable<Optional<Integer>> actualBackReferences = new BackReferences(predicate.arguments(tContext));

            Matcher<Iterable<? extends Optional<Integer>>> expectedMatcher = iteratesTo(mExpectedBackReferences);

            if (!expectedMatcher.matches(actualBackReferences))
            {
                mismatchDescription.appendText("Predicate Argument backreferences mismatch: ");
                expectedMatcher.describeMismatch(actualBackReferences, mismatchDescription);
                return false;
            }
            return true;
        }


        @Override
        public void describeTo(Description description)
        {
            description.appendText("Predicate has Argument backreferences: ");
            description.appendValueList("[", ", ", "]", mExpectedBackReferences);
        }

    }


    /**
     * {@link Matcher} that matches a {@link Predicate}s {@link Predicate#selection(TransactionContext)} value.
     *
     * @author Gabor Keszthelyi
     */
    static final class SelectionMatcher extends TypeSafeDiagnosingMatcher<Predicate>
    {
        private final CharSequence mExpectedSelection;

        // Note: The mock cannot be created in ctor time, Mockito throws in that case, that's why Optional is used
        private final Optional<TransactionContext> mOptTransactionContext;


        private SelectionMatcher(CharSequence expectedSelection, Optional<TransactionContext> optTransactionContext)
        {
            mExpectedSelection = expectedSelection;
            mOptTransactionContext = optTransactionContext;
        }


        SelectionMatcher(CharSequence expectedSelection, TransactionContext transactionContext)
        {
            this(expectedSelection, new Present<>(transactionContext));
        }


        SelectionMatcher(CharSequence expectedSelection)
        {
            this(expectedSelection, Absent.<TransactionContext>absent());
        }


        @Override
        protected boolean matchesSafely(Predicate item, Description mismatchDescription)
        {
            TransactionContext tContext = mOptTransactionContext.value(dummy(TransactionContext.class));
            String actualSelection = item.selection(tContext).toString();
            boolean matches = actualSelection.equals(mExpectedSelection.toString());
            if (!matches)
            {
                mismatchDescription.appendText(String.format("had selection '%s'", actualSelection));
            }
            return matches;
        }


        @Override
        public void describeTo(Description description)
        {
            description.appendText(String.format("has selection '%s'", mExpectedSelection));
        }
    }


    /**
     * @author Marten Gajda
     */
    static final class Values extends DelegatingIterable<String>
    {
        Values(Iterable<Predicate.Argument> arguments)
        {
            super(new Mapped<>(arguments, new Function<Predicate.Argument, String>()
            {
                @Override
                public String apply(Predicate.Argument argument)
                {
                    return argument.value();
                }
            }));
        }
    }


    /**
     * @author Marten Gajda
     */
    private static final class BackReferences extends DelegatingIterable<Optional<Integer>>
    {
        BackReferences(Iterable<Predicate.Argument> arguments)
        {
            super(new Mapped<>(arguments, new Function<Predicate.Argument, Optional<Integer>>()
            {
                @Override
                public Optional<Integer> apply(Predicate.Argument argument)
                {
                    return argument.backReference();
                }
            }));
        }
    }
}
