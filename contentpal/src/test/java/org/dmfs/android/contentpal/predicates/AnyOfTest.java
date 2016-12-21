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

package org.dmfs.android.contentpal.predicates;

import org.junit.Test;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public class AnyOfTest
{

    @Test
    public void testSelection() throws Exception
    {
        assertEquals("1", new AnyOf().selection().toString());
        assertEquals("x", new AnyOf(new Mocked("x", "a")).selection().toString());
        assertEquals("( x ) or ( y )", new AnyOf(new Mocked("x", "a"), new Mocked("y", "1")).selection().toString());
        assertEquals("( x ) or ( z ) or ( y )", new AnyOf(new Mocked("x", "a"), new Mocked("z", "w", "z"), new Mocked("y", "1")).selection().toString());
    }


    @Test
    public void testArguments() throws Exception
    {
        assertThat(new AnyOf().arguments(), emptyIterable());
        assertThat(new AnyOf(new Mocked("x", "a")).arguments(), contains("a"));
        assertThat(new AnyOf(new Mocked("x", "a"), new Mocked("y", "1")).arguments(), contains("a", "1"));
        assertThat(new AnyOf(new Mocked("x", "a"), new Mocked("z", "w", "z"), new Mocked("y", "1")).arguments(), contains("a", "w", "z", "1"));
    }
}