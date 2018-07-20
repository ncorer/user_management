package sk.elct.java.user_management;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.mysql.cj.jdbc.MysqlDataSource;

public class MysqlUserDao implements UserDao {

	private JdbcTemplate jdbctemplate;
	
public MysqlUserDao() {
	MysqlDataSource dataSource = new MysqlDataSource();

	dataSource.setUrl("jdbc:mysql://localhost/user_management?"  + "serverTimezone=Europe/Bratislava&nullNamePatternMatchesAll=true\"");
	dataSource.setDatabaseName("user_management");
	dataSource.setUser("um");
	dataSource.setPassword("elct");
	this.jdbctemplate = new JdbcTemplate(dataSource);
}
	public MysqlUserDao(MysqlDataSource dataSource) {
		this.jdbctemplate = new JdbcTemplate(dataSource);
	}
	
	public void add(User user) {
//		String sql = ("INSERT INTO USER (name, password,email,lastLogin) VALUES (?,?,?,?)"); 
//		jdbctemplate.update(sql, user.getName(),user.getPassword(),user.getEmail(),user.getLastLogin());

		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbctemplate);
		simpleJdbcInsert.withTableName("user");
		simpleJdbcInsert.usingGeneratedKeyColumns("id");
		simpleJdbcInsert.usingColumns("name", "password", "email", "lastLogin");
		
		Map<String, Object> hodnoty = new HashMap<>();
		hodnoty.put("name", user.getName());
		hodnoty.put("password", user.getPassword());
		hodnoty.put("email", user.getEmail());
		hodnoty.put("lastLogin", user.getLastLogin());
		
		long noveId = simpleJdbcInsert.executeAndReturnKey(hodnoty).longValue();
		user.setId(noveId);
		for(Group group: user.getGroups()) {
			addToGroup(user,group);
		}
	}

	public List<User> getAll() {
		String sql = "SELECT * FROM `USER`";
		UserRowMapper rowMapper = new UserRowMapper();
		List<User> zoznam = jdbctemplate.query(sql, rowMapper);
		GroupDao groupDao = DaoFactory.INSTANCE.getGroupDao();
		for(User user: zoznam) {
			user.setGroups(groupDao.getByUser(user));
		}
		return zoznam;
	}

	public void update(User user) {
		String sql = ("UPDATE USER set name = ? ,password = ? ,email = ? ,lastLogin = ? where ID = ?" ); 
		jdbctemplate.update(sql, user.getName(), user.getPassword(),user.getEmail(), user.getLastLogin(), user.getId());	
		jdbctemplate.update("Delete from user_group where id_user = ?", user.getId());
		for(Group group: user.getGroups()) {
			addToGroup(user,group);
			}
	}

	public void delete(long id) {
		jdbctemplate.update("DELETE FROM user_group WHERE ID_user = " + id);
		jdbctemplate.update("DELETE FROM USER WHERE ID = " + id);

	}
	
	public void addToGroup(User user, Group group) {
		String sql = "Insert into `User_group` (id_user, id_group) Values (?,?)";
		jdbctemplate.update(sql,user.getId(),group.getId());
		
	}

}
