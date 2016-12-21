package org.dmfs.android.contentpal.batches;

import org.junit.Test;

import java.util.NoSuchElementException;

import static junit.framework.Assert.assertFalse;


/**
 * @author Marten Gajda
 */
public class EmptyBatchTest
{

    @Test
    public void iterator() throws Exception
    {
        assertFalse(new EmptyBatch().iterator().hasNext());
    }


    @Test(expected = NoSuchElementException.class)
    public void iterator2() throws Exception
    {
        new EmptyBatch().iterator().next();
    }

}