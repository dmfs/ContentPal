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

import android.net.Uri;

import org.dmfs.android.contentpal.InsertOperation;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.operations.internal.RawInsert;
import org.dmfs.android.contentpal.rowdata.CharSequenceRowData;
import org.dmfs.android.contentpal.testing.answers.FailAnswer;
import org.dmfs.optional.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.android.contentpal.testing.contentoperationbuilder.OperationType.insertOperation;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithExpectedCount.withoutExpectedCount;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withValuesOnly;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithYieldAllowed.withYieldNotAllowed;
import static org.dmfs.android.contentpal.testing.contentvalues.Containing.containing;
import static org.dmfs.android.contentpal.testing.operations.OperationMatcher.builds;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


/**
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class PopulatedTest
{
    @Test
    public void testReference() throws Exception
    {
        Optional<SoftRowReference<Object>> reference = mock(Optional.class, new FailAnswer());
        InsertOperation<Object> operation = mock(InsertOperation.class, new FailAnswer());

        doReturn(reference).when(operation).reference();

        assertThat(new Populated<>(new CharSequenceRowData<>("a", "x"), operation).reference(), sameInstance(reference));
    }


    @Test
    public void testContentOperationBuilder() throws Exception
    {
        assertThat(
                new Populated<>(new CharSequenceRowData<>("a", "x"), new RawInsert<>(Uri.EMPTY)),
                builds(
                        insertOperation(),
                        withYieldNotAllowed(),
                        withoutExpectedCount(),
                        withValuesOnly(
                                containing("a", "x"))));
    }
}