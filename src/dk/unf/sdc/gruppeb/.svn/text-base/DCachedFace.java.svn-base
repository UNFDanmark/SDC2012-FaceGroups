package dk.unf.sdc.gruppeb;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class DCachedFace extends DBasicFace implements Parcelable {

	private Bitmap image;

	public DCachedFace(DBasicFace originalFace,Bitmap img) {
		super(originalFace.getCenterX(),originalFace.getCenterY(),originalFace.getEyeDistance());
		image = img;
	}

	private DCachedFace(Parcel in) {
		super(in);
		this.image = (Bitmap) in.readParcelable(Bitmap.class.getClassLoader());
	}

	public static final Parcelable.Creator CREATOR =
	    	new Parcelable.Creator() {
	            public DCachedFace createFromParcel(Parcel in) {
	                return new DCachedFace(in);
	            }
	 
	            public DCachedFace[] newArray(int size) {
	                return new DCachedFace[size];
	            }
	        };
	
	/**
	 * @return billedet, der hører til ansigtet
	 */
	public Bitmap getImage() {
		return image;
	}

	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		//Log.d("DCachedFace", Boolean.toString(getImage() != null));
		dest.writeParcelable(this.getImage(), 0);
	}
}
