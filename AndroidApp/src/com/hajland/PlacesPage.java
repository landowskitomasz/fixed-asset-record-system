package com.hajland;

import java.util.List;

import com.hajland.logic.Engine;
import com.hajland.models.Place;
import android.widget.AdapterView.OnItemClickListener;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;

public class PlacesPage extends ListActivity  {


	private List<Place> placesList;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.places);
        
        this.placesList = Engine.getInstance().getMapper().getPlaces();

        setListAdapter(new ArrayAdapter<Place>(this, R.layout.places_list_item, this.placesList));
        
        ListView lv = getListView();
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
