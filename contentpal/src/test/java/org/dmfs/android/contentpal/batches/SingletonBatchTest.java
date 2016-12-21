package org.dmfs.android.contentpal.batches;

import org.dmfs.android.contentpal.Operation;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;


/**
 * @author Marten Gajda
 */
public class SingletonBatchTest
{

    @Test
    public void test() throws Exception
    {
        Operation op = mock(Operation.class);
        assertThat(new SingletonBatch(op), Matchers.<Operation<?>>contains(op));
    }
}