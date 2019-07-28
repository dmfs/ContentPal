/*
 * Copyright 2019 dmfs GmbH
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

package org.dmfs.android.contactspal.predicates;

import android.provider.ContactsContract;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.predicates.AllOf;
import org.dmfs.android.contentpal.predicates.DelegatingPredicate;
import org.dmfs.android.contentpal.predicates.EqArg;
import org.dmfs.android.contentpal.predicates.IsNull;


/**
 * A {@link Predicate} which is satisfied by transient raw contacts, i.e. raw contacts which were deleted before they got synced.
 *
 * @author Marten Gajda
 */
public final class TransientRawContact extends DelegatingPredicate<ContactsContract.RawContacts>
{
    public TransientRawContact()
    {
        super(new AllOf<>(
                new IsNull<>(ContactsContract.RawContacts.SOURCE_ID),
                new EqArg<>(ContactsContract.RawContacts.DELETED, 1)));
    }
}
