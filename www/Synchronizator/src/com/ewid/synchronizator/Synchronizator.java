package com.ewid.synchronizator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.ewid.ewidmanagers.client.ejb.remote.MobeelizerManagerRemote;
import com.ewid.ewidmanagers.client.struct.mobeelizer.model.Mapping;
import com.mobeelizer.java.Mobeelizer;
import com.mobeelizer.java.MobeelizerConfiguration;
import com.mobeelizer.java.MobeelizerConfirmSyncCallback;
import com.mobeelizer.java.MobeelizerSyncCallback;
import com.mobeelizer.java.api.MobeelizerErrors;
import com.mobeelizer.java.api.MobeelizerFile;
import com.mobeelizer.java.api.MobeelizerMode;


public class Synchronizator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Synchronizator sync = new Synchronizator();
		sync.doSync();
	}
	public void doSync() {
		System.out.println("SynchronizationBean.doSync");
		
		Mobeelizer mobeelizer = getMobeelizer();
		
		MobeelizerSyncCallback pushToCloudCallback = new MobeelizerSyncCallback(){
			public void onSyncFinishedWithError(Exception arg0) {
				System.out.println("pushToCloudCallback.onSyncFinishedWithError exception" + arg0);
			}
			public void onSyncFinishedWithError(MobeelizerErrors arg0) {
				System.out.println("pushToCloudCallback.onSyncFinishedWithError MobeelizerErrors: " + arg0.toString());
			}
			public void onSyncFinishedWithSuccess(Iterable<Object> arg0,
					Iterable<MobeelizerFile> arg1, Iterable<String> arg2,
					MobeelizerConfirmSyncCallback arg3) {
				System.out.println("pushToCloudCallback.onSyncFinishedWithSuccess");
				arg3.confirm();
			}
		};
		
		
		MobeelizerManagerRemote beanRemote = Synchronizator.getBean();
		
		List<Object> objectToSyncList = new ArrayList<Object>();
		
		objectToSyncList.addAll(beanRemote.getEmployeesToSync());
		objectToSyncList.addAll(beanRemote.getEquipmentToSync());
		objectToSyncList.addAll(beanRemote.getPlacesToSync());
		objectToSyncList.addAll(beanRemote.getUsersToSync());
		

		//wysłanie encji do chmury
		mobeelizer.sync(objectToSyncList, new ArrayList<MobeelizerFile>(), pushToCloudCallback);
		System.out.println("WYWOŁANIE SYNCHRONIZACJI RÓŻNICOWEJ Z MOBEELIZEREM PRZEBIEGŁO POMYŚLNIE");
		
		MobeelizerSyncCallback pullFromCloudCallback = new MobeelizerSyncCallback(){
			public void onSyncFinishedWithError(Exception arg0) {
				System.out.println("pullFromCloudCallback.onSyncFinishedWithError exception: " + arg0);
			}
			public void onSyncFinishedWithError(MobeelizerErrors arg0) {
				System.out.println("pullFromCloudCallback.onSyncFinishedWithError MobeelizerErrors: " + arg0.toString());
			}
			public void onSyncFinishedWithSuccess(Iterable<Object> entities,
					Iterable<MobeelizerFile> files, Iterable<String> deletedFiles,
					MobeelizerConfirmSyncCallback callback) {
				System.out.println("pullFromCloudCallback.onSyncFinishedWithSuccess");
				List<Mapping> mappingsToAdd = new ArrayList<Mapping>();
				
				if(entities != null) {
					for(Object entity : entities) {
						if(entity instanceof Mapping) {
							mappingsToAdd.add((Mapping)entity);
						}
					}
				}
				System.out.println("MAPOWANIA_Z_CHMURY: " + mappingsToAdd);
				
				MobeelizerManagerRemote beanRemote = Synchronizator.getBean();
				beanRemote.addMappingsToDb(mappingsToAdd);
				
				callback.confirm();
			}
		};
		//pobranie mapowan z chmury
		mobeelizer.syncAll(pullFromCloudCallback);
		System.out.println("WYWOŁANIE SYNCHRONIZACJI Z MOBEELIZEREM PRZEBIEGŁO POMYŚLNIE");
	}
	
	private Mobeelizer getMobeelizer() {
		File f = new File("EwidencjaTes.xml"); 
		InputStream in = null;
		try {
			in = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			System.out.println("SynchronizationBean.getMobeelizer exception:" + e);
			return null;
		}
		
		MobeelizerConfiguration configuration = new MobeelizerConfiguration();
		configuration.setDefinition(in);
		configuration.setDevice("HttpClient");
		configuration.setDeviceIdentifier("deviceIdentifier");
		configuration.setPackageName("com.ewid.ewidmanagers.client.struct.mobeelizer.model");
		configuration.setMode(MobeelizerMode.TEST);
		configuration.setInstance("test");
		configuration.setUser("httpClient");
		configuration.setPassword("qwerty");
		  
		return new Mobeelizer(configuration);
	}
	
	private static MobeelizerManagerRemote getBean() {
		Properties properties = new Properties();
		properties.put("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
		properties.put("java.naming.factory.url.pkgs","=org.jboss.naming:org.jnp.interfaces");
		properties.put("java.naming.provider.url","localhost:1099");
		
		try {
			Context context = new InitialContext(properties);
		    MobeelizerManagerRemote beanRemote = (MobeelizerManagerRemote)context.lookup("EwidServerAdmin-ear/MobeelizerManager/remote"); 
		    return beanRemote;
		} catch (NamingException e) {
		    System.out.println("WYSTAPIŁ BŁAD DODAWANIA MAPOWAŃ DO BAZY");
		    e.printStackTrace();
		    throw new RuntimeException(e);
		}
	}
}
