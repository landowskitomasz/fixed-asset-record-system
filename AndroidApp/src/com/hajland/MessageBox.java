package com.hajland;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class MessageBox {
	
	public static void ShowToast(String message, Context context)
	{
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, message, duration);
		toast.show();
	}
	
	public static void Show(String title, String message, ButtonType button, Context context, final MessageBoxCallback callback)
	{
		AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);                      
	    dlgAlert.setMessage(message);
	    dlgAlert.setTitle(title);  
		if( button == ButtonType.Ok)
		{
	        dlgAlert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	                 callback.onFinished(MessageBoxResult.Ok);
	            }
	       });
		}
		else if (button == ButtonType.OkCancel)
		{
	        dlgAlert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	                 callback.onFinished(MessageBoxResult.Ok);
	            }
	       });
	        dlgAlert.setNegativeButton("Anuluj",new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	                 callback.onFinished(MessageBoxResult.Cancel);
	            }
	       });
		}
		else if (button == ButtonType.YesNo)
		{
	        dlgAlert.setPositiveButton("Tak",new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	                 callback.onFinished(MessageBoxResult.Yes);
	            }
	       });
	        dlgAlert.setNegativeButton("Nie",new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	                 callback.onFinished(MessageBoxResult.No);
	            }
	       });
		}

        dlgAlert.create().show();
	}
}
