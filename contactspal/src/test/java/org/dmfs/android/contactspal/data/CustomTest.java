/*
 * Copyright 2021 dmfs GmbH
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

package org.dmfs.android.contactspal.data;

import android.provider.ContactsContract;

import org.dmfs.android.contentpal.rowdata.CharSequenceRowData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withValuesOnly;
import static org.dmfs.android.contentpal.testing.contentvalues.Containing.containing;
import static org.dmfs.android.contentpal.testing.rowdata.RowDataMatcher.builds;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class CustomTest
{
    @Test
    public void test()
    {
        assertThat(
            new Custom("abc", new CharSequenceRowData<>("x", "y")),
            builds(
                withValuesOnly(
                    containing(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_CUSTOM),
                    containing(ContactsContract.CommonDataKinds.Email.LABEL, "abc"),
                    containing("x", "y"))));

        assertThat(
            new Custom("abc", new CharSequenceRowData<>(ContactsContract.CommonDataKinds.Email.LABEL, "xyz")),
            builds(
                withValuesOnly(
                    containing(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_CUSTOM),
                    containing(ContactsContract.CommonDataKinds.Email.LABEL, "abc"))));
    }
}