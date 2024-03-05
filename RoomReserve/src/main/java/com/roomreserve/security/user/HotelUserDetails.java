package com.roomreserve.security.user;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.roomreserve.entities.User;

public class HotelUserDetails implements UserDetails{
	
	private long id;
	
	private String email;
	
	private String password;
	
	private Collection<GrantedAuthority> authorities;
	
	public static HotelUserDetails buildUserDetails(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
										.map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	
		return new HotelUserDetails(user.getId() , user.getEmail() , user.getPassword() , authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public HotelUserDetails(long id, String email, String password, Collection<GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public HotelUserDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	
}
