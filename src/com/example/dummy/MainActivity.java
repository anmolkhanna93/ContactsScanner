package com.example.dummy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ----param for views
		LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		// ----param1 for layout
		LayoutParams params1 = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		// create a layout
		final LinearLayout layout = new LinearLayout(this);
		layout.setLayoutParams(params1);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setGravity(Gravity.CENTER);
		layout.setBackgroundColor(Color.parseColor("#FFFFFF"));

		// create a image
		final ImageView iv = new ImageView(this);
		iv.setLayoutParams(params);
		iv.setContentDescription("Logo");
		iv.setImageResource(R.drawable.coverone);

		// create a textview
		// final TextView tv = new TextView(this);
		// tv.setLayoutParams(params);
		// tv.setText("© 2015 AAK Technologies");
		// tv.setTextSize(10);
		// tv.setTextColor(Color.parseColor("#78909C"));

		this.addContentView(layout, params1);

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				layout.addView(iv);
				// layout.addView(tv);
			}
		}, 300);

		// Folder Camtech created
		File f = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"CardTech");
		if (!f.exists()) {
			f.mkdirs();
		}

		Handler handler1 = new Handler();
		handler1.postDelayed(new Runnable() {
			public void run() {
				Intent intent = new Intent(getBaseContext(), Home.class);
				startActivity(intent);
				finish();
			}
		}, 2925);

		// eng.trained file
		try {
			AssetManager assetmanager = getAssets();
			InputStream in = assetmanager.open("tessdata/" + "eng.traineddata");
			File sdCard = Environment.getExternalStorageDirectory();
			File directory = new File(sdCard.getAbsolutePath()
					+ "/Android/data/com.example.dummy/tessdata");
			directory.mkdirs();
			File file = new File(directory, "eng.traineddata");
			FileOutputStream out = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
			in.close();
			out.close();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
