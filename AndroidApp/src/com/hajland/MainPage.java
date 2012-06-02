package com.hajland;

import com.hajland.logic.Engine;
import com.hajland.logic.LoginCallback;
import com.mobeelizer.mobile.android.api.MobeelizerLoginStatus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainPage extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.disableButtons();
        Engine.getInstance().login(new LoginCallback(){
        	public void onLoginFinished(final MobeelizerLoginStatus status)
        	{
        		if(status == MobeelizerLoginStatus.OK)
        		{
	        		MessageBox.ShowToast("Login finished!", getApplicationContext());
	        		enableButtons();
        		}
        		else
        		{
        			MessageBox.ShowToast("Login failed!", getApplicationContext());
        		}
        	}
        });
    }
    
    public void onBeginWork(View target) 
    {
    	Intent sec = new Intent(MainPage.this, PlacesPage.class);
    	startActivity(sec);
    }
    
    public void onSynchronize(View target) 
    {
    /*	MobeelizerSyncStatus allstat =  Mobeelizer.syncAll();
		MessageBox.ShowToast(" "+ allstat, getApplicationContext());
		
    	MobeelizerDatabase database = Mobeelizer.getDatabase();
    	Employee employee = new Employee();
    	employee.setId(1);
    	employee.setEmail("landowskitomasz@gmail.com");
    	employee.setName("Tomasz");
    	employee.setSurname("Landowski");
    	employee.setDateOfBirth(new Date(Date.UTC(1989, 8, 31, 0, 0, 0)));
    	employee.setPesel(89083112);
    	employee.setPlaceOfBirth("Nowy Targ");
    	database.save(employee);
    	employee = new Employee();
    	employee.setId(2);
    	employee.setEmail("mhajduczek@gmail.com");
    	employee.setName("Marcin");
    	employee.setSurname("Hajduczek");
    	employee.setDateOfBirth(new Date(Date.UTC(1989, 3, 12, 0, 0, 0)));
    	employee.setPesel(89012112);
    	employee.setPlaceOfBirth("Leszczyny");
    	database.save(employee);
    	
    	Place place = new Place();
    	place.setId(1);
    	place.setCity("Kraków");
    	place.setBuilding("SSE1");
    	place.setCountry("Polska");
    	place.setFloor(0);
    	place.setPostalCode("34-400");
    	place.setProvince("Ma³opolska");
    	place.setRoomNumber(13);
    	place.setStreet("Jana Paw³a II");
    	database.save(place);
    	place = new Place();
    	place.setId(2);
    	place.setCity("Kraków");
    	place.setBuilding("SSE1");
    	place.setCountry("Polska");
    	place.setFloor(0);
    	place.setPostalCode("34-400");
    	place.setProvince("Ma³opolska");
    	place.setRoomNumber(14);
    	place.setStreet("Jana Paw³a II");
    	database.save(place);
    	place = new Place();
    	place.setId(3);
    	place.setCity("Kraków");
    	place.setBuilding("SSE1");
    	place.setCountry("Polska");
    	place.setFloor(0);
    	place.setPostalCode("34-400");
    	place.setProvince("Ma³opolska");
    	place.setRoomNumber(15);
    	place.setStreet("Jana Paw³a II");
    	database.save(place);
    	place = new Place();
    	place.setId(4);
    	place.setCity("Kraków");
    	place.setBuilding("SSE2");
    	place.setCountry("Polska");
    	place.setFloor(0);
    	place.setPostalCode("34-400");
    	place.setProvince("Ma³opolska");
    	place.setRoomNumber(134);
    	place.setStreet("Jana Paw³a II");
    	database.save(place);
    	
    	Equipment equipment = new Equipment();
    	equipment.setId(1);
    	equipment.setBrand("AMD");
    	equipment.setModel("PC");
    	equipment.setSerialNumer("SF5SDF345DSGF5");
    	database.save(equipment);
    	equipment = new Equipment();
    	equipment.setId(2);
    	equipment.setBrand("AMD");
    	equipment.setModel("Monitor");
    	equipment.setSerialNumer("SF5SHFG45DSGF5");
    	database.save(equipment);
    	
    	User user = new User();
    	user.setId(1);
    	user.setLogin("tomasz.landowski");
    	user.setName("Tomasz");
    	user.setSurname("Landowski");
    	database.save(user);
    	user = new User();
    	user.setId(2);
    	user.setLogin("hajduczek.marcin");
    	user.setName("Marcin");
    	user.setSurname("Hajduczek");
    	database.save(user);
    	
    	MobeelizerSyncStatus stat =  Mobeelizer.sync();
    	if(stat == MobeelizerSyncStatus.FINISHED_WITH_SUCCESS)
    	{
    		MessageBox.ShowToast("Synchoronized!", getApplicationContext());
    	}
    	else
    	{
    		MobeelizerSyncStatus status =  Mobeelizer.checkSyncStatus();
    		MessageBox.ShowToast("Failed: " + stat, getApplicationContext());
    	}*/
    }
    
    public void onClear(View target) 
    {
		MessageBox.Show("Uwaga", "Jesteœ pewny ¿e chcesz wyczyœciæ œrodowisko pracy? To usunie wszystkie dotychczasowe zmiany.", ButtonType.YesNo, this, new MessageBoxCallback(){
			public void onFinished(MessageBoxResult result)
			{
				
			}
		});
    }
    
    private void disableButtons()
    {
    	Button begin = (Button)this.findViewById(R.id.beginButton);
    	Button clear = (Button)this.findViewById(R.id.clearButton);
    	Button synchronize = (Button)this.findViewById(R.id.synchronizeButton);
    	begin.setEnabled(false);
    	clear.setEnabled(false);
    	synchronize.setEnabled(false);
    }
    
    private void enableButtons()
    {
    	Button begin = (Button)this.findViewById(R.id.beginButton);
    	Button clear = (Button)this.findViewById(R.id.clearButton);
    	Button synchronize = (Button)this.findViewById(R.id.synchronizeButton);
    	begin.setEnabled(true);
    	clear.setEnabled(true);
    	synchronize.setEnabled(true);
    
    }
}