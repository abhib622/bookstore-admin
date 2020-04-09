package com.bookstore.admin.portal.repository;

import org.springframework.data.repository.CrudRepository;

import com.bookstore.admin.portal.entity.User;



public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
	
	User findByEmail(String email);
}
