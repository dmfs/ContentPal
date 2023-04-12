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

package org.dmfs.android.contactspal.projections;

import android.provider.ContactsContract;

import org.junit.Test;

import static org.dmfs.android.contentpal.testing.projection.ProjectionMatcher.projects;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * @author Marten Gajda
 */
public class RawContactsProjectionTest
{
    @Test
    public void test()
    {
        assertThat(new RawContactsProjection(),
            projects(
                ContactsContract.RawContacts._ID,
                ContactsContract.RawContacts.CONTACT_ID,
                ContactsContract.RawContacts.AGGREGATION_MODE,
                ContactsContract.RawContacts.DELETED,
                ContactsContract.RawContacts.DISPLAY_NAME_SOURCE,
                ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY,
                ContactsContract.RawContacts.DISPLAY_NAME_ALTERNATIVE,
                ContactsContract.RawContacts.PHONETIC_NAME_STYLE,
                ContactsContract.RawContacts.PHONETIC_NAME,
                ContactsContract.RawContacts.SORT_KEY_PRIMARY,
                ContactsContract.RawContacts.SORT_KEY_ALTERNATIVE,
                ContactsContract.RawContacts.ACCOUNT_NAME,
                ContactsContract.RawContacts.ACCOUNT_TYPE,
                ContactsContract.RawContacts.DIRTY,
                ContactsContract.RawContacts.SOURCE_ID,
                ContactsContract.RawContacts.VERSION));
    }

}