package com.roomreserve.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.roomreserve.entities.Role;
import com.roomreserve.entities.User;
import com.roomreserve.exception.ResourceNotFoundException;
import com.roomreserve.exception.RoleAlreadyExistException;
import com.roomreserve.exception.UserAlreadyExistException;
import com.roomreserve.repository.RoleRepository;
import com.roomreserve.repository.UserRepository;
import com.roomreserve.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public List<Role> getRoles() {
		return roleRepository.findAll();
	}

	@Override
	public Role createRole(Role role) {
		String roleName = "ROLE_"+ role.getName().toUpperCase();
		Role theRole = new Role(roleName);
		if(roleRepository.existsByName(roleName)) {
			throw new RoleAlreadyExistException(role.getName() + "role already exists");
		}
		return roleRepository.save(theRole);
	}

	@Override
	public void deleteRole(long roleId) {
		Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
		this.removeAllUsersFromRole(roleId);
		roleRepository.delete(role);
	}

	@Override
	public Role findByName(String name) {
		Role role = roleRepository.findByName(name).get();
		return role;
	}

	@Override
	public User removeUserFromRole(long userId, long roleId) {
		Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		if(!role.getUsers().contains(user )) {
			throw new UsernameNotFoundException("User not found in role");
		}
		role.removeUserFromRole(user);
		roleRepository.save(role);
		return user;
	}

	@Override
	public User assignRoleToUser(long userId, long roleId) {
		Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		if(user.getRoles().contains(role)) {
			throw new UserAlreadyExistException(user.getFirstName() + " is already assigned to the" + role.getName() + "role");
		}
		role.assignRoleToUser(user);
		roleRepository.save(role);
		return user;
	}

	@Override
	public Role removeAllUsersFromRole(long roleId) {
		Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
		role.removeAllUsersFromRole();
		return roleRepository.save(role);
	}

}
