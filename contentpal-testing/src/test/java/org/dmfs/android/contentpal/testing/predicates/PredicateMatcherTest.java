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
import org.dmfs.iterables.EmptyIterable;
import org.dmfs.jems.hamcrest.matchers.AbsentMatcher;
import org.dmfs.optional.Absent;
import org.dmfs.optional.Present;
import org.junit.Test;

import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.argumentValues;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.backReferences;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.emptyArguments;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.predicateWith;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.selection;
import static org.dmfs.jems.hamcrest.matchers.PresentMatcher.isPresent;
import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Unit test for {@link PredicateMatcher}.
 *
 * @author Gabor Keszthelyi
 */
public final class PredicateMatcherTest
{

    @Test
    public void testSelection()
    {
        // Without TC
        selection("a").matches(
                when(mock(Predicate.class).selection(any(TransactionContext.class))).thenReturn("a").getMock());

        // With TC
        TransactionContext dummyTc = dummy(TransactionContext.class);

        assertTrue(selection(dummyTc, "a").matches(
                when(mock(Predicate.class).selection(dummyTc)).thenReturn("a").getMock()));
    }


    @Test
    public void testArgumentValue()
    {
        // Single
        Predicate.Argument argumentA = when(mock(Predicate.Argument.class).value()).thenReturn("a").getMock();
        assertTrue(argumentValues("a").matches(
                when(mock(Predicate.class).arguments(any(TransactionContext.class))).thenReturn(new ArrayIterable<>(argumentA)).getMock()));

        // Multiple
        Predicate.Argument aB = when(mock(Predicate.Argument.class).value()).thenReturn("b").getMock();
        Predicate.Argument aC = when(mock(Predicate.Argument.class).value()).thenReturn("c").getMock();
        Predicate.Argument aD = when(mock(Predicate.Argument.class).value()).thenReturn("d").getMock();
        assertTrue(argumentValues("b", "c", "d").matches(
                when(mock(Predicate.class).arguments(any(TransactionContext.class))).thenReturn(new ArrayIterable<>(aB, aC, aD)).getMock()));

        // Single with TC
        TransactionContext dummyTc = dummy(TransactionContext.class);
        Predicate.Argument argumentE = when(mock(Predicate.Argument.class).value()).thenReturn("e").getMock();
        assertTrue(argumentValues(dummyTc, "e").matches(
                when(mock(Predicate.class).arguments(dummyTc)).thenReturn(new ArrayIterable<>(argumentE)).getMock()));
    }


    @Test
    public void testBackReferences()
    {
        // Single
        Predicate.Argument argumentA = when(mock(Predicate.Argument.class).backReference()).thenReturn(new Present<>(1)).getMock();
        assertTrue(backReferences(isPresent(1)).matches(
                when(mock(Predicate.class).arguments(any(TransactionContext.class))).thenReturn(new ArrayIterable<>(argumentA)).getMock()));

        // Multiple
        Predicate.Argument aB = when(mock(Predicate.Argument.class).backReference()).thenReturn(new Present<>(1)).getMock();
        Predicate.Argument aC = when(mock(Predicate.Argument.class).backReference()).thenReturn(Absent.<Integer>absent()).getMock();
        Predicate.Argument aD = when(mock(Predicate.Argument.class).backReference()).thenReturn(new Present<>(3)).getMock();
        assertTrue(backReferences(isPresent(1), AbsentMatcher.<Integer>isAbsent(), isPresent(3)).matches(
                when(mock(Predicate.class).arguments(any(TransactionContext.class))).thenReturn(new ArrayIterable<>(aB, aC, aD)).getMock()));

        // Single with TC
        TransactionContext dummyTc = dummy(TransactionContext.class);
        Predicate.Argument argumentE = when(mock(Predicate.Argument.class).backReference()).thenReturn(new Present<>(1)).getMock();
        assertTrue(backReferences(dummyTc, isPresent(1)).matches(
                when(mock(Predicate.class).arguments(dummyTc)).thenReturn(new ArrayIterable<>(argumentE)).getMock()));
    }


    @Test
    public void testEmptyArguments()
    {
        assertTrue(emptyArguments().matches(
                when(mock(Predicate.class).arguments(any(TransactionContext.class))).thenReturn(EmptyIterable.<Predicate.Argument>instance()).getMock()));

        TransactionContext dummyTc = dummy(TransactionContext.class);
        assertTrue(emptyArguments(dummyTc).matches(
                when(mock(Predicate.class).arguments(dummyTc)).thenReturn(EmptyIterable.<Predicate.Argument>instance()).getMock()));

    }


    @Test
    public void testCombined()
    {
        Predicate predicateMock = failingMock(Predicate.class);
        Predicate.Argument argumentMock = failingMock(Predicate.Argument.class);

        doReturn("sel").when(predicateMock).selection(any(TransactionContext.class));
        doReturn("arg").when(argumentMock).value();
        doReturn(new ArrayIterable<>(argumentMock)).when(predicateMock).arguments(any(TransactionContext.class));

        assertTrue(predicateWith(selection("sel"), argumentValues("arg"))
                .matches(predicateMock));
    }

}