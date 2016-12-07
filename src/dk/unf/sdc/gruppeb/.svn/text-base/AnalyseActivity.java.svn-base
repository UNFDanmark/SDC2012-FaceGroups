package dk.unf.sdc.gruppeb;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AnalyseActivity extends Activity {

	private AFaceView faceView;
	private Bitmap sourceImage;
	private boolean mCompleteCalculate = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.analyse_layout);

		Intent intent = getIntent();
		String imagePath = intent.getExtras().getString("imagePath");
		Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
		this.sourceImage = this.createScaledBitmap(bitmap,
				intent.getBooleanExtra("size", true));

		FrameLayout myLayout = (FrameLayout) findViewById(R.id.linier_layout_lang_navn);
		Button next_button = (Button) findViewById(R.id.button1); // new
																	// Button(this);
		OnClickListener btnListener = new OnClickListener() {

			public void onClick(View v) {
				DBasicFaceCollection faceCollection = faceView
						.getFaceCollection();
				//Tjek for null
				if (faceCollection == null || faceCollection.Count() < 2)
				{
					Toast.makeText(getApplicationContext(), "Du skal mininum vælge 2 ansigter", Toast.LENGTH_SHORT).show();
					return;
				}
				
				DCachedFaceList cachedFaceList = new DCachedFaceList(
						faceCollection, sourceImage);
				//Log.d("AnalyseActivity", "Cropping...");
				cachedFaceList.Crop();
				//Log.d("AnalyseActivity", "Done cropping!");

				int count = 0;
				for (DCachedFace face : cachedFaceList.getCachedFaces()) {
					if (face.getImage() != null) {
						count++;
					}
				}
				//Log.d("AnalyseActivity", "Antal faces med billeder: " + count);

				/*Log.d("AnalyseActivity",
						"Sender parcel med ansigter til IndstillingerActivity");*/
				Intent intent = new Intent(getApplicationContext(),
						IndstillingerActivity.class);
				intent.putExtra("faces", cachedFaceList.getCachedFaces());
				startActivity(intent);
			}
		};
		next_button.setOnClickListener(btnListener);

		faceView = new AFaceView(getApplicationContext(), sourceImage);
		myLayout.addView(faceView);
		// setContentView(new FaceView(this, image));

		Thread thread = new Thread(new Runnable() {
			public void run() {
				while (true) {
					if (faceView.mLoadComplete && mCompleteCalculate) {
						Message msg = new Message();
						Bundle b = new Bundle();
						b.putString("TestData", "DATA");
						msg.setData(b);
						handler.sendMessage(msg);
						mCompleteCalculate = false;
						break;
					}
				}
			}
		});
		thread.start();

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			((LinearLayout) findViewById(R.id.analyseButtonLayout))
					.setVisibility(1);
			((LinearLayout) findViewById(R.id.progressLayout)).setVisibility(0);
			((LinearLayout) findViewById(R.id.analyseButtonLayout))
					.bringToFront();
		}
	};

	private Bitmap createScaledBitmap(Bitmap sourceBitmap, boolean big) {
		// Find skærmopløsning
		// TODO: Find Activity size (dvs størrelsen uden phone bar og title bar)
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int imageWidth = sourceBitmap.getWidth();
		int imageHeight = sourceBitmap.getHeight();
		float aspectRatio = Math.max(imageWidth, imageHeight)
				/ Math.min(imageWidth, imageHeight);

		// Komprimer billede
		Bitmap scaledBitmap = sourceBitmap;

		if (big) {
			scaledBitmap = Bitmap
					.createScaledBitmap(sourceBitmap,
							(int) (1600 * aspectRatio),
							(int) (960 * aspectRatio), true);

		} else {

			scaledBitmap = Bitmap.createScaledBitmap(sourceBitmap,
					(int) (800 * aspectRatio), (int) (480 * aspectRatio), true);
		}

		return scaledBitmap;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_analyse, menu);
		return true;
	}

}
