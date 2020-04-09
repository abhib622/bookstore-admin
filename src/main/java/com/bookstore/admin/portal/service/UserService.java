package com.bookstore.admin.portal.service;

import java.util.Set;

import com.bookstore.admin.portal.entity.User;
import com.bookstore.admin.portal.entity.UserRole;


public interface UserService {
	User createUser(User user, Set<UserRole> userRoles) throws Exception;
	
	User save(User user);
}
