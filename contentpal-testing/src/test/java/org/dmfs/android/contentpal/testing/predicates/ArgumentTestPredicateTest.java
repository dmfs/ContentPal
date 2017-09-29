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
import org.dmfs.android.contentpal.testing.dummies.BounceTransactionContext;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.mock;


/**
 * @author Marten Gajda
 */
public class ArgumentTestPredicateTest
{
    @Test(expected = UnsupportedOperationException.class)
    public void testSelection() throws Exception
    {
        TransactionContext dummyTransactionContext = new BounceTransactionContext();
        new ArgumentTestPredicate(dummyTransactionContext, new ValueArgument("1")).selection(dummyTransactionContext);
    }


    @Test
    public void testArguments() throws Exception
    {
        TransactionContext dummyTransactionContext = mock(TransactionContext.class);
        Predicate.Argument dummyArg1 = mock(Predicate.Argument.class);
        Predicate.Argument dummyArg2 = mock(Predicate.Argument.class);
        Predicate.Argument dummyArg3 = mock(Predicate.Argument.class);

        assertThat(new ArgumentTestPredicate(dummyTransactionContext).arguments(dummyTransactionContext), Matchers.<Predicate.Argument>emptyIterable());
        assertThat(new ArgumentTestPredicate(dummyTransactionContext, dummyArg1).arguments(dummyTransactionContext), contains(dummyArg1));
        assertThat(new ArgumentTestPredicate(dummyTransactionContext, dummyArg1, dummyArg2).arguments(dummyTransactionContext), contains(dummyArg1, dummyArg2));
        assertThat(new ArgumentTestPredicate(dummyTransactionContext, dummyArg1, dummyArg2, dummyArg3).arguments(dummyTransactionContext),
                contains(dummyArg1, dummyArg2, dummyArg3));
    }


    @Test(expected = AssertionError.class)
    public void testArgumentsWrongTransactionContext() throws Exception
    {
        TransactionContext dummyTransactionContext = mock(TransactionContext.class);
        TransactionContext dummyTransactionContext2 = mock(TransactionContext.class);

        new ArgumentTestPredicate(dummyTransactionContext).arguments(dummyTransactionContext2);
    }

}