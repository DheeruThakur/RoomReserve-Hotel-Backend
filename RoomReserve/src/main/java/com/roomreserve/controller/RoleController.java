package com.roomreserve.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.roomreserve.entities.Role;
import com.roomreserve.entities.User;
import com.roomreserve.exception.RoleAlreadyExistException;
import com.roomreserve.service.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {
	
	@Autowired
	RoleService roleService;

	@GetMapping("/all-roles")
	public ResponseEntity<List<Role>> getAllRoles(){
		List<Role> roles = roleService.getRoles();
		return ResponseEntity.ok(roles);
	}
	
	@PostMapping("/create-new-role")
	public ResponseEntity<String> createRole(@RequestBody Role role){
		try {
		
			Role role2 = roleService.createRole(role);
			return ResponseEntity.ok("new role created successfully!");
			
		} catch (RoleAlreadyExistException ex) {
			return new ResponseEntity<String>(ex.getMessage() , HttpStatus.CONFLICT);
		}
	}
	
	@DeleteMapping("/delete/{roleId}")
	public void deleteRole(@PathVariable("roleId") long roleId){
		roleService.deleteRole(roleId);
	}
	
	@PostMapping("/remove-all-users-from-role/{roleId}")
	public Role removeAllUsersFromRole(@PathVariable("roleId") long roleId) {
		Role role = roleService.removeAllUsersFromRole(roleId);
		return role;
	}
	
	@PostMapping("/remove-user-from-role")
	public User removeUserFromRole(@RequestParam("userId") long userId , @RequestParam("roleId") long roleId) {
		return roleService.removeUserFromRole(userId, roleId);
	}
	
	@PostMapping("/assign-role-to-user")
	public ResponseEntity<?> assignRoleToUser(@RequestParam("userId") long userId , @RequestParam("roleId") long roleId) {
		try {
			
			User user = roleService.assignRoleToUser(userId, roleId);
			return ResponseEntity.ok("role assigned successfully!");
			
		} catch (Exception ex) {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
