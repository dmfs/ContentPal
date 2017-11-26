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

package org.dmfs.android.contentpal.testing.projection;

import org.dmfs.android.contentpal.Projection;
import org.dmfs.iterables.EmptyIterable;
import org.dmfs.iterables.elementary.Seq;
import org.dmfs.iterators.EmptyIterator;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Iterator;

import static org.dmfs.android.contentpal.testing.projection.ProjectionMatcher.projects;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;


/**
 * @author Marten Gajda
 */
public class ProjectionMatcherTest
{
    @Test
    public void testMatchesSafelyEmpty() throws Exception
    {
        Projection emptyMockProjection = failingMock(Projection.class);
        doReturn(new String[0]).when(emptyMockProjection).toArray();
        doReturn(new EmptyIterator<>()).when(emptyMockProjection).iterator();

        assertThat(projects(EmptyIterable.<String>instance()).matches(emptyMockProjection), is(true));
        assertThat(projects(new Seq<>("123")).matches(emptyMockProjection), is(false));
    }


    @Test
    public void testMatchesSafelyNonEmpty() throws Exception
    {
        Projection mockProjection = failingMock(Projection.class);
        doReturn(new String[] { "1", "2", "3" }).when(mockProjection).toArray();
        doAnswer(new Answer<Iterator<String>>()
        {
            @Override
            public Iterator<String> answer(InvocationOnMock invocation) throws Throwable
            {
                return new org.dmfs.iterators.elementary.Seq<>("1", "2", "3");
            }
        }).when(mockProjection).iterator();

        assertThat(projects(new Seq<>("1", "2", "3")).matches(mockProjection), is(true));
        assertThat(projects(new Seq<>("3", "2", "1")).matches(mockProjection), is(true));
    }


    @Test
    public void testMismatchesSafely1() throws Exception
    {
        Projection mockProjection = failingMock(Projection.class);
        doReturn(new String[] { "1", "2", "3" }).when(mockProjection).toArray();
        doAnswer(new Answer<Iterator<String>>()
        {
            @Override
            public Iterator<String> answer(InvocationOnMock invocation) throws Throwable
            {
                return new org.dmfs.iterators.elementary.Seq<>("1", "2", "3", "4");
            }
        }).when(mockProjection).iterator();

        assertThat(projects(new Seq<>("1", "2", "3")).matches(mockProjection), is(false));
        assertThat(projects(new Seq<>("3", "2", "1")).matches(mockProjection), is(false));
    }


    @Test
    public void testMismatchesSafely2() throws Exception
    {
        Projection mockProjection = failingMock(Projection.class);
        doReturn(new String[] { "1", "2", "3", "4" }).when(mockProjection).toArray();
        doAnswer(new Answer<Iterator<String>>()
        {
            @Override
            public Iterator<String> answer(InvocationOnMock invocation) throws Throwable
            {
                return new org.dmfs.iterators.elementary.Seq<>("1", "2", "3");
            }
        }).when(mockProjection).iterator();

        assertThat(projects(new Seq<>("1", "2", "3")).matches(mockProjection), is(false));
        assertThat(projects(new Seq<>("3", "2", "1")).matches(mockProjection), is(false));
    }


    @Test
    public void testMismatchesSafely3() throws Exception
    {
        Projection mockProjection = failingMock(Projection.class);
        doReturn(new String[] { "1", "2" }).when(mockProjection).toArray();
        doAnswer(new Answer<Iterator<String>>()
        {
            @Override
            public Iterator<String> answer(InvocationOnMock invocation) throws Throwable
            {
                return new org.dmfs.iterators.elementary.Seq<>("1", "2", "3");
            }
        }).when(mockProjection).iterator();

        assertThat(projects(new Seq<>("1", "2", "3")).matches(mockProjection), is(false));
        assertThat(projects(new Seq<>("3", "2", "1")).matches(mockProjection), is(false));
    }


    @Test
    public void testMismatchesSafely4() throws Exception
    {
        Projection mockProjection = failingMock(Projection.class);
        doReturn(new String[] { "1", "2", "3" }).when(mockProjection).toArray();
        doAnswer(new Answer<Iterator<String>>()
        {
            @Override
            public Iterator<String> answer(InvocationOnMock invocation) throws Throwable
            {
                return new org.dmfs.iterators.elementary.Seq<>("1", "2");
            }
        }).when(mockProjection).iterator();

        assertThat(projects(new Seq<>("1", "2", "3")).matches(mockProjection), is(false));
        assertThat(projects(new Seq<>("3", "2", "1")).matches(mockProjection), is(false));
    }


    @Test
    public void testMismatchesSafely5() throws Exception
    {
        Projection mockProjection = failingMock(Projection.class);
        doReturn(new String[] { "1", "2", "4" }).when(mockProjection).toArray();
        doAnswer(new Answer<Iterator<String>>()
        {
            @Override
            public Iterator<String> answer(InvocationOnMock invocation) throws Throwable
            {
                return new org.dmfs.iterators.elementary.Seq<>("1", "2", "3");
            }
        }).when(mockProjection).iterator();

        assertThat(projects(new Seq<>("1", "2", "3")).matches(mockProjection), is(false));
        assertThat(projects(new Seq<>("3", "2", "1")).matches(mockProjection), is(false));
    }


    @Test
    public void testMismatchesSafely6() throws Exception
    {
        Projection mockProjection = failingMock(Projection.class);
        doReturn(new String[] { "1", "2", "3" }).when(mockProjection).toArray();
        doAnswer(new Answer<Iterator<String>>()
        {
            @Override
            public Iterator<String> answer(InvocationOnMock invocation) throws Throwable
            {
                return new org.dmfs.iterators.elementary.Seq<>("1", "2", "4");
            }
        }).when(mockProjection).iterator();

        assertThat(projects(new Seq<>("1", "2", "3")).matches(mockProjection), is(false));
        assertThat(projects(new Seq<>("3", "2", "1")).matches(mockProjection), is(false));
    }


    @Test
    public void testDescribeTo() throws Exception
    {
        Description description = new StringDescription();
        projects(new Seq<>("123", "456")).describeTo(description);
        assertThat(description.toString(), is("A projection of the columns [\"123\", \"456\"]"));
    }


    @Test
    public void testDescribeToEmpty() throws Exception
    {
        Description description = new StringDescription();
        projects(new EmptyIterable<String>()).describeTo(description);
        assertThat(description.toString(), is("A projection of the columns []"));
    }

}