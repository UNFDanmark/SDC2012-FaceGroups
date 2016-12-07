package dk.unf.sdc.gruppeb;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class OneGroupView extends View {

	private ArrayList<ImageView> imgViews = new ArrayList<ImageView>();
	
	public OneGroupView(Context context,ArrayList<DCachedFace> faceList) {
		super(context);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
	            (LayoutParams.WRAP_CONTENT), (LayoutParams.WRAP_CONTENT));
		RelativeLayout relative = new RelativeLayout(context);
		relative.setLayoutParams(lp);
		
		
		//Looper igennem ansigterne, laver et nyt ImageView til det
		for (DCachedFace face : faceList) {
			ImageView tempView = new ImageView(context);
			tempView.setImageBitmap(face.getImage());
			tempView.setLayoutParams(lp);
			relative.addView(tempView);
			imgViews.add(tempView);
		}
		
	}

}
