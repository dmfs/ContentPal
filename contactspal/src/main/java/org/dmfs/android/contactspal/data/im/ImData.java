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

package org.dmfs.android.contactspal.data.im;

import android.content.ContentProviderOperation;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import org.dmfs.android.contactspal.data.Custom;
import org.dmfs.android.contactspal.data.Typed;
import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.TransactionContext;


/**
 * Im data.
 * <p>
 * Use {@link Typed} or {@link Custom} to add a type.
 *
 * @author Marten Gajda
 */
public final class ImData implements RowData<ContactsContract.Data>
{
    private final CharSequence mAddress;
    private final int mProtocol;
    private final CharSequence mCustomProtocol;


    public ImData(@NonNull CharSequence address, int protocol)
    {
        this(address, protocol, "");
    }


    public ImData(@NonNull CharSequence address, @NonNull CharSequence customProtocol)
    {
        this(address, ContactsContract.CommonDataKinds.Im.PROTOCOL_CUSTOM, customProtocol);
    }


    private ImData(@NonNull CharSequence address, int protocol, @NonNull CharSequence customProtocol)
    {
        mAddress = address;
        mProtocol = protocol;
        mCustomProtocol = customProtocol;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder)
    {
        return builder
                .withValue(ContactsContract.CommonDataKinds.Im.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Im.DATA, mAddress.toString())
                .withValue(ContactsContract.CommonDataKinds.Im.PROTOCOL, mProtocol)
                .withValue(ContactsContract.CommonDataKinds.Im.CUSTOM_PROTOCOL, mCustomProtocol.toString());
    }
}
