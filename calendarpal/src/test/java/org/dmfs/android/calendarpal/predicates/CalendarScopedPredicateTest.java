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
import org.dmfs.iterables.SingletonIterable;
import org.dmfs.optional.Present;
import org.hamcrest.collection.IsIterableWithSize;
import org.junit.Test;

import java.util.Iterator;

import static org.dmfs.optional.hamcrest.PresentMatcher.isPresent;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
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
        Predicate predicate = failingMock(Predicate.class);
        Predicate.Argument argument = failingMock(Predicate.Argument.class);

        doReturn(rowReferenceDummy).when(calendarRow).reference();

        doReturn(new SingletonIterable<>(argument)).when(predicate).arguments(tContextDummy);

        doReturn(new Present<>(3)).when(argument).backReference();
        doReturn("5").when(argument).value();

        doReturn(new BackReference<>(dummy(Uri.class), 12)).when(tContextDummy).resolved(rowReferenceDummy);

        Iterable<Predicate.Argument> arguments = new CalendarScoped(calendarRow, predicate).arguments(tContextDummy);
        assertThat(arguments, IsIterableWithSize.<Predicate.Argument>iterableWithSize(2));

        Iterator<Predicate.Argument> it = arguments.iterator();

        Predicate.Argument arg1 = it.next();
        assertThat(arg1.backReference(), isPresent(3));
        assertThat(arg1.value(), is("5"));

        Predicate.Argument arg2 = it.next();
        assertThat(arg2.backReference(), isPresent(12));
        assertThat(arg2.value(), is("-1"));
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