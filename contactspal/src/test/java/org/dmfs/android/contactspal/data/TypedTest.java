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
import org.dmfs.android.contentpal.rowdata.IntegerRowData;
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
public class TypedTest
{
    @Test
    public void test()
    {
        assertThat(
            new Typed(123, new CharSequenceRowData<>("x", "y")),
            builds(
                withValuesOnly(
                    containing(ContactsContract.CommonDataKinds.Email.TYPE, 123),
                    containing("x", "y"))));

        assertThat(
            new Typed(123, new IntegerRowData<>(ContactsContract.CommonDataKinds.Email.TYPE, 124)),
            builds(
                withValuesOnly(
                    containing(ContactsContract.CommonDataKinds.Email.TYPE, 123))));
    }
}