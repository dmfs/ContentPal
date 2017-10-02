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

package org.dmfs.android.contentpal.views;

import android.accounts.Account;
import android.database.Cursor;
import android.os.RemoteException;
import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.UriParams;
import org.dmfs.android.contentpal.View;
import org.dmfs.android.contentpal.predicates.AccountEq;
import org.dmfs.android.contentpal.predicates.AllOf;
import org.dmfs.android.contentpal.tools.uriparams.AccountScopedParams;
import org.dmfs.optional.Optional;


/**
 * A {@link View} filtered by account. This only works for view that support the {@code account_name} and {@code account_type} query parameters.
 *
 * @param <T>
 *         The contract of this view.
 *
 * @author Marten Gajda
 */
public final class AccountScoped<T> implements View<T>
{
    private final View<T> mDelegate;
    private final Account mAccount;


    public AccountScoped(@NonNull Account account, @NonNull View<T> delegate)
    {
        mDelegate = delegate;
        mAccount = account;
    }


    @NonNull
    @Override
    public Cursor rows(@NonNull UriParams uriParams, @NonNull Predicate predicate, @NonNull Optional<String> sorting) throws RemoteException
    {
        return mDelegate.rows(new AccountScopedParams(mAccount, uriParams), new AllOf(predicate, new AccountEq(mAccount)), sorting);
    }


    @NonNull
    @Override
    public Table<T> table()
    {
        return new org.dmfs.android.contentpal.tables.AccountScoped<>(mAccount, mDelegate.table());
    }


    @NonNull
    @Override
    public View<T> withProjection(@NonNull String... projection)
    {
        return new AccountScoped<>(mAccount, mDelegate.withProjection(projection));
    }
}
