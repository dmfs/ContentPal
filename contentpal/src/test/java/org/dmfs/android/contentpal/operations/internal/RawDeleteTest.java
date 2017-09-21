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

import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.testing.answers.FailAnswer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithExpectedCount.withoutExpectedCount;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.OperationType.deleteOperation;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withoutValues;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithYieldAllowed.withYieldNotAllowed;
import static org.dmfs.android.contentpal.testing.operations.OperationMatcher.builds;
import static org.dmfs.optional.hamcrest.AbsentMatcher.isAbsent;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;


/**
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class RawDeleteTest
{
    @Test
    public void testReference() throws Exception
    {
        assertThat(new RawDelete<>(mock(Uri.class, new FailAnswer())).reference(), isAbsent((SoftRowReference<Object>) mock(SoftRowReference.class)));
    }


    @Test
    public void testContentOperationBuilder() throws Exception
    {
        assertThat(new RawDelete<>(mock(Uri.class, new FailAnswer())),
                builds(
                        deleteOperation(),
                        withYieldNotAllowed(),
                        withoutExpectedCount(),
                        withoutValues()));
    }
}