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

package org.dmfs.android.contentpal.testing.predicates;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.iterables.ArrayIterable;
import org.junit.Test;

import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public class ValuesTest
{
    @Test
    public void test()
    {
        assertThat(new Values(new ArrayIterable<Predicate.Argument>()), emptyIterable());
        assertThat(new Values(new ArrayIterable<Predicate.Argument>(new ValueArgument("a"))), contains("a"));
        assertThat(new Values(new ArrayIterable<Predicate.Argument>(new ValueArgument("a"), new ValueArgument("b"))), contains("a", "b"));
    }
}