package sk.elct.java.user_management;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.cj.jdbc.MysqlDataSource;

public enum DaoFactory {
	INSTANCE;         //premenna, ktora drzi jedinu instanciu triedy userdaofactory
	
	
	private UserDao userDao;
	
	private MysqlDataSource getDataSource() {
		MysqlDataSource dataSource = new MysqlDataSource();

		dataSource.setUrl("jdbc:mysql://localhost/user_management?"  + "serverTimezone=Europe/Bratislava&nullNamePatternMatchesAll=true\"");
		dataSource.setDatabaseName("user_management");
		dataSource.setUser("um");
		dataSource.setPassword("elct");
		return dataSource;
	}
	
	public UserDao getUserDao() {
		if(userDao == null) {
			userDao = new MysqlUserDao(getDataSource());
		}
	return new MysqlUserDao();	
	}
	
	public GroupDao getGroupDao() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		return new MysqlGroupDao(jdbcTemplate);
	}
	
}
