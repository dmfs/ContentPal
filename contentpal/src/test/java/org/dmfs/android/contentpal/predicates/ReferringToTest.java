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

package org.dmfs.android.contentpal.predicates;

import android.net.Uri;

import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.operations.FailAnswer;
import org.dmfs.android.contentpal.predicates.utils.BackReferences;
import org.dmfs.android.contentpal.predicates.utils.Values;
import org.dmfs.android.contentpal.references.BackReference;
import org.dmfs.optional.iterable.PresentValues;
import org.junit.Test;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


/**
 * @author Marten Gajda
 */
public class ReferringToTest
{
    @Test
    public void testSelection() throws Exception
    {
        TransactionContext transactionContext = mock(TransactionContext.class, new FailAnswer());
        SoftRowReference<Object> rowReference = mock(SoftRowReference.class, new FailAnswer());
        RowSnapshot<Object> rowSnapshot = mock(RowSnapshot.class, new FailAnswer());

        // let the RowSnapshot return the mock RowReference
        doReturn(rowReference).when(rowSnapshot).reference();

        // let the TransactionContext resolve the RowReference to a BackReference with index 10
        doReturn(new BackReference<>(mock(Uri.class, new FailAnswer()), 10)).when(transactionContext).resolved(rowReference);

        assertThat(new ReferringTo<>("x", rowSnapshot).selection(transactionContext).toString(), is("x = ?"));
    }


    @Test
    public void testArguments() throws Exception
    {
        TransactionContext transactionContext = mock(TransactionContext.class, new FailAnswer());
        SoftRowReference<Object> rowReference = mock(SoftRowReference.class, new FailAnswer());
        RowSnapshot<Object> rowSnapshot = mock(RowSnapshot.class, new FailAnswer());

        // let the RowSnapshot return the mock RowReference
        doReturn(rowReference).when(rowSnapshot).reference();

        // let the TransactionContext resolve the RowReference to a BackReference with index 10
        doReturn(new BackReference<>(mock(Uri.class, new FailAnswer()), 10)).when(transactionContext).resolved(rowReference);

        assertThat(new Values(new ReferringTo<>("x", rowSnapshot).arguments(transactionContext)), contains("-1"));
        assertThat(new PresentValues<>(new BackReferences(new ReferringTo<>("x", rowSnapshot).arguments(transactionContext))), contains(10));
    }
}