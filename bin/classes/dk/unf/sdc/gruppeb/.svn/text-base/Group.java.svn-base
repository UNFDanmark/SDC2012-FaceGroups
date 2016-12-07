package dk.unf.sdc.gruppeb;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Group implements Parcelable {
	
	private ArrayList<DCachedFace> faces;
	
	public Group(ArrayList<DCachedFace> faces) {
		this.faces = faces;
	}
	
	public Group(int index) {
		this.faces = new ArrayList<DCachedFace>();
	}

	private Group(Parcel in) {
		faces = new ArrayList<DCachedFace>();
		in.readList(faces, faces.getClass().getClassLoader());
	}
	

	public ArrayList<DCachedFace> getFaces() {
		return faces;
	}

	public void add(DCachedFace face) {
		getFaces().add(face);
	}
	
	public static final Parcelable.Creator CREATOR =
	    	new Parcelable.Creator() {
	            public Group createFromParcel(Parcel in) {
	                return new Group(in);
	            }
	 
	            public Group[] newArray(int size) {
	                return new Group[size];
	            }
	        };
	
	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeList(faces);
	}
}
