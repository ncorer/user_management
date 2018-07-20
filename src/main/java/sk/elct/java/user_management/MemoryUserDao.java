package sk.elct.java.user_management;

import java.util.ArrayList;
import java.util.List;

public class MemoryUserDao implements UserDao {

	private List<User> users = new ArrayList<User>();
	
	//CREATE
	public void add(User user) {
		if (user != null) {
			users.add(user);
		}
	}
	
	//READ
	public List<User> getAll(){
		return users;
	}
	
	//UPDATE	
	public void update(User user) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getId() == user.getId()) {
				users.set(i, user);
				break;
			}
		}
	}
	
	//DELETE
	public void delete(long id) {
		for(User u : users) {
			if (u.getId() == id) {
				users.remove(u);
				break;
			}
		}
		
	}

	@Override
	public void addToGroup(User user, Group group) {
		// TODO Auto-generated method stub
		
	}
	
}
