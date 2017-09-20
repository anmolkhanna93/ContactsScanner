package com.example.dummy;

import com.googlecode.tesseract.android.TessBaseAPI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Edit extends Activity {

	private ImageView imageview1;
	private EditText edittext1, edittext2, edittext3, edittext4, edittext5,
			edittext6, edittext7;

	private String path, name, designation, cell, work, email, address,
			company;
	public long id;
	DBAdapter db = new DBAdapter(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit);

		android.app.ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#073C69")));

		imageview1 = (ImageView) findViewById(R.id.ImageView1);
		edittext1 = (EditText) findViewById(R.id.EditText1);
		edittext2 = (EditText) findViewById(R.id.EditText6);
		edittext3 = (EditText) findViewById(R.id.EditText2);
		edittext4 = (EditText) findViewById(R.id.EditText3);
		edittext5 = (EditText) findViewById(R.id.EditText4);
		edittext6 = (EditText) findViewById(R.id.EditText5);
		edittext7 = (EditText) findViewById(R.id.EditText7);

		id = getIntent().getLongExtra("Id", 0);
		path = getIntent().getStringExtra("String");

		BitmapFactory.Options options = new BitmapFactory.Options();
		Bitmap bitmap = BitmapFactory.decodeFile(path, options);

		imageview1.setImageBitmap(bitmap);
		edittext1.setText(getIntent().getStringExtra("Name"));
		edittext2.setText(getIntent().getStringExtra("Designation"));
		edittext3.setText(getIntent().getStringExtra("Cell"));
		edittext4.setText(getIntent().getStringExtra("Work"));
		edittext5.setText(getIntent().getStringExtra("Email"));
		edittext6.setText(getIntent().getStringExtra("Address"));
		edittext7.setText(getIntent().getStringExtra("Company"));

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
		inflater.inflate(R.menu.edit_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.action_update:

			name = edittext1.getText().toString();
			designation = edittext2.getText().toString();
			cell = edittext3.getText().toString();
			work = edittext4.getText().toString();
			email = edittext5.getText().toString();
			address = edittext6.getText().toString();
			company = edittext7.getText().toString();

			db.open();

			if (db.updatecards(id, path, name, designation, cell, work, email,
					address, company)) {
				Toast.makeText(this, "Update successful with id" + id,
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT)
						.show();
			}

			db.close();

			return true;

		case R.id.action_contacts:
			//
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	public void onClickConvert(View view) {

		String position[] = { "Director", "Professor", "Manager", "Head",
				"Executive", "Sales", "Deputy", "President" };

		String m_str = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/Android/data/com.example.dummy/";
		TessBaseAPI baseApi = new TessBaseAPI();
		baseApi.setDebug(true);
		baseApi.init(m_str, "eng");
		BitmapFactory.Options options = new BitmapFactory.Options();
		final Bitmap bitmap = BitmapFactory.decodeFile(path, options);
		baseApi.setImage(bitmap);
		String Output_text = baseApi.getUTF8Text();

		String b[] = Output_text.split("\n");
		String s[] = Output_text.split("\\s");

		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < position.length; j++) {
				if (b[i].contains(position[j])) {
					edittext2.setText(b[i]);
					edittext1.setText(b[i - 1]);
					edittext2.setVisibility(View.VISIBLE);
					edittext1.setVisibility(View.VISIBLE);
				}
			}
		}

		for (int i = 0; i < s.length; i++) {
			if (s[i].contains("@")) {
				edittext5.setText(s[i]);
				edittext5.setVisibility(View.VISIBLE);
			} else if (s[i].contains("(")) {
				edittext4.setText(s[i] + s[i + 1]);
				edittext4.setVisibility(View.VISIBLE);

			} else if (s[i].contains("-")) {
				edittext4.setText(s[i]);
				edittext4.setVisibility(View.VISIBLE);
			}

		}
		baseApi.end();

	}

	private void CreateMenu(Menu menu) {
	}

	private boolean MenuChoice(MenuItem item) {
		return false;
	}

}
