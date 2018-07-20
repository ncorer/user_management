package sk.elct.java.user_management;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class App
{
    public static void main( String[] args )
    {
 
 /*   	User jano = new User();
    	jano.setName("Jano");
    	jano.setPassword("haha");
    	jano.setLastLogin(LocalDateTime.now());
    	jano.setEmail("jano@haha.sk");
    	UserDao userDao = new MemoryUserDao();
    	
    	User anka = new User();
    	anka.setName("Anka");
    	anka.setPassword("haha");
    	anka.setLastLogin(LocalDateTime.now());
    	anka.setEmail("anka@haha.sk");
    	
    	
    	userDao.add(anka);
    	userDao.add(jano);
 
    	System.out.println(userDao.getAll());
    	
    	UserDao userDao2 = new MysqlUserDao();
    	
 //   	userDao2.add(anka);
    	
    	System.out.println(userDao2.getAll());
    	
 //   	userDao2.delete(anka.getId());
    	
    	System.out.println(userDao2.getAll());
    	
    	anka.setName("Anicka");
    	userDao2.update(anka);
    	System.out.println(userDao2.getAll());
    	*/
    	
    	GroupDao groupDao = DaoFactory.INSTANCE.getGroupDao();
    	
    	Group informatici = new Group();
    	informatici.setName("informatici");
    	informatici.addPrivilege("stazovat sa");

 
    	
    	try {
			groupDao.add(informatici);
			groupDao.addPrivilege(informatici, "pytat si vyssi plat");
		   	List<Group> groups = groupDao.getAll();

			groupDao.delete(informatici.getId());
			
			for(Group dao: groups) {
	    		System.out.println(dao);
	    	}
			
		} catch (DuplicateGroupNameException e) {
			// ak sa nepodari vlozit nerobim nic
		}
 
    	UserDao userDao2 = new MysqlUserDao();
    	
    	List<Group> groups = groupDao.getAll();
    	Group prvaSkupina = groups.get(0);
    	User peto = new User();
    	peto.setName("Peto");
    	List<Group> petoveSkupiny = new ArrayList<>();
    	petoveSkupiny.add(prvaSkupina);
    	userDao2.add(peto);
    	for(User user: userDao2.getAll()) {
    		System.out.println(user);
    	}
    	
    	System.out.println("----------------");
    	List<Group> grupa = groupDao.getByUser(peto);
    	
    	for(Group nieco: grupa) {
    		System.out.println(nieco);
    	}
    	

    	

    	

    	
    	
    }
    
}
