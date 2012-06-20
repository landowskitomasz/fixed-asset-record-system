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
import com.hajland.models.Equipment;

public class EquipmentArrayAdapter extends ArrayAdapter<Equipment> {

	private final Context context;
	private final List<Equipment> values;

	public EquipmentArrayAdapter(Context context, List<Equipment> values) {
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
		imageView.setImageResource(R.drawable.equipment_icon);
		return rowView;
	}
}
