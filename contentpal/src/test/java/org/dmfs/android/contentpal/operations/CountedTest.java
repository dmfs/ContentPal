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

package org.dmfs.android.contentpal.operations;

import android.content.ContentProviderOperation;

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.optional.Optional;
import org.junit.Test;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


/**
 * @author Marten Gajda
 */
public class CountedTest
{
    @Test
    public void testReference() throws Exception
    {
        final Optional<SoftRowReference<Object>> testReference = mock(Optional.class, new FailAnswer());
        Operation<Object> mockOperation = mock(Operation.class, new FailAnswer());
        doReturn(testReference).when(mockOperation).reference();

        assertThat(new Counted<>(1, mockOperation).reference(), sameInstance(testReference));
    }


    @Test
    public void testContentOperationBuilder() throws Exception
    {
        final ContentProviderOperation.Builder testBuilder = mock(ContentProviderOperation.Builder.class, new FailAnswer());
        doReturn(testBuilder).when(testBuilder).withExpectedCount(10);

        final TransactionContext testContext = mock(TransactionContext.class);

        Operation<?> mockOperation = mock(Operation.class, new FailAnswer());
        doReturn(testBuilder).when(mockOperation).contentOperationBuilder(testContext);

        assertThat(new Counted<>(10, mockOperation).contentOperationBuilder(testContext), sameInstance(testBuilder));
    }
}