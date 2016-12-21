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

package org.dmfs.contentpal.demo;

import android.Manifest;
import android.content.ContentProviderClient;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.dmfs.android.contactspal.data.Custom;
import org.dmfs.android.contactspal.data.Primary;
import org.dmfs.android.contactspal.data.Typed;
import org.dmfs.android.contactspal.data.email.EmailData;
import org.dmfs.android.contactspal.data.event.EventData;
import org.dmfs.android.contactspal.data.im.ImData;
import org.dmfs.android.contactspal.data.name.DisplayNameData;
import org.dmfs.android.contactspal.data.nickname.NicknameData;
import org.dmfs.android.contactspal.data.note.NoteData;
import org.dmfs.android.contactspal.data.organization.CompanyData;
import org.dmfs.android.contactspal.data.organization.DepartmentData;
import org.dmfs.android.contactspal.data.organization.JobData;
import org.dmfs.android.contactspal.data.organization.SymbolData;
import org.dmfs.android.contactspal.data.organization.WorkOrganization;
import org.dmfs.android.contactspal.data.phone.PhoneData;
import org.dmfs.android.contactspal.data.postal.CityData;
import org.dmfs.android.contactspal.data.postal.CountryData;
import org.dmfs.android.contactspal.data.postal.PostcodeData;
import org.dmfs.android.contactspal.data.postal.StreetData;
import org.dmfs.android.contactspal.data.postal.WorkPostal;
import org.dmfs.android.contactspal.data.relation.RelationData;
import org.dmfs.android.contactspal.data.sip.SipAddressData;
import org.dmfs.android.contactspal.operations.RawContactData;
import org.dmfs.android.contactspal.operations.TransientRawContactCleanup;
import org.dmfs.android.contactspal.rowsets.Unsynced;
import org.dmfs.android.contactspal.tables.Local;
import org.dmfs.android.contactspal.tables.RawContacts;
import org.dmfs.android.contentpal.OperationsQueue;
import org.dmfs.android.contentpal.RowDataSnapshot;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.batches.Joined;
import org.dmfs.android.contentpal.batches.MultiInsertBatch;
import org.dmfs.android.contentpal.batches.SingletonBatch;
import org.dmfs.android.contentpal.operations.Put;
import org.dmfs.android.contentpal.queues.BasicOperationsQueue;
import org.dmfs.android.contentpal.rowsnapshots.VirtualRowSnapshot;
import org.dmfs.iterables.ArrayIterable;


public class DemoActivity extends AppCompatActivity
{

    private ContentProviderClient mContactsClient;
    private Table<ContactsContract.RawContacts> mRawContacts;
    private OperationsQueue mContactsQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRawContacts = new Local(new RawContacts());
    }


    @Override
    protected void onDestroy()
    {
        if (mContactsClient != null)
        {
            mContactsClient.release();
        }
        super.onDestroy();
    }


    /**
     * Demonstrates how a single contact can be inserted.
     *
     * @param view
     *
     * @throws RemoteException
     * @throws OperationApplicationException
     */
    public void insertJohnDoe(View view) throws RemoteException, OperationApplicationException
    {
        if (!ensureContactPermissions())
        {
            return;
        }

        // create a "virtual" row snapshot of the new RawContact row
        RowSnapshot<ContactsContract.RawContacts> rawContact = new VirtualRowSnapshot<>(mRawContacts);

        mContactsQueue.enqueue(
                new Joined(
                        // insert the virtual RawContact row first
                        new SingletonBatch(
                                new Put<>(rawContact)),
                        // insert a couple of data rows that are based on the contactData prototype
                        new MultiInsertBatch<>(
                                // use a prototype which creates data rows for this rawContact
                                new RawContactData(rawContact),
                                new DisplayNameData("John Doe"),
                                new Primary(new Typed(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE, new PhoneData("123"))),
                                new Custom("personal", new EmailData("john@example.com")))));

        mContactsQueue.flush();

        // add some more data to this contact, but in a new transaction to demonstrate how row references work across transactions
        mContactsQueue.enqueue(
                new MultiInsertBatch<>(
                        // use a prototype which creates data rows for this rawContact, we could even use the same prototype as above
                        new RawContactData(rawContact),
                        new ArrayIterable<>(
                                new NoteData("A note"),
                                new RelationData(ContactsContract.CommonDataKinds.Relation.TYPE_SISTER, "Jane Doe"),
                                new NicknameData("Johnny"),
                                new Custom("someLabel", new ImData("abc", "custom")),
                                new Typed(ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY, new EventData("2017-04-02")),
                                new Custom("weekend", new SipAddressData("12345")),
                                new WorkOrganization(
                                        new JobData("Developer"),
                                        new CompanyData("dmfs GmbH"),
                                        new SymbolData("dmfs"),
                                        new DepartmentData("Android development")),

                                // the following two approaches result in the same data
//                                new WorkPostal(
//                                        new StreetData("Schandauer Straße 34",
//                                                new PostcodeData("01309",
//                                                        new CityData("Dresden",
//                                                                new CountryData("Germany"))))),
                                new WorkPostal(
                                        new StreetData("Schandauer Straße 34"),
                                        new PostcodeData("01309"),
                                        new CityData("Dresden"),
                                        new CountryData("Germany")))));
        mContactsQueue.flush();
    }


    public void logUnsyncedRawContacts(View view)
    {
        if (!ensureContactPermissions())
        {
            return;
        }

        for (RowSnapshot<ContactsContract.RawContacts> row : new Unsynced(mRawContacts.view(mContactsClient)))
        {
            Log.v("ContentPal Demo", "---- Raw Contact ----");
            RowDataSnapshot<?> values = row.values();
            for (String key : values)
            {
                Log.v("ContentPal Demo", String.format("%s: %s", key, values.charData(key).value("")));
            }
        }

    }


    public void wipeTransientRawContacts(View view) throws RemoteException, OperationApplicationException
    {
        if (!ensureContactPermissions())
        {
            return;
        }

        mContactsQueue.enqueue(new SingletonBatch(new TransientRawContactCleanup(mRawContacts)));
        mContactsQueue.flush();
    }


    private boolean ensureContactPermissions()
    {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS }, 0);
            return false;
        }
        if (mContactsClient == null)
        {
            mContactsClient = getContentResolver().acquireContentProviderClient(ContactsContract.AUTHORITY_URI);
            mContactsQueue = new BasicOperationsQueue(mContactsClient);
        }
        return true;
    }
}
