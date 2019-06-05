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

import android.content.ContentProviderOperation;
import android.provider.ContactsContract;

import org.dmfs.android.contentpal.TransactionContext;

import androidx.annotation.NonNull;


/**
 * {@link StructuredNameData} which doesn't add any values. This has package scope because it's not supposed to be used by developers.
 *
 * @author Marten Gajda
 */
final class EmptyNameData implements StructuredNameData
{
    public final static StructuredNameData INSTANCE = new EmptyNameData();


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(@NonNull TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder)
    {
        // add content item type here, so other name data can just delegate this part
        return builder.withValue(ContactsContract.CommonDataKinds.StructuredName.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
    }
}
