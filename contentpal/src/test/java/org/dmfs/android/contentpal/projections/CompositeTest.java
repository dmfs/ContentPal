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

package org.dmfs.android.contentpal.projections;

import org.dmfs.android.contentpal.Projection;
import org.dmfs.android.contentpal.testing.table.Contract;
import org.dmfs.iterables.EmptyIterable;
import org.dmfs.iterables.elementary.Seq;
import org.junit.Test;

import static org.dmfs.android.contentpal.testing.projection.ProjectionMatcher.projects;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public class CompositeTest
{
    @Test
    public void testEmpty() throws Exception
    {
        assertThat(new Composite<>(new Seq<Projection<Contract>>(new EmptyProjection<Contract>())), projects(EmptyIterable.<String>instance()));
        assertThat(new Composite<>(new Seq<Projection<Contract>>(new EmptyProjection<Contract>(), new EmptyProjection())),
                projects(EmptyIterable.<String>instance()));
        assertThat(new Composite<>(new Seq<Projection<Contract>>(new EmptyProjection<Contract>(), new EmptyProjection(), new EmptyProjection())),
                projects(EmptyIterable.<String>instance()));
    }


    @Test
    public void testNonEmpty() throws Exception
    {
        assertThat(new Composite<>(new Seq<Projection<Contract>>(new MultiProjection<Contract>("abc"))), projects(new Seq<>("abc")));
        assertThat(new Composite<>(new Seq<Projection<Contract>>(new MultiProjection<Contract>("abc", "xyz", "qrs"))),
                projects(new Seq<>("abc", "xyz", "qrs")));
        assertThat(new Composite<>(new Seq<Projection<Contract>>(new MultiProjection<Contract>("abc"), new MultiProjection<Contract>("xyz"))),
                projects(new Seq<>("abc", "xyz")));
        assertThat(new Composite<>(new Seq<Projection<Contract>>(new MultiProjection<Contract>("abc", "xyz", "qrs"))),
                projects(new Seq<>("abc", "xyz", "qrs")));
        assertThat(new Composite<>(
                        new Seq<Projection<Contract>>(new MultiProjection<Contract>("abc", "xyz", "qrs"), new MultiProjection<Contract>("123", "456", "789"))),
                projects(new Seq<>("abc", "xyz", "qrs", "123", "456", "789")));
    }


    @Test
    public void testDuplicateColumns() throws Exception
    {
        assertThat(new Composite<>(
                        new Seq<Projection<Contract>>(
                                new MultiProjection<Contract>("abc", "xyz", "qrs", "123", "456", "789"),
                                new MultiProjection<Contract>("123", "456", "789", "abc", "xyz", "qrs"))),
                projects(new Seq<>("abc", "xyz", "qrs", "123", "456", "789")));
    }

}