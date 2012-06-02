package com.hajland;


import java.util.ArrayList;
import java.util.List;

import com.hajland.logic.Engine;
import com.hajland.models.Employee;
import com.hajland.models.Place;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class EmpolyeesPage extends Activity 
{
	private Place place;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employees);
        
        Bundle b = getIntent().getExtras();

        int placeID = b.getInt("placeId", 0);
        this.place = Engine.getInstance().getMapper().getPlace(placeID);
        setPageTitle();
        List<Employee> employees = Engine.getInstance().getMapper().findEmpoyees(this.place);
        this.fillEmployeesList(employees);
    }
    
    public void onRemoveEmployee(View target)
    {
    	final List<Employee> employees = Engine.getInstance().getMapper().findEmpoyees(this.place);
    	final CharSequence[] items = new  CharSequence[employees.size()];
    	for(int i =0; i< employees.size(); ++i)
    	{
    		Employee employee = employees.get(i);
    		String item = employee.getName()+ " " + employee.getSurname() + "\n"+ employee.getEmail(); 
    		items[i] = item;
    	}
    	
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Usuñ pracownika");
	
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	Engine.getInstance().getMapper().removeMapping(place, employees.get(item));
		    	refreshEmployeesList();
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
    }
    
    public void onAddEmployee(View target)
    {
    	final List<Employee> employees = Engine.getInstance().getMapper().findOtherEmployees(this.place);
    	final CharSequence[] items = new  CharSequence[employees.size()];
    	for(int i =0; i< employees.size(); ++i)
    	{
    		Employee employee = employees.get(i);
    		Place employeePlace = Engine.getInstance().getMapper().getEmployeePlace(employee);
    		String item = employee.getName()+ " " + employee.getSurname() + "\n"+ employee.getEmail() + "\nPokój: "+ ((employeePlace == null)?"brak":employeePlace.getFloor()+employeePlace.getRoomNumber()+ " ("+ employeePlace.getBuilding()+ " " + employeePlace.getCity() + ")"); 
    		items[i] = item;
    	}
    	
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Dodaj pracownika");
	
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	addEmployee(employees.get(item));
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
    }
    
    private void addEmployee(final Employee employee)
    {
    	Place employeePlace = Engine.getInstance().getMapper().getEmployeePlace(employee);
		if(employeePlace != null)
		{
			MessageBox.Show("Uwaga!", "Na pewno chcesz dodaæ pracownika który jest przypisany do pokoju "+ employeePlace.getFloor()+employeePlace.getRoomNumber()+ " ("+ employeePlace.getBuilding()+ " " + employeePlace.getCity() + ")"+", od tej pory zmieni miejsce pracy!", ButtonType.YesNo, this, new MessageBoxCallback(){
				public void onFinished(MessageBoxResult result)
				{
					if(result == MessageBoxResult.Yes)
					{
				    	Engine.getInstance().getMapper().map(place, employee);
				    	refreshEmployeesList();
					}
				}
			});
		}
		else
		{
	    	Engine.getInstance().getMapper().map(place, employee);
	    	refreshEmployeesList();
		}
    }
    
    private void refreshEmployeesList()
    {
    	List<Employee> employees = Engine.getInstance().getMapper().findEmpoyees(this.place);
        this.fillEmployeesList(employees);
    }
    
    private void fillEmployeesList(final List<Employee> employeesList)
    {
    	ListView listView = (ListView)this.findViewById(R.id.employeesListView);
    	if(employeesList.isEmpty())
    	{
    		List<String> listIsEmpty = new ArrayList<String>();
    		listIsEmpty.add("Nie znaleziono pracowników w tym pokoju.");
	    	listView.setAdapter(new ArrayAdapter<String>(this, R.layout.places_list_item, listIsEmpty));
	        listView.setTextFilterEnabled(true);
	        disableRemoveButton();
    	}
    	else
    	{
	    	listView.setAdapter(new ArrayAdapter<Employee>(this, R.layout.places_list_item, employeesList));
	        listView.setTextFilterEnabled(true);
	        listView.setOnItemClickListener(new OnItemClickListener() {
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	            {
	          	Employee employee = employeesList.get((int)id);
	            	Intent sec = new Intent(EmpolyeesPage.this, EquipmentPage.class);
	            	Bundle b = new Bundle();
	            	b.putInt("employeeId", employee.getId());
	                Log.i("EmplyeePage", "Id =" + employee.getId());
	            	sec.putExtras(b);
	            	startActivity(sec);
	            }
	          });
	        enableRemoveButton();
    	}
    }
    
    private void setPageTitle()
    {
        TextView textView = (TextView)this.findViewById(R.id.placeTitle);
        textView.setText(this.place.toString());
    }
    
    private void disableRemoveButton()
    {

    	Button remove = (Button)this.findViewById(R.id.removeEmployeeButton);
    	remove.setEnabled(false);
    }
    
    private void enableRemoveButton()
    {
    	Button remove = (Button)this.findViewById(R.id.removeEmployeeButton);
    	remove.setEnabled(true);
    }
}
