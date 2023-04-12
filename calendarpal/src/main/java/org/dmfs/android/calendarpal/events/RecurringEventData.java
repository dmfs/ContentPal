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

package org.dmfs.android.calendarpal.events;

import android.content.ContentProviderOperation;
import android.provider.CalendarContract;
import android.text.TextUtils;

import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.jems2.Function;
import org.dmfs.jems2.iterable.Just;
import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.Duration;
import org.dmfs.rfc5545.recur.RecurrenceRule;

import androidx.annotation.NonNull;

import static org.dmfs.jems2.iterable.EmptyIterable.emptyIterable;


/**
 * The base {@link RowData} of a recurring event.
 *
 * @author Marten Gajda
 */
public final class RecurringEventData implements RowData<CalendarContract.Events>
{
    private final DateTime mStart;
    private final Duration mDuration;
    private final CharSequence mTitle;
    private final Iterable<RecurrenceRule> mRules;
    private final Iterable<DateTime> mExDates;
    private final Iterable<DateTime> mRDates;

    private final Function<DateTime, DateTime> mUtcDate = argument -> argument.shiftTimeZone(DateTime.UTC);


    public RecurringEventData(@NonNull CharSequence title, @NonNull DateTime start, @NonNull Duration duration, @NonNull RecurrenceRule rule)
    {
        this(title, start, duration, new Just<>(rule), emptyIterable(), emptyIterable());
    }


    public RecurringEventData(
        @NonNull CharSequence title,
        @NonNull DateTime start,
        @NonNull Duration duration,
        @NonNull RecurrenceRule rule,
        @NonNull Iterable<DateTime> exDates)
    {
        this(title, start, duration, new Just<>(rule), exDates, emptyIterable());
    }


    public RecurringEventData(
        @NonNull CharSequence title,
        @NonNull DateTime start,
        @NonNull Duration duration,
        @NonNull RecurrenceRule rule,
        @NonNull Iterable<DateTime> exDates,
        @NonNull Iterable<DateTime> rdates)
    {
        this(title, start, duration, new Just<>(rule), exDates, rdates);
    }


    public RecurringEventData(@NonNull CharSequence title, @NonNull DateTime start, @NonNull Duration duration, @NonNull Iterable<RecurrenceRule> rules)
    {
        this(title, start, duration, rules, emptyIterable(), emptyIterable());
    }


    public RecurringEventData(
        @NonNull CharSequence title,
        @NonNull DateTime start,
        @NonNull Duration duration,
        @NonNull Iterable<RecurrenceRule> rules,
        @NonNull Iterable<DateTime> exDates)
    {
        this(title, start, duration, rules, exDates, emptyIterable());
    }


    public RecurringEventData(
        @NonNull CharSequence title,
        @NonNull DateTime start,
        @NonNull Duration duration,
        @NonNull Iterable<RecurrenceRule> rules,
        @NonNull Iterable<DateTime> exDates,
        @NonNull Iterable<DateTime> rdates)
    {
        mTitle = title;
        mStart = start;
        mDuration = duration;
        mRules = rules;
        mExDates = exDates;
        mRDates = rdates;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(@NonNull TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder)
    {
        return builder.withValue(CalendarContract.Events.DTSTART, mStart.getTimestamp())
            .withValue(CalendarContract.Events.EVENT_TIMEZONE, mStart.isAllDay() ? "UTC" : mStart.getTimeZone().getID())
            .withValue(CalendarContract.Events.ALL_DAY, mStart.isAllDay() ? 1 : 0)
            .withValue(CalendarContract.Events.DURATION, mDuration.toString())
            .withValue(CalendarContract.Events.TITLE, mTitle.toString())
            .withValue(CalendarContract.Events.RRULE, TextUtils.join("\n", mRules))
            .withValue(CalendarContract.Events.RDATE, TextUtils.join(",", new Mapped<>(mUtcDate, mRDates)))
            .withValue(CalendarContract.Events.EXDATE, TextUtils.join(",", new Mapped<>(mUtcDate, mExDates)))
            // explicitly (re-)set DTEND to null
            .withValue(CalendarContract.Events.DTEND, null)
            .withValue(CalendarContract.Events.EVENT_END_TIMEZONE, null);
    }
}
