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

import org.dmfs.android.contactspal.data.Custom;
import org.dmfs.android.contactspal.data.Typed;
import org.dmfs.android.contentpal.TransactionContext;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * Organization Symbol. Use it standalone or as decorator to other {@link OrganizationData}.
 * <p>
 * Use {@link Typed} or {@link Custom} to add a type.
 *
 * @author Marten Gajda
 */
public final class SymbolData implements OrganizationData
{
    private final OrganizationData mDelegate;
    private final CharSequence mSymbol;


    public SymbolData(@Nullable CharSequence symbol)
    {
        this(symbol, EmptyOrganizationData.INSTANCE);
    }


    public SymbolData(@Nullable CharSequence symbol, @NonNull OrganizationData delegate)
    {
        mDelegate = delegate;
        mSymbol = symbol;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(@NonNull TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder)
    {
        return mDelegate.updatedBuilder(transactionContext, builder)
                .withValue(ContactsContract.CommonDataKinds.Organization.SYMBOL, mSymbol == null ? null : mSymbol.toString());
    }
}
