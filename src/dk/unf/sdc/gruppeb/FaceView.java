package dk.unf.sdc.gruppeb;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.FaceDetector;
import android.util.Log;
import android.view.View;

public class FaceView extends View {

	private static final int SQUARE_SIZE = 4;
	private static final int NUM_FACES = 64; // max is 64
	private static final boolean DEBUG = false;

	private FaceDetector arrayFaces;
	private FaceDetector.Face getAllFaces[] = new FaceDetector.Face[NUM_FACES];
	private FaceDetector.Face getFace = null;

	private PointF eyesMidPts[] = new PointF[NUM_FACES];
	private float eyesDistance[] = new float[NUM_FACES];

	private Bitmap sourceImage;

	private Paint tmpPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint pOuterBullsEye = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint pInnerBullsEye = new Paint(Paint.ANTI_ALIAS_FLAG);

	private int picWidth, picHeight;
	private float xRatio, yRatio;

	/**
	 * View med egen canvas som kan tegnes til.
	 * 
	 * @param context
	 * @param sourceImg
	 */
	public FaceView(Context context, Bitmap sourceImg) {
		super(context);

		pInnerBullsEye.setStyle(Paint.Style.FILL);
		pInnerBullsEye.setColor(Color.WHITE);

		pOuterBullsEye.setStyle(Paint.Style.STROKE);
		pOuterBullsEye.setColor(Color.WHITE);

		tmpPaint.setStyle(Paint.Style.STROKE);
		tmpPaint.setTextAlign(Paint.Align.CENTER);

		BitmapFactory.Options bfo = new BitmapFactory.Options();
		bfo.inPreferredConfig = Bitmap.Config.RGB_565;

		sourceImage = sourceImg; // Dynamic loading

		picWidth = sourceImage.getWidth();
		picHeight = sourceImage.getHeight();
		//Log.d("FaceView", "Width: " + Integer.toString(sourceImage.getWidth()));
		/*Log.d("FaceView",
				"Height: " + Integer.toString(sourceImage.getHeight()));*/

		arrayFaces = new FaceDetector(picWidth, picHeight, NUM_FACES);
		arrayFaces.findFaces(sourceImage, getAllFaces);

		for (int i = 0; i < getAllFaces.length; i++) {
			getFace = getAllFaces[i];
			try {
				PointF eyesMP = new PointF();
				getFace.getMidPoint(eyesMP);
				eyesDistance[i] = getFace.eyesDistance();
				eyesMidPts[i] = eyesMP;

				if (DEBUG) {
					Log.d("FaceView", i + " " + getFace.confidence() + " "
							+ getFace.eyesDistance() + " " + "Pose: ("
							+ getFace.pose(FaceDetector.Face.EULER_X) + ","
							+ getFace.pose(FaceDetector.Face.EULER_Y) + ","
							+ getFace.pose(FaceDetector.Face.EULER_Z) + ")"
							+ "Eyes Midpoint: (" + eyesMidPts[i].x + ","
							+ eyesMidPts[i].y + ")");
				}
			} catch (Exception e) {

			}

		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// Ratio i forhold til hvor meget billedet er blevet compressed
		xRatio = getWidth() * 1.0f / picWidth;
		yRatio = getHeight() * 1.0f / picHeight;

		// Importer vores bitmap i vores canvas
		canvas.drawBitmap(sourceImage, null, new Rect(0, 0, getWidth(),
				getHeight()), tmpPaint);
		for (int i = 0; i < eyesMidPts.length; i++) {
			if (eyesMidPts[i] != null) {
				// Sæt tykkelsen af stregen af vores ydre kvadrat
				pOuterBullsEye.setStrokeWidth(eyesDistance[i] / 6);

				// Finder vores centrum i x og y kordinaten
				float cx = (eyesMidPts[i].x * xRatio);
				float cy = (eyesMidPts[i].y * yRatio);

				/*
				 * Vi minusser vores centrum med længden mellem øjnene gange
				 * vores SQUARE_SIZE der definerer hvor stor hvores kvadrat skal
				 * være
				 */
				float x = cx - (eyesDistance[i] * SQUARE_SIZE);
				float y = cy - (eyesDistance[i] * SQUARE_SIZE);

				/*
				 * Plusser vores x/y kordinat med længde mellem øjnene gange med
				 * 2 Vi ganger med 2 fordi at vores x/y har minusset med længden
				 * mellem øjnene og vi skal plusse med længden mellem øjnene
				 */
				float width = x + (eyesDistance[i] * SQUARE_SIZE * 2);
				float height = y + (eyesDistance[i] * SQUARE_SIZE * 2);

				// Debug info
				if (DEBUG) {
					Log.d("FaceView", Float.toString(eyesDistance[i]));
					Log.d("FaceView", "Face: " + Float.toString(i)
							+ " Centrum_X: " + Float.toString(cx)
							+ " Centrum_Y: " + Float.toString(cy) + " X: "
							+ Float.toString(x) + " Y: " + Float.toString(y)
							+ " Width: " + Float.toString(width) + " Height: "
							+ Float.toString(height));
				}

				// Tegner vores kvadrat og tegner vores midtpunkt
				canvas.drawRect(new RectF(x, y, width, height), pOuterBullsEye);
				canvas.drawCircle(cx, cy, eyesDistance[i] / 6, pInnerBullsEye);
			}
		}
	}
}
