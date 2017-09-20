package com.example.dummy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapter extends BaseAdapter {

	ArrayList<String> result;
	ArrayList<Long> result1;
	ArrayList<String> result2;
	ArrayList<String> result3;
	ArrayList<String> result4;
	ArrayList<String> result5;
	ArrayList<String> result6;
	ArrayList<String> result7;
	ArrayList<String> result8;

	Context context;
	private static LayoutInflater inflater = null;
	private static final String IMAGE_DIRECTORY_NAME = "CardTech";

	public CustomAdapter(Home home, ArrayList<String> Imagepath,
			ArrayList<Long> prgmId, ArrayList<String> prgmNameList,
			ArrayList<String> prgmDesignation, ArrayList<String> prgmCell,
			ArrayList<String> prgmWork, ArrayList<String> prgmEmail,
			ArrayList<String> prgmAddress, ArrayList<String> prgmCompany) {

		result8 = Imagepath;
		result = prgmNameList;
		result1 = prgmId;
		result2 = prgmDesignation;
		result3 = prgmCell;
		result4 = prgmWork;
		result5 = prgmEmail;
		result6 = prgmAddress;
		result7 = prgmCompany;

		context = home;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public class Holder {
		TextView tv, tv1;
		ImageView img;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return result.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder = new Holder();
		View rowView = inflater.inflate(R.layout.program_list1, null);
		holder.tv = (TextView) rowView.findViewById(R.id.textview1);
		holder.tv1 = (TextView) rowView.findViewById(R.id.textView2);
		holder.img = (ImageView) rowView.findViewById(R.id.imageview1);

		holder.tv.setText(result.get(position));
		holder.tv1.setText(result2.get(position));

		String s = result8.get(position);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 8;
		Bitmap bitmap = BitmapFactory.decodeFile(s, options);

		holder.img.setImageBitmap(bitmap);

		rowView.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {

				Intent i = new Intent(view.getContext(), Preview.class);

				long id = result1.get(position);

				String path = result8.get(position);
				String name = result.get(position);
				String designation = result2.get(position);
				String cell = result3.get(position);
				String work = result4.get(position);
				String email = result5.get(position);
				String address = result6.get(position);
				String company = result7.get(position);

				i.putExtra("Id", id);
				i.putExtra("String", path);
				i.putExtra("Name", name);
				i.putExtra("Designation", designation);
				i.putExtra("Cell", cell);
				i.putExtra("Work", work);
				i.putExtra("Email", email);
				i.putExtra("Address", address);
				i.putExtra("Company", company);

				view.getContext().startActivity(i);
				((Activity) context).finish();
				
			}

		});
		return rowView;

	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return false;
	}
}