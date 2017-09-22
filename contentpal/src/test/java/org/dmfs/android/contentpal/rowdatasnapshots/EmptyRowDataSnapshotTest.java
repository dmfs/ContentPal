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

package org.dmfs.android.contentpal.rowdatasnapshots;

import org.dmfs.optional.hamcrest.AbsentMatcher;
import org.junit.Test;

import static org.dmfs.optional.hamcrest.AbsentMatcher.isAbsent;
import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public class EmptyRowDataSnapshotTest
{
    @Test
    public void testCharData() throws Exception
    {
        assertThat(new EmptyRowDataSnapshot<>().charData("key"), AbsentMatcher.<CharSequence>isAbsent(""));
    }


    @Test
    public void testByteData() throws Exception
    {
        assertThat(new EmptyRowDataSnapshot<>().byteData("key"), isAbsent(new byte[0]));
    }


    @Test
    public void testIterator() throws Exception
    {
        assertThat(new EmptyRowDataSnapshot<>(), emptyIterable());
    }

}