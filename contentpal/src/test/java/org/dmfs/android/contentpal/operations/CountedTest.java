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
import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.optional.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class CountedTest
{
    @Test
    public void testReference() throws Exception
    {
        final Optional<SoftRowReference<Object>> testReference = mock(Optional.class);

        assertThat(
                new Counted<>(1,
                        new Operation<Object>()
                        {
                            @NonNull
                            @Override
                            public Optional<SoftRowReference<Object>> reference()
                            {
                                return testReference;
                            }


                            @NonNull
                            @Override
                            public ContentProviderOperation.Builder contentOperationBuilder(@NonNull TransactionContext transactionContext) throws UnsupportedOperationException
                            {
                                fail("wrong method called");
                                return null;
                            }
                        }).reference(),
                sameInstance(testReference));
    }


    @Test
    public void testContentOperationBuilder() throws Exception
    {
        final ContentProviderOperation.Builder testBuilder = mock(ContentProviderOperation.Builder.class);
        when(testBuilder.withExpectedCount(10)).thenReturn(testBuilder);
        final TransactionContext testContext = mock(TransactionContext.class);

        assertThat(
                new Counted<>(
                        10,
                        new Operation<Object>()
                        {
                            @NonNull
                            @Override
                            public Optional<SoftRowReference<Object>> reference()
                            {
                                fail("wrong method called");
                                return null;
                            }


                            @NonNull
                            @Override
                            public ContentProviderOperation.Builder contentOperationBuilder(@NonNull TransactionContext transactionContext) throws UnsupportedOperationException
                            {
                                assertThat(transactionContext, sameInstance(testContext));
                                return testBuilder;
                            }
                        }).contentOperationBuilder(testContext),
                sameInstance(testBuilder));
    }
}