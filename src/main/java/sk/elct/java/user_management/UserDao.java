package sk.elct.java.user_management;

import java.util.List;

public interface UserDao {

	//CREATE
	void add(User user);

	//READ
	List<User> getAll();

	//UPDATE	
	void update(User user);

	//DELETE
	void delete(long id);

	void addToGroup(User user, Group group);
	
}