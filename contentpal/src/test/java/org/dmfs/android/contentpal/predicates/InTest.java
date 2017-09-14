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

import org.dmfs.android.contentpal.transactions.contexts.EmptyTransactionContext;
import org.junit.Test;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public class InTest
{
    @Test
    public void testSelection() throws Exception
    {
        assertEquals("x in ( ) ", new In("x").selection(EmptyTransactionContext.INSTANCE).toString());
        assertEquals("x in ( ? ) ", new In("x", "a").selection(EmptyTransactionContext.INSTANCE).toString());
        assertEquals("x in ( ?, ? ) ", new In("x", "a", 1).selection(EmptyTransactionContext.INSTANCE).toString());
        assertEquals("x in ( ?, ?, ? ) ", new In("x", "a", 1, 1.2).selection(EmptyTransactionContext.INSTANCE).toString());
    }


    @Test
    public void testArguments() throws Exception
    {
        assertThat(new In("x").arguments(EmptyTransactionContext.INSTANCE), emptyIterable());
        assertThat(new In("x", "a").arguments(EmptyTransactionContext.INSTANCE), contains("a"));
        assertThat(new In("x", "a", 1).arguments(EmptyTransactionContext.INSTANCE), contains("a", "1"));
        assertThat(new In("x", "a", 1, 1.2).arguments(EmptyTransactionContext.INSTANCE), contains("a", "1", "1.2"));
    }

}