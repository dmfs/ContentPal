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

package org.dmfs.android.contentpal.batches;

import android.content.ContentProviderOperation;
import android.net.Uri;

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.TransactionContext;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithExpectedCount.withoutExpectedCount;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.OperationType.updateOperation;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withoutValues;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithYieldAllowed.withYieldAllowed;
import static org.dmfs.android.contentpal.testing.operations.OperationMatcher.builds;
import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


/**
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class YieldableTest
{

    @Test
    public void testEmpty() throws Exception
    {
        assertThat(new Yieldable(new EmptyBatch()),
                emptyIterable());
    }


    @Test
    public void testOperations() throws Exception
    {
        Operation<?> mockOp1 = mock(Operation.class);
        Operation<?> mockOp2 = mock(Operation.class);
        Operation<?> mockOp3 = mock(Operation.class);

        doReturn(ContentProviderOperation.newUpdate(Uri.EMPTY)).when(mockOp1).contentOperationBuilder(ArgumentMatchers.any(TransactionContext.class));

        // every last operation of each batch should allow yielding

        assertThat(new Yieldable(new MultiBatch(mockOp1)),
                Matchers.contains(
                        builds(
                                updateOperation(),
                                withoutValues(),
                                withoutExpectedCount(),
                                withYieldAllowed())));

        assertThat(new Yieldable(new MultiBatch(mockOp2, mockOp1)),
                Matchers.contains(
                        Matchers.<Operation>is(mockOp2),
                        builds(
                                updateOperation(),
                                withoutValues(),
                                withoutExpectedCount(),
                                withYieldAllowed())));

        assertThat(new Yieldable(new MultiBatch(mockOp3, mockOp2, mockOp1)),
                Matchers.contains(
                        Matchers.<Operation>is(mockOp3),
                        Matchers.<Operation>is(mockOp2),
                        builds(
                                updateOperation(),
                                withoutValues(),
                                withoutExpectedCount(),
                                withYieldAllowed())));
    }

}