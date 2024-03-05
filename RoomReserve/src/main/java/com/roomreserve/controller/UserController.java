package com.roomreserve.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roomreserve.entities.User;
import com.roomreserve.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	
	@GetMapping("/all-users")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<User>> getUsers(){
		List<User> users = userService.getUsers();
		return new ResponseEntity<List<User>>(users , HttpStatus.OK);
	}
	
	@GetMapping("/{email}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email){
        try{
            User theUser = userService.getUser(email);
            return ResponseEntity.ok(theUser);
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching user");
        }
    }
	
	@DeleteMapping("/delete/{email}") 
	@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #email == principal.username)")
	public ResponseEntity<String> deleteUser(@PathVariable("email") String email){
		try {
			userService.deleteUser(email);
			return new ResponseEntity<String>("User deleted successfully" , HttpStatus.OK);
		} catch (UsernameNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage() , HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			return new ResponseEntity<String>("Error deleting user" , HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
