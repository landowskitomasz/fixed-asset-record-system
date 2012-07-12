package com.ewid.ewidmanagers.client.ejb.common;

import java.util.List;

import com.ewid.ewidmanagers.client.exceptions.EwidAddException;
import com.ewid.ewidmanagers.client.exceptions.EwidDeleteException;
import com.ewid.ewidmanagers.client.exceptions.EwidEditException;
import com.ewid.ewidmanagers.client.exceptions.EwidGetListException;
import com.ewid.ewidmanagers.client.exceptions.EwidGetObjectException;
import com.ewid.ewidmanagers.client.struct.Group;
import com.ewid.ewidmanagers.client.struct.User;

public interface UsersManagerCommon {
	public List<User> getUsers() throws EwidGetListException;
	public void addUser(User user) throws EwidAddException;
	public void deleteUser(int userId) throws EwidDeleteException;
	public void editUser(User user) throws EwidEditException;
	public User getUserById(int userId) throws EwidGetObjectException;
	public User getUserByLogin(String login) throws EwidGetObjectException;
	public List<Group> getGroups() throws EwidGetListException;
	public Group getGroupById(int groupId) throws EwidGetObjectException;
	public List<String> getRoles() throws EwidGetListException;
	public List<String> getRolesForGroup(int groupId) throws EwidGetListException;
	public void addRoleToGroup(Group group, String role) throws EwidAddException;
	public void removeRoleFromGroup(Group group, String role) throws EwidDeleteException;
	public boolean checkIfLoginEmpty(String login);
	public List<String> getFreeRolesForGroup(int groupId) throws EwidGetListException;
}
