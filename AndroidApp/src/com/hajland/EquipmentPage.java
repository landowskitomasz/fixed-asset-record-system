package com.hajland;

import java.util.ArrayList;
import java.util.List;

import com.hajland.adapters.EquipmentArrayAdapter;
import com.hajland.logic.Engine;
import com.hajland.models.Employee;
import com.hajland.models.Equipment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class EquipmentPage extends Activity {
	
	private Employee employee;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equipment);
        
        Bundle b = getIntent().getExtras();
        int employeeID = b.getInt("employeeId", 0);
        Log.i("EquipmentPage", "Id =" + employeeID);
        this.employee = Engine.getInstance().getMapper().getEmployee(employeeID);
        setPageTitle();
        List<Equipment> equipmentList = Engine.getInstance().getMapper().findEmployeeEquipment(this.employee);
        this.fillEquipmentList(equipmentList);
    }

    public void onRemoveEquipment(View target)
    {
    	final List<Equipment> equipmentList = Engine.getInstance().getMapper().findEmployeeEquipment(this.employee);
    	final CharSequence[] items = new  CharSequence[equipmentList.size()];
    	for(int i =0; i< equipmentList.size(); ++i)
    	{
    		Equipment equipment = equipmentList.get(i);
    		String item = equipment.getModel()+ " " + equipment.getBrand() + "\n"+ equipment.getSerialNumer(); 
    		items[i] = item;
    	}
    	
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Usuñ sprzêt");
	
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	Engine.getInstance().getMapper().removeMapping(employee, equipmentList.get(item));
		    	refreshEquipmentList();
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
    }
    
    public void onAddEquipment(View target)
    {
    	final List<Equipment> equipmentList = Engine.getInstance().getMapper().findOtherEquipment(this.employee);
    	final CharSequence[] items = new  CharSequence[equipmentList.size()];
    	for(int i =0; i< equipmentList.size(); ++i)
    	{
    		Equipment equipment = equipmentList.get(i);
    		Employee owner = Engine.getInstance().getMapper().getEquipmentOwner(equipment);
    		String item = equipment.getModel()+ " " + equipment.getBrand() + "\nNr: "+ equipment.getSerialNumer() + "\nW³aœciciel: " + ((owner == null)? "brak" : owner.getName() +" " +owner.getSurname()); 
    		items[i] = item;
    	}
    	
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Dodaj sprzêt");
	
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, final int item) {
		    	addEquipment(equipmentList.get(item));
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
    }
    
    private void addEquipment(final Equipment equipment)
    {
    	Employee owner = Engine.getInstance().getMapper().getEquipmentOwner(equipment);
    	if(owner != null)
    	{
    		MessageBox.Show("Uwaga!", "Na pewno chcesz dodaæ sprzêt który nale¿y do "+ owner.getName() +" "+ owner.getSurname()+", od tej pory nie bêdzie ju¿ jego w³aœcicielem!", ButtonType.YesNo, this, new MessageBoxCallback(){
    			public void onFinished(MessageBoxResult result)
    			{
    				if(result == MessageBoxResult.Yes)
    				{
    			    	Engine.getInstance().getMapper().changeMaping(employee, equipment);
    			    	refreshEquipmentList();
    				}
    			}
    		});
    	}
    	else
    	{
	    	Engine.getInstance().getMapper().map(employee, equipment);
	    	refreshEquipmentList();
    	}
    }
    
    private void refreshEquipmentList()
    {
    	List<Equipment> equipmentList = Engine.getInstance().getMapper().findEmployeeEquipment(this.employee);
        this.fillEquipmentList(equipmentList);
    }
    

	private void setPageTitle() {
        TextView textView = (TextView)this.findViewById(R.id.employeeTitle);
        textView.setText(this.employee.toString());
		
	}

	private void fillEquipmentList(List<Equipment> equipmentList) {
		ListView listView = (ListView)this.findViewById(R.id.equipmentListView);
    	if(equipmentList.isEmpty())
    	{
    		List<String> listIsEmpty = new ArrayList<String>();
    		listIsEmpty.add("Nie znaleziono sprzêtu przypisanego do tego pracownika.");
	    	listView.setAdapter(new ArrayAdapter<String>(this, R.layout.places_list_item, listIsEmpty));
	        listView.setTextFilterEnabled(true);
	        disableRemoveButton();
    	}
    	else
    	{
	    	listView.setAdapter(new EquipmentArrayAdapter(this, equipmentList));
	        listView.setTextFilterEnabled(true);
	        enableRemoveButton();
    	}
		
	}

	private void enableRemoveButton() {
    	Button remove = (Button)this.findViewById(R.id.removeEquipmentButton);
    	remove.setEnabled(true);
	}

	private void disableRemoveButton() {
    	Button remove = (Button)this.findViewById(R.id.removeEquipmentButton);
    	remove.setEnabled(false);
	}
}
