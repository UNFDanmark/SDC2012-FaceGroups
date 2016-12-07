package dk.unf.sdc.gruppeb;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class GruppeActivity extends ListActivity{

	private int antalGrp;
	private ArrayList<ArrayList<DCachedFace>> groups;
	private ItemsAdapter adapter;
	private CharSequence[] groupsString;
	private ViewFlipper flipper;
	private int currentGroup = 0;
	private GruppeActivity main = this;
	private TextView gruppeView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gruppe);
		gruppeView = (TextView) findViewById(R.id.gruppeNummer);
		ListView list = getListView();

		// Get groups
		groups = new ArrayList<ArrayList<DCachedFace>>();
		Intent grpIntent = getIntent();
		antalGrp = grpIntent.getIntExtra("size", 0);
		groupsString = new CharSequence[antalGrp - 1];
		for (int i = 0; i < antalGrp; i++) {
			ArrayList<DCachedFace> group = grpIntent
					.getParcelableArrayListExtra("gruppe" + i);

			groups.add(group);
		}
		Button nextButton = (Button) findViewById(R.id.nextButton);
		nextButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				currentGroup++;
				if (currentGroup < 0) {
					currentGroup = antalGrp;
				} else if (currentGroup > antalGrp-1){
					currentGroup = 0;
				}
				gruppeView.setText("Gruppe "+(currentGroup+1));
				adapter = new ItemsAdapter(main, R.layout.one_group_layout,
						groups.get(currentGroup));
				adapter.notifyDataSetChanged();
				setListAdapter(adapter);
			}
		});
		Button prevButton = (Button) findViewById(R.id.prevButton);
		prevButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				currentGroup--;
				if (currentGroup < 0) {
					currentGroup = antalGrp-1;
				} else if (currentGroup > antalGrp-1){
					currentGroup = 0;
				}
				gruppeView.setText("Gruppe "+(currentGroup+1));
				adapter = new ItemsAdapter(main, R.layout.one_group_layout,
						groups.get(currentGroup));
				adapter.notifyDataSetChanged();
				setListAdapter(adapter);
			}
		});
		// List Activtistuff:
		adapter = new ItemsAdapter(this, R.layout.one_group_layout,
				groups.get(currentGroup));
		setListAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_gruppe, menu);
		return true;
	}
	

}

class ItemsAdapter extends ArrayAdapter<DCachedFace> {

	private ArrayList<DCachedFace> items;
	private Context context;

	public ItemsAdapter(Context tempContext, int textViewResourceId,
			ArrayList<DCachedFace> items) {
		super(tempContext, textViewResourceId, items);
		this.items = items;
		this.context = tempContext;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.one_group_layout, null);
		}

		DCachedFace it = items.get(position);
		if (it != null) {
			ImageView iv = (ImageView) v.findViewById(R.id.image);
			if (iv != null) {
				iv.setImageBitmap(it.getImage());
			}
		}

		return v;
	}
}
