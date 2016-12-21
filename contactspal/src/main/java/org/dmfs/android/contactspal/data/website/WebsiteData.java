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

package org.dmfs.android.contactspal.data.website;

import android.content.ContentProviderOperation;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import org.dmfs.android.contactspal.data.Custom;
import org.dmfs.android.contactspal.data.Typed;
import org.dmfs.android.contentpal.RowData;


/**
 * Data of a {@link ContactsContract.CommonDataKinds.Website} row with type {@link ContactsContract.CommonDataKinds.Website#TYPE_OTHER}.
 * <p>
 * Use {@link Typed} or {@link Custom} to add a type.
 *
 * @author Marten Gajda
 */
public final class WebsiteData implements RowData<ContactsContract.Data>
{
    private final CharSequence mWebSite;


    public WebsiteData(@NonNull CharSequence webSite)
    {
        mWebSite = webSite;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(@NonNull ContentProviderOperation.Builder builder)
    {
        return builder
                .withValue(ContactsContract.CommonDataKinds.Website.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Website.URL, mWebSite.toString());
    }
}
