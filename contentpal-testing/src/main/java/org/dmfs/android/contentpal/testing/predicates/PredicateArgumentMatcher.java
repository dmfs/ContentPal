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
import org.mockito.ArgumentMatcher;
import org.mockito.hamcrest.MockitoHamcrest;


/**
 * Factory methods for Mockito argument matching {@link Predicate}s (Using {@link ArgumentMatcher}s).
 *
 * @author Gabor Keszthelyi
 */
public final class PredicateArgumentMatcher
{

    public static Predicate predicateWithSelection(CharSequence expectedSelection)
    {
        return MockitoHamcrest.argThat(new PredicateMatcher.SelectionMatcher(expectedSelection));
    }


    public static Predicate predicateWithSelection(CharSequence expectedSelection, TransactionContext tContext)
    {
        return MockitoHamcrest.argThat(new PredicateMatcher.SelectionMatcher(expectedSelection, tContext));
    }


    private PredicateArgumentMatcher()
    {

    }

}
