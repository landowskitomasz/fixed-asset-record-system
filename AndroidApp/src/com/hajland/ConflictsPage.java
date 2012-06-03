package com.hajland;

import java.util.ArrayList;
import java.util.List;
import com.hajland.logic.ConflictItem;
import com.hajland.logic.Engine;
import com.hajland.logic.Settings;
import com.hajland.logic.SynchronizationStatus;
import com.hajland.logic.SynchronizeCallback;
import com.hajland.models.Employee;
import com.hajland.models.Equipment;
import com.hajland.models.Mapping;
import com.hajland.models.Place;
import com.mobeelizer.mobile.android.Mobeelizer;
import com.mobeelizer.mobile.android.api.MobeelizerDatabase;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ConflictsPage  extends Activity {	
	private List<ConflictItem> mappings = new ArrayList<ConflictItem>();
    private ProgressDialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conflicts);
        Engine.getInstance().lookForConflicts();
        refreshConflictsList();
    }
    
    private void refreshConflictsList()
    {
    	MobeelizerDatabase database = Mobeelizer.getDatabase();
    	mappings.clear();
    	List<Mapping> employees= Engine.getInstance().getEmployeeInConflictList();
    	List<Mapping> equipmentList = Engine.getInstance().getEquipmentInConflictList();
    	
    	for(Mapping mapping: employees)
    	{
    		ConflictItem item = new ConflictItem();
    		Employee employee = database.get(Employee.class, mapping.getEmployee());
    		Place place = database.get(Place.class, mapping.getPlace());
    		item.setItemHeader(employee.getName() + " " + employee.getSurname() + " ("+ employee.getEmail()+") - " +place.getFloor() +""+place.getRoomNumber()+" ("+place.getBuilding() + " "+place.getCity()+")" + "\nData dodania: "+ mapping.getCreationDate().toLocaleString());
    		item.setMapping(mapping);
    		mappings.add(item);
    	}
    	
    	for(Mapping mapping: equipmentList)
    	{
    		ConflictItem item = new ConflictItem();
    		Employee employee = database.get(Employee.class, mapping.getEmployee());
    		Equipment equipment = database.get(Equipment.class, mapping.getEquipment());
    		item.setMapping(mapping);
    		item.setItemHeader(equipment.getModel() + " " + equipment.getBrand() + " ("+equipment.getSerialNumer()+ ") - " + employee.getName() + " " + employee.getSurname() + " ("+ employee.getEmail()+")"+ "\nData dodania: "+ mapping.getCreationDate().toLocaleString());
    		mappings.add(item);
    	}
    	ListView listView = (ListView)this.findViewById(R.id.conflictsListView);
	    listView.setAdapter(new ArrayAdapter<ConflictItem>(this, R.layout.places_list_item, mappings));
	    listView.setTextFilterEnabled(true);
    	if(this.mappings.size() == 0)
    	{
    		Button removeButton = (Button)this.findViewById(R.id.removeMappingButton);
    		removeButton.setText("ZartwierdŸ rozwi¹zanie");
    	}    
    }
    
    public void onRemoveMapping(View view)
    {
    	if(this.mappings.size() == 0)
    	{
        	dialog = ProgressDialog.show(this, "Wysy³anie", "Proszê czekaj, trwa wysy³anie rozwi¹zania...");
    		Engine.getInstance().synchronize(new SynchronizeCallback(){
    		public void onFinished(SynchronizationStatus status)
    		{
    			dialog.cancel();
    			if(status == SynchronizationStatus.SUCCESS)
    			{
    				Settings.getInstance().setConflictStatus(false);
    	    		Button removeButton = (Button)ConflictsPage.this.findViewById(R.id.removeMappingButton);
    	    		removeButton.setEnabled(false);
    				MessageBox.Show("Sukces", "Rozwi¹zanie zatwierdzone.", ButtonType.Ok, ConflictsPage.this, null);
    			}
    			else if(status == SynchronizationStatus.CONFLICTS)
    			{
    		    	ConflictsPage.this.refreshConflictsList();
    			}
    			else if(status == SynchronizationStatus.FATALERROR)
    			{
    				MessageBox.Show("B³¹d", "Nie uda³o siê wys³aæ rozwi¹zania, spróbuje ponownie póŸniej.", ButtonType.Ok, ConflictsPage.this, null);
    			}
    		}
    	});
    	}
    	else
    	{
	    	final CharSequence[] items = new  CharSequence[mappings.size()];
	    	for(int i =0; i< mappings.size(); ++i)
	    	{
	    		items[i] = mappings.get(i).toString();
	    	}
	    	
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Dodaj pracownika");
		
			builder.setItems(items, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
			    	removeMapping(mappings.get(item).getMapping());
			    }
			});
			AlertDialog alert = builder.create();
			alert.show();
    	}
    }
    
    private void removeMapping(Mapping mapping)
    {
    	Log.i("ConflictsPage", "remove "+ mapping.getGuid());
    	Engine.getInstance().getMapper().removeMapping(mapping);
    	Engine.getInstance().lookForConflicts();
    	this.refreshConflictsList();
    }
}
