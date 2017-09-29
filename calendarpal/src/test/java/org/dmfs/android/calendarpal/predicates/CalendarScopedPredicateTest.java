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
import org.dmfs.android.contentpal.testing.answers.FailAnswer;
import org.dmfs.android.contentpal.testing.predicates.ArgumentTestPredicate;
import org.dmfs.android.contentpal.testing.predicates.BackReferences;
import org.dmfs.android.contentpal.testing.predicates.ValueArgument;
import org.dmfs.android.contentpal.testing.predicates.Values;
import org.dmfs.optional.hamcrest.AbsentMatcher;
import org.junit.Test;

import static org.dmfs.optional.hamcrest.PresentMatcher.isPresent;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


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
        TransactionContext tContextDummy = dummy(TransactionContext.class);
        SoftRowReference<CalendarContract.Calendars> rowReferenceDummy = dummy(SoftRowReference.class);
        RowSnapshot<CalendarContract.Calendars> calendarRow = failingMock(RowSnapshot.class);
        Predicate predicate = failingMock(Predicate.class);

        doReturn(rowReferenceDummy).when(calendarRow).reference();
        doReturn("sel").when(predicate).selection(tContextDummy);
        doReturn(new BackReference<>(dummy(Uri.class), 12)).when(tContextDummy).resolved(rowReferenceDummy);

        assertThat(new CalendarScoped(calendarRow, predicate).selection(tContextDummy).toString(),
                is("( sel ) and ( calendar_id = ? )"));
    }


    @Test
    public void testArguments() throws Exception
    {
        TransactionContext tContextDummy = dummy(TransactionContext.class);
        SoftRowReference<CalendarContract.Calendars> rowReferenceDummy = dummy(SoftRowReference.class);
        RowSnapshot<CalendarContract.Calendars> calendarRow = failingMock(RowSnapshot.class);
        doReturn(rowReferenceDummy).when(calendarRow).reference();
        doReturn(new BackReference<>(dummy(Uri.class), 12)).when(tContextDummy).resolved(rowReferenceDummy);

        Iterable<Predicate.Argument> arguments = new CalendarScoped(calendarRow,
                new ArgumentTestPredicate(tContextDummy, new ValueArgument("5"))).arguments(tContextDummy);
        assertThat(new Values(arguments), contains("5", "-1"));
        assertThat(new BackReferences(arguments), contains(AbsentMatcher.isAbsent(0), isPresent(12)));
    }


    private <T> T dummy(Class<T> classToMock)
    {
        return mock(classToMock, new FailAnswer());
    }


    private <T> T failingMock(Class<T> classToMock)
    {
        return mock(classToMock, new FailAnswer());
    }

}