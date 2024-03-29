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

package org.dmfs.android.contentpal.operations.internal;

import android.net.Uri;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.android.contentpal.testing.contentoperationbuilder.OperationType.updateOperation;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.TargetMatcher.targets;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithExpectedCount.withoutExpectedCount;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withoutValues;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithYieldAllowed.withYieldNotAllowed;
import static org.dmfs.android.contentpal.testing.operations.OperationMatcher.builds;
import static org.dmfs.jems2.hamcrest.matchers.optional.AbsentMatcher.absent;
import static org.dmfs.jems2.mockito.doubles.TestDoubles.dummy;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsSame.sameInstance;


/**
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class RawUpdateTest
{
    @Test
    public void testReference()
    {
        assertThat(new RawUpdate<>(dummy(Uri.class)).reference(), is(absent()));
    }


    @Test
    public void testContentOperationBuilder()
    {
        Uri dummyUri = dummy(Uri.class);
        assertThat(new RawUpdate<>(dummyUri),
            builds(
                targets(sameInstance(dummyUri)),
                updateOperation(),
                withYieldNotAllowed(),
                withoutExpectedCount(),
                withoutValues()));
    }
}