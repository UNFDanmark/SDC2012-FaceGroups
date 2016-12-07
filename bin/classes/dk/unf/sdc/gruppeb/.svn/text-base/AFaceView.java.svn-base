package dk.unf.sdc.gruppeb;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class AFaceView extends View {

	private DBasicFaceCollection calculater;
	private ACanvasWriter writer;
	private long lastClickTime = 0;
	private long difDowUp = 0;
	private long startTime = 0;
	private Thread runnable;
	private boolean runs = false;
	private float rX, rY;
	private boolean removed = true;
	private Context mContext;
	public boolean mLoadComplete;
	private boolean mthreadComplete = false;

	private Bitmap img;

	public AFaceView(Context context, Bitmap sourceImg) {
		super(context);

		img = sourceImg;
		mContext = context;
		Thread thread = new Thread(new Runnable() {
			public void run() {
				if (mthreadComplete == false) {
					Message msg = new Message();
					Bundle b = new Bundle();
					b.putString("TestData", "DATA");
					msg.setData(b);
					msg.obj = (new ABecalculate(img)).getOutput();
					msg.arg1 = 2;
					handler.sendMessage(msg);
					mthreadComplete = true;
				}
			}
		});
		thread.start();
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.arg1 == 1) {
				if (removed == false) {
					removed = true;
					removeFace(rX, rY);
				}
			} else if (msg.arg1 == 2) {
				// Sæt calculater
				calculater = (DBasicFaceCollection) msg.obj;
				updateUserFace();
				mLoadComplete = true;
			}
		}
	};

	private void updateUserFace() {
		super.invalidate();
	}

	private void startThread() {
		runnable = new Thread(new Runnable() {
			public void run() {
				while (runs) {
					try {
						for (int i = 0; i < 500; i++) {
							Thread.sleep(1);
							if (!runs) {
								break;
							}
						} // calculater = new ABecalculate(sourceImg);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (runs) {
						// Send besked til handler
						Message msg = new Message();
						Bundle b = new Bundle();
						b.putString("TestData", "DATA");
						msg.setData(b);
						msg.arg1 = 1;
						handler.sendMessage(msg);

						//Log.d("AFaceView", "TODO: Vibrate!!!");
						// Get instance of Vibrator from current Context
						Vibrator v = (Vibrator) mContext
								.getSystemService(Context.VIBRATOR_SERVICE);

						// Vibrate for 300 milliseconds
						v.vibrate(300);

						runs = false;
					}
				}
			}
		});
		runnable.start();
	}

	// private DateTime time;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (calculater != null) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				runs = true;
				rX = event.getX();
				rY = event.getY();
				startThread();
				startTime = SystemClock.uptimeMillis();
				removed = false;
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				difDowUp = SystemClock.uptimeMillis() - startTime;

				if (difDowUp > 250) {
					// do stuff
					if (removed == false) {
						removed = true;
						removeFace(event.getX(), event.getY());
					}
					//Log.d("AFaceView", "LONG CLICK");
				} else {
					//Log.d("AFaceView", "SHORT CLICK");
					runs = false;
					if (SystemClock.uptimeMillis() - lastClickTime < 500) {
						// double click
						Log.d("AFaceView", "DOUBLE CLICKZZ");
						lastClickTime = 0;
						writer.addNewFace(event.getX(), event.getY(),
								writer.calculateAverageEyesDistance());
						super.invalidate();
					} else {
						lastClickTime = SystemClock.uptimeMillis();
					}
				}

				//Log.d("AFaceView", "Dif: " + difDowUp);
			}
			// else if (event.getAction() == MotionEvent.)
		}

		return true;
		// return super.onTouchEvent(event);
	}

	private void removeFace(float x, float y) {
		// Loop igennem vores facecollection for at finde face der er tættest på
		// vores x (cx) og y (cy)
		DBasicFace selectedFace = new DBasicFace(0, 0, 0);
		float selectedFaceAfstand = getWidth() * 10;
		boolean found = false;
		for (int i = 0; i < getFaceCollection().Count(); i++) {
			DBasicFace tmp = getFaceCollection().getFaces().get(i);
			float afstand;
			float centerX = tmp.getCenterX() * writer.getXRatio();
			float centerY = tmp.getCenterY() * writer.getYRatio();

			// Udregn afstanden
			afstand = (float) Math.hypot(
					Math.max(centerX, x) - Math.min(centerX, x),
					Math.max(centerY, y) - Math.min(centerY, y));

			// Indsæt
			if (selectedFaceAfstand > afstand) {
				selectedFace = tmp;
				selectedFaceAfstand = afstand;
				found = true;
			}
		}

		//Log.d("AFaceView", "Max afstand  " + (int) getWidth() / 5);
		//Log.d("AFaceView", "selectedFaceAfstand" + (int) selectedFaceAfstand);

		if (selectedFaceAfstand < getWidth() / 5) {
			if (selectedFace != null && found == true) {
				writer.removeFace(selectedFace);
				super.invalidate();
			}
		}
		super.invalidate();
	}

	public DBasicFaceCollection getFaceCollection() {
		return writer.getFaceCollection();
	}

	public Bitmap getImg() {
		return img;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		if (calculater != null) {
			if (writer == null) {
				writer = new ACanvasWriter(img, calculater, getWidth(),
						getHeight(), canvas);
			} else
				writer.updateCanvas();

			//Log.d("AFaceView", "Drawed");
		} else {

		}
	}

}
