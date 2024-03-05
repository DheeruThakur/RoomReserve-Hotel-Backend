package com.roomreserve.entities;

import java.util.Collection;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER , cascade = {CascadeType.PERSIST , CascadeType.MERGE , CascadeType.DETACH})
	@JoinTable(name = "user_roles" , 
			joinColumns = @JoinColumn(name = "user_id" , referencedColumnName = "id") ,
			inverseJoinColumns = @JoinColumn(name = "role_id" , referencedColumnName = "id"))
	private Collection<Role>roles = new HashSet<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public User(long id, String firstName, String lastName, String email, String password , Collection<Role> roles) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
