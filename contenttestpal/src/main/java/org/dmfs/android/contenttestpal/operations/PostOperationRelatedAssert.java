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

package org.dmfs.android.contenttestpal.operations;

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.operations.Counted;
import org.dmfs.android.contentpal.operations.DelegatingOperation;


/**
 * An assert {@link Operation} that can be used to check for one row in a table <code>T</code> that refers to a row from
 * another table <code>V</code> through the given foreign key column.
 * <p>
 * IMPORTANT: This assert only works if the referred {@link RowSnapshot} had already been committed in a previous transaction.
 *
 * @author Gabor Keszthelyi
 */
public final class PostOperationRelatedAssert<T, V> extends DelegatingOperation<T>
{
    public PostOperationRelatedAssert(Table<T> table, RowSnapshot<V> rowSnapshot, String foreignKeyColumn, RowData<T> rowData, Predicate predicate)
    {
        super(new Counted<T>(1, new PostOperationRelatedBulkAssert<>(table, rowSnapshot, foreignKeyColumn, rowData, predicate)));
    }


    public PostOperationRelatedAssert(Table<T> table, RowSnapshot<V> rowSnapshot, String foreignKeyColumn, RowData<T> rowData)
    {
        super(new Counted<T>(1, new PostOperationRelatedBulkAssert<>(table, rowSnapshot, foreignKeyColumn, rowData)));
    }


    public PostOperationRelatedAssert(Table<T> table, RowSnapshot<V> rowSnapshot, String foreignKeyColumn, Predicate predicate)
    {
        super(new Counted<T>(1, new PostOperationRelatedBulkAssert<>(table, rowSnapshot, foreignKeyColumn, predicate)));
    }


    public PostOperationRelatedAssert(Table<T> table, RowSnapshot<V> rowSnapshot, String foreignKeyColumn)
    {
        super(new Counted<T>(1, new PostOperationRelatedBulkAssert<>(table, rowSnapshot, foreignKeyColumn)));
    }
}
