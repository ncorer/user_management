package sk.elct.java.user_management;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class MysqlGroupDao implements GroupDao {

	
	private JdbcTemplate jdbcTemplate;
	
	public MysqlGroupDao(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void add(Group group) throws DuplicateGroupNameException{
		
		try {
			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
			simpleJdbcInsert.withTableName("`group`");
			simpleJdbcInsert.usingGeneratedKeyColumns("id");
			simpleJdbcInsert.usingColumns("name");
			
			Map<String, Object> hodnoty = new HashMap<>();
			hodnoty.put("name", group.getName());

			//tu pride exception
			long noveId = simpleJdbcInsert.executeAndReturnKey(hodnoty).longValue();
			group.setId(noveId);
			
			//toto sa vykona iba ak je to noav skupina
			
			for(String privilege : group.getPrivileges()) {
				addPrivilege(group, privilege);
				}
			
		} catch (DuplicateKeyException e) {
			//v databaze uz je rovnomenna skupina
			DuplicateGroupNameException mojaVynimka = new DuplicateGroupNameException("Uz existuje rovnomenna skupina \"" + group.getName() + "\"", e);
			throw mojaVynimka;
		}

		
	}



	@Override
	public void addPrivilege(Group group, String privilege) {
		String sql = "Insert into `privileges` (id_group, privilege) Values (?,?)";
		jdbcTemplate.update(sql,group.getId(),privilege);
		
	}

	@Override
	public List<Group> getAll() {
		String sql = "SELECT `group`.id, name, privilege from `group` JOIN privileges on (id=id_group) order by `group`.id";
		return jdbcTemplate.query(sql, new GroupResultSetExtractor());

	}

	@Override
	public List<Group> getByUser(User user) {
		String sql = "SELECT `group`.id, name, privilege from `group` JOIN privileges on (id=id_group) JOIN user_group on `group`.id=user_group.id_group where user_group.id_user =" + user.getId() + " order by `group`.id";

		 return jdbcTemplate.query(sql, new GroupResultSetExtractor());
	}

	@Override
	public void delete(long id) {
		jdbcTemplate.update("Delete FROM `privileges` WHERE ID_group = " + id);
		jdbcTemplate.update("Delete FROM `user_group` WHERE ID_group = " + id);
		jdbcTemplate.update("Delete FROM `group` WHERE ID = " + id);
		
	}



}
