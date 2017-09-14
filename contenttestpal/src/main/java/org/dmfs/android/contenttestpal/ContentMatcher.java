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

package org.dmfs.android.contenttestpal;

import android.content.ContentProvider;
import android.content.ContentProviderClient;
import android.content.OperationApplicationException;

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.OperationsBatch;
import org.dmfs.android.contentpal.OperationsQueue;
import org.dmfs.android.contentpal.batches.MultiBatch;
import org.dmfs.android.contentpal.queues.BasicOperationsQueue;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;


/**
 * {@link Matcher} that checks whether executing the target {@link OperationsBatch} results in a state of the {@link ContentProvider}
 * that corresponds to the given assert {@link OperationsBatch}s, i.e. they can be executed successfully.
 * <p>
 * If there is mismatch, this {@link Matcher} throws an {@link AssertionError} rather than returning <code>false</code>.
 *
 * @author Gabor Keszthelyi
 */
public final class ContentMatcher extends TypeSafeMatcher<OperationsBatch>
{
    private final OperationsQueue mOperationsQueue;
    private final OperationsBatch mAssertOperationBatch;


    public ContentMatcher(OperationsQueue operationsQueue, OperationsBatch assertOperationBatch)
    {
        mOperationsQueue = operationsQueue;
        mAssertOperationBatch = assertOperationBatch;
    }


    @Override
    protected boolean matchesSafely(OperationsBatch targetBatch)
    {
        try
        {
            mOperationsQueue.enqueue(targetBatch);
            mOperationsQueue.flush();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Exception during executing the target OperationBatch", e);
        }

        try
        {
            mOperationsQueue.enqueue(mAssertOperationBatch);
            mOperationsQueue.flush();
        }
        catch (OperationApplicationException e)
        {
            throw new AssertionError(e);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Exception during executing the assert OperationBatch", e);
        }
        return true;
    }


    @Override
    public void describeTo(Description description)
    {
        throw new UnsupportedOperationException("Should not be called for this class as it fails with exception");
    }


    public static Matcher<OperationsBatch> resultsIn(OperationsQueue queue, OperationsBatch assertingBatch)
    {
        return new ContentMatcher(queue, assertingBatch);
    }


    @SafeVarargs
    public static Matcher<OperationsBatch> resultsIn(OperationsQueue queue, Operation... assertOperations)
    {
        return new ContentMatcher(queue, new MultiBatch(assertOperations));
    }


    @SafeVarargs
    public static Matcher<OperationsBatch> resultsIn(ContentProviderClient client, Operation... assertOperations)
    {
        return new ContentMatcher(new BasicOperationsQueue(client), new MultiBatch(assertOperations));
    }
}
