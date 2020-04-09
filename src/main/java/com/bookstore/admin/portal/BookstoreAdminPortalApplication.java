package com.bookstore.admin.portal;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bookstore.admin.portal.Enum.RoleName;
import com.bookstore.admin.portal.entity.Role;
import com.bookstore.admin.portal.entity.User;
import com.bookstore.admin.portal.entity.UserRole;
import com.bookstore.admin.portal.repository.RoleRepository;
import com.bookstore.admin.portal.service.UserService;
import com.bookstore.admin.portal.utility.SecurityUtility;

@SpringBootApplication
public class BookstoreAdminPortalApplication implements CommandLineRunner {
	
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(BookstoreAdminPortalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user1 = new User();
		user1.setUsername("admin");
		user1.setPassword(SecurityUtility.passwordEncoder().encode("admin"));
		user1.setEmail("admin@gmail.com");		
		Set<UserRole> userRoles = new HashSet<>();
		Role role = roleRepository.findByname(RoleName.ROLE_ADMIN);		
		
		if(role != null) {
			userRoles.add(new UserRole(user1, role));
		} else {
			Role role1 = new Role();
			role1.setName(RoleName.ROLE_ADMIN);
			userRoles.add(new UserRole(user1, role1));
		}
		userService.createUser(user1, userRoles);
	}

}
