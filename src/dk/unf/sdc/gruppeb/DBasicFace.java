package dk.unf.sdc.gruppeb;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;

public class DBasicFace implements Parcelable {

	public static final int SQUARE_SIZE = 4;
	private float cX;
	private float cY;
	private float eyeDistance;
	private Rect rectangle;

	public DBasicFace(float centerX, float centerY, float eyeDistance) {
		this.cX = centerX;
		this.cY = centerY;
		this.eyeDistance = eyeDistance;
		float x = cX - (eyeDistance * 1.3f);
		float y = cY - (eyeDistance * 1.2f);
		float width = x + (eyeDistance * 2 * 1.3f);
		float height = y + (eyeDistance * 2 * 1.35f);

		rectangle = new Rect((int) (x), (int) (y), (int) (width),
				(int) (height));

	}

	protected DBasicFace(Parcel in) {
		cX = in.readFloat();
		cY = in.readFloat();
		eyeDistance = in.readFloat();
		rectangle = (Rect) in.readParcelable(null);	
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public DBasicFace createFromParcel(Parcel in) {
			return new DBasicFace(in);
		}

		public DBasicFace[] newArray(int size) {
			return new DBasicFace[size];
		}
	};

	protected void setCenterX(float cX) {
		this.cX = cX;
	}

	protected void setCenterY(float cY) {
		this.cY = cY;
	}

	protected void setEyeDistance(float eyeDistance) {
		this.eyeDistance = eyeDistance;
	}

	protected void setRectangle(Rect rectangle) {
		this.rectangle = rectangle;
	}

	/**
	 * @return the cX
	 */
	public float getCenterX() {
		return cX;
	}

	/**
	 * @return the cY
	 */
	public float getCenterY() {
		return cY;
	}

	/**
	 * @return the eyeDistance
	 */
	public float getEyeDistance() {
		return eyeDistance;
	}

	public Rect getRectangle() {
		return rectangle;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeFloat(this.getCenterX());
		dest.writeFloat(this.getCenterY());
		dest.writeFloat(this.getEyeDistance());
		dest.writeParcelable(this.getRectangle(), 0);
	}
}
