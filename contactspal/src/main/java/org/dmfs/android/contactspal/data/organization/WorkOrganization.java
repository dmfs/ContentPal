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

import org.dmfs.android.contactspal.data.Typed;
import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.rowdata.Composite;
import org.dmfs.android.contentpal.rowdata.DelegatingRowData;
import org.dmfs.iterables.SingletonIterable;
import org.dmfs.jems.iterable.composite.Joined;
import org.dmfs.jems.iterable.elementary.Seq;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * A Work organization.
 */
public final class WorkOrganization extends DelegatingRowData<ContactsContract.Data> implements OrganizationData
{
    @Deprecated
    public WorkOrganization()
    {
        this(EmptyOrganizationData.INSTANCE);
    }


    public WorkOrganization(@Nullable String company)
    {
        this(new SingletonIterable<>(new CompanyData(company)));
    }


    public WorkOrganization(
        @Nullable String company,
        @Nullable String department)
    {
        this(new Seq<>(
            new CompanyData(company),
            new DepartmentData(department)));
    }


    @Deprecated
    public WorkOrganization(@NonNull OrganizationData... data)
    {
        this(new Seq<>(data));
    }


    public WorkOrganization(@NonNull Iterable<? extends RowData<ContactsContract.Data>> data)
    {
        this(new Composite<>(
            new Joined<>(
                new Seq<>(
                    new CompanyData(null),
                    new DepartmentData(null),
                    new JobData(null),
                    new OfficeLocationData(null),
                    new SymbolData(null),
                    new PhoneticNameData(null),
                    new TitleData(null)),
                data)));
    }


    @Deprecated
    public WorkOrganization(@NonNull OrganizationData delegate)
    {
        this((RowData<ContactsContract.Data>) delegate);
    }


    private WorkOrganization(@NonNull RowData<ContactsContract.Data> delegate)
    {
        super(new Typed(ContactsContract.CommonDataKinds.Organization.TYPE_WORK, delegate));
    }
}
