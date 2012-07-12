package com.ewid.ewidserveradmin.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import com.ewid.ewidmanagers.client.ejb.local.UsersManagerLocal;
import com.ewid.ewidmanagers.client.exceptions.EwidAddException;
import com.ewid.ewidmanagers.client.exceptions.EwidDeleteException;
import com.ewid.ewidmanagers.client.struct.Group;
import com.ewid.ewidmanagers.client.struct.User;
import com.ewid.ewidserveradmin.InjectManagers;
import com.ewid.ewidserveradmin.Manager;
import com.ewid.ewidserveradmin.util.SHA1;

@AutoCreate
@Name("adminPanelBean")
@InjectManagers
@Scope(ScopeType.PAGE)
public class AdminPanelBean implements Serializable {

	private static final long serialVersionUID = 514765060903384776L;

	private Logger logger = Logger.getLogger(AdminPanelBean.class);
	
	@Manager
	private UsersManagerLocal userManager;

	private List<User> users;
	private List<Group> groups;
	private List<User> userToAddFake;
	private User userToAdd;
	private User userToDelete;
	private User userToEdit;
	private User userToShowDetails;
	private Group groupToEdit;
	private List<String> roles;
	private List<String> freeRoles;
	
	private boolean editComplete;
		
	@Create
	public void onCreate() {
		logger.info("AdminPanelBean.onCreate");
		retrieveUsers();
		retrieveGroups();
		retrieveRoles();
		
		userToAddFake = new ArrayList<User>();
		userToAddFake.add(new User());
		userToAdd = new User();
		if(groups != null && groups.size() > 0) {
			groupToEdit = groups.get(0);
			getRolesForGroup();
		}
		else {
			groupToEdit = new Group();
		}
		retrieveFreeRoles();
	}
	
	private void retrieveUsers() {
		logger.info("AdminPanelBean.retrieveUsers");
		users = userManager.getUsers();
	}
	
	private void retrieveGroups() {
		logger.info("AdminPanelBean.retrieveGroups");
		groups = userManager.getGroups();
	}
	
	private void retrieveRoles() {
		logger.info("AdminPanelBean.retrieveRoles");
		roles = userManager.getRoles();
	}
	
	private void retrieveFreeRoles() {
		logger.info("AdminPanelBean.retrieveFreeRoles");
		freeRoles = userManager.getFreeRolesForGroup(this.groupToEdit.getId());
	}
	
	public List<String> getAvailableRolesForGroup(Group group) {
		logger.info("AdminPanelBean.getAvailableRolesForGroup");
		List<String> availableRoles = new ArrayList<String>();
		for(String r : roles) {
			if(group.getRoles() != null || !group.getRoles().contains(r)) {
				availableRoles.add(r);
			}
		}
		return availableRoles;
	}
	
	private void getRolesForGroup() {
		logger.info("AdminPanelBean.getRolesForGroup");
		groupToEdit.setRoles(userManager.getRolesForGroup(groupToEdit.getId()));
	}
	
	public void refreshShuttleListData() {
		logger.info("AdminPanelBean.refreshShuttleListData");
		retrieveFreeRoles();
		getRolesForGroup();
	}
	
	public void rolesListValueChangedListener(ValueChangeEvent evt) {
		@SuppressWarnings("unchecked")
		List<String> newRoles = (List<String>) Object[].class.cast(evt.getNewValue())[1];
		@SuppressWarnings("unchecked")
		List<String> newFree = (List<String>) Object[].class.cast(evt.getNewValue())[0];
		@SuppressWarnings("unchecked")
		List<String> oldRoles = (List<String>) Object[].class.cast(evt.getOldValue())[1];
		@SuppressWarnings("unchecked")
		List<String> oldFree = (List<String>) Object[].class.cast(evt.getOldValue())[0];
		try {
			if (oldRoles.size() < newRoles.size()) {
				for (int i = oldRoles.size(); i < newRoles.size(); i++) {
					this.userManager.addRoleToGroup(this.groupToEdit, newRoles.get(i));
				}
			} else {
				for (int i = oldFree.size(); i < newFree.size(); i++) {
					this.userManager.removeRoleFromGroup(this.groupToEdit, newFree.get(i));
				}
			}
			
		} catch (EwidDeleteException e) {
			this.freeRoles = oldFree;
			this.groupToEdit.setRoles(oldRoles);
			//this.globalMessages.addMessage("Podczas usuwania roli z grupy wystąpił błąd");
		} catch (EwidAddException e) {
			this.freeRoles = oldFree;
			this.groupToEdit.setRoles(oldRoles);
			//this.globalMessages.addMessage("Podczas dodawania roli do grupy wystąpił błąd");
		}/* catch (ObjectGetException e) {
			this.globalMessages.addMessage("Podczas wykonywania operacji wystąpił błąd");
			this.group = this.oldGroup.clone();
		} catch (ObjectNotFoundException e) {
			this.globalMessages.addMessage("Podczas wykonywania operacji  wystąpił błąd");
			this.group = this.oldGroup.clone();
		}*/

	}
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<User> getUserToAddFake() {
		return userToAddFake;
	}

	public void setUserToAddFake(List<User> userToAddFake) {
		this.userToAddFake = userToAddFake;
	}
	
	public void addUser(User user) {
		logger.info("AdminPanelBean.addUser");
		try {
			String hashedPassword = SHA1.doSHA1(user.getPassword());
			user.setPassword(hashedPassword);
			userManager.addUser(user);
			retrieveUsers();
			userToAdd = new User();
		} catch (Exception e) {
			logger.error("AdminPanelBean.addUser error: ",e);
		}
	}
	
	public void deleteUser(User user) {
		logger.info("AdminPanelBean.deleteUser");
		userManager.deleteUser(user.getId());
		retrieveUsers();
		userToDelete = new User();
	}
	
	public void editUser(User user) {
		logger.info("AdminPanelBean.editUser");
		try {
			String userPassword = user.getPassword();
			if(userPassword.length() != 40) {
				String hashedPassword = SHA1.doSHA1(userPassword);
				user.setPassword(hashedPassword);
			}
			userManager.editUser(user);
			retrieveUsers();
			userToEdit = new User();
			editComplete =  true;
		} catch (Exception e) {
			logger.error("AdminPanelBean.addUser error: ",e);
			editComplete = false;
		}
	}

	public User getUserToAdd() {
		return userToAdd;
	}

	public void setUserToAdd(User userToAdd) {
		this.userToAdd = userToAdd;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public User getUserToDelete() {
		return userToDelete;
	}

	public void setUserToDelete(User userToDelete) {
		this.userToDelete = userToDelete;
	}

	public User getUserToEdit() {
		return userToEdit;
	}

	public void setUserToEdit(User userToEdit) {
		this.userToEdit = userToEdit;
	}

	public User getUserToShowDetails() {
		return userToShowDetails;
	}

	public void setUserToShowDetails(User userToShowDetails) {
		this.userToShowDetails = userToShowDetails;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public Group getGroupToEdit() {
		return groupToEdit;
	}

	public void setGroupToEdit(Group groupToEdit) {
		this.groupToEdit = groupToEdit;
	}

	public List<String> getFreeRoles() {
		return freeRoles;
	}

	public void setFreeRoles(List<String> freeRoles) {
		this.freeRoles = freeRoles;
	}

	public boolean isEditComplete() {
		return editComplete;
	}

	public void setEditComplete(boolean editComplete) {
		this.editComplete = editComplete;
	}
}
