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

package org.dmfs.android.contactspal.data.organization;

import android.provider.ContactsContract;

import org.dmfs.android.contactspal.data.Custom;
import org.dmfs.android.contactspal.data.MimeTypeData;
import org.dmfs.android.contactspal.data.Typed;
import org.dmfs.android.contentpal.rowdata.CharSequenceRowData;
import org.dmfs.android.contentpal.rowdata.Composite;
import org.dmfs.android.contentpal.rowdata.DelegatingRowData;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * Department of an organization.
 * <p>
 * Use {@link Typed} or {@link Custom} to add a type.
 */
public final class DepartmentData extends DelegatingRowData<ContactsContract.Data> implements OrganizationData
{

    public DepartmentData(@Nullable CharSequence department)
    {
        super(new Composite<>(
            new MimeTypeData(ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE),
            new CharSequenceRowData<>(ContactsContract.CommonDataKinds.Organization.DEPARTMENT, department)));
    }


    @Deprecated
    public DepartmentData(@Nullable CharSequence department, @NonNull OrganizationData delegate)
    {
        super(new Composite<>(
            delegate,
            new MimeTypeData(ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE),
            new CharSequenceRowData<>(ContactsContract.CommonDataKinds.Organization.DEPARTMENT, department)));
    }
}
