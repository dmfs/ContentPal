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

import org.dmfs.android.contentpal.testing.predicates.BackReferences;
import org.dmfs.android.contentpal.testing.predicates.Mocked;
import org.dmfs.android.contentpal.testing.predicates.Values;
import org.dmfs.android.contentpal.transactions.contexts.EmptyTransactionContext;
import org.dmfs.optional.iterable.PresentValues;
import org.junit.Test;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public class NotTest
{

    @Test
    public void testSelection() throws Exception
    {
        assertEquals("not ( x )", new Not(new Mocked("x", "a")).selection(EmptyTransactionContext.INSTANCE).toString());
        assertEquals("not ( x )", new Not(new Mocked("x", "a", "z", "w")).selection(EmptyTransactionContext.INSTANCE).toString());
    }


    @Test
    public void testArguments() throws Exception
    {
        assertThat(new Values(new Not(new Mocked("x", "a")).arguments(EmptyTransactionContext.INSTANCE)), contains("a"));
        assertThat(new PresentValues<>(new BackReferences(new Not(new Mocked("x", "a")).arguments(EmptyTransactionContext.INSTANCE))), emptyIterable());

        assertThat(new Values(new Not(new Mocked("x", "a", "z", "w")).arguments(EmptyTransactionContext.INSTANCE)), contains("a", "z", "w"));
        assertThat(new PresentValues<>(new BackReferences(new Not(new Mocked("x", "a", "z", "w")).arguments(EmptyTransactionContext.INSTANCE))),
                emptyIterable());
    }
}