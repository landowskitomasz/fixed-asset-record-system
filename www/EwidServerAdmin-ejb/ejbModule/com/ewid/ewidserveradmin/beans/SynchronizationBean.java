package com.ewid.ewidserveradmin.beans;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import com.ewid.ewidserveradmin.InjectManagers;

@AutoCreate
@Name("synchronizationBean")
@InjectManagers
@Scope(ScopeType.APPLICATION)
public class SynchronizationBean implements Serializable {

	private static final long serialVersionUID = -2516398688684245896L;

//	private Logger logger = Logger.getLogger(SynchronizationBean.class);
//	
//	@Manager
//	private MobeelizerManagerLocal mobeelizerManager;
//	
//	public void doSync() {
//		logger.info("SynchronizationBean.doSync");
//		
//		Mobeelizer mobeelizer = getMobeelizer();
//		
//		MobeelizerSyncCallback pushToCloudCallback = new MobeelizerSyncCallback(){
//			public void onSyncFinishedWithError(Exception arg0) {
//				//logger.error("pushToCloudCallback.onSyncFinishedWithError exception", arg0);
//				System.out.println("pushToCloudCallback.onSyncFinishedWithError exception" + arg0);
//			}
//			public void onSyncFinishedWithError(MobeelizerErrors arg0) {
//				//logger.error("pushToCloudCallback.onSyncFinishedWithError MobeelizerErrors: " + arg0.toString());
//				System.out.println("pushToCloudCallback.onSyncFinishedWithError MobeelizerErrors: " + arg0.toString());
//			}
//			public void onSyncFinishedWithSuccess(Iterable<Object> arg0,
//					Iterable<MobeelizerFile> arg1, Iterable<String> arg2,
//					MobeelizerConfirmSyncCallback arg3) {
//				//logger.info("pushToCloudCallback.onSyncFinishedWithSuccess");
//				System.out.println("pushToCloudCallback.onSyncFinishedWithSuccess");
//				arg3.confirm();
//			}
//		};
//		
//		List<Object> objectToSyncList = new ArrayList<Object>(); 
//		objectToSyncList.addAll(mobeelizerManager.getEmployeesToSync());
//		objectToSyncList.addAll(mobeelizerManager.getEquipmentToSync());
//		objectToSyncList.addAll(mobeelizerManager.getPlacesToSync());
//		objectToSyncList.addAll(mobeelizerManager.getUsersToSync());
//		
//		//wysłanie encji do chmury
//		mobeelizer.sync(objectToSyncList, new ArrayList<MobeelizerFile>(), pushToCloudCallback);
///*		
//		MobeelizerSyncCallback pullFromCloudCallback = new MobeelizerSyncCallback(){
//			public void onSyncFinishedWithError(Exception arg0) {
//				logger.error("pullFromCloudCallback.onSyncFinishedWithError exception", arg0);
//			}
//			public void onSyncFinishedWithError(MobeelizerErrors arg0) {
//				logger.error("pullFromCloudCallback.onSyncFinishedWithError MobeelizerErrors: " + arg0.toString());
//			}
//			public void onSyncFinishedWithSuccess(Iterable<Object> entities,
//					Iterable<MobeelizerFile> files, Iterable<String> deletedFiles,
//					MobeelizerConfirmSyncCallback callback) {
//				logger.info("pullFromCloudCallback.onSyncFinishedWithSuccess");
//				List<Mapping> mappingsToAdd = new ArrayList<Mapping>();
//				
//				if(entities != null) {
//					for(Object entity : entities) {
//						if(entity instanceof Mapping) {
//							mappingsToAdd.add((Mapping)entity);
//						}
//					}
//				}
//				mobeelizerManager.addMappingsToDb(mappingsToAdd);
//				callback.confirm();
//			}
//		};
//		//pobranie mapowan z chmury
//		mobeelizer.syncAll(pullFromCloudCallback);
//*/	}
//	
//	private Mobeelizer getMobeelizer() {
//		File f = new File("c:\\EwidencjaTes.xml"); 
//		InputStream in = null;
//		try {
//			in = new FileInputStream(f);
//		} catch (FileNotFoundException e) {
//			logger.error("SynchronizationBean.getMobeelizer exception", e);
//			return null;
//		}
//		
//		MobeelizerConfiguration configuration = new MobeelizerConfiguration();
//		configuration.setDefinition(in);
//		configuration.setDevice("HttpClient");
//		configuration.setDeviceIdentifier("deviceIdentifier");
//		configuration.setPackageName("com.ewid.ewidmanagers.client.struct.mobeelizer.model");
//		configuration.setMode(MobeelizerMode.TEST);
//		configuration.setInstance("test");
//		configuration.setUser("httpClient");
//		configuration.setPassword("qwerty");
//		  
//		return new Mobeelizer(configuration);
//	}
}
