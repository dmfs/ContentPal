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

import org.dmfs.android.contentpal.Operation;
import org.dmfs.iterables.ArrayIterable;
import org.dmfs.iterables.EmptyIterable;
import org.dmfs.optional.Absent;
import org.dmfs.optional.Optional;
import org.dmfs.optional.Present;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;


/**
 * @author Marten Gajda
 */
public class MultiBatchTest
{
    @Test
    public void testEmptyBatches() throws Exception
    {
        assertThat(new MultiBatch(new Optional[0]), emptyIterable());
        assertThat(new MultiBatch(new Operation[0]), emptyIterable());
        assertThat(new MultiBatch(new EmptyIterable<Operation<?>>()), emptyIterable());

        assertThat(new MultiBatch(new Absent<Operation<?>>()), emptyIterable());
        assertThat(new MultiBatch(new Absent<Operation<?>>(), new Absent<Operation<?>>()), emptyIterable());
    }


    @Test
    public void testOptionals() throws Exception
    {

        Operation<?> mockOp1 = mock(Operation.class);
        Operation<?> mockOp2 = mock(Operation.class);
        Operation<?> mockOp3 = mock(Operation.class);

        assertThat(new MultiBatch(new Present<Operation<?>>(mockOp1)),
                Matchers.<Operation>contains(mockOp1));
        assertThat(new MultiBatch(new Present<Operation<?>>(mockOp1), new Present<Operation<?>>(mockOp2)),
                Matchers.<Operation>contains(mockOp1, mockOp2));
        assertThat(new MultiBatch(new Present<Operation<?>>(mockOp1), new Present<Operation<?>>(mockOp2), new Present<Operation<?>>(mockOp3)),
                Matchers.<Operation>contains(mockOp1, mockOp2, mockOp3));

        // inject some absent values
        assertThat(new MultiBatch(new Present<Operation<?>>(mockOp1), Absent.<Operation<?>>absent()),
                Matchers.<Operation>contains(mockOp1));
        assertThat(new MultiBatch(new Present<Operation<?>>(mockOp1), Absent.<Operation<?>>absent(), new Present<Operation<?>>(mockOp2)),
                Matchers.<Operation>contains(mockOp1, mockOp2));
        assertThat(new MultiBatch(new Present<Operation<?>>(mockOp1), Absent.<Operation<?>>absent(), new Present<Operation<?>>(mockOp2),
                        Absent.<Operation<?>>absent(), new Present<Operation<?>>(mockOp3)),
                Matchers.<Operation>contains(mockOp1, mockOp2, mockOp3));
    }


    @Test
    public void testOperations() throws Exception
    {
        Operation<?> mockOp1 = mock(Operation.class);
        Operation<?> mockOp2 = mock(Operation.class);
        Operation<?> mockOp3 = mock(Operation.class);

        assertThat(new MultiBatch(mockOp1),
                Matchers.<Operation>contains(mockOp1));
        assertThat(new MultiBatch(mockOp1, mockOp2),
                Matchers.<Operation>contains(mockOp1, mockOp2));
        assertThat(new MultiBatch(mockOp1, mockOp2, mockOp3),
                Matchers.<Operation>contains(mockOp1, mockOp2, mockOp3));
    }


    @Test
    public void testOperationsIterable() throws Exception
    {

        Operation<?> mockOp1 = mock(Operation.class);
        Operation<?> mockOp2 = mock(Operation.class);
        Operation<?> mockOp3 = mock(Operation.class);

        assertThat(new MultiBatch(new ArrayIterable<Operation<?>>(mockOp1)),
                Matchers.<Operation>contains(mockOp1));
        assertThat(new MultiBatch(new ArrayIterable<>(mockOp1, mockOp2)),
                Matchers.<Operation>contains(mockOp1, mockOp2));
        assertThat(new MultiBatch(new ArrayIterable<>(mockOp1, mockOp2, mockOp3)),
                Matchers.<Operation>contains(mockOp1, mockOp2, mockOp3));
    }

}