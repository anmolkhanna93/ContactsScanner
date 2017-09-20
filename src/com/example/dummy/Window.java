package com.example.dummy;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.googlecode.tesseract.android.TessBaseAPI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Window extends Activity {
	private ImageView imageview1;
	public String s;
	public Uri uri;
	public int count = 0;

	public String wcon[] = { "Office", "Tel", "T", "Work", "Ph", "Tel Dir",
			"Off", "O", "P" };
	public String hcon[] = { "Home", "M", "Mobile", "Cell", "Mob" };
	public String position[] = { "AGM", "DGM", "ZSM", "Asstt.", "Actor",
			"Actress", "Developer", "Manager", "Director", "President",
			"Chairman", "Executive", "Editor", "Publisher", "Writer",
			"Composer", "Producer", "Administrator", "Chief", "General",
			"Dean", "Secretary", "Singer", "Administrative", "Reporter",
			"Designer", "Programmer", "Hacker", "Fellow", "PhD", "Ph.D.",
			"MBA", "Engineer", "M.B.A.", "MBBS", "M.B.B.S.", "M.B.B.S",
			"B.Tech", "M.Tech", "B.Tech.", "M.Tech.", "BTech", "MTech", "M.E.",
			"M.Eng.", "ME", "MEng", "B.E.", "B.Eng.", "BE", "BEng",
			"Professor", "Commissioner", "Minister", "Justice", "Supreme",
			"High", "Court", "Cabinet", "Governor", "Governors", "Judge",
			"Lieutenant", "Commission", "Head", "Sales", "Deputy", "Officer",
			"Associate", "Assistant", "Analyst", "Principal", "Advisor",
			"Investigator", "Appraiser", "Counselor", "Coordinator",
			"Evaluator", "Comedian", "Specialist", "CEO", "Partner",
			"Accountant", "Supervisor", "Junior", "Auditor", "Controller",
			"Senior", "Strategist", "Agent", "Representative", "Financial",
			"Market", "Member", "Dealer", "Attendant", "Receptionist",
			"Recruitment", "Inspector", "Leader", "Operator", "Lawyer",
			"Attorney", "Consultant", "Technician", "Technologist",
			"Scientist", "Electrician", "Surgeon", "Artist", "Decorator",
			"Painter", "Clerk", "Col.", " Col", "COL", "M.D.", "MD", "M.S.",
			"M.A.", "M.A.", "MS", "MA", "M.S", "BDS", "B.D.S.", "B.D.S",
			"Master", "B.Sc.", "B.Sc", "M.Sc", "M.Sc.", "MSc", "BSc", "D.Sc",
			"D.Sc.", "DSc", "B.Pharma", "B Pharm", "B.Pharm", "M.D", "B-Pharm",
			"BPharm", "B Pharma", "B. Pharm.", "B.Pharm.", "B.Pharm",
			"M.Pharma", "M Pharm", "M.Pharm", "M.D.", "M-Pharm", "MPharm",
			"M Pharma", "M. Pharm.", "M.Pharm.", "M.Pharm", "D Pharm",
			"D.Pharm", "D-Pharm", "DPharm", "D Pharma", "D. Pharm.",
			"D.Pharm.", "D.Pharm", "M.Phil.", "MPhil", "CA", "FCA", "B.L.",
			"LL.B.", "BCom", "B.Com.", "BComm", "B.Comm.", "BCA.", "BCA" };

	private int index0, index1 = 0;
	private String temp, temp1;
	private String stemp1[] = {};
	private boolean iflag = false;
	private boolean sflag = false;
	public long id = 0;
	EditText editText1, editText6, editText2, editText3, editText4, editText5,
			editText7;
	String name, designation, cell, work, email, address, company;

	DBAdapter db = new DBAdapter(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.window);

		android.app.ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#073C69")));

		imageview1 = (ImageView) findViewById(R.id.imageView1);
		uri = getIntent().getData();
		s = uri.getPath().toString();
		BitmapFactory.Options options = new BitmapFactory.Options();
		final Bitmap bitmap = BitmapFactory.decodeFile(s, options);
		imageview1.setImageBitmap(bitmap);

	}

	public void onClickConvert(View view) {

		editText1 = (EditText) findViewById(R.id.editText1);
		editText6 = (EditText) findViewById(R.id.editText6);
		editText2 = (EditText) findViewById(R.id.editText2);
		editText3 = (EditText) findViewById(R.id.editText3);
		editText4 = (EditText) findViewById(R.id.editText4);
		editText5 = (EditText) findViewById(R.id.editText5);
		editText7 = (EditText) findViewById(R.id.editText7);

		Bitmap bitmap1 = null;
		try {

			bitmap1 = BitmapFactory.decodeStream(getContentResolver()
					.openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String m_str = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/Android/data/com.example.dummy/";
		TessBaseAPI baseApi = new TessBaseAPI();
		baseApi.setDebug(true);
		baseApi.init(m_str, "eng");
		baseApi.setImage(bitmap1);
		String Output_text = baseApi.getUTF8Text();

		String b[] = Output_text.split("\n");
		String s[] = Output_text.split("\\s");

		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < position.length; j++) {
				if (b[i].contains(position[j])) {
					editText6.setText(b[i]);
					editText1.setText(b[i - 1]);
					editText6.setVisibility(View.VISIBLE);
					editText1.setVisibility(View.VISIBLE);
				}
			}
		}

		for (int i = 0; i < s.length; i++) {
			if (s[i].contains("@")) {
				editText4.setText(s[i]);
				editText4.setVisibility(View.VISIBLE);
			}
		}

		baseApi.end();

		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < wcon.length; j++) {
				if (b[i].contains(wcon[j])) {

					temp = b[i];

				}
			}
		}

		if (temp != null) {
			String stemp[] = temp.split("\\s");

			for (int i = 0; i < stemp.length; i++) {
				for (int j = 0; j < wcon.length; j++) {
					if (stemp[i].contains(wcon[j])) {
						index0 = i;
					}
				}
			}

			for (int i = index0 + 1; i < stemp.length; i++) {
				editText3.append(stemp[i]);
				editText3.setVisibility(View.VISIBLE);
			}

		}

		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < hcon.length; j++) {
				if (b[i].contains(hcon[j])) {

					temp1 = b[i];
					sflag = true;
				}
			}
		}

		if (sflag == true) {
			stemp1 = temp1.split("\\s");
			for (int i = 0; i < stemp1.length; i++) {
				for (int j = 0; j < hcon.length; j++) {
					if (stemp1[i].contains(hcon[j])) {
						index0 = i;
						iflag = true;
					}
				}
			}
		}

		if (iflag == true) {
			for (int i = index0 + 1; i < stemp1.length; i++) {
				editText2.append(stemp1[i]);
				editText2.setVisibility(View.VISIBLE);
			}
		}

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
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		CreateMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.preview_window, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.action_save:

			editText1 = (EditText) findViewById(R.id.editText1);
			editText6 = (EditText) findViewById(R.id.editText6);
			editText2 = (EditText) findViewById(R.id.editText2);
			editText3 = (EditText) findViewById(R.id.editText3);
			editText4 = (EditText) findViewById(R.id.editText4);
			editText5 = (EditText) findViewById(R.id.editText5);
			editText7 = (EditText) findViewById(R.id.editText7);

			name = editText1.getText().toString();
			designation = editText6.getText().toString();
			cell = editText2.getText().toString();
			work = editText3.getText().toString();
			email = editText4.getText().toString();
			address = editText5.getText().toString();
			company = editText7.getText().toString();

			db.open();
			if (count <= 0) {
				id = db.insertcards(s, name, designation, cell, work, email,
						address, company);
				Toast.makeText(this, "Saved with id" + id + "",
						Toast.LENGTH_SHORT).show();

			} else {
				// update the following id
				if (db.updatecards(id, s, name, designation, cell, work, email,
						address, company)) {
					Toast.makeText(this, "Updated with id" + id + "",
							Toast.LENGTH_SHORT).show();
				} else
					Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT)
							.show();
			}

			db.close();
			count++;

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
}
