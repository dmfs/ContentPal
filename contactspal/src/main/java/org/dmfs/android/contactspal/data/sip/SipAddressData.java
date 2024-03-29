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

import android.provider.ContactsContract;

import org.dmfs.android.contactspal.data.Custom;
import org.dmfs.android.contactspal.data.MimeTypeData;
import org.dmfs.android.contactspal.data.Typed;
import org.dmfs.android.contentpal.rowdata.CharSequenceRowData;
import org.dmfs.android.contentpal.rowdata.Composite;
import org.dmfs.android.contentpal.rowdata.DelegatingRowData;

import androidx.annotation.NonNull;


/**
 * Data of a {@link ContactsContract.CommonDataKinds.SipAddress} row with type {@link ContactsContract.CommonDataKinds.SipAddress#TYPE_OTHER}
 * <p>
 * Use {@link Typed} or {@link Custom} to add a type.
 */
public final class SipAddressData extends DelegatingRowData<ContactsContract.Data>
{
    public SipAddressData(@NonNull CharSequence sipAddress)
    {
        super(new Typed(ContactsContract.CommonDataKinds.SipAddress.TYPE_OTHER,
            new Composite<>(
                new MimeTypeData(ContactsContract.CommonDataKinds.SipAddress.CONTENT_ITEM_TYPE),
                new CharSequenceRowData<>(ContactsContract.CommonDataKinds.SipAddress.SIP_ADDRESS, sipAddress))));
    }
}
