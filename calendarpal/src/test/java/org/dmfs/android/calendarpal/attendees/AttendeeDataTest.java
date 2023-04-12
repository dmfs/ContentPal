/*
 * Copyright 2018 dmfs GmbH
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

package org.dmfs.android.calendarpal.attendees;

import android.provider.CalendarContract;

import org.dmfs.android.contentpal.rowdata.CharSequenceRowData;
import org.dmfs.android.contentpal.rowdata.Composite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withValuesOnly;
import static org.dmfs.android.contentpal.testing.contentvalues.Containing.containing;
import static org.dmfs.android.contentpal.testing.rowdata.RowDataMatcher.builds;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Test {@link AttendeeData}.
 */
@RunWith(RobolectricTestRunner.class)
public class AttendeeDataTest
{
    @Test
    public void testUpdatedBuilder()
    {
        // without any attributes this sets email and defaults
        assertThat(new AttendeeData("xyz"), builds(
            withValuesOnly(
                containing(CalendarContract.Attendees.ATTENDEE_EMAIL, "xyz"),
                containing(CalendarContract.Attendees.ATTENDEE_TYPE, CalendarContract.Attendees.TYPE_NONE),
                containing(CalendarContract.Attendees.ATTENDEE_RELATIONSHIP, CalendarContract.Attendees.RELATIONSHIP_NONE),
                containing(CalendarContract.Attendees.ATTENDEE_STATUS, CalendarContract.Attendees.ATTENDEE_STATUS_NONE)
            )));
    }


    @Test
    public void testUpdatedBuilderAttributes()
    {
        // attributes should be added too
        assertThat(new AttendeeData("xyz", new CharSequenceRowData<>("test", "value")), builds(
            withValuesOnly(
                containing(CalendarContract.Attendees.ATTENDEE_EMAIL, "xyz"),
                containing(CalendarContract.Attendees.ATTENDEE_TYPE, CalendarContract.Attendees.TYPE_NONE),
                containing(CalendarContract.Attendees.ATTENDEE_RELATIONSHIP, CalendarContract.Attendees.RELATIONSHIP_NONE),
                containing(CalendarContract.Attendees.ATTENDEE_STATUS, CalendarContract.Attendees.ATTENDEE_STATUS_NONE),
                containing("test", "value")
            )));
    }


    @Test
    public void testUpdatedBuilderOverrides()
    {
        // overrides should be kept
        assertThat(new AttendeeData("xyz", new Composite<>(
                new CharSequenceRowData<>(CalendarContract.Attendees.ATTENDEE_TYPE, "type"),
                new CharSequenceRowData<>(CalendarContract.Attendees.ATTENDEE_RELATIONSHIP, "rel"),
                new CharSequenceRowData<>(CalendarContract.Attendees.ATTENDEE_STATUS, "stat"),
                new CharSequenceRowData<>("test", "value")
            )),
            builds(
                withValuesOnly(
                    containing(CalendarContract.Attendees.ATTENDEE_EMAIL, "xyz"),
                    containing(CalendarContract.Attendees.ATTENDEE_TYPE, "type"),
                    containing(CalendarContract.Attendees.ATTENDEE_RELATIONSHIP, "rel"),
                    containing(CalendarContract.Attendees.ATTENDEE_STATUS, "stat"),
                    containing("test", "value")
                )));
    }

}