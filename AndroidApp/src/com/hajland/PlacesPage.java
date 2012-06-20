package com.hajland;

import java.util.List;

import com.hajland.adapters.PlaceArrayAdapter;
import com.hajland.logic.Engine;
import com.hajland.models.Place;
import android.widget.AdapterView.OnItemClickListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.View;

public class PlacesPage extends Activity  {


	private List<Place> placesList;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.places);
        
        this.placesList = Engine.getInstance().getMapper().getPlaces();
        
        ListView lv = (ListView)this.findViewById(R.id.placesListView);
        lv.setAdapter(new PlaceArrayAdapter(this, this.placesList));
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
          {
        	Place place = placesList.get((int)id);
          	Intent sec = new Intent(PlacesPage.this, EmpolyeesPage.class);
          	Bundle b = new Bundle();
          	b.putInt("placeId", place.getId());
          	sec.putExtras(b);
          	startActivity(sec);
          }
        });
    }
}
