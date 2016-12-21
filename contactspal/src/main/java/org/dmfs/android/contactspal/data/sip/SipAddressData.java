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

package org.dmfs.android.contactspal.data.sip;

import android.content.ContentProviderOperation;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import org.dmfs.android.contactspal.data.Custom;
import org.dmfs.android.contactspal.data.Typed;
import org.dmfs.android.contentpal.RowData;


/**
 * Data of a {@link ContactsContract.CommonDataKinds.SipAddress} row with type {@link ContactsContract.CommonDataKinds.SipAddress#TYPE_OTHER}
 * <p>
 * Use {@link Typed} or {@link Custom} to add a type.
 *
 * @author Marten Gajda
 */
public final class SipAddressData implements RowData<ContactsContract.Data>
{
    private final CharSequence mSipAddress;


    public SipAddressData(@NonNull CharSequence sipAddress)
    {
        mSipAddress = sipAddress;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(@NonNull ContentProviderOperation.Builder builder)
    {
        return builder
                .withValue(ContactsContract.CommonDataKinds.SipAddress.MIMETYPE, ContactsContract.CommonDataKinds.SipAddress.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.SipAddress.SIP_ADDRESS, mSipAddress.toString());
    }
}
