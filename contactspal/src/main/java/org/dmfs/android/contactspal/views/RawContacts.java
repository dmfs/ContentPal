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

package org.dmfs.android.contactspal.views;

import android.content.ContentProviderClient;
import android.provider.ContactsContract;

import org.dmfs.android.contentpal.views.BaseView;
import org.dmfs.android.contentpal.views.DelegatingView;

import androidx.annotation.NonNull;


/**
 * The ContactsProvider RawContacts view.
 *
 * @author Marten Gajda
 */
public final class RawContacts extends DelegatingView<ContactsContract.RawContacts>
{
    public RawContacts(@NonNull ContentProviderClient client)
    {
        super(new BaseView<>(client, ContactsContract.RawContacts.CONTENT_URI));
    }
}
