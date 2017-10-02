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
import org.dmfs.android.contentpal.testing.answers.FailAnswer;
import org.dmfs.optional.Absent;
import org.dmfs.optional.Optional;
import org.dmfs.optional.Present;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.mockito.Mockito;
import org.mockito.hamcrest.MockitoHamcrest;


/**
 * {@link Matcher} that matches a {@link Predicate}s {@link Predicate#selection(TransactionContext)} value.
 *
 * @author Gabor Keszthelyi
 */
public final class PredicateSelectionMatcher extends TypeSafeDiagnosingMatcher<Predicate>
{
    private final CharSequence mExpectedSelection;

    // Note: The mock cannot be created in ctor time, Mockito throws in that case, that's why Optional is used
    private final Optional<TransactionContext> mOptTransactionContext;


    private PredicateSelectionMatcher(CharSequence expectedSelection, Optional<TransactionContext> optTransactionContext)
    {
        mExpectedSelection = expectedSelection;
        mOptTransactionContext = optTransactionContext;
    }


    public PredicateSelectionMatcher(CharSequence expectedSelection, TransactionContext transactionContext)
    {
        this(expectedSelection, new Present<>(transactionContext));
    }


    public PredicateSelectionMatcher(CharSequence expectedSelection)
    {
        this(expectedSelection, Absent.<TransactionContext>absent());
    }


    @Override
    protected boolean matchesSafely(Predicate item, Description mismatchDescription)
    {
        TransactionContext tContext = mOptTransactionContext.value(Mockito.mock(TransactionContext.class, new FailAnswer()));
        String actualSelection = item.selection(tContext).toString();
        boolean matches = actualSelection.equals(mExpectedSelection.toString());
        if (!matches)
        {
            mismatchDescription.appendText(String.format("had selection '%s'", mExpectedSelection));
        }
        return matches;
    }


    @Override
    public void describeTo(Description description)
    {
        description.appendText(String.format("has selection '%s'", mExpectedSelection));
    }


    public static Matcher<Predicate> hasSelection(CharSequence expectedSelection)
    {
        return new PredicateSelectionMatcher(expectedSelection);
    }


    public static Matcher<Predicate> hasSelection(CharSequence expectedSelection, TransactionContext tContext)
    {
        return new PredicateSelectionMatcher(expectedSelection, tContext);
    }


    public static Predicate predicateWithSelection(CharSequence expectedSelection)
    {
        return MockitoHamcrest.argThat(new PredicateSelectionMatcher(expectedSelection));
    }


    public static Predicate predicateWithSelection(CharSequence expectedSelection, TransactionContext tContext)
    {
        return MockitoHamcrest.argThat(new PredicateSelectionMatcher(expectedSelection, tContext));
    }

}
