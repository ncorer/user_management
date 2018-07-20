package sk.elct.java.user_management;

import java.util.List;

public interface GroupDao {
	
	//CREATE
	void add(Group group) throws DuplicateGroupNameException;
	void addPrivilege(Group group, String privilege);

	//READ
	List<Group> getAll();
	List<Group> getByUser(User user);
	void delete(long id);


}
