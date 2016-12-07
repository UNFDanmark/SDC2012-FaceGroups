package dk.unf.sdc.gruppeb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class IndstillingerActivity extends Activity {
	private int text_size = 24;

	private int antalPers = 1; // Total antal personer
	private int antalPersPG = 1; // pers. per gruppe
	private int antalGrp = 1; // Antal grupper
	private int antalRest = 1;

	private SeekBar slider;

	private Button sliderVBut; // knapper på hhv. venstre og højre side af
	private Button sliderHBut;
	private Button nextABut; // knap som starter næste activity

	/*
	 * private TextView antalGrpDyn; // xxxDyn = tekst output til GUI private
	 * TextView antalGrpFix; private TextView antalPersDyn; private TextView
	 * antalPersFix; private TextView antalRestDyn; private TextView
	 * antalRestFix;
	 * 
	 * private TextView antalGrpDyn1; private TextView antalGrpFix1; private
	 * TextView antalPersDyn1; private TextView antalPersFix1;
	 * 
	 * private TextView antalGrpDyn2; private TextView antalGrpFix2; private
	 * TextView antalPersDyn2; private TextView antalPersFix2;
	 */

	private LinearLayout textViewRoot;

	private RadioButton persRadioBut;
	private RadioButton grpRadioBut;
	private Boolean grpValgtRB = true;
	private ArrayList<ArrayList<DCachedFace>> groups;

	private ArrayList<DCachedFace> faceList;

	// RadioButtons
	private OnClickListener radioButListen = new OnClickListener() {

		public void onClick(View v) {
			if (v.getId() == grpRadioBut.getId()) {
				grpValgtRB = true;
			}
			if (v.getId() == persRadioBut.getId()) {
				grpValgtRB = false;
			}
			updateTxt(slider.getProgress());
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.indstillinger);

		Intent imported = getIntent();
		Bundle data = imported.getExtras();
		data.setClassLoader(getClassLoader());
		faceList = imported.getParcelableArrayListExtra("faces");

		text_size = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 20, getResources()
						.getDisplayMetrics());

		antalPers = faceList.size();

		//Log.d("IndstillingerActivity", antalPers + "");

		slider = (SeekBar) findViewById(R.id.indstSlider);
		slider.setMax(antalPers - 1);

		//Log.d("IndstillingerActivity", "test");

		// this.textViewRoot = (LinearLayout) findViewById(R.id.textViewRoot);
		sliderVBut = (Button) findViewById(R.id.sliderVBut);
		sliderHBut = (Button) findViewById(R.id.sliderHBut);

		persRadioBut = (RadioButton) findViewById(R.id.persRadioBut);
		grpRadioBut = (RadioButton) findViewById(R.id.grpRadioBut);

		nextABut = (Button) findViewById(R.id.nextABut);

		/*
		 * antalGrpDyn = (TextView) findViewById(R.id.indstAntalGrpDyn);
		 * antalGrpDyn.setText(""); antalGrpFix = (TextView)
		 * findViewById(R.id.indstAntalGrpFix); antalGrpFix.setText("");
		 * antalPersDyn = (TextView) findViewById(R.id.indstAntalPersDyn);
		 * antalPersDyn.setText(""); antalPersFix = (TextView)
		 * findViewById(R.id.indstAntalPersFix); antalPersFix.setText("");
		 * antalRestDyn = (TextView) findViewById(R.id.indstAntalRestDyn);
		 * antalRestDyn.setText(""); antalRestFix = (TextView)
		 * findViewById(R.id.indstAntalRestFix); antalRestFix.setText("");
		 * antalRestDyn.setText("");
		 * 
		 * antalGrpDyn1 = (TextView) findViewById(R.id.indstAntalGrpDyn1);
		 * antalGrpFix1 = (TextView) findViewById(R.id.indstAntalGrpFix1);
		 * antalPersDyn1 = (TextView) findViewById(R.id.indstAntalPersDyn1);
		 * antalPersFix1 = (TextView) findViewById(R.id.indstAntalPersFix1);
		 * 
		 * antalGrpDyn2 = (TextView) findViewById(R.id.indstAntalGrpDyn2);
		 * antalGrpFix2 = (TextView) findViewById(R.id.indstAntalGrpFix2);
		 * antalPersDyn2 = (TextView) findViewById(R.id.indstAntalPersDyn2);
		 * antalPersFix2 = (TextView) findViewById(R.id.indstAntalPersFix2);
		 * 
		 * // antalGrpDyn1.setTeandroid:paddingTopxt("1");
		 * antalGrpFix1.setText(" gruppe med ");
		 * antalPersDyn1.setText(Integer.toString(antalPers));
		 * antalPersFix1.setText(" personer");
		 * 
		 * antalGrpDyn2.setText(""); antalGrpFix2.setText("");
		 * antalPersDyn2.setText(""); antalPersFix2.setText("");
		 */

		textViewRoot = (LinearLayout) findViewById(R.id.textViewRoot);

		persRadioBut.setOnClickListener(radioButListen);
		grpRadioBut.setOnClickListener(radioButListen);

		// Knap til venstre
		OnClickListener vBut = new OnClickListener() {
			public void onClick(View v) {
				if (slider.getProgress() > 0)
					slider.setProgress(slider.getProgress() - 1);
			}
		};
		sliderVBut.setOnClickListener(vBut);

		// Knap til højre
		OnClickListener hBut = new OnClickListener() {
			public void onClick(View v) {
				if (slider.getProgress() < slider.getMax()) {
					slider.setProgress(slider.getProgress() + 1);
				}
			}
		};
		sliderHBut.setOnClickListener(hBut);

		// slider
		slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			public void onStopTrackingTouch(SeekBar seekBar) {

			}

			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				updateTxt(progress);
			}
		});

		// Knap som åbner gruppeActivity
		OnClickListener listenerNextABut = new OnClickListener() {
			public void onClick(View v) {
				Intent gruppeIntent = new Intent(getApplicationContext(),
						GruppeActivity.class);

				for (int i = 0; i < groups.size(); i++) {
					gruppeIntent.putParcelableArrayListExtra("gruppe" + i,
							groups.get(i));
				}
				gruppeIntent.putExtra("size", groups.size());

				startActivity(gruppeIntent);

			}
		};
		nextABut.setOnClickListener(listenerNextABut);

		// slider.setProgress(0);
		updateTxt(0);

	}

	private void updateTxt(int progress) {
		textViewRoot.removeAllViews();
		// Korrigér for lorte android slider
		progress = progress + 1;

		ArrayList<ArrayList<DCachedFace>> grupper = new ArrayList<ArrayList<DCachedFace>>();

		// Antal grupper
		if (grpValgtRB) {
			grupper = DCachedFaceList.grosAlgoritme(faceList, progress);
		}
		// Antal personer pr. gruppe
		else {

			grupper = DCachedFaceList.madsAlgoritme(faceList, progress);
		}

		if (grupper.isEmpty()) {
			// pis
			Log.e("IndstillingerActivity", "Jeg har ikke skabt nogen grupper.");
			return;
		}
		groups = grupper;

		HashMap<Integer, Integer> groupSizes = new HashMap<Integer, Integer>();
		for (ArrayList<DCachedFace> group : grupper) {
			int groupSize = group.size();

			if (groupSizes.containsKey(groupSize)) {
				int numGroups = groupSizes.get(groupSize);
				numGroups++;
				groupSizes.put(groupSize, numGroups);
			} else {
				if (groupSize > 0) {
					groupSizes.put(groupSize, 1);
				}
			}
		}

		Iterator<Entry<Integer, Integer>> it = groupSizes.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			TextView textView = new TextView(getApplicationContext());

			String gruppeString = "" + pairs.getValue() + " grupper á "
					+ pairs.getKey() + " personer";
			textView.setText(gruppeString);
			textView.setTextSize(text_size);
			textView.setTextColor(Color.BLACK);
			textViewRoot.addView(textView);
			it.remove(); // avoids a ConcurrentModificationException
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.indstillinger, menu);
		return true;
	}

}
