package dk.unf.sdc.gruppeb;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.util.Log;

public class ABecalculate {
	private static final int NUM_FACES = 64; // max is 64
	private static final boolean DEBUG = false;

	private DBasicFaceCollection mOutput;

	private FaceDetector arrayFaces;
	private FaceDetector.Face getAllFaces[] = new FaceDetector.Face[NUM_FACES];
	private FaceDetector.Face getFace = null;

	private PointF eyesMidPts[] = new PointF[NUM_FACES];
	private float eyesDistance[] = new float[NUM_FACES];

	private Bitmap sourceImage;
	private int picWidth, picHeight;

	/**
	 * Finder alle ansigter på et givent billede og laver et output i
	 * FaceCollection Output læses vha. getOutput
	 * 
	 * @param sourceImg
	 *            Billede der skal behandles
	 */
	public ABecalculate(Bitmap sourceImg) {
		// SLET IKKE - udkommenter i tilfælde af fejl
		BitmapFactory.Options bfo = new BitmapFactory.Options();
		bfo.inPreferredConfig = Bitmap.Config.RGB_565;

		sourceImage = sourceImg;

		// Højde/Bredde defination
		picWidth = sourceImage.getWidth();
		picHeight = sourceImage.getHeight();
		if (DEBUG) {
			Log.d("ABecalculate",
					"PicWidth: " + Integer.toString(picWidth));
			Log.d("ABecalculate",
					"PicHeight: " + Integer.toString(picHeight));
		}

		// Find ansigter
		arrayFaces = new FaceDetector(picWidth, picHeight, NUM_FACES);
		arrayFaces.findFaces(sourceImage, getAllFaces);

		if (DEBUG)
		{
			Log.d("ABecalculate", "Faces number: " + getAllFaces.length);
		}
		
		// Loop igennem alle ansigterne
		ArrayList<DBasicFace> arr = new ArrayList<DBasicFace>();
		for (int i = 0; i < getAllFaces.length; i++) {
			getFace = getAllFaces[i];
			try {		
				// Øjne midtpunkt
				PointF eyesMP = new PointF();
				getFace.getMidPoint(eyesMP);
				eyesMidPts[i] = eyesMP;

				// Øje afstand
				eyesDistance[i] = getFace.eyesDistance();
				
				// Finder vores centrum i x og y kordinaten - vi har ikke ratio
				// med
				float cx = eyesMP.x;
				float cy = eyesMP.y;
				
				// Afstand mellem øjnene
				float eyedist = eyesDistance[i];

				// Opret BasicFace og tilføj til vores ArrayList
				DBasicFace tmp = new DBasicFace(cx, cy, eyedist);
				arr.add(tmp);
				
				if (DEBUG) {
					Log.d("ABecalculate", i + " " + getFace.confidence() * 100
							+ "% " + getFace.eyesDistance() + " " + "Pose: ("
							+ getFace.pose(FaceDetector.Face.EULER_X) + ","
							+ getFace.pose(FaceDetector.Face.EULER_Y) + ","
							+ getFace.pose(FaceDetector.Face.EULER_Z) + ")"
							+ "Eyes Midpoint: (" + eyesMidPts[i].x + ","
							+ eyesMidPts[i].y + ")");
				}
			} catch (Exception e) {
				// Ansigtet ikke defineret
				Log.d("ABecalculate", i + " not defined");
			}

		}

		// Sæt resultat
		mOutput = new DBasicFaceCollection(arr, sourceImage);
	}

	public DBasicFaceCollection getOutput() {
		return mOutput;
	}

}
