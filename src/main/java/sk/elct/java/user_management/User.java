package sk.elct.java.user_management;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {

	private Long id;	
	private String name;
	private String password;
	private String email;
	private LocalDateTime lastLogin;
	private List<Group> groups = new ArrayList<>();
	
	public List<Group> getGroups() {
		return groups;
	}
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDateTime getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", lastLogin=" + lastLogin + "]";
	}
		
	
	
}
