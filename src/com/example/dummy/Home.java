package com.example.dummy;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class Home extends Activity {

	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	public static final int MEDIA_TYPE_IMAGE = 1;
	private static final String IMAGE_DIRECTORY_NAME = "CardTech";
	private Uri fileUri;
	public ListView lv;
	private static int RESULT_LOAD_IMG = 1;

	ArrayList<String> Imagepath = new ArrayList<String>();
	ArrayList<String> prgmNameList = new ArrayList<String>();
	ArrayList<String> prgmEmail = new ArrayList<String>();
	ArrayList<String> prgmDesignation = new ArrayList<String>();
	ArrayList<String> prgmCell = new ArrayList<String>();
	ArrayList<String> prgmWork = new ArrayList<String>();
	ArrayList<String> prgmAddress = new ArrayList<String>();
	ArrayList<String> prgmCompany = new ArrayList<String>();
	ArrayList<Long> prgmId = new ArrayList<Long>();

	DBAdapter db = new DBAdapter(this);
	public static final BitmapFactory.Options options = new BitmapFactory.Options();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		android.app.ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#073C69")));

		Imagepath.clear();
		prgmId.clear();
		prgmNameList.clear();
		prgmDesignation.clear();
		prgmCell.clear();
		prgmWork.clear();
		prgmEmail.clear();
		prgmAddress.clear();
		prgmCompany.clear();

		db.open();

		int rows = db.count_rows();

		String rows1 = String.valueOf(rows);

		actionBar.setTitle("CardTech" + "   " + "(" + rows1 + ")");

		Cursor c = db.getAllcards();
		if (c.moveToFirst()) {
			do {
				DisplayCards(c);

			} while (c.moveToNext());
		}

		lv = (ListView) findViewById(R.id.listview);
		lv.setAdapter(new CustomAdapter(this, Imagepath, prgmId, prgmNameList,
				prgmDesignation, prgmCell, prgmWork, prgmEmail, prgmAddress,
				prgmCompany));
		db.close();

	}

	private void DisplayCards(Cursor c) {
		long s2 = c.getLong(0);

		String s = c.getString(1);
		String s1 = c.getString(2);
		String s3 = c.getString(3);
		String s4 = c.getString(4);
		String s5 = c.getString(5);
		String s6 = c.getString(6);
		String s7 = c.getString(7);
		String s8 = c.getString(8);

		Imagepath.add(s);
		prgmId.add(s2);
		prgmNameList.add(s1);
		prgmDesignation.add(s3);
		prgmCell.add(s4);
		prgmWork.add(s5);
		prgmEmail.add(s6);
		prgmAddress.add(s7);
		prgmCompany.add(s8);

	}

	public void onClick(View view) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
			if (requestCode == 1) {
				Uri selectedImageUri = data.getData();
				String s = getRealPathFromURI(selectedImageUri);
				Uri uriFromPath = Uri.fromFile(new File(s));
				Intent intent = new Intent(this, Window.class);
				intent.setData(uriFromPath);
				startActivity(intent);
				finish();
			}
		}

		if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {

				previewCaptureImage();

			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(getApplicationContext(),
						"User cancelled image capture", Toast.LENGTH_SHORT)
						.show();

			} else {
				Toast.makeText(getApplicationContext(),
						"Sorry! Failed to capture image", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	private void previewCaptureImage() {

		// String s = fileUri.getPath().toString();

		Intent intent = new Intent(this, Window.class);
		// intent.putExtra("string", s);
		intent.setData(fileUri);
		startActivity(intent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		CreateMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.action_add:
			Intent intent = new Intent();
			intent.setType("*/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(intent, 1);
			return true;
		case R.id.action_search:
			//
			return true;
		case R.id.action_settings:
			//
			return true;
		case R.id.action_aboutus:

			Intent intent1 = new Intent(this, About.class);
			startActivity(intent1);
			finish();

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

	// helper methods
	public Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	private static File getOutputMediaFile(int type) {
		// External sdcard location
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				IMAGE_DIRECTORY_NAME);
		/*
		 * if (!mediaStorageDir.exists()) { if (!mediaStorageDir.mkdirs()) {
		 * Log.d(IMAGE_DIRECTORY_NAME, "Failed to create" + IMAGE_DIRECTORY_NAME
		 * + "directory"); return null; } }
		 */
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");

		} else {
			return null;
		}

		return mediaFile;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable("file_uri", fileUri);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		fileUri = savedInstanceState.getParcelable("file_uri");

	}

	public String getRealPathFromURI(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		@SuppressWarnings("deprecation")
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

}