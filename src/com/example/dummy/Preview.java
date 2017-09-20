package com.example.dummy;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Preview extends Activity {

	private ImageView imageview1;
	private TextView textview1, textview2, textview3, textview4, textview5,
			textview6, textview7;
	public String path, name, designation, cell, work, email, address, company;

	public long id;
	DBAdapter db = new DBAdapter(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preview);

		android.app.ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#073C69")));

		imageview1 = (ImageView) findViewById(R.id.imageView1);
		textview1 = (TextView) findViewById(R.id.textView1);
		textview2 = (TextView) findViewById(R.id.textView2);
		textview3 = (TextView) findViewById(R.id.textView3);
		textview4 = (TextView) findViewById(R.id.textView4);
		textview5 = (TextView) findViewById(R.id.textView5);
		textview6 = (TextView) findViewById(R.id.textView6);
		textview7 = (TextView) findViewById(R.id.textView7);

		id = getIntent().getLongExtra("Id", 0);
		path = getIntent().getStringExtra("String");
		name = getIntent().getStringExtra("Name");
		designation = getIntent().getStringExtra("Designation");
		cell = getIntent().getStringExtra("Cell");
		work = getIntent().getStringExtra("Work");
		email = getIntent().getStringExtra("Email");
		address = getIntent().getStringExtra("Address");
		company = getIntent().getStringExtra("Company");

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;
		Bitmap bitmap = BitmapFactory.decodeFile(path, options);

		imageview1.setImageBitmap(bitmap);
		textview1.setText(name);
		textview2.setText(designation);
		textview3.setText(company);
		textview4.setText(cell);
		textview5.setText(work);
		textview6.setText(email);
		textview7.setText(address);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		CreateMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.display_final, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(this, Home.class);
			startActivity(intent);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.action_delete:

			File file = new File(path);
			file.delete();
			db.open();
			if (db.deletecard(id)) {
				Toast.makeText(this, "Delete successful with id" + id,
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT)
						.show();
			}

			db.close();

			Intent intent = new Intent(this, Home.class);
			startActivity(intent);
			finish();

			return true;

		case R.id.action_edit:

			Intent intent1 = new Intent(this, Edit.class);

			intent1.putExtra("Id", id);
			intent1.putExtra("String", path);
			intent1.putExtra("Name", name);
			intent1.putExtra("Designation", designation);
			intent1.putExtra("Cell", cell);
			intent1.putExtra("Work", work);
			intent1.putExtra("Email", email);
			intent1.putExtra("Address", address);
			intent1.putExtra("Company", company);

			startActivity(intent1);
			finish();

			return true;

		case R.id.action_contacts:

			String DisplayName = name;
			String MobileNumber = cell;
			String WorkNumber = work;
			String emailID = email;
			String jobTitle = designation;
			String company_name = company;

			ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

			ops.add(ContentProviderOperation
					.newInsert(ContactsContract.RawContacts.CONTENT_URI)
					.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
					.withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
					.build());

			// Names
			if (DisplayName != null) {
				ops.add(ContentProviderOperation
						.newInsert(ContactsContract.Data.CONTENT_URI)
						.withValueBackReference(
								ContactsContract.Data.RAW_CONTACT_ID, 0)
						.withValue(
								ContactsContract.Data.MIMETYPE,
								ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
						.withValue(
								ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
								DisplayName).build());
			}
			// MobileNumber
			if (MobileNumber != null) {
				ops.add(ContentProviderOperation
						.newInsert(ContactsContract.Data.CONTENT_URI)
						.withValueBackReference(
								ContactsContract.Data.RAW_CONTACT_ID, 0)
						.withValue(
								ContactsContract.Data.MIMETYPE,
								ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
						.withValue(
								ContactsContract.CommonDataKinds.Phone.NUMBER,
								MobileNumber)
						.withValue(
								ContactsContract.CommonDataKinds.Phone.TYPE,
								ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
						.build());
			}
			// WorkNumber
			if (WorkNumber != null) {
				ops.add(ContentProviderOperation
						.newInsert(ContactsContract.Data.CONTENT_URI)
						.withValueBackReference(
								ContactsContract.Data.RAW_CONTACT_ID, 0)
						.withValue(
								ContactsContract.Data.MIMETYPE,
								ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
						.withValue(
								ContactsContract.CommonDataKinds.Phone.NUMBER,
								WorkNumber)
						.withValue(
								ContactsContract.CommonDataKinds.Phone.TYPE,
								ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
						.build());
			}
			// Email
			if (emailID != null) {
				ops.add(ContentProviderOperation
						.newInsert(ContactsContract.Data.CONTENT_URI)
						.withValueBackReference(
								ContactsContract.Data.RAW_CONTACT_ID, 0)
						.withValue(
								ContactsContract.Data.MIMETYPE,
								ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
						.withValue(ContactsContract.CommonDataKinds.Email.DATA,
								emailID)
						.withValue(
								ContactsContract.CommonDataKinds.Email.TYPE,
								ContactsContract.CommonDataKinds.Email.TYPE_WORK)
						.build());
			}
			// Organization
			if (!company_name.equals("") || !jobTitle.equals("")) {
				ops.add(ContentProviderOperation
						.newInsert(ContactsContract.Data.CONTENT_URI)
						.withValueBackReference(
								ContactsContract.Data.RAW_CONTACT_ID, 0)
						.withValue(
								ContactsContract.Data.MIMETYPE,
								ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
						.withValue(
								ContactsContract.CommonDataKinds.Organization.COMPANY,
								company_name)
						.withValue(
								ContactsContract.CommonDataKinds.Organization.TYPE,
								ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
						.withValue(
								ContactsContract.CommonDataKinds.Organization.TITLE,
								jobTitle)
						.withValue(
								ContactsContract.CommonDataKinds.Organization.TYPE,
								ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
						.build());
			}

			// Asking the Contact provider to create a new contact
			try {
				getContentResolver()
						.applyBatch(ContactsContract.AUTHORITY, ops);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Toast.makeText(this, "Contact successfully added",
					Toast.LENGTH_SHORT).show();

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private void CreateMenu(Menu menu) {
	}

	private boolean MenuChoice(MenuItem item) {
		return false;
	}

	// using built in methods
	public void onClickMakeCallsCell(View view) {
		Intent i = new Intent(android.content.Intent.ACTION_DIAL,
				Uri.parse("tel:" + cell));
		startActivity(i);
	}

	public void onClickMakeCallsWork(View view) {
		Intent i = new Intent(android.content.Intent.ACTION_DIAL,
				Uri.parse("tel:" + work));
		startActivity(i);
	}

	public void onClickSendEmail(View view) {
		Intent i = new Intent(android.content.Intent.ACTION_SEND);
		i.setType("plain/text");
		i.putExtra(Intent.EXTRA_EMAIL, new String[] { email }); // recipients
		startActivity(i);
	}

	public void onClickShowMap(View view) {
		Intent i = new Intent(android.content.Intent.ACTION_VIEW,
				Uri.parse("geo:0,0?q=" + address));

		startActivity(i);
	}
}
