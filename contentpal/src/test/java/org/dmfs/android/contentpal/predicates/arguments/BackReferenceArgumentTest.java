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

package org.dmfs.android.contentpal.predicates.arguments;

import org.dmfs.android.contentpal.Predicate;
import org.junit.Test;

import static org.dmfs.jems2.hamcrest.matchers.optional.PresentMatcher.present;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Unit test for {@link BackReferenceArgument}.
 *
 * @author Gabor Keszthelyi
 */
public final class BackReferenceArgumentTest
{

    @Test
    public void test()
    {
        Predicate.Argument underTest = new BackReferenceArgument(4);
        assertThat(underTest.value(), is("-1"));
        assertThat(underTest.backReference(), is(present(4)));
    }
}