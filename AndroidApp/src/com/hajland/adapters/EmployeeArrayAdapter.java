package com.hajland.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hajland.R;
import com.hajland.models.Employee;

public class EmployeeArrayAdapter extends ArrayAdapter<Employee> {

	private final Context context;
	private final List<Employee> values;

	public EmployeeArrayAdapter(Context context, List<Employee> values) {
		super(context, R.layout.list_mobile, values);
		this.context = context;
		this.values = values;
	}
 
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.list_mobile, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		textView.setText(values.get(position).toString());
		imageView.setImageResource(R.drawable.employee_icon);
		return rowView;
	}
}
