package dk.unf.sdc.gruppeb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

public class ACanvasWriter {
	// Størrelsen af kvadratet
	private static final float SQUARE_SIZE = (float) 1.3;
	private static final int color = Color.WHITE;
	private static final boolean DEBUG = false;

	// Variabler til billedet og canvas
	private Bitmap mSourceImage;
	private Canvas mCanvas;
	private DBasicFaceCollection mFaceCollection;
	private int mPicWidth, mPicHeight, mScreenWidth, mScreenHeight;
	private float mXRatio, mYRatio;

	// Paint variabler
	private Paint mtmpPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint mpOuterBullsEye = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint mpInnerBullsEye = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint mpTextBullsEye = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint mpHelpTextBullsEye = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint mpNumberTextBullsEye = new Paint(Paint.ANTI_ALIAS_FLAG);

	/**
	 * Brug getCanvas for at få vores Canvas
	 * 
	 * @param image
	 * @param faceCollection
	 * @param screenWidth
	 * @param screenHeight
	 */
	public ACanvasWriter(Bitmap image, DBasicFaceCollection faceCollection,
			int screenWidth, int screenHeight, Canvas canvas) {
		// Sæt variabler
		mFaceCollection = faceCollection;
		mSourceImage = image;
		mPicWidth = image.getWidth();
		mPicHeight = image.getHeight();
		mScreenWidth = screenWidth;
		mScreenHeight = screenHeight;
		mCanvas = canvas;

		// Udregn vores skalering
		mXRatio = mScreenWidth * 1.0f / mPicWidth;
		mYRatio = mScreenHeight * 1.0f / mPicHeight;

		// Style sættelse
		mpInnerBullsEye.setStyle(Paint.Style.FILL);
		mpInnerBullsEye.setColor(color);
		// mpInnerBullsEye.setColor(Color.WHITE);

		mpOuterBullsEye.setStyle(Paint.Style.STROKE);
		mpOuterBullsEye.setColor(color);
		// mpOuterBullsEye.setColor(Color.WHITE);
		
		mpTextBullsEye.setStyle(Paint.Style.FILL);
		mpTextBullsEye.setColor(Color.BLACK);
		mpTextBullsEye.setStrokeWidth(2);
		mpTextBullsEye.setTextSize(40);
		
		mpHelpTextBullsEye.setStyle(Paint.Style.FILL);
		mpHelpTextBullsEye.setColor(Color.BLACK);
		mpHelpTextBullsEye.setStrokeWidth(2);
		mpHelpTextBullsEye.setTextSize(20);
		
		mpNumberTextBullsEye.setStyle(Paint.Style.FILL);
		mpNumberTextBullsEye.setColor(Color.WHITE);
		mpNumberTextBullsEye.setStrokeWidth(1);
		mpNumberTextBullsEye.setTextSize(10);
		
		mtmpPaint.setStyle(Paint.Style.STROKE);
		mtmpPaint.setTextAlign(Paint.Align.CENTER);

		// Antal farver i det behandlede billede
		BitmapFactory.Options bfo = new BitmapFactory.Options();
		bfo.inPreferredConfig = Bitmap.Config.RGB_565;

		// Debug
		if (DEBUG) {
			Log.d("ACanvasWriter",
					"Width: " + Integer.toString(mSourceImage.getWidth()));
			Log.d("ACanvasWriter",
					"Height: " + Integer.toString(mSourceImage.getHeight()));
		}

		// Opdater
		updateCanvas();
	}

	/**
	 * Opdaterer vores Canvas
	 */
	public void updateCanvas() {
		mCanvas.drawBitmap(mSourceImage, null, new Rect(0, 0, mScreenWidth,
				mScreenHeight), mtmpPaint);

		for (int i = 0; i < mFaceCollection.Count(); i++) {
			/*Log.d("ACanvasWriter",
					Float.toString(mFaceCollection.getFaces().get(i)
							.getCenterX()));*/
			try {
				if (mFaceCollection.getFaces().get(i) != null) {
					float eyesDistance, cx, cy, x, y, width, height;

					// Finder vores eyeDistance og eyeMidPtc
					eyesDistance = mFaceCollection.getFaces().get(i)
							.getEyeDistance()
							* mXRatio * SQUARE_SIZE;

					// Sæt tykkelsen af stregen af vores ydre kvadrat
					mpOuterBullsEye.setStrokeWidth(eyesDistance / 10);

					// Finder vores centrum i x og y kordinaten
					cx = (mFaceCollection.getFaces().get(i).getCenterX() * mXRatio);
					cy = (mFaceCollection.getFaces().get(i).getCenterY() * mYRatio);

					/*
					 * Vi minusser vores centrum med længden mellem øjnene gange
					 * vores SQUARE_SIZE der definerer hvor stor hvores kvadrat
					 * skal være
					 */
					x = cx - (eyesDistance* 1.3f);
					y = cy - (eyesDistance* 1.2f);

					/*
					 * Plusser vores x/y kordinat med længde mellem øjnene gange
					 * med 2 Vi ganger med 2 fordi at vores x/y har minusset med
					 * længden mellem øjnene og vi skal plusse med længden
					 * mellem øjnene
					 */
					width = x + (eyesDistance * 2 * 1.3f);
					height = y + (eyesDistance * 2 * 1.35f);

					// Debug info
					if (DEBUG) {
						Log.d("ACanvasWriter",
								"Face: " + Float.toString(i) + " Centrum_X: "
										+ Float.toString(cx) + " Centrum_Y: "
										+ Float.toString(cy) + " X: "
										+ Float.toString(x) + " Y: "
										+ Float.toString(y) + " Width: "
										+ Float.toString(width) + " Height: "
										+ Float.toString(height)
										+ " Eyedistance "
										+ Float.toString(eyesDistance));
					}

					// Tegner vores kvadrat og tegner vores midtpunkt
					mCanvas.drawRect(new RectF(x, y, width, height),
							mpOuterBullsEye);
					mCanvas.drawCircle(cx, cy, eyesDistance / 10,
							mpInnerBullsEye);
					Log.d("ACanvasWriter", "Circle(" + cx + "," + cy + ","
							+ (eyesDistance / 10));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//Ansigt nr
		for (int i = 0; i < mFaceCollection.Count(); i++) {
			//Kommentar til noget af koden ovenover
			float eyesDistance, cx, cy, x, y;
			eyesDistance = mFaceCollection.getFaces().get(i)
					.getEyeDistance()
					* mXRatio * SQUARE_SIZE;
			cx = (mFaceCollection.getFaces().get(i).getCenterX() * mXRatio);
			cy = (mFaceCollection.getFaces().get(i).getCenterY() * mYRatio);
			x = cx - (eyesDistance* 1.3f);
			y = cy - (eyesDistance* 1.2f);
			
			if (x < 1)
				x = 1;
			if (y < 1)
				y = 1;
			
			//Slip for at skrive over
			x += 2 + (eyesDistance / 10);
			y += 12 + (eyesDistance / 10);
			
			if (x > mScreenWidth)
				x = mScreenWidth - 3;
			if (y > mScreenWidth)
				y = mScreenWidth - 13;
			
			//Log.d("ACanvasWriterText", "Write number " + x + "," + y);
			try
			{
				mCanvas.drawText(Integer.toString(i + 1), x, y,  mpNumberTextBullsEye);
			}
			catch (Exception ex)
			{
			//	Log.d("ACanvasWriterText", "Write error");
			}
			//mpNumberTextBullsEye
		}
		

		mCanvas.drawText(mFaceCollection.Count() + " personer", 10, 40, mpTextBullsEye);
		
		//Hjælpe tekst
		mCanvas.drawText("Dobbeltklik for at tilføje", 10, mScreenHeight - 60, mpHelpTextBullsEye);
		mCanvas.drawText("Hold inde for at fjerne", 10, mScreenHeight - 40, mpHelpTextBullsEye);
	}

	/**
	 * Opdaterer automatisk canvas
	 * 
	 * @param cx
	 *            Center X
	 * @param cy
	 *            Center Y
	 * @param eyedistance
	 *            Afstand mellem øjnene
	 */
	public void addNewFace(float cx, float cy, float eyedistance) {
		// TODO: TJEK ratio
		DBasicFace face = new DBasicFace(cx/mXRatio, cy/mYRatio, eyedistance);///mXRatio);
		mFaceCollection.add(face);
	}
	
	/**
	 * Udregner et gennemsnit af øjnbreden af kendte ansigter. 
	 * Bruges til størrelse for nye ansigter
	 *  @return Gennemsnits øjnbrede
	 */
	public float calculateAverageEyesDistance() {
		
		//Looper kun igennem, hvis der registeres ansigter
		//Eller sætter den eyesDistance til skærm bredde / 20
		if (mFaceCollection.getFaces().size() > 0) {
			float sum = 0;
			for (DBasicFace face : mFaceCollection.getFaces()) {
				sum += face.getEyeDistance();
			//	Log.v("CanvasWriter",Float.toString(face.getEyeDistance()));
			}
			
			//Log.v("CanvasWriter",Float.toString(mFaceCollection.getFaces().size()));
			float average = sum / (float)mFaceCollection.getFaces().size();
			//Log.v("CanvasWriter",Float.toString(average));
			return average;
		} else {
			return mScreenWidth/20;
		}
	}

	/**
	 * Opdaterer automatisk canvas
	 * 
	 * @param face
	 */
	public void removeFace(DBasicFace face) {
		mFaceCollection.remove(face);
	}

	/**
	 * Anmod om det underliggende billede		Log.d("AFaceView", "Max afstand  " + (int) getWidth()/5);

	 * 
	 * @return
	 */
	public Bitmap getImage() {
		return mSourceImage;
	}

	/**
	 * Anmod om vores Canvas
	 * 
	 * @return
	 */
	public Canvas getCanvas() {
		return mCanvas;
	}

	/**
	 * Få vores underliggende facecollection
	 * 
	 * @return
	 */
	public DBasicFaceCollection getFaceCollection() {
		return mFaceCollection;
	}

	public float getXRatio() {
		return mXRatio;
	}

	public float getYRatio() {
		return mYRatio;
	}

}
