package dk.unf.sdc.gruppeb;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Bitmap;
import android.util.Log;

public class DCachedFaceList extends DBasicFaceCollection {

	public DCachedFaceList(DBasicFaceCollection faces, Bitmap sourceImage) {
		super(faces.getFaces(), sourceImage);
		cachedFaces = new ArrayList<DCachedFace>();
	}

	public static ArrayList<ArrayList<DCachedFace>> grosAlgoritme(
			ArrayList<DCachedFace> faces, int numGroups) {
		Random random = new Random();
		ArrayList<DCachedFace> kopiAfFaces = new ArrayList<DCachedFace>(faces);
		ArrayList<ArrayList<DCachedFace>> groups = new ArrayList<ArrayList<DCachedFace>>();

		for (int i = 0; i < numGroups; i++) {
			groups.add(new ArrayList<DCachedFace>());
		}
		//Log.i("DCachedFaceList", "Antal grupper: " + groups.size());

		while (kopiAfFaces.size() > 0) {
			for (int i = 0; i < numGroups; i++) {
				if (kopiAfFaces.size() > 0) {
					//Log.i("DCachedFaceList", "Size: " + kopiAfFaces.size());
					int next = kopiAfFaces.size();
					if (next > 1) {
						next--;
					}

					int randomTal = random.nextInt(next);
					DCachedFace randomPerson = kopiAfFaces.get(randomTal);
					ArrayList<DCachedFace> group = groups.get(i);

					group.add(randomPerson);
					kopiAfFaces.remove(randomPerson);
				}
			}
		}

		/*for (int i = 0; i < groups.size(); i++) {
			Log.i("DCachedFaceList", "Gruppe " + (i + 1) + " har "
					+ groups.get(i).size() + " medlemmer");
		}*/

		return groups;
	}

	public static ArrayList<ArrayList<DCachedFace>> madsAlgoritme(
			ArrayList<DCachedFace> faces, int antalPersPG) {
		int antalPers = faces.size();
		int antalGrp = antalPers / antalPersPG;
		int antalRest = antalPers % antalPersPG;

		Random random = new Random();
		ArrayList<ArrayList<DCachedFace>> groups = new ArrayList<ArrayList<DCachedFace>>();
		ArrayList<DCachedFace> kopiAfFaces = new ArrayList<DCachedFace>(faces);
		// Restgruppe
		if (antalRest > 0) {
			ArrayList<DCachedFace> restGruppe = new ArrayList<DCachedFace>();
			
			for (int x = 0; x < antalRest; x++) {

				
				int randomTal = random.nextInt(kopiAfFaces.size() - 1);
				DCachedFace randomPerson = kopiAfFaces.get(randomTal);
				
				restGruppe.add(randomPerson);
				kopiAfFaces.remove(randomPerson);
			}
			
			groups.add(restGruppe);
		}
		
		for (int i = 0; i < antalGrp; i++) {
			ArrayList<DCachedFace> group = new ArrayList<DCachedFace>();
			for (int j = 0; j < antalPersPG; j++) {
				//Log.i("DCachedFaceList", "Size: " + kopiAfFaces.size());
				int next = kopiAfFaces.size();
				if (next > 1) {
					next--;
				}
				
				int randomTal = random.nextInt(next);
				DCachedFace randomPerson = kopiAfFaces.get(randomTal);

				group.add(randomPerson);
				kopiAfFaces.remove(randomPerson);
			}
			groups.add(group);
		}
		
		return groups;
	}

	public void Crop() {
		for (DBasicFace face : this.getFaces()) {
			int x = face.getRectangle().left;
			int y = face.getRectangle().top;

			int width = face.getRectangle().width();
			int height = face.getRectangle().height();
			boolean needScaling = false;

			// Check if some of the face is outside the image
			if (x <= 0) {
				x = 0;
				needScaling = true;
			} else if (x + width >= getBitmap().getWidth()) {
				x = getBitmap().getWidth() - width;
				needScaling = true;
			}
			if (y <= 0) {
				y = 0;
				needScaling = true;
			} else if (y + height >= getBitmap().getHeight()) {
				y = getBitmap().getHeight() - height;
				needScaling = true;
			}
			Bitmap tempBitmap = Bitmap.createBitmap(getBitmap(), x, y, width,
					height);
			if (needScaling) {
				tempBitmap = Bitmap.createScaledBitmap(tempBitmap, face
						.getRectangle().width(), face.getRectangle().height(),
						true);
			}
			DCachedFace tempCachedFace = new DCachedFace(face, tempBitmap);
			cachedFaces.add(tempCachedFace);
		}
	}

	private ArrayList<DCachedFace> cachedFaces;

	public ArrayList<DCachedFace> getCachedFaces() {
		return cachedFaces;
	}
}
