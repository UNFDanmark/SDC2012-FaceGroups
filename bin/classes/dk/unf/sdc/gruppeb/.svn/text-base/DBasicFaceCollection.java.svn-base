package dk.unf.sdc.gruppeb;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class DBasicFaceCollection implements Parcelable {

	private Bitmap bitmap;
	private ArrayList<DBasicFace> faces;

	public Bitmap getBitmap() {
		return bitmap;
	}

	public ArrayList<DBasicFace> getFaces() {
		return this.faces;
	}

	public DBasicFaceCollection(ArrayList<DBasicFace> faces, Bitmap sourceImage) {
		this.faces = faces;
		this.bitmap = sourceImage;
	}

	private DBasicFaceCollection(Parcel in) {
		// Læser liste fra "pakken" (Parcel) kaldet "in"
		// in.readList(); putter listen over i this.faces
		in.readList(this.faces, null);
		bitmap = in.readParcelable(null);
	}

	public static final Parcelable.Creator CREATOR =
	    	new Parcelable.Creator() {
	            public DBasicFaceCollection createFromParcel(Parcel in) {
	                return new DBasicFaceCollection(in);
	            }
	 
	            public DBasicFaceCollection[] newArray(int size) {
	                return new DBasicFaceCollection[size];
	            }
	        };
	
	/**
	 * @return antallet af ansigter
	 */
	public int Count() {
		return faces.size();
	}

	/**
	 * Tilføj et nyt face
	 * 
	 * @param face
	 *            Face
	 */
	void add(DBasicFace face) {
		faces.add(face);
	}

	/**
	 * Fjerner et face
	 * 
	 * @param face
	 *            Face
	 */
	void remove(DBasicFace face) {
		faces.remove(face);
	}

	/**
	 * 
	 * @param numMembers
	 *            antallet af gruppemedlemmer
	 * @return en liste af liste af ansigter (liste af grupper)
	 */
	public ArrayList<ArrayList<DBasicFace>> tempNavn2(int numMembers) {
		return null;
	}

	// Metoder herunder er krævet af Parcelable

	public int describeContents() {
		// TODO: Find ud af hvad denne metode gør ift. Parcelable
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		// Metoden skriver vores liste af ansigter til et Parcel objekt
		dest.writeList(faces);
		dest.writeParcelable(this.bitmap, 0);
	}
}
