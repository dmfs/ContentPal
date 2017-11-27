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
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public class DataProjectionTest
{
    @Test
    public void test() throws Exception
    {
        assertThat(new DataProjection(),
                projects(
                        ContactsContract.Data._ID,
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.Data.RAW_CONTACT_ID,
                        ContactsContract.Data.IS_PRIMARY,
                        ContactsContract.Data.IS_SUPER_PRIMARY,
                        ContactsContract.Data.DATA_VERSION,
                        ContactsContract.Data.IS_READ_ONLY,
                        ContactsContract.Data.DATA1,
                        ContactsContract.Data.DATA2,
                        ContactsContract.Data.DATA3,
                        ContactsContract.Data.DATA4,
                        ContactsContract.Data.DATA5,
                        ContactsContract.Data.DATA6,
                        ContactsContract.Data.DATA7,
                        ContactsContract.Data.DATA8,
                        ContactsContract.Data.DATA9,
                        ContactsContract.Data.DATA10,
                        ContactsContract.Data.DATA11,
                        ContactsContract.Data.DATA12,
                        ContactsContract.Data.DATA13,
                        ContactsContract.Data.DATA14,
                        ContactsContract.Data.DATA15,
                        ContactsContract.Data.SYNC1,
                        ContactsContract.Data.SYNC2,
                        ContactsContract.Data.SYNC3,
                        ContactsContract.Data.SYNC4));
    }

}