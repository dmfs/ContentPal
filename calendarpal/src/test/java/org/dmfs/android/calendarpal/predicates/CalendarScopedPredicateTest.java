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

import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.predicates.EqArg;
import org.dmfs.android.contentpal.references.BackReference;
import org.junit.Test;

import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.argumentValues;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.backReferences;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.predicateWith;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.selection;
import static org.dmfs.jems2.hamcrest.matchers.optional.AbsentMatcher.absent;
import static org.dmfs.jems2.hamcrest.matchers.optional.PresentMatcher.present;
import static org.dmfs.jems2.mockito.doubles.TestDoubles.dummy;
import static org.dmfs.jems2.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doReturn;


/**
 * Unit test for {@link CalendarScoped}.
 *
 * @author Gabor Keszthelyi
 */
public final class CalendarScopedPredicateTest
{

    @Test
    public void testSelection()
    {
        TransactionContext mockTc = failingMock(TransactionContext.class);
        SoftRowReference<CalendarContract.Calendars> dummyReference = dummy(SoftRowReference.class);
        RowSnapshot<CalendarContract.Calendars> mockCalendarRow = failingMock(RowSnapshot.class);

        doReturn(dummyReference).when(mockCalendarRow).reference();
        doReturn(new BackReference<>(dummy(Uri.class), 12)).when(mockTc).resolved(dummyReference);

        assertThat(new CalendarScoped(mockCalendarRow, new EqArg<>("x", "y")),
            predicateWith(
                selection(mockTc, "( x = ? ) and ( calendar_id = ? )"),
                argumentValues(mockTc, "y", "-1"),
                backReferences(mockTc, is(absent()), is(present(12)))
            ));
    }

}