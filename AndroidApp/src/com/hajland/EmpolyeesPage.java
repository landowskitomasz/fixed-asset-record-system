package com.hajland;


import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import com.hajland.logic.Engine;
import com.hajland.models.Employee;
import com.hajland.models.Place;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    		String item = employee.getName()+ " " + employee.getSurname() + "\n"+ employee.getEmail(); 
    		items[i] = item;
    	}
    	
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Dodaj pracownika");
	
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	Engine.getInstance().getMapper().map(place, employees.get(item));
		    	refreshEmployeesList();
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
    }
    
    private void refreshEmployeesList()
    {
    	List<Employee> employees = Engine.getInstance().getMapper().findEmpoyees(this.place);
        this.fillEmployeesList(employees);
    }
    
    private void fillEmployeesList(List<Employee> employeesList)
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
