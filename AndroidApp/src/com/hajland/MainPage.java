package com.hajland;

import com.hajland.logic.ClearingCallback;
import com.hajland.logic.ClearingStatus;
import com.hajland.logic.Engine;
import com.hajland.logic.LoginCallback;
import com.hajland.logic.Settings;
import com.hajland.logic.SynchronizationStatus;
import com.hajland.logic.SynchronizeCallback;
import com.hajland.models.User;
import com.mobeelizer.mobile.android.api.MobeelizerLoginStatus;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainPage extends Activity {

    private ProgressDialog dialog;
    
    public static final String PREFS_NAME = "applicationSettingsFile";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Settings.getInstance().init(getSharedPreferences(PREFS_NAME, 0));
        this.disableButtons();
        this.dialog = ProgressDialog.show(MainPage.this, "Inicjacja", "Proszê czekaj, trwa inicjowanie aplikacji..");
        Engine.getInstance().login(new LoginCallback(){
        	public void onLoginFinished(final MobeelizerLoginStatus status)
        	{
        		dialog.cancel();
        		if(status == MobeelizerLoginStatus.OK)
        		{
	        		MessageBox.ShowToast("Login finished!", getApplicationContext());
	        		User user = Engine.getInstance().getUserIdentyfication().getCurrentUser();
	        		TextView loginStatus = (TextView)MainPage.this.findViewById(R.id.loginStatus);
	        		if(user != null)
	        		{
		        		enableButtons();
		        		loginStatus.setText("Jesteœ zidentyfikowany jako: \n\t"+user.getName() + " " + user.getSurname());

		            	Button loginButton = (Button)MainPage.this.findViewById(R.id.loginButton);
		            	loginButton.setText(R.string.changeLogin);
	        		}
	        		else
	        		{
		        		loginStatus.setText(R.string.loginStatusDefault);
	        			enableSyncAllButton();
		            	Button loginButton = (Button)MainPage.this.findViewById(R.id.loginButton);
		            	loginButton.setText(R.string.confirmLogin);
	        		}
        		}
        		else
        		{
        			MessageBox.ShowToast("Login failed!", getApplicationContext());
        		}
        	}
        });
    }
    
    @Override
    public void onStop(){
        super.onStop();
    	//Engine.getInstance().logout();
    }
    
    public void onLogin(View target) 
    {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);                 
		alert.setTitle("Identyfikacja");  
		alert.setMessage("Login:");                
		final EditText input = new EditText(this); 
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {  
		    public void onClick(DialogInterface dialog, int whichButton) {  
		        String value = input.getText().toString();
		    	if(Engine.getInstance().getUserIdentyfication().tryIdentifyUser(value))
		    	{
	        		User user = Engine.getInstance().getUserIdentyfication().getCurrentUser();
	        		TextView loginStatus = (TextView)MainPage.this.findViewById(R.id.loginStatus);
	        		loginStatus.setText("Jesteœ zidentyfikowany jako: \n\t"+user.getName() + " " + user.getSurname());
					MessageBox.Show("Sukces", "U¿ytkownik zidentyfikowany prawid³owo.", ButtonType.Ok, MainPage.this, null);
		    		enableButtons();
	            	Button loginButton = (Button)MainPage.this.findViewById(R.id.loginButton);
	            	loginButton.setText(R.string.changeLogin);
		    	}
		    	else
		    	{
		    		Engine.getInstance().getUserIdentyfication().forgetUser();
					MessageBox.Show("B³¹d", "Nie rozpoznano u¿ytkownika, spróbuj ponownie.", ButtonType.Ok, MainPage.this, null);
	            	Button loginButton = (Button)MainPage.this.findViewById(R.id.loginButton);
	            	loginButton.setText(R.string.confirmLogin);
	            	
	        		TextView loginStatus = (TextView)MainPage.this.findViewById(R.id.loginStatus);
	            	loginStatus.setText(R.string.loginStatusDefault);
        			MainPage.this.disableButtons();
	            	MainPage.this.enableSyncAllButton();
		    	}
		        return;                  
		       }  
		});  
		alert.show();
    		
    }
    
    public void onBeginWork(View target) 
    {
    	Intent sec = new Intent(MainPage.this, PlacesPage.class);
    	startActivity(sec);
    }
    
    public void onSynchronize(View target) 
    {
    	dialog = ProgressDialog.show(this, "Synchronizacja", "Proszê czekaj, trwa zatwierdzanie Twojej pracy...");
    	Engine.getInstance().synchronize(new SynchronizeCallback(){
    		public void onFinished(SynchronizationStatus status)
    		{
    			dialog.cancel();
    			if(status == SynchronizationStatus.SUCCESS)
    			{
    				MessageBox.Show("Sukces", "Synchronizacja danych roboczych z serwerem zakoñczy³a siê powodzeniem.", ButtonType.Ok, MainPage.this, null);
    			}
    			else if(status == SynchronizationStatus.CONFLICTS)
    			{
    				MessageBox.Show("B³¹d", "Wyst¹pi³y konflikty, czy chcesz je rozwi¹zaæ teraz?.", ButtonType.YesNo, MainPage.this, new MessageBoxCallback(){

						public void onFinished(MessageBoxResult result) {
							if(result == MessageBoxResult.Yes)
							{
								// TODO: Navigate to resolve conflict page 
							}
						}
    					
    				});
    			}
    			else if(status == SynchronizationStatus.FATALERROR)
    			{
    				MessageBox.Show("B³¹d", "Synchronizacja danych roboczych z serwerem nie powiod³a siê, spróbuje ponownie póŸniej.", ButtonType.Ok, MainPage.this, null);
    			}
    		}
    	});
    }
    
    public void onClear(View target) 
    {
		MessageBox.Show("Uwaga", "Jesteœ pewny ¿e chcesz wyczyœciæ œrodowisko pracy? To usunie wszystkie dotychczasowe zmiany.", ButtonType.YesNo, this, new MessageBoxCallback(){
			public void onFinished(MessageBoxResult result)
			{
				if(result == MessageBoxResult.Yes)
				{
					dialog = ProgressDialog.show(MainPage.this, "Czyszczenie", "Pobieranie aktualnego œrodowiska pracy...");
					Engine.getInstance().clearWorkingEnvironment(new ClearingCallback(){
						public void onClearingFinished(ClearingStatus status) {
							dialog.cancel();
							if(status == ClearingStatus.Finished)
							{
								MessageBox.Show("Sukces", "Czyszczenie œrodowiska pracy zakoñczy³o siê sukcesem.", ButtonType.Ok, MainPage.this, null);
							}
							else if (status == ClearingStatus.Failed)
							{
								MessageBox.Show("B³¹d!", "Czyszczenie œrodowiska pracy nie powiod³o siê, spróbuj ponownie póŸniej.", ButtonType.Ok, MainPage.this, null);
							}
						}
					});
				}
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

	private void enableSyncAllButton() {
    	Button clear = (Button)this.findViewById(R.id.clearButton);
    	clear.setEnabled(true);
	}
}