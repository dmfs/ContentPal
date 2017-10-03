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
import org.junit.Test;

import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;


/**
 * Test for {@link Joined}.
 *
 * @author Gabor Keszthelyi
 */
public class JoinedTest
{

    @Test
    public void testSingle()
    {
        Operation<?> dummyOperation = dummy(Operation.class);
        assertThat(new Joined(new SingletonBatch(dummyOperation)), contains(new Operation<?>[] { dummyOperation }));
    }


    @Test
    public void testMultiSingle()
    {
        Operation<?> dummyOperation1 = dummy(Operation.class);
        Operation<?> dummyOperation2 = dummy(Operation.class);
        Operation<?> dummyOperation3 = dummy(Operation.class);

        assertThat(new Joined(new SingletonBatch(dummyOperation1), new SingletonBatch(dummyOperation2), new SingletonBatch(dummyOperation3)),
                contains(dummyOperation1, dummyOperation2, dummyOperation3));
    }


    @Test
    public void testMulti()
    {
        Operation<?> dummyOperation1 = dummy(Operation.class);
        Operation<?> dummyOperation2 = dummy(Operation.class);
        Operation<?> dummyOperation3 = dummy(Operation.class);

        assertThat(new Joined(new MultiBatch(dummyOperation1, dummyOperation2, dummyOperation3)),
                contains(dummyOperation1, dummyOperation2, dummyOperation3));
    }


    @Test
    public void testMultiMulti()
    {
        Operation<?> dummyOperation1 = dummy(Operation.class);
        Operation<?> dummyOperation2 = dummy(Operation.class);
        Operation<?> dummyOperation3 = dummy(Operation.class);

        Operation<?> dummyOperationA = dummy(Operation.class);
        Operation<?> dummyOperationB = dummy(Operation.class);
        Operation<?> dummyOperationC = dummy(Operation.class);

        assertThat(new Joined(new MultiBatch(dummyOperation1, dummyOperation2, dummyOperation3),
                        new MultiBatch(dummyOperationA, dummyOperationB, dummyOperationC)),
                contains(dummyOperation1, dummyOperation2, dummyOperation3, dummyOperationA, dummyOperationB, dummyOperationC));
    }

}