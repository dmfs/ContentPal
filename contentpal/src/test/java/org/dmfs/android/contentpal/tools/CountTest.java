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

package org.dmfs.android.contentpal.tools;

import android.database.Cursor;
import android.os.RemoteException;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.View;
import org.dmfs.android.contentpal.testing.answers.FailAnswer;
import org.dmfs.android.contentpal.tools.uriparams.EmptyUriParams;
import org.dmfs.optional.Absent;
import org.junit.Test;

import static org.dmfs.android.contentpal.testing.predicates.PredicateSelectionMatcher.predicateWithSelection;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


/**
 * Test for {@link Count}.
 *
 * @author Gabor Keszthelyi
 */
public final class CountTest
{

    @Test
    public void test_thatCursorGetCountIsReturned() throws RemoteException
    {
        View<Object> mockView = mock(View.class, new FailAnswer());
        Predicate dummyPredicate = mock(Predicate.class, new FailAnswer());
        Cursor mockCursor = mock(Cursor.class, new FailAnswer());

        doReturn(mockCursor).when(mockView).rows(EmptyUriParams.INSTANCE, dummyPredicate, Absent.<String>absent());
        doReturn(4).when(mockCursor).getCount();
        doNothing().when(mockCursor).close();

        assertThat(new Count<>(mockView, dummyPredicate).value(), is(4));
    }


    @Test
    public void test_ctorWithoutPredicate_predicateHasSelection1() throws RemoteException
    {
        View<Object> mockView = mock(View.class, new FailAnswer());
        Cursor mockCursor = mock(Cursor.class, new FailAnswer());

        doReturn(mockCursor).when(mockView).rows(same(EmptyUriParams.INSTANCE), predicateWithSelection("1"), same(Absent.<String>absent()));
        doReturn(5).when(mockCursor).getCount();
        doNothing().when(mockCursor).close();

        assertThat(new Count<>(mockView).value(), is(5));
    }

}