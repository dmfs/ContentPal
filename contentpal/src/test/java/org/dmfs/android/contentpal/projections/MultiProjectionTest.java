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

import org.dmfs.android.contentpal.testing.table.Contract;
import org.dmfs.iterables.EmptyIterable;
import org.dmfs.iterables.elementary.Seq;
import org.junit.Test;

import static org.dmfs.android.contentpal.testing.projection.ProjectionMatcher.projects;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public class MultiProjectionTest
{
    @Test
    public void test() throws Exception
    {
        assertThat(new MultiProjection<Contract>(), projects(EmptyIterable.<String>instance()));
        assertThat(new MultiProjection<Contract>("abc"), projects(new Seq<>("abc")));
        assertThat(new MultiProjection<Contract>("abc", "xyz"), projects(new Seq<>("abc", "xyz")));
        assertThat(new MultiProjection<Contract>("abc", "xyz", "qrs"), projects(new Seq<>("abc", "xyz", "qrs")));
    }

}