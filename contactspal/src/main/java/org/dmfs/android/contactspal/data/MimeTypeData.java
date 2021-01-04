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

package org.dmfs.android.contactspal.data;

import android.provider.ContactsContract;

import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.rowdata.CharSequenceRowData;
import org.dmfs.android.contentpal.rowdata.DelegatingRowData;

import androidx.annotation.NonNull;


/**
 * {@link RowData} setting the {@link ContactsContract.Data#MIMETYPE} of a {@link ContactsContract.Data} row.
 */
public final class MimeTypeData extends DelegatingRowData<ContactsContract.Data>
{
    public MimeTypeData(@NonNull CharSequence mimeType)
    {
        super(new CharSequenceRowData<>(ContactsContract.Data.MIMETYPE, mimeType));
    }
}
