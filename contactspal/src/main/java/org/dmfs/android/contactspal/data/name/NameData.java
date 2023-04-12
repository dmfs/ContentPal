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

package org.dmfs.android.contactspal.data.name;

import android.provider.ContactsContract;

import org.dmfs.android.contentpal.rowdata.Composite;
import org.dmfs.android.contentpal.rowdata.DelegatingRowData;
import org.dmfs.jems2.iterable.Seq;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * Name data.
 */
public final class NameData extends DelegatingRowData<ContactsContract.Data> implements StructuredNameData
{

    @Deprecated
    public NameData(@NonNull StructuredNameData... nameData)
    {
        this(new Seq<>(nameData));
    }


    public NameData(@Nullable CharSequence firstName, @Nullable CharSequence middleName, @Nullable CharSequence lastName)
    {
        super(new Composite<>(
            new FirstNameData(firstName),
            new MiddleNameData(middleName),
            new LastNameData(lastName)));
    }


    public NameData(@Nullable CharSequence firstName, @Nullable CharSequence lastName)
    {
        super(new Composite<>(
            new FirstNameData(firstName),
            new MiddleNameData(null),
            new LastNameData(lastName)));
    }


    @Deprecated
    public NameData(@Nullable CharSequence firstName, @Nullable CharSequence lastName, @NonNull StructuredNameData delegate)
    {
        super(new Composite<>(
            new FirstNameData(firstName),
            new MiddleNameData(null),
            new LastNameData(lastName),
            delegate));
    }


    @Deprecated
    public NameData(@NonNull Iterable<StructuredNameData> nameData)
    {
        super(new Composite<>(nameData));
    }

}
