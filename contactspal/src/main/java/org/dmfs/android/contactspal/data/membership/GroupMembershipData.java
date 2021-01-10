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

package org.dmfs.android.contactspal.data.membership;

import android.provider.ContactsContract;

import org.dmfs.android.contactspal.data.MimeTypeData;
import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.rowdata.CharSequenceRowData;
import org.dmfs.android.contentpal.rowdata.Composite;
import org.dmfs.android.contentpal.rowdata.DelegatingRowData;
import org.dmfs.android.contentpal.rowdata.Referring;

import androidx.annotation.NonNull;


/**
 * {@link GroupMembershipData} {@link RowData}. Sets a group membership, either via {@link ContactsContract.CommonDataKinds.GroupMembership#GROUP_SOURCE_ID} or
 * {@link ContactsContract.CommonDataKinds.GroupMembership#GROUP_ROW_ID}
 */
public final class GroupMembershipData extends DelegatingRowData<ContactsContract.Data>
{

    public GroupMembershipData(@NonNull CharSequence groupSourceId)
    {
        this(new CharSequenceRowData<>(ContactsContract.CommonDataKinds.GroupMembership.GROUP_SOURCE_ID, groupSourceId));
    }


    public GroupMembershipData(@NonNull RowSnapshot<ContactsContract.Groups> group)
    {
        this(new Referring<>(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID, group));
    }


    private GroupMembershipData(@NonNull RowData<ContactsContract.Data> reference)
    {
        super(new Composite<>(
            new MimeTypeData(ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE),
            reference));
    }
}
