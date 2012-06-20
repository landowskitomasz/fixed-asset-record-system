package com.hajland;

import com.hajland.logic.ClearingCallback;
import com.hajland.logic.ClearingStatus;
import com.hajland.logic.Engine;
import com.hajland.logic.LoginCallback;
import com.hajland.logic.Settings;
import com.hajland.logic.SynchronizationStatus;
import com.hajland.logic.SynchronizeCallback;
import com.hajland.models.User;
import com.mobeelizer.mobile.android.Mobeelizer;
import com.mobeelizer.mobile.android.api.MobeelizerLoginStatus;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainPage extends Activity {

    private ProgressDialog dialog;
    
    public static final String PREFS_NAME = "applicationSettingsFile";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.i("MainPage", "oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Settings.getInstance().init(getSharedPreferences(PREFS_NAME, 0));
        loginMobeelizer();
       
    }
    
    @Override
    public void onStart()
    {
    	super.onStart();
    	if(Mobeelizer.isLoggedIn())
    	{
    		this.checkConflictStatus();
    	}
    }
    
    private void loginMobeelizer()
    {
    	 this.disableButtons();
         this.dialog = ProgressDialog.show(MainPage.this, "Inicjacja", "Proszê czekaj, trwa inicjowanie aplikacji..");
         Engine.getInstance().login(new LoginCallback(){
         	public void onLoginFinished(final MobeelizerLoginStatus status)
         	{
         		dialog.cancel();
         		if(status == MobeelizerLoginStatus.OK)
         		{
 	        		MessageBox.ShowToast("Login finished!", getApplicationContext());	
 	        		
 	        		/*User user = new User();
 	        		user.setId(4);
 	        		user.setLogin("user");
 	        		user.setName("Tomasz");
 	        		user.setSurname("Landowski");
 	        		try
 	        		{
 	        			user.setPassword(SHA1.doSHA1("password"));
 	        		}
 	        		catch(NoSuchAlgorithmException e)
 	        		{
 	        			
 	        		}
 	        		catch(UnsupportedEncodingException e)
 	        		{
 	        			
 	        		}
 	        		Mobeelizer.getDatabase().save(user);*/
 	        		
 	        		checkConflictStatus();
 	        		checkIdentyficationStatus();
         		}
         		else
         		{
         			MainPage.this.disableResolveButton();
         			MessageBox.Show("B³¹d", "Inicjalizacja aplikacji siê nie powiod³a, prawdodpodobnie nie masz dostêpu do internetu", ButtonType.Ok, MainPage.this, null);
         		}
         	}
         });
    }
    
    private void checkConflictStatus()
    {
    	Log.i("MainPage", "check conflict status");
		if(Settings.getInstance().isConflicted())
    	{
	    	Log.i("MainPage", "there are conflict");
	    	MainPage.this.disableButtons();
    		MainPage.this.enableResolveButton();
    	}
    	else
    	{
	    	Log.i("MainPage", "conflict status false");
			this.enableButtons();
    		MainPage.this.disableResolveButton();
    	}
    }
    
    private void checkIdentyficationStatus()
    {
    	User user = null;
    	try 
    	{
    		user = Engine.getInstance().getUserIdentyfication().getCurrentUser();
		} 
    	catch (IllegalStateException e) 
    	{
			MessageBox.Show("B³¹d", "Wystapi³y problemu z list¹ u¿ytkowników, pobierz œrodowisko pracy od nowa, jeœli problem siê powtózy skontaktuj siê z administratorem.", ButtonType.Ok, this, null);
		}
    	
    
		TextView loginStatus = (TextView)MainPage.this.findViewById(R.id.loginStatus);
		if(user != null)
		{
    		enableButtons();
    		loginStatus.setText("Jesteœ zalogowany jako: \n\t"+user.getName() + " " + user.getSurname());

        	Button loginButton = (Button)MainPage.this.findViewById(R.id.loginButton);
        	loginButton.setText(R.string.changeLogin);
		}
		else
		{
    		loginStatus.setText(R.string.loginStatusDefault);
       	    this.disableButtons();
			enableSyncAllButton();
        	Button loginButton = (Button)MainPage.this.findViewById(R.id.loginButton);
        	loginButton.setText(R.string.confirmLogin);
		}
    }
    
    private void disableResolveButton() {
    	Button button = (Button)this.findViewById(R.id.resolveButton);
    	button.setEnabled(false);
	}

	private void enableResolveButton() {
    	Button button = (Button)this.findViewById(R.id.resolveButton);
    	button.setEnabled(true);
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
		alert.setMessage("Podaj login i has³o:");        
		final LinearLayout list = new LinearLayout(this);
		list.setOrientation(1);
		final EditText input = new EditText(this); 
		final EditText password = new EditText(this);
		password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		list.addView(input);
		list.addView(password);
		alert.setView(list);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {  
		    public void onClick(DialogInterface dialog, int whichButton) {  
		        String userLogin = input.getText().toString();
		        String userPassword = password.getText().toString();
		    	if(Engine.getInstance().getUserIdentyfication().tryIdentifyUser(userLogin, userPassword))
		    	{
	        		User user = Engine.getInstance().getUserIdentyfication().getCurrentUser();
	        		TextView loginStatus = (TextView)MainPage.this.findViewById(R.id.loginStatus);
	        		loginStatus.setText("Jesteœ zalogowany jako: \n\t"+user.getName() + " " + user.getSurname());
					MessageBox.Show("Sukces", "U¿ytkownik zalogowany prawid³owo.", ButtonType.Ok, MainPage.this, null);
		    		enableButtons();
	            	Button loginButton = (Button)MainPage.this.findViewById(R.id.loginButton);
	            	loginButton.setText(R.string.changeLogin);
		    	}
		    	else
		    	{
		    		Engine.getInstance().getUserIdentyfication().forgetUser();
					MessageBox.Show("B³¹d", "Logowanie nie powiod³o siê, spróbuj ponownie.", ButtonType.Ok, MainPage.this, null);
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
    
    public void onResolve(View target) 
    {
    	Intent sec = new Intent(MainPage.this, ConflictsPage.class);
    	startActivity(sec);
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
    	Button loginButton = (Button)this.findViewById(R.id.loginButton);
    	begin.setEnabled(false);
    	clear.setEnabled(false);
    	synchronize.setEnabled(false);
    	loginButton.setEnabled(false);
    }
    
    private void enableButtons()
    {
    	Button begin = (Button)this.findViewById(R.id.beginButton);
    	Button clear = (Button)this.findViewById(R.id.clearButton);
    	Button synchronize = (Button)this.findViewById(R.id.synchronizeButton);
    	Button loginButton = (Button)this.findViewById(R.id.loginButton);
    	begin.setEnabled(true);
    	clear.setEnabled(true);
    	synchronize.setEnabled(true);
    	loginButton.setEnabled(true);
    }

	private void enableSyncAllButton() {
    	Button clear = (Button)this.findViewById(R.id.clearButton);
    	clear.setEnabled(true);
	}
}
