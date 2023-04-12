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

import org.junit.Test;

import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.absentBackReferences;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.argumentValues;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.predicateWith;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.selection;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


/**
 * Test {@link TransientRawContact}
 *
 * @author Marten Gajda
 */
public class TransientRawContactTest
{
    @Test
    public void test()
    {
        assertThat(new TransientRawContact(),
            is(predicateWith(
                selection(String.format("( %s is null ) and ( %s = ? )", ContactsContract.RawContacts.SOURCE_ID, ContactsContract.RawContacts.DELETED)),
                argumentValues("1"),
                absentBackReferences(1))));
    }
}