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

import android.content.ContentProviderOperation;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import org.dmfs.android.contactspal.data.Typed;
import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.rowdata.Composite;
import org.dmfs.iterables.ArrayIterable;


/**
 * Decorator for work organizations.
 *
 * @author Marten Gajda
 */
public final class WorkOrganization implements OrganizationData
{
    private final RowData<ContactsContract.Data> mDelegate;


    public WorkOrganization()
    {
        this(EmptyOrganizationData.INSTANCE);
    }


    public WorkOrganization(@NonNull OrganizationData... data)
    {
        this(new ArrayIterable<>(data));
    }


    public WorkOrganization(@NonNull Iterable<OrganizationData> data)
    {
        this(new Composite<>(data));
    }


    public WorkOrganization(@NonNull OrganizationData delegate)
    {
        this((RowData<ContactsContract.Data>) delegate);
    }


    private WorkOrganization(@NonNull RowData<ContactsContract.Data> delegate)
    {
        mDelegate = new Typed(ContactsContract.CommonDataKinds.Organization.TYPE_WORK, delegate);
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(@NonNull ContentProviderOperation.Builder builder)
    {
        return mDelegate.updatedBuilder(builder);
    }
}
