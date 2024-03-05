package com.roomreserve.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	
	@ManyToMany(mappedBy = "roles")
	@JsonIgnore
	private Collection<User> users = new HashSet<>();
	
	
	
	public void assignRoleToUser(User user) {
		user.getRoles().add(this);
		this.getUsers().add(user);
	}
	
	public void removeUserFromRole(User user) {
		user.getRoles().remove(this);
		this.getUsers().remove(user);
	}
	
	public void removeAllUsersFromRole() {
		if(this.getUsers() != null) {
			List<User> listOfUsers = this.getUsers().stream().toList();
			listOfUsers.forEach(user -> this.removeUserFromRole(user));
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name != null ? name : "";
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	public Role(long id, String name, Collection<User> users) {
		super();
		this.id = id;
		this.name = name;
		this.users = users;
	}
	
	public Role(String name) {
		super();
		this.name = name;
	}

	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
