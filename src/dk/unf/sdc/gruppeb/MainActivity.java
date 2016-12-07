package dk.unf.sdc.gruppeb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private String imagePath;
	private Button takePicBut;
	private Button usePicBut;
	private Button tutorialBut;
	private boolean bigGroup;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proto);
		bigGroup = true;
		takePicBut = (Button) findViewById(R.id.takePicBut);
		usePicBut = (Button) findViewById(R.id.usePicBut);
		tutorialBut = (Button) findViewById(R.id.tutorialBut);
		takePicBut.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				takePictureIntent(1);
			}
		});

		tutorialBut.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent vejledningIntent = new Intent(getApplicationContext(),
						VejledningActivity.class);
				startActivity(vejledningIntent);
			}
		});

		usePicBut.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(intent, 10);
			}
		});

		Intent theIntent = getIntent();
		if (theIntent != null) {
			//Log.d("MainActivity", theIntent.getAction());
			if (theIntent.getAction().equals(Intent.ACTION_SEND)) {
				if (theIntent.getType().startsWith("image/")) {
					// Vi har modtaget et billede
					try {
						this.handleImageIntent(theIntent);
					} catch (FileNotFoundException e) {
						// TODO: Something
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void handleImageIntent(Intent imageIntent)
			throws FileNotFoundException {
		Uri imageUri = (Uri) imageIntent
				.getParcelableExtra(Intent.EXTRA_STREAM);
		if (imageUri != null) {
			SharedPreferences shared = getSharedPreferences("imagePath", 0);
			SharedPreferences.Editor editor = shared.edit();
			editor.putString("imagePath", this.getPath(imageUri));
			editor.commit();
			this.startFaceView(this.getPath(imageUri));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_proto, menu);
		return true;
	}

	private File getImageDir() {
		File storageDir = null;

		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {

			String picturesFolder = Environment
					.getExternalStoragePublicDirectory(
							Environment.DIRECTORY_PICTURES).getAbsolutePath();
			String appPhotoFolder = picturesFolder + "/"
					+ "FaceGroup/";

			storageDir = new File(appPhotoFolder);
			if (storageDir.exists() == false) {
				storageDir.mkdirs();
				/*Log.i("ProtoActivity",
						storageDir.getAbsolutePath().concat(" created"));*/
			}
		} else {
			/*Log.v(getString(R.string.app_name),
					"External storage is not mounted READ/WRITE.");*/
		}

		return storageDir;
	}

	private File createImageFile() throws IOException {
		// Create an image file name
		String fileName = "image" + System.currentTimeMillis();
		File dir = getImageDir();
		FileWriter f = new FileWriter(dir.getAbsoluteFile() + "/" + fileName + ".jpg");
		f.close();
		File file = new File(dir.getAbsoluteFile() + "/" + fileName + ".jpg");
		return file;
	}

	public void takePictureIntent(int actionCode) {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File imageFile = null;
		try {
			imageFile = createImageFile();
		} catch (IOException ex) {
			// TODO: Something something
			// TODO: Crash violently
			ex.printStackTrace();
		}

		if (imageFile != null) {
			Uri imageUri = Uri.fromFile(imageFile);
			imagePath = imageFile.getAbsolutePath();
			SharedPreferences shared = getSharedPreferences("imagePath", 0);
			SharedPreferences.Editor editor = shared.edit();
			editor.putString("imagePath", imagePath);
			editor.commit();
			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			startActivityForResult(takePictureIntent, actionCode);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// RequestCode er intent result fra Kamera
		if (requestCode == 1) {
			//Resultcode -1 betyder succes
			//Resultcode 0 betyder at der er blevet trykket return
			if (resultCode == -1) {
				super.onActivityResult(requestCode, resultCode, data);
				Log.d("MainActivity", "Got Intend with following code:"
						+ resultCode);
				this.startFaceView(this.imagePath);
			} else {
				return;
			}
		

			// RequestCode er intent result fra Galleri
		} else if (requestCode == 10) {
			if (data != null) {
				Uri targetUri = data.getData();
				SharedPreferences shared = getSharedPreferences("imagePath", 0);
				SharedPreferences.Editor editor = shared.edit();
				editor.putString("imagePath", this.getPath(targetUri));
				editor.commit();
				this.startFaceView(this.getPath(targetUri));
			}
		}

	}

	private void startFaceView(String imagePath) {
		Intent analyseIntent = new Intent(getApplicationContext(),
				AnalyseActivity.class);

		SharedPreferences shared = getSharedPreferences("imagePath", 0);
		imagePath = shared.getString("imagePath", null);
		analyseIntent.putExtra("size", bigGroup);
		analyseIntent.putExtra("imagePath", imagePath);
		startActivity(analyseIntent);
		finish();
	}

	/**
	 * Kan omregne en URI til real path Ved den ser mærkelig ud med, den skal
	 * være sådanne. - Lasse
	 * 
	 * @param uri
	 * @return real path
	 */
	@SuppressWarnings("deprecation")
	private String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
}
