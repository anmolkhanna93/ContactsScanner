package com.example.dummy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {
	static final String KEY_ID = "id";
	static final String KEY_IMG = "string";
	static final String KEY_NAME = "name";
	static final String KEY_DESIGNATION = "designation";
	static final String KEY_PHONE_CELL = "cell";
	static final String KEY_PHONE_WORK = "work";
	static final String KEY_EMAIL = "email";
	static final String KEY_ADDRESS = "address";
	static final String KEY_COMPANY = "company";
	static final String TAG = "DBAdapter";
	static final String DATABASE_NAME = "CardTech_Database2";
	static final String DATABASE_TABLE = "cards";
	static final int DATABASE_VERSION = 1;
	static final String DATABASE_CREATE = "create table cards(id integer primary key autoincrement,"
			+ " string text ,"
			+ "name text,"
			+ "designation text,"
			+ "cell text,"
			+ "work text,"
			+ "email text,"
			+ "address text,"
			+ "company text" + ");";

	final Context context;
	DatabaseHelper DBHelper;
	SQLiteDatabase db;

	public DBAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(DATABASE_CREATE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}

	}

	// ---opens the database---
	public DBAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	// ---closes the database---

	public void close() {
		DBHelper.close();
	}

	// ---count the number of rows---
	public int count_rows() {
		Cursor c = db.rawQuery("select * from cards", null);
		int count = c.getCount();
		return count;
	}

	// ---insert a contact into the database---
	public long insertcards(String string, String name, String designation,
			String cell, String work, String email, String address,
			String company) {
		ContentValues initialValues = new ContentValues();

		initialValues.put(KEY_IMG, string);
		initialValues.put(KEY_NAME, name);
		initialValues.put(KEY_DESIGNATION, designation);
		initialValues.put(KEY_PHONE_CELL, cell);
		initialValues.put(KEY_PHONE_WORK, work);
		initialValues.put(KEY_EMAIL, email);
		initialValues.put(KEY_ADDRESS, address);
		initialValues.put(KEY_COMPANY, company);
		return db.insert(DATABASE_TABLE, null, initialValues);
	}

	// ---retrieves all the contacts---
	public Cursor getAllcards() {
		return db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_IMG,
				KEY_NAME, KEY_DESIGNATION, KEY_PHONE_CELL, KEY_PHONE_WORK,
				KEY_EMAIL, KEY_ADDRESS, KEY_COMPANY }, null, null, null, null,
				null);
	}

	public boolean updatecards(long rowId, String string, String name,
			String designation, String cell, String work, String email,
			String address, String company) {
		ContentValues args = new ContentValues();
		args.put(KEY_IMG, string);
		args.put(KEY_NAME, name);
		args.put(KEY_DESIGNATION, designation);
		args.put(KEY_PHONE_CELL, cell);
		args.put(KEY_PHONE_WORK, work);
		args.put(KEY_EMAIL, email);
		args.put(KEY_ADDRESS, address);
		args.put(KEY_COMPANY, company);
		return db.update(DATABASE_TABLE, args, KEY_ID + "=" + rowId, null) > 0;
	}

	// ---delete a particular contact
	public boolean deletecard(long rowId) {
		return db.delete(DATABASE_TABLE, KEY_ID + "=" + rowId, null) > 0;
	}

}
