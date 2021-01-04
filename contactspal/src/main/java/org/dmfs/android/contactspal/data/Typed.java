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

import androidx.annotation.NonNull;

import org.dmfs.android.contactspal.data.email.EmailData;
import org.dmfs.android.contactspal.data.event.EventData;
import org.dmfs.android.contactspal.data.nickname.NicknameData;
import org.dmfs.android.contactspal.data.organization.OrganizationData;
import org.dmfs.android.contactspal.data.phone.PhoneData;
import org.dmfs.android.contactspal.data.postal.StructuredPostalData;
import org.dmfs.android.contactspal.data.relation.RelationData;
import org.dmfs.android.contactspal.data.sip.SipAddressData;
import org.dmfs.android.contactspal.data.website.WebsiteData;
import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.rowdata.Composite;
import org.dmfs.android.contentpal.rowdata.DelegatingRowData;
import org.dmfs.android.contentpal.rowdata.IntegerRowData;


/**
 * Gives the decorated {@link RowData} a specific type. Works with
 * <ul>
 * <li>{@link EmailData},</li>
 * <li>{@link EventData},</li>
 * <li>{@link NicknameData} and</li>
 * <li>{@link OrganizationData}</li>
 * <li>{@link PhoneData},</li>
 * <li>{@link RelationData} (although not necessary)</li>
 * <li>{@link SipAddressData},</li>
 * <li>{@link StructuredPostalData}</li>
 * <li>{@link WebsiteData}.</li>
 * </ul>
 */
public final class Typed extends DelegatingRowData<ContactsContract.Data>
{
    public Typed(int type, @NonNull RowData<ContactsContract.Data> delegate)
    {
        super(new Composite<>(
            delegate,
            new IntegerRowData<>(ContactsContract.CommonDataKinds.Phone.TYPE, type)));
    }
}
