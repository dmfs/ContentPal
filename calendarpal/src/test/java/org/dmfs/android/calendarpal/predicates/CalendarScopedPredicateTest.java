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

package org.dmfs.android.calendarpal.predicates;

import android.net.Uri;
import android.provider.CalendarContract;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.references.BackReference;
import org.dmfs.android.contentpal.testing.predicates.ArgumentTestPredicate;
import org.dmfs.android.contentpal.testing.predicates.BackReferences;
import org.dmfs.android.contentpal.testing.predicates.ValueArgument;
import org.dmfs.android.contentpal.testing.predicates.Values;
import org.dmfs.jems.hamcrest.matchers.AbsentMatcher;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.PresentMatcher.isPresent;
import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.doReturn;


/**
 * Unit test for {@link CalendarScoped}.
 *
 * @author Gabor Keszthelyi
 */
public final class CalendarScopedPredicateTest
{

    @Test
    public void testSelection() throws Exception
    {
        TransactionContext dummyTransactionContext = dummy(TransactionContext.class);
        SoftRowReference<CalendarContract.Calendars> dummyReference = dummy(SoftRowReference.class);
        RowSnapshot<CalendarContract.Calendars> mockCalendarRow = failingMock(RowSnapshot.class);
        Predicate predicate = failingMock(Predicate.class);

        doReturn(dummyReference).when(mockCalendarRow).reference();
        doReturn("sel").when(predicate).selection(dummyTransactionContext);
        doReturn(new BackReference<>(dummy(Uri.class), 12)).when(dummyTransactionContext).resolved(dummyReference);

        assertThat(new CalendarScoped(mockCalendarRow, predicate).selection(dummyTransactionContext).toString(),
                is("( sel ) and ( calendar_id = ? )"));
    }


    @Test
    public void testArguments() throws Exception
    {
        TransactionContext dummyTransactionContext = dummy(TransactionContext.class);
        SoftRowReference<CalendarContract.Calendars> dummyReference = dummy(SoftRowReference.class);
        RowSnapshot<CalendarContract.Calendars> mockCalendarRow = failingMock(RowSnapshot.class);
        doReturn(dummyReference).when(mockCalendarRow).reference();
        doReturn(new BackReference<>(dummy(Uri.class), 12)).when(dummyTransactionContext).resolved(dummyReference);

        Iterable<Predicate.Argument> arguments = new CalendarScoped(mockCalendarRow,
                new ArgumentTestPredicate(dummyTransactionContext, new ValueArgument("5"))).arguments(dummyTransactionContext);
        assertThat(new Values(arguments), contains("5", "-1"));
        assertThat(new BackReferences(arguments), contains(AbsentMatcher.<Integer>isAbsent(), isPresent(12)));
    }

}